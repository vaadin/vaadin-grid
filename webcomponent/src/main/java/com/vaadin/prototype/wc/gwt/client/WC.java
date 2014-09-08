package com.vaadin.prototype.wc.gwt.client;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.console;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.dom.client.Document;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.user.client.EventListener;
import com.vaadin.prototype.wc.gwt.client.components.CoreIcon;
import com.vaadin.prototype.wc.gwt.client.components.CoreIcons;
import com.vaadin.prototype.wc.gwt.client.components.CoreSubmenu;
import com.vaadin.prototype.wc.gwt.client.components.CoreTransitionCss;
import com.vaadin.prototype.wc.gwt.client.components.PaperDialog;
import com.vaadin.prototype.wc.gwt.client.components.PaperDialogTransition;
import com.vaadin.prototype.wc.gwt.client.html.HTMLDocument;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.html.HTMLWindow;

public abstract class WC {
    
    private static final Set<String> loaded = new HashSet<String>();
    
    public static HTMLWindow window = HTMLWindow.window;
    public static HTMLDocument document = window.document();
    public static HTMLElement head = document.head();
    public static HTMLElement body = document.body();
    
    private static boolean platformLoaded = false;
    private static final String WC = "components/";
    
    /*
     * Load platform polyfills, for non WC capable browsers 
     */
    private static void loadPlatform() {
        // Run once
        if (!platformLoaded) {
            String url = WC + "/platform/platform.js";
            console.log("Loaded Platform: " + url);
            Ajax.loadScript(url);

            monkeyPatchHTMLElement();
            platformLoaded = true;
        }
    }
    
    /*
     * FIXME: GWT does not support closures (aka @FunctionalInterface) yet.
     * patching addEventListener so as we can handle GWT EventListener instances.
     */
    private static native void monkeyPatchHTMLElement() /*-{
        var org = $wnd.HTMLElement.prototype.addEventListener;
        $wnd.HTMLElement.prototype.addEventListener = function(name, obj, bol) {
            var fnc = @com.vaadin.prototype.wc.gwt.client.WC::isEventListener(*)(obj) ?
                function(e) {
                    obj.@com.google.gwt.user.client.EventListener::onBrowserEvent(*)(e);
                } : obj;
            org.call(this, name, fnc, bol);
        }
    }-*/;

    /*
     * Used in patched addEventListener to know whether a function is a GWT
     * EventListener object.
     */
    private static boolean isEventListener(Object o) {
        return o instanceof EventListener;
    }
    
    /**
     * Create a WebComponent element of the provided class.
     * 
     * It takes care of importing the template and check if platform
     * was previously loaded.
     * 
     * Note That we follow this convention:
     *    className:   PaperSlider
     *    tagName:     paper-slider
     *    template:    publicFolder/paper-slider/paper-slider.html
     */
    @SuppressWarnings("unchecked")
    public static <T extends HTMLElement> T create(Class<T> clazz) {
        return (T)Document.get().createElement(load(clazz));
    }
    
    public static HTMLElement create(String tag) {
        return document.createElement(tag);
    }
    
    /**
     * Import the WebComponent of the provided class if it wasn't done already.
     */
    public static <T extends HTMLElement> String load(Class<T> clazz) {

        String tag = getTag(clazz);
        String path = tag;
        
        if (clazz == CoreIcon.class) {
            load(CoreIcons.class);
        }
        if (clazz == CoreIcons.class) {
            // Import all icon templates
            for (String s : new String[]{"",
                    "av",
                    "device",
                    "communication",
                    "hardware",
                    "image",
                    "maps",
                    "notification",
                    "png",
                    "social"
                    }) {
                s = s.isEmpty() ? s : (s + "-");
                importTemplate(tag + "/iconsets", s + "icons");
            }
            return tag;
        }
        if (clazz == CoreSubmenu.class) {
            path = "core-menu";
        }
        if (clazz == PaperDialog.class) {
            importTemplate(tag, getTag(PaperDialogTransition.class));
        }
        if (clazz == PaperDialogTransition.class) {
            path = "paper-dialog";
        }
        if (clazz == CoreTransitionCss.class) {
            path = "core-transition";
        }
        
        importTemplate(path, tag);
        return tag;
    }
    
    /**
     * Import a list of WebComponents.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void load(Class... classes) {
        for (Class clz : classes) {
            load(clz);
        }
    }
    
    private static void importTemplate(String path, String tag) {
        loadPlatform();
        String url = WC + path + "/" + tag + ".html";
        if (!loaded.contains(url)) {
            console.log("Loaded WebComponent: " + url);
            $(head).append("<link rel='import' href='" + url + "'/>");
            loaded.add(url);
        }
    }
    
    private static String getTag(Class<?> clazz) {
        String name = clazz.getSimpleName().replaceFirst(".*\\$", "");
        return JsUtils.hyphenize(name).replaceFirst("^-+", "");
    }
}
