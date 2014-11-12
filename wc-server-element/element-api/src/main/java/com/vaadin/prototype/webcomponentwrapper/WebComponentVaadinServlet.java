package com.vaadin.prototype.webcomponentwrapper;

import javax.servlet.ServletException;

import com.vaadin.prototype.webcomponentwrapper.element.WebComponentWrapper;
import com.vaadin.server.VaadinServlet;

public class WebComponentVaadinServlet extends VaadinServlet {

    // All this just to include a script in the head in the generated HTML
    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();

        WebComponentWrapper.initBootstrap(getService());
    }
}