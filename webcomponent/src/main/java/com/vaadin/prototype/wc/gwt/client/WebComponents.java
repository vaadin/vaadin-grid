package com.vaadin.prototype.wc.gwt.client;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.console;
import static com.google.gwt.query.client.GQuery.document;
import static com.google.gwt.user.client.Window.alert;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.js.Js;
import com.google.gwt.query.client.Function;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.prototype.wc.gwt.client.Native.Document;
import com.vaadin.prototype.wc.gwt.client.Native.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.components.PaperSlider;

/**
 * Example code for a GwtQuery application
 */
public class WebComponents implements EntryPoint {
    
    public void onModuleLoad() {
        $("<div id='hi'>Module Loaded</div>").appendTo(document);
        
        final PaperSlider slider = Loader.create(PaperSlider.class);
        
        slider.max(80).value(20);
        
        slider.addEventListener("change", new EventListener() {
            public void onBrowserEvent(Event event) {
                console.log("changed");
            }
        });
        
//        slider.decrement();
        
        $(slider).on("change", new Function(){
            public boolean f(Event e) {
                console.log("GQ Change " + slider.value());
                return true;
            }
        });
        
        $(slider).appendTo(document);
        
        
        console.log(slider);
    }

    private void createWebComponentUsingJs() {
        Document document = Js.js("$wnd.document");
        HTMLElement element = document.createElement("");
        element.addEventListener("click", new EventListener() {
            public void onBrowserEvent(Event event) {
                alert("Hi");
            }
        });
    }
}
