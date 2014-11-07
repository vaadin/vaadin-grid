package com.vaadin.prototype.webcomponentwrapper.template;

import java.io.Serializable;

import com.vaadin.prototype.webcomponentwrapper.element.Element;

public interface TemplateDefinition extends Serializable {

	String getId();
	String getInnerHTML();
	TemplateInstance<? extends Element> instantiate();
}
