package com.vaadin.prototype.webcomponentwrapper.element;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.vaadin.server.JsonCodec;
import com.vaadin.shared.communication.ServerRpc;
import com.vaadin.ui.JavaScriptFunction;

import elemental.json.JsonArray;

public class ElementImpl extends NodeImpl implements Element {
    /**
     * 
     */
    private static final long serialVersionUID = -2310139720636980919L;

    private final String tag;
    
    protected List<Node> shadowDOMNodes = Collections.unmodifiableList(new ArrayList<>(0));

    public ElementImpl(String tag) {
        this.tag = tag;
    }

    @Override
    public String getTag() {
        return tag;
    }

    private Map<String, String> attributes = new LinkedHashMap<>();

    // TODO create a wrapper class instead of using two lists
    private List<String> evalQueue = new ArrayList<>();
    private List<Object[]> evalParamQueue = new ArrayList<>();

    private Map<Integer, JavaScriptFunction> callbacks = new LinkedHashMap<>();

    @Override
    public void setAttribute(String name, String value) {
        if (value == null) {
            attributes.remove(name);
        } else {
            attributes.put(name, value);
        }

        Document document = getDocument();
        if (document != null) {
            document.setAttributeChange(this, name);
        }
    }

    @Override
    public Collection<String> getAttributeNames() {
        return Collections.unmodifiableCollection(attributes.keySet());
    }

    @Override
    public String getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public void setInnerText(String text) {
        for (Node n : this.getChildren()) {
            if (n instanceof TextNode) {
                ((TextNode) n).setText(text);
                return;
            }
        }
        appendChild(new TextNode(text));
    }

    @Override
    public void setAttribute(String name, boolean value) {
        if (value) {
            setAttribute(name, "");
        } else {
            removeAttribute(name);
        }
    }

    @Override
    public boolean hasAttribute(String name) {
        return attributes.containsKey(name);
    }

    @Override
    public void removeAttribute(String name) {
        setAttribute(name, null);
    }

    @Override
    public void eval(String script, Object... arguments) {
        Document document = getDocument();
        if (document != null) {
            document.eval(this, script, arguments);
        } else {
            evalQueue.add(script);
            evalParamQueue.add(arguments);
        }
    }

    void flushEvals() {
        if (!evalQueue.isEmpty()) {
            Document document = getDocument();
            assert document != null;

            if (document != null) {
                for (int i = 0; i < evalQueue.size(); i++) {
                    eval(evalQueue.get(i), evalParamQueue.get(i));
                }
                evalQueue.clear();
                evalParamQueue.clear();
            }
        }
    }

    void setCallback(int cid, JavaScriptFunction callback) {
        callbacks.put(Integer.valueOf(cid), callback);
    }

    JavaScriptFunction getCallback(int cid) {
        return callbacks.get(Integer.valueOf(cid));
    }

    @Override
    public void addEventListener(String eventName, JavaScriptFunction listener,
            String... arguments) {
        String argumentBuilder = String.join(",", arguments);
        eval("e.addEventListener('" + eventName
                + "', function (event) { param[0](" + argumentBuilder + ") })",
                listener);
    }

    @Override
    public void addEventListener(EventListener listener) {
        List<Method> listenerMethods = findInterfaceMethods(listener.getClass());

        for (Method method : listenerMethods) {
            if (method.getDeclaringClass() == Object.class) {
                // Ignore
                continue;
            }

            String name = method.getName();
            if (!name.startsWith("on")) {
                throw new RuntimeException(method.toString());
            }

            name = name.substring(2).toLowerCase();

            if (method.getParameterCount() != 1) {
                throw new RuntimeException();
            }

            if (method.getReturnType() != void.class) {
                throw new RuntimeException();
            }

            Map<String, Integer> methodOrder = new LinkedHashMap<>();
            Class<?> eventType = method.getParameterTypes()[0];

            Method[] eventGetters = eventType.getDeclaredMethods();
            String[] argumentBuilders = new String[eventGetters.length];

            for (int i = 0; i < eventGetters.length; i++) {
                Method getter = eventGetters[i];
                if (getter.getParameterCount() != 0) {
                    throw new RuntimeException(getter.toString());
                }

                String paramName = Elements.getPropertyName(getter.getName());

                methodOrder.put(getter.getName(), Integer.valueOf(i));
                argumentBuilders[i] = "event." + paramName;
            }

            addEventListener(name, new JavaScriptFunction() {
                /**
                 * 
                 */
                private static final long serialVersionUID = -7978455071455245154L;

                @Override
                public void call(final JsonArray arguments) {
                    InvocationHandler invocationHandler = (proxy, calledMethod,
                            args) -> {
                        if (calledMethod.getDeclaringClass() == Object.class) {
                            // Standard object methods
                            return calledMethod.invoke(proxy, args);
                        } else {
                            String methodName = calledMethod.getName();
                            int indexOf = methodOrder.get(methodName)
                                    .intValue();
                            return JsonCodec.decodeInternalOrCustomType(
                                    calledMethod.getGenericReturnType(),
                                    arguments.get(indexOf), null);
                        }
                    };

                    Object event = Proxy.newProxyInstance(
                            eventType.getClassLoader(),
                            new Class[] { eventType }, invocationHandler);

                    try {
                        method.invoke(listener, event);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }, argumentBuilders);
        }
    }

    private List<Method> findInterfaceMethods(Class<?> type) {
        return Arrays.asList(type.getInterfaces()).stream()
                .flatMap(iface -> Arrays.asList(iface.getMethods()).stream())
                .collect(Collectors.toList());
    }

    @Override
    public void addEventListener(ServerRpc rpc) {
        List<Method> interfaceMethods = findInterfaceMethods(rpc.getClass());

        for (Method method : interfaceMethods) {
            String eventName = method.getName().toLowerCase();

            String[] arguments = new String[method.getParameterCount()];

            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                EventParam eventParam = parameters[i]
                        .getAnnotation(EventParam.class);
                arguments[i] = "event." + eventParam.value();
            }

            addEventListener(eventName, new JavaScriptFunction() {
                /**
                 * 
                 */
                private static final long serialVersionUID = -8672184304611678361L;

                @Override
                public void call(JsonArray arguments) {
                    Object[] args = new Object[parameters.length];
                    for (int i = 0; i < args.length; i++) {
                        // TODO handle null for primitive return types
                        args[i] = JsonCodec.decodeInternalOrCustomType(
                                parameters[i].getParameterizedType(),
                                arguments.get(i), null);
                    }

                    try {
                        method.invoke(rpc, args);
                    } catch (IllegalAccessException | IllegalArgumentException
                            | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, arguments);
        }
    }

    @Override
    public void setInnerHtml(String html) {
        Elements.parse(html).forEach(this::appendChild);
    }

    @Override
    public String asHtml() {
        StringBuilder b = new StringBuilder();

        // Open tag
        b.append('<');
        b.append(getTag());

        // Attributes in opening tag
        // TODO html encode
        getAttributeNames().forEach(name -> {
            b.append(' ');
            b.append(name);

            String value = getAttribute(name);
            if (!value.isEmpty()) {
                b.append("=\"");
                b.append(value);
                b.append('"');
            }
        });
        b.append('>');

        // Child nodes
        getChildren().forEach(c -> b.append(c.asHtml()));

        // Close tag
        b.append("</");
        b.append(getTag());
        b.append('>');

        return b.toString();
    }

    @Override
    public List<Node> getShadowDOMNodes() {
        return shadowDOMNodes;
    }
    
    /**
     * This is internal for the framework and should never be used outside support for Templates
     * 
     * @param shadowDOMNodes
     */
    public void setShadowDOMNodes(List<Node> shadowDOMNodes) {
        this.shadowDOMNodes = shadowDOMNodes;
    }
    
    

}
