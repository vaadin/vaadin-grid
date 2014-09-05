package com.vaadin.prototype.wc.gwt.client;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.console;

import java.util.HashMap;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Promise;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.query.client.plugins.ajax.Ajax;

public abstract class Loader {
    
    private static HashMap<String, Class<?>> cache = new HashMap<String, Class<?>>();
    private static Element head = Document.get().getHead();
    
    private static Promise platform;
    private static final String WC = "components/";
    
    private static void loadPlatform() {
        if (platform == null) {
            String url = WC + "/platform/platform.js";
            console.log("Loaded Platform: " + url);
            platform = Ajax.loadScript(url);
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> clazz) {
        String name = clazz.getSimpleName().replaceFirst(".*\\$", "");
        String tag = JsUtils.hyphenize(name).replaceFirst("^-+", "");
        
        if (!cache.values().contains(clazz)) {
            loadPlatform();

            String url = WC + tag + "/" + tag + ".html";

            console.log("Loaded WebComponent: " + name + " " + tag + " " + url);
            $("<link rel='import' href='" + url + "'/>").appendTo(head);
            cache.put(name, clazz);
        }

        return (T)Document.get().createElement(tag);
    }

}
