package com.vaadin.prototype.wc.gwt.client.widgets;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.console;
import static com.google.gwt.query.client.GQuery.document;
import static com.google.gwt.query.client.GQuery.window;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Predicate;
import com.google.gwt.query.client.Promise;
import com.google.gwt.query.client.plugins.deferred.PromiseFunction;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Timer;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;

public class WCUtils {
    public static boolean getAttrBooleanValue(HTMLElement el, String attr, boolean def) {
        return Boolean.valueOf(getAttrValue(el, attr, String.valueOf(def)));
    }

    public static int getAttrIntValue(HTMLElement el, String attr, int def) {
        return (int) getAttrFloatValue(el, attr, def);
    }

    public static double getAttrDoubleValue(HTMLElement el, String attr,
            double def) {
        return getAttrFloatValue(el, attr, (float) def);
    }

    public static String getAttrValue(HTMLElement el, String attr, String def) {
        String val = el.getAttribute(attr);
        return val == null || val.isEmpty() ? def : val;
    }

    public static float getAttrFloatValue(HTMLElement el, String attr, float def) {
        try {
            return Float.valueOf(getAttrValue(el, attr, String.valueOf(def)));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static Promise ready(Function... fncs) {
        return new PromiseFunction() {
            public void f(final com.google.gwt.query.client.Promise.Deferred dfd) {
                if ("complete" == $(document).prop("readyState")) {
                    dfd.resolve();
                } else {
                    new Timer() {
                        public void run() {
                            f(dfd);
                        }
                    }.schedule(5);
                }
            }
        }.done(fncs);
    }

    public static void loadVaadinGlobalTheme() {
        ready().done(new Function(){
            public void f() {
                GQuery body = $("body");
                String theme = body.attr("vaadin-theme");
                if (!theme.isEmpty()) {
                    GQuery style = $("#__vaadin-style");
                    if (style.isEmpty()) {
                        style = $("<style id='__vaadin-style' language='text/css'></style>").appendTo($("head"));
                    }
                    loadTheme(body, style, theme);
                }
            }
        });
    }

    public static void loadVaadinTheme(HTMLElement container, HTMLElement el, HTMLElement style, String def, Function... f) {
        String theme = getAttrValue(el, "theme", def);
        if (theme == null) {
            return;
        }
        if ($(theme).text().contains(theme)) {
            return;
        }
        console.log("loadVaadinTheme", theme);
        loadTheme($(container), $(style), theme);
        $(window).delay(100, f);
    }

    public static GQuery linksAndScripts(final GQuery g) {
        GQuery imports = g.find("link[rel='import'], script[src]");
        imports.each(new Function(){
            public void f() {
                GQuery a = $("<a>", g.get(0));
                String attr = $(this).prop("href") != null ? "href" : "src";
                a.prop("href", $(this).attr(attr));
                $(this).attr(attr, a.prop("href"));
            }
        });
        GQuery toAdd = $();
        for (Element e : imports.elements()) {
            Element d = $(e).prop("import");
            if (d != null) {
                toAdd.add(linksAndScripts($(d)));
            }
        }
        return imports.add(toAdd);
    }

    private static void loadTheme(GQuery container, GQuery style, String theme) {
        // Get all scripts and links in the page, even those nested in imports
        GQuery links = linksAndScripts($(document));
        // This code is very tricky and tries to figure out where vaadin themes are.
        // GWT moduleBaseUrl does not work if we use link imports.
        GQuery l = links.filter(new Predicate(){
            public boolean f(Element e, int index) {
                String h = $(e).attr("href");
                return h.matches("^(|.*/)(vaadin-[\\w\\-]+)\\.html");
            }
        });
        GQuery s = links.filter(new Predicate(){
            public boolean f(Element e, int index) {
                String h = $(e).attr("src");
                return h.matches("^.*\\.nocache.js.*");
            }
        });
        GQuery v = links.filter(new Predicate(){
            public boolean f(Element e, int index) {
                String h = $(e).attr("src");
                return h.endsWith("vaadin-components.js");
            }
        });
        String base = GWT.getModuleBaseURL().replace(GWT.getModuleName() + "/", "");
        if (!l.isEmpty()) {
            base = l.attr("href").replaceFirst("[\\w\\-]+\\.html", "");
            if (base.matches("^(|/|.*[\\w\\-]/)$")) {
//                base += "../vaadin-themes/";
                base += "../vaadin-";
            } else {
                base += "VAADIN/themes/";
            }
        } else if (base.contains("VAADIN/widgetsets")) {
            base += "../themes/";
        } else if (!v.isEmpty()) {
            base = v.attr("src").replace("vaadin-components.js", "themes/");
        } else if (!s.isEmpty()) {
            base += "VAADIN/themes/";
        }
        base += theme + "/styles.css";
        console.log("Theme Url: " + base);
        container.addClass(theme);
        style.text("@import url('" + base + "')");
    }

    public static native void observe(JavaScriptObject jso, EventListener ev) /*-{
      var fnc = function(changes) {
        ev.@com.google.gwt.user.client.EventListener::onBrowserEvent(*)(null);
      };
      jso.__fnc = fnc;
      Object.observe(jso, fnc);
   }-*/;

    public static native void unobserve(JavaScriptObject jso) /*-{
      if (jso && jso.__fnc) {
        Object.unobserve(jso, jso.__fnc);
        jso.__fnc = undefined;
      }
   }-*/;
}
