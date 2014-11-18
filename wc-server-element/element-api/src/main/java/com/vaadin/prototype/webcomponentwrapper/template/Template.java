package com.vaadin.prototype.webcomponentwrapper.template;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Template {

    /**
     * Name of the file with template content. The file will be loaded from the
     * classpath
     * 
     * @return file name of the template
     */
    String value();
}
