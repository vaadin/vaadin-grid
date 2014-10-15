package com.vaadin.prototype.wc.gwt.client;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.console;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.user.client.EventListener;
import com.vaadin.prototype.wc.gwt.client.html.HTMLDocument;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.html.HTMLWindow;

/**
 * This is the helper class to create, import or register web components.
 *
 * This should produce a public API in Vaadin framework, and it should
 * be discussed.
 *
 * Righ now we can do:
 *
 * WC.load(ImportedWebComponent.class)
 * WC.create(ImportedWebComponent.class)
 * WC.create("a-tag")
 * WC.register(ExportedWebComponent.class)
 *
 * @author manolo
 *
 */
public abstract class WC {

    private static final Set<String> loaded = new HashSet<String>();

    public static final String NS_WC = "$wnd.wc";

    public static HTMLWindow window = HTMLWindow.window;
    public static HTMLDocument document = window.document();
    public static HTMLElement head = document.head();
    public static HTMLElement body = document.body();

    private static final String WC = GWT.getModuleBaseForStaticFiles() + "components/";

    private static boolean platformLoaded = ((Element)window).getPropertyJSO("Platform") != null;

    static {
        monkeyPatchHTMLElement();
    }

    /*
     * Load platform polyfills, for non WC capable browsers
     */
    private static void loadPlatform() {
        if (!platformLoaded) {
            String url = WC + "platform/platform.js";
            console.log("Loaded Platform polyfills: " + url);
            Ajax.loadScript(url);
            platformLoaded = true;
        }
    }

    /*
     * FIXME: GWT does not support closures (aka @FunctionalInterface) yet.
     * patching addEventListener so as we can handle GWT EventListener instances.
     */
    private static native void monkeyPatchHTMLElement() /*-{
        if ($wnd.__addEventListener_org) return;
        console.log("Monkey Patched HTMLElement.addEventListener");
        $wnd.__addEventListener_org = $wnd.HTMLElement.prototype.addEventListener;
        $wnd.__addEventListener_patched = function(name, obj, bol) {
            var fnc = @com.vaadin.prototype.wc.gwt.client.WC::isEventListener(*)(obj) ?
                function(e) {
                    obj.@com.google.gwt.user.client.EventListener::onBrowserEvent(*)(e);
                } : obj;
            $wnd.__addEventListener_org.call(this, name, fnc, bol);
        };
        $wnd.HTMLElement.prototype.addEventListener = $wnd.__addEventListener_patched;
    }-*/;

    /*
     * Used in patched addEventListener to know whether a function is a GWT
     * EventListener object.
     */
    private static boolean isEventListener(Object o) {
        return o instanceof EventListener;
    }


    public static HTMLElement create(String tag) {
        return document.createElement(tag);
    }

    /**
     * Import a list of WebComponents.
     */
    @SuppressWarnings({"rawtypes" })
    public static void load(Class... classes) {
        for (Class clz : classes) {
            load(clz);
        }
    }

    private static void importTemplate(String path, String tag) {
        loadPlatform();
        String url = WC + path + "/" + tag + ".html";
        if (!loaded.contains(url)) {
            console.log("Imported External WebComponent: " + url);
            $(head).append("<link rel='import' href='" + url + "'/>");
            loaded.add(url);
        }
    }

    private static String getTag(Class<?> clazz) {
        String name = clazz.getSimpleName().replaceFirst(".*\\$", "");
        return JsUtils.hyphenize(name).replaceFirst("^-+", "");
    }

    public static void register(String tag, Class<?> clazz) {
        document.registerElement(tag, getWCConstructor(clazz));
    }

    private static JavaScriptObject getWCConstructor(Class<?> clz) {
        // Using a convention HTML<tag>Element$Prototype as super class name
        Class<?> sup = clz.getSuperclass() != null ? clz.getSuperclass() : HTMLElement.class;
        String prt = sup.getSimpleName().replace("$Prototype", "");
        return getWCConstructor(NS_WC, clz.getSimpleName(), prt);
    }

    /**
     * FIXME: extending HTMLElement.Prototype we should be able to merge
     * HTMLElement with our widget, but JsInterop fails.
     */
    private static native JavaScriptObject getWCConstructor(String namespace, String classname, String protoName) /*-{
        // TODO: this is a trivial way to extend, maybe we need something
        // more sophisticated, but hopefully JsInterop works as expected soon.
        function mixin(dest, src) {
          for (var k in src) {
            if (src.hasOwnProperty(k)) {
              // TODO: This is a hack to define js properties calling
              // java functions, should be automatic with @JsProperty annotation
              // Our workaround uses a magic method jsProperty to mark the
              // attribute and calls the corresponding setter and getter
              var r = /^jsProperty([A-Z].+)$/.exec(k);
              if (r) {
                // descapitalize
                var prpName = r[1].charAt(0).toLowerCase() + r[1].slice(1);
                var getName = "get" + r[1];
                var setName = "set" + r[1];
                // don't set twice
                if (src[prpName] === undefined) {
                  Object.defineProperty(dest, prpName, {
                      get: src[getName] || function(){},
                      set: src[setName] || function(){},
                      enumerable: true,
                      configurable: true
                  });
                }
                // We don't want getters and setters of defined properties
                delete dest[getName];
                delete dest[setName];
              } else {
                dest[k] = src[k];
              }
            }
          }
        }

        console.log("Mixing prototypes of : " + protoName + " + " + namespace  + "." + classname);

        var widget = eval(namespace + "." + classname);
        function Wc() {
           // Call HTMLElement constructor
           HTMLElement.call(this);
           // Call our object constructor
           widget.call(this);
        }

        Wc.prototype = Object.create($wnd[protoName].prototype);
        mixin(Wc.prototype, widget.prototype);

        // TODO: Why do we need to monkey patch again?
        if ($wnd.__addEventListener_patched)
           Wc.prototype.addEventListener = $wnd.__addEventListener_patched;

        return Wc;
    }-*/;

    private static GQuery vaadinStyle;
    public static void importVaadinTheme(String theme) {
        if (vaadinStyle == null) {
            vaadinStyle = $("<style language='text/css'></style>").appendTo($(head));
        }
        vaadinStyle.text("@import url('VAADIN/themes/" + theme + "/styles.css')");
        $(body).attr("class", theme);
    }
}
