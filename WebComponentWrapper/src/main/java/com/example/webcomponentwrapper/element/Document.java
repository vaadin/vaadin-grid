package com.example.webcomponentwrapper.element;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.vaadin.server.EncodeResult;
import com.vaadin.server.JsonCodec;
import com.vaadin.ui.Component;
import com.vaadin.ui.JavaScriptFunction;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonValue;

public class Document extends ElementImpl {
    private Component owner;

    public Document(Component owner) {
        super("div");
        this.owner = owner;

        Integer ownId = Integer.valueOf(0);
        nodeToId.put(this, ownId);
        idToNode.put(ownId, this);
    }

    private int callbackIdSequence = 0;
    private int nodeIdSequence = 1;

    private final Map<Node, Integer> nodeToId = new HashMap<>();
    private final Map<Integer, Node> idToNode = new HashMap<>();

    private final Set<String> imports = new HashSet<>();

    private JsonArray pendingCommands = Json.createArray();

    private void addCommand(String name, Node target, JsonValue... params) {
        assert target == null || target.getDocument() == this;

        JsonArray c = Json.createArray();
        c.set(0, name);

        if (target != null) {
            c.set(1, nodeToId.get(target).doubleValue());
        }

        Arrays.asList(params).forEach(p -> c.set(c.length(), p));

        pendingCommands.set(pendingCommands.length(), c);

        owner.markAsDirty();
    }

    void setAttributeChange(ElementImpl element, String name) {
        String value = element.getAttribute(name);
        if (value == null) {
            addCommand("removeAttribute", element, Json.create(name));
        } else {
            addCommand("setAttribute", element, Json.create(name),
                    Json.create(value));
        }
    }

    void setChildAdded(Node parent, Node child) {
        assert parent.getDocument() == this;

        Integer oldId = nodeToId.remove(child);
        if (oldId != null) {
            idToNode.remove(oldId);
        }

        Integer id = Integer.valueOf(nodeIdSequence++);

        nodeToId.put(child, id);
        idToNode.put(id, child);

        for (Class<?> iface : child.getClass().getInterfaces()) {
            Import annotation = iface.getAnnotation(Import.class);
            if (annotation != null) {
                addImport(annotation.value());
                break;
            }
        }

        // Attach child
        child.setParent(parent);

        // Enqueue initialization operations
        if (child instanceof ElementImpl) {
            ElementImpl e = (ElementImpl) child;

            addCommand("createElement", child, Json.create(e.getTag()));

            e.getAttributeNames().forEach(name -> setAttributeChange(e, name));
            e.flushEvals();
        } else if (child instanceof TextNode) {
            TextNode t = (TextNode) child;

            addCommand("createText", child, Json.create(t.getText()));
        } else {
            throw new RuntimeException("Unsupported node type: "
                    + child.getClass());
        }

        // Attach sub children
        child.getChildren().forEach(c -> setChildAdded(child, c));

        // Finally add append command
        addCommand("appendChild", parent, Json.create(id.doubleValue()));
    }

    JsonValue flushPendingCommands() {
        JsonArray payload = pendingCommands;
        pendingCommands = Json.createArray();
        return payload;
    }

    @Override
    public Document getDocument() {
        return this;
    }

    void remove(Node child) {
        assert child.getDocument() == this;

        addCommand("remove", child);

        // XXX remove child nodes?

        child.setParent(null);
    }

    public void addImport(String url) {
        if (imports.add(url)) {
            addCommand("import", null, Json.create(url));
        }
    }

    void eval(ElementImpl element, String script, Object[] arguments) {
        // Param values
        JsonArray params = Json.createArray();

        // Array of param indices that should be treated as callbacks
        JsonArray callbacks = Json.createArray();

        for (int i = 0; i < arguments.length; i++) {
            Object value = arguments[0];
            Class<? extends Object> type = value.getClass();

            if (JavaScriptFunction.class.isAssignableFrom(type)) {
                // TODO keep sequence per element instead of "global"
                int cid = callbackIdSequence++;
                element.setCallback(cid, (JavaScriptFunction) value);

                value = Integer.valueOf(cid);
                type = Integer.class;

                callbacks.set(callbacks.length(), i);
            }

            EncodeResult encodeResult = JsonCodec.encode(value, null, type,
                    owner.getUI().getConnectorTracker());
            params.set(i, encodeResult.getEncodedValue());
        }

        addCommand("eval", element, Json.create(script), params, callbacks);
    }

    public void handleCallback(JsonArray arguments) {
        int elementId = (int) arguments.getNumber(0);
        int cid = (int) arguments.getNumber(1);
        JsonArray params = arguments.getArray(2);

        ElementImpl element = (ElementImpl) idToNode.get(Integer
                .valueOf(elementId));
        if (element == null) {
            System.out.println(cid + " detached?");
            return;
        }

        JavaScriptFunction callback = element.getCallback(cid);
        callback.call(params);
    }

    @Override
    public String asHtml() {
        StringBuilder b = new StringBuilder();

        for (String importUrl : imports) {
            b.append("<link rel=\"import\" href=\"" + importUrl + "\" />");
        }

        b.append(super.asHtml());

        return b.toString();
    }

}
