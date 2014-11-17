package com.vaadin.prototype.webcomponentwrapper.template;

import com.vaadin.prototype.webcomponentwrapper.element.Element;

/**
 * This interface, even though public, and its use should be considered
 * implementation detail. It's supposed to only be used internally by the
 * framework
 * 
 * @author mprzepiora
 *
 * @param <E>
 */
public interface TemplateDefinition<E extends Element> {
    String getTag();

    String getHtml();

    Class<E> getElementClass();

    TemplateInstance<E> instantiate();
}
