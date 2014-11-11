package com.vaadin.prototype.webcomponentwrapper.template;

import com.vaadin.prototype.webcomponentwrapper.element.Element;
import com.vaadin.prototype.webcomponentwrapper.element.Elements;

public class ParametrizedTemplateInstance<E extends Element> implements TemplateInstance<E> {
	
	E myElement;
	
	
	public ParametrizedTemplateInstance(Class<E> clazz) {
		myElement = Elements.create(clazz);
	}

	@Override
	public E getElement() {
		return myElement;
	}

}
