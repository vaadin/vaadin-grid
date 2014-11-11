package com.vaadin.prototype.webcomponentwrapper.template;

import java.io.Serializable;

import com.vaadin.prototype.webcomponentwrapper.element.Element;

interface TemplateDefinition<E extends Element> extends Serializable {

	String getId();
	String getInnerHTML();
	TemplateInstance<E> instantiate();
}
