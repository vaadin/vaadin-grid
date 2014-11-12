package com.vaadin.prototype.webcomponentwrapper.template;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Template {
    String value();
}
