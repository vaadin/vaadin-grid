package com.vaadin.prototype.wc.gwt.client.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;

public abstract class BaseWidget extends Widget  {
    
    private HashMap<String, List<EventListener>> handlers;
    
    protected abstract String[] events();
    protected abstract <T extends HTMLElement> T element();
    
    public BaseWidget(HTMLElement element) {
        setElement((Element) element);
        for (String s: events()) {
            element.addEventListener(s, this);
        }
        element.addEventListener("click", this);
        handlers = new HashMap<String, List<EventListener>>();
    }

    @Override
    public void onBrowserEvent(Event event) {
        List<EventListener> listeners = handlers.get(event.getType());
        if (listeners != null) {
            for (EventListener l : listeners) {
                l.onBrowserEvent(event);
            }
        } else {
            super.onBrowserEvent(event);
        }
    }
    
    public void addEventHandler(String eventName, EventListener arg) {
        for (String s : events()) {
            if (s.equalsIgnoreCase(eventName)) {
                List<EventListener> listeners = handlers.get(eventName);
                if (listeners == null) {
                    listeners = new ArrayList<EventListener>();
                    handlers.put(eventName, listeners);
                }
                listeners.add(arg);
                return;
            }
        }
    }
    
    public void addChangeHandler(EventListener arg) {
        addEventHandler("change", arg);
    }
    
    public void addClickHandler(EventListener arg) {
        addEventHandler("click", arg);
    }
    
    public void removeEventListener(EventListener listener) {
        for (List<EventListener> listeners: handlers.values()) {
            if (listeners.remove(listener))
                return;
        }
    }
    
    public void removeEventListeners(String eventName) {
        handlers.remove(eventName);
    }
}

