package com.vaadin.prototype.wc.gwt.client;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.console;

import java.util.Arrays;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.vaadin.prototype.wc.gwt.client.components.PaperToast;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.widgets.WCButton;
import com.vaadin.prototype.wc.gwt.client.widgets.WCDateBox;
import com.vaadin.prototype.wc.gwt.client.widgets.WCDatepicker;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVSlider;

/**
 * Example code for a GwtQuery application
 */
public class DemoExportWC implements EntryPoint {

    Panel gwtPanel;
    public void onModuleLoad() {
        // Use sexy gQuery for finding any widget or element in the dom
        // so as we can have decoupled views.
        gwtPanel = $("div").widgets(FlowPanel.class).get(0);
        if (gwtPanel == null) {
            console.log("Seems that DemoWrap was not run.");
            return;
        }

        final PaperToast toast = (PaperToast)$("paper-toast").get(0);

        // Register web components so as we can use those tags from html
        WC.register(WCButton.TAG, WCButton.class);
        WC.register(WCVSlider.TAG, WCVSlider.class);
        WC.register(WCDateBox.TAG, WCDateBox.class);
        WC.register(WCDatepicker.TAG, WCDatepicker.class);

        gwtPanel.add(new Label("HTML button exported as <x-button> web component:"));
        // We can create elements adding the tag with gQuery or Js
        final GQuery g = $("<x-button message='WebComponents Rock'/>")
                .appendTo($(gwtPanel));
        // The new element created has the HTMLElement and WCButton prototypes.
        // So we can cast from Element to HTMLElement to WCButton
        final WCButton btn = (WCButton)(HTMLElement) g.get(0);
        console.log(btn instanceof WCButton);
        btn.addEventListener("click", new EventListener() {
            public void onBrowserEvent(Event event) {
                toast.text(btn.getAttribute("message")).show();
            }
        });
        
        gwtPanel.add(new Label("Vaadin widget exported as <v-slider> web component, note that each one has a different theme and is in a different shadow root."));
        for (String theme : Arrays.asList("valo", "reindeer", "runo", "chameleon")) {
            final WCVSlider sld = (WCVSlider)(HTMLElement)
                    $("<v-slider style='display:inline-block; margin-right:15px' min=0 max=100 value=30 theme='" + theme + "' />").appendTo($(gwtPanel)).get(0);
                sld.addEventListener("change", new EventListener() {
                    @Override
                    public void onBrowserEvent(Event event) {
                        toast.text("Changed slider " + sld.getAttribute("value")).show();
                    }
                });
        }
        
        gwtPanel.add(new Label("GWT widget exported as <x-date-box> web component: "));
        final WCDateBox dbox = (WCDateBox)(HTMLElement)
            $("<x-date-box theme='dark'/>").appendTo($(gwtPanel)).get(0);
        dbox.addEventListener("change", new EventListener() {
            public void onBrowserEvent(Event event) {
                toast.text("Changed date " + dbox.getAttribute("value")).show();
            }
        });
        
        gwtPanel.add(new Label("GWT widget exported as <x-date-picker> web component demonstrating that we can mix gwt themes using shadow root"));
        for (String theme : Arrays.asList("chrome", "standard", "dark", "clean")) {
            final WCDatepicker dpicker = (WCDatepicker)(HTMLElement)
                    $("<x-date-picker style='display:inline-block' theme='"+ theme + "''/>").appendTo($(gwtPanel)).get(0);
                dpicker.addEventListener("change", new EventListener() {
                    public void onBrowserEvent(Event event) {
                        toast.text("Changed date " + dpicker.getAttribute("value")).show();
                    }
                });        
        }
    }
}
