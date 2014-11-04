package com.vaadin.prototype.webcomponentwrapper.element;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EventParam {

    String value();

}
