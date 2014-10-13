package com.vaadin.prototype.wc.server;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.prototype.wc.gwt.client.components.Icon;
import com.vaadin.prototype.wc.server.ui.BaseComponent.EventListener;
import com.vaadin.prototype.wc.server.ui.PaperButtonComponent;
import com.vaadin.prototype.wc.server.ui.PaperSliderComponent;
import com.vaadin.prototype.wc.server.ui.PaperToggleButtonComponent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Widgetset("WebComponents")
@Theme("valo")
public class WCUI extends UI{

    @WebServlet(urlPatterns = {"/vaadin/*", "/VAADIN/themes/*"})
    @VaadinServletConfiguration(productionMode = false, ui = WCUI.class)
    public static class WCServlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        setContent(layout);
        
        Button vbutton = new Button("Button");
        vbutton.addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                Notification.show("Clicked on a vaadin button");
            }
        });
        layout.addComponent(vbutton);
        
        PaperButtonComponent button = new PaperButtonComponent();
        button.icon(Icon.IMAGE_AUTO_FIX);
        button.addClickHandler(new EventListener() {
            public void onBrowserEvent(String eventName) {
                Notification.show("Clicked");
            }
        });
        layout.addComponent(button);
        

        final Label toggleLabel = new Label();
        layout.addComponent(toggleLabel);
        final PaperToggleButtonComponent toggle = new PaperToggleButtonComponent();
        toggle.checked(true);
        toggle.addChangeHandler(new EventListener() {
            public void onBrowserEvent(String eventName) {
                toggleLabel.setCaption("" + toggle.checked());
            }
        });
        layout.addComponent(toggle);

        final Label sliderLabel = new Label();
        layout.addComponent(sliderLabel);
        final PaperSliderComponent slider = new PaperSliderComponent();
        slider.pin(true);
        slider.max(1);
        slider.min(0);
        slider.value(0.9);
        slider.step(0.01);
        slider.snaps(true);
        slider.addChangeHandler(new EventListener() {
            public void onBrowserEvent(String eventName) {
                sliderLabel.setCaption("" + slider.value());
            }
        });
        layout.addComponent(slider);
    }
}
