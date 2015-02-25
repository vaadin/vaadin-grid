package com.vaadin.components.common.util;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.console;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.user.client.EventListener;
import com.vaadin.components.common.html.HTMLDocument;
import com.vaadin.components.common.html.HTMLElement;
import com.vaadin.components.common.html.HTMLWindow;

/**
 * This is the helper class to create, import or register web components.
 *
 * @author manolo
 */
public abstract class Elements {

    private static  Set<String> urlImported;

    // This has to match with the @JsNamespace of the package-info of exported components
    public static final String VAADIN_JS_NAMESPACE = "vaadin";

    public static HTMLWindow window;
    public static HTMLDocument document;
    public static HTMLElement head;
    public static HTMLElement body;

    // public path of imported components
    private static final String WC = GWT.getModuleBaseForStaticFiles() + "bower_components/";

    private static boolean platformLoaded = false;

    static {
        urlImported = new HashSet<String>();
        window = HTMLWindow.window;
        document = window.document();
        head = document.head();
        body = document.body();
        monkeyPatchHTMLElement();
    }

    /*
     * Load webcomponents polyfil
     */
    private static void loadPlatform() {
        platformLoaded = platformLoaded || $(window).prop("Platform") != null || $(window).prop("WebComponents") != null;
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
        $wnd.__addEventListener_org = $wnd.HTMLElement.prototype.addEventListener;
        $wnd.__addEventListener_patched = function(name, obj, bol) {
            var fnc = @com.vaadin.components.common.util.Elements::isEventListener(*)(obj) ?
                function(e) {
                    obj.@com.google.gwt.user.client.EventListener::onBrowserEvent(*)(e);
                } : obj;
            $wnd.__addEventListener_org.call(this, name, fnc, bol);
        };
        $wnd.HTMLElement.prototype.addEventListener = $wnd.__addEventListener_patched;
    }-*/;

    /*
     * Used in monkey patched addEventListener to know whether a function is a GWT
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
     *    className:   ChessBoard
     *    tagName:     chess-board
     *    template:    publicFolder/chess-board/chess-board.html
     */
    @SuppressWarnings("unchecked")
    public static <T extends HTMLElement> T create(Class<T> clazz) {
        return (T)create(importElement(clazz));
    }

    public static HTMLElement create(String tag) {
        return document.createElement(tag);
    }

    /**
     * Import the WebComponent of the provided class if it wasn't done already.
     */
    public static <T extends HTMLElement> String importElement(Class<T> clazz) {
        String tag = computeTag(clazz);
        String path = tag;
        importTemplate(path, tag);
        return tag;
    }

    public static void importTemplate(String path, String tag) {
        loadPlatform();
        String url = WC + path + "/" + tag + ".html";
        if (!urlImported.contains(url)) {
            console.log("Imported External WebComponent: " + url);
            $(head).append("<link rel='import' href='" + url + "'/>");
            urlImported.add(url);
        }
    }

    public static String computeTag(Class<?> clazz) {
        String name = clazz.getSimpleName().replaceFirst(".*\\$", "");
        return JsUtils.hyphenize(name).replaceFirst("^-+", "");
    }

    public static void registerElement(String tag, HTMLElement elem) {
        JavaScriptObject js = getWCOptions(elem);
        document.registerElement(tag, js);
    }

    /**
     * FIXME: extending HTMLElement.Prototype we should be able to merge
     * HTMLElement with our widget, but JsInterop fails.
     */
    private static native JavaScriptObject getWCOptions(HTMLElement elem) /*-{
        // TODO: avoid having to mix prototypes with JsInterop ?
        function mixin(dest, src) {
          for (var k in src) {
            if (src.hasOwnProperty(k)) {
              // TODO: This is a huge hack to define js properties calling
              // java functions, should be automatic with @JsProperty annotation
              // Our workaround uses a magic method jsProperty to mark the
              // attribute and call the corresponding setter and getter based
              // on this name
              var r = /^jsProperty([A-Z].+)$/.exec(k);
              if (r) {
                // Convention:
                //  k = jsPropertyValue -> prpName=value getName=getValue setName=setValue
                var prpName = r[1].charAt(0).toLowerCase() + r[1].slice(1);
                var getName = "get" + r[1];
                var setName = "set" + r[1];
                // don't set twice
                if (src[prpName] === undefined) {
                  Object.defineProperty(dest, prpName, {
                      get: src[getName] || function(){},
                      set: src[setName] || function(){},
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

        // TODO: we shouldn't have to mix prototypes since HTMLElement already
        // should be extending native Node, but we need this in FF in order that
        // the generated web element is instance-of Node.
        var eproto = $wnd.Object.create($wnd.HTMLElement.prototype);
        var cproto = $wnd.Object.getPrototypeOf(elem);
        mixin(eproto, cproto);

        // TODO: Why do we need to monkey patch again?
        if ($wnd.__addEventListener_patched)
           eproto.addEventListener = $wnd.__addEventListener_patched;

        return {prototype: eproto};
    }-*/;
}
