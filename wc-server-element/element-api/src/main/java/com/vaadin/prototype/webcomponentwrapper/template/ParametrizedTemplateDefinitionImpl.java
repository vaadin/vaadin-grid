package com.vaadin.prototype.webcomponentwrapper.template;

import com.vaadin.prototype.webcomponentwrapper.element.Element;

class ParametrizedTemplateDefinitionImpl<E extends Element> implements
        TemplateDefinition<E> {

    /**
	 * 
	 */
    private static final long serialVersionUID = -157253537370157108L;
    private String id;
    private String innerHTML;
    private Class<E> clazz;

    ParametrizedTemplateDefinitionImpl(String tag, String html, Class<E> clazz) {
        this.id = tag;
        this.innerHTML = html;
        this.clazz = clazz;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getInnerHTML() {
        return innerHTML;
    }

    @Override
    public TemplateInstance<E> instantiate() {
        return new ParametrizedTemplateInstance<E>(clazz);
    }

}
