package com.vaadin.prototype.wc.server;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Slider;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Widgetset("WebComponents")
@Theme("valo")
public class WCUI extends UI{

    @WebServlet(urlPatterns = {"/vaadin/*", "/VAADIN/*"})
    @VaadinServletConfiguration(productionMode = false, ui = WCUI.class)
    public static class WCServlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
//        Button button = new Button("Vaadin-Button");
//        setContent(button);
//        Slider slider = new Slider("AAAA");
//        slider.setValue(40d);
//        slider.setMax(100d);
//        setContent(slider);
    }
}
