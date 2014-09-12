package com.vaadin.prototype.wc.server;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Widgetset("WebComponents")
public class WCUI extends UI{
    
    @WebServlet(urlPatterns = {"/vaadin/*", "/VAADIN/*"})
    @VaadinServletConfiguration(productionMode = false, ui = WCUI.class)
    public static class WCServlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
    }
}
