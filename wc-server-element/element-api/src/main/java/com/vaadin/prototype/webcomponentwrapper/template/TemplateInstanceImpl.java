package com.vaadin.prototype.webcomponentwrapper.template;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.prototype.webcomponentwrapper.element.Element;
import com.vaadin.prototype.webcomponentwrapper.element.Elements;
import com.vaadin.prototype.webcomponentwrapper.element.Node;
import com.vaadin.prototype.webcomponentwrapper.element.ElementProcessor;

public class TemplateInstanceImpl<E extends Element> implements
        TemplateInstance<E> {

    private E element;
    private TemplateDefinition<E> td;
    private List<Node> shadowDOM;
    private Map<String, Element> idsCache;

    public TemplateInstanceImpl(TemplateDefinition<E> td) {
        this.td = td;
        E element = null;
        if (td.getElementClass().equals(Element.class)) {
            element = Elements.create(td.getTag());
        } else {
            element = Elements.create(td.getElementClass());
        }

        this.element = element;
        this.shadowDOM = parseFromDefinition();
    }

    private List<Node> parseFromDefinition() {
        List<Node> nodeList = Elements.parse(td.getHtml());
        return Collections.unmodifiableList(nodeList);
    }

    @Override
    public E getElement() {
        return element;
    }

    @Override
    public <EL extends Element> EL getElementById(String id) {
        if (idsCache == null) {
            buildIdsCache();
        }

        @SuppressWarnings("unchecked")
        EL result = (EL) idsCache.get(id);
        return result;
    }

    private void buildIdsCache() {
        idsCache = new LinkedHashMap<>();
        walkInnerNodes(shadowDOM, this::addElementIdToCache);
    }

    private void walkInnerNodes(List<Node> nodes, ElementProcessor processor) {
        for (Node n : nodes) {
            if (n instanceof Element) {
                Element e = (Element) n;
                processor.processNode(e);
                walkInnerNodes(e.getChildren(), processor);
            }
        }
    }

    private void addElementIdToCache(Element element) {
        String id = element.getAttribute("id");
        if (id != null && id.length() > 0) {
            idsCache.put(id, element);
        }
    }

}
