package com.vaadin.prototype.wc.server.ui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.el.ArraySuffix;
import org.apache.commons.lang3.ArrayUtils;

import com.google.gwt.query.client.GQ;
import com.google.gwt.query.client.IsProperties;
import com.vaadin.prototype.wc.gwt.client.ui.EventServerRpc;
import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.AbstractFieldState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public abstract class BaseComponent extends AbstractComponent {
    
    public static interface EventListener {
        void onBrowserEvent(String eventName);
    }
    
    private HashMap<String, List<EventListener>> handlers;
    
    protected String[] events(){
       return new String[]{};   
    }
    protected String[] attributes(){
        return new String[]{};   
    }
    
    protected String[] concat(String[] a1, String[] a2) {
        return ArrayUtils.addAll(a1, a2);
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
    
    public BaseComponent() {
        handlers = new HashMap<String, List<EventListener>>();
        if (events().length > 0) {
            registerRpc(new EventServerRpc() {
                @Override
                public void stateChanged(String eventName, String json) {
                    System.out.println("Changed " + eventName + " " + json);
                    IsProperties p = GQ.create(json);
                    AbstractComponentState c = getState();
                    Class<?> clz = c.getClass();
                    while (clz != null) {
                        for (String s : attributes()) {
                            Object o = p.get(s);
                            Field field;
                            try {
                                field = clz.getDeclaredField(s);
                                field.setAccessible(true);
                                field.set(c, o);
                            } catch (NoSuchFieldException e) {
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        clz = clz.getSuperclass() != AbstractFieldState.class ? clz.getSuperclass() : null;
                    }
                    
                    List<EventListener> listeners = handlers.get(eventName);
                    if (listeners != null) {
                        for (EventListener l : listeners) {
                            l.onBrowserEvent(eventName);
                        }
                    }
                }
            });
        }
    }

}