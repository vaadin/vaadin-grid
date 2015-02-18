package com.vaadin.components.common.util;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.console;
import static com.google.gwt.query.client.GQuery.document;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Predicate;
import com.google.gwt.query.client.Promise;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.query.client.plugins.deferred.PromiseFunction;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Timer;
import com.vaadin.components.common.html.HTMLElement;

public class DOMUtils {
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

    public static Promise ready() {
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
        };
    }

    private static Function postThemeLoad = new Function() {
        public void f() {
            for (Element e : $("v-grid").elements()) {
                // ((WCVGrid)(HTMLElement)e).redraw();
                // GQuery to the rescue. Seems that you cannot call redraw in the java class.
                JsUtils.jsni(e, "redraw");
            }
        }
    };

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
                    waitUntilThemeLoaded(style, postThemeLoad);
                }
            }
        });
    }

    private static void waitUntilThemeLoaded(final GQuery style, final Function f) {
        try {
            final JavaScriptObject sheet = style.prop("sheet");
            if (sheet != null) {
                final JsArray<JavaScriptObject> rows = JsUtils.prop(sheet, "cssRules");
                if ( rows != null && rows.length() > 0) {
                    new Timer() {
                        public void run() {
                            try {
                                JavaScriptObject importedSheet = JsUtils.prop(rows.get(0), "styleSheet");
                                if (importedSheet != null) {
                                     new Timer() {
                                        @Override
                                        public void run() {
                                            f.f();
                                        }
                                    }.schedule(500);
                                    console.log("Theme rules were loaded: " + style.text());
                                    cancel();
                                    }
                                } catch (Exception e) {
                                    console.log(e);
                                }
                        }
                    // Don't slow down the browser
                    }.scheduleRepeating(100);
                }
            }
        } catch (Exception e) {
            // Firefox likes to fail if we start poking too early...
            Scheduler.get().scheduleDeferred(new ScheduledCommand() {
                @Override
                public void execute() {
                    waitUntilThemeLoaded(style, f);
                }
            });
        }
    }

    public static void loadVaadinTheme(HTMLElement container, HTMLElement el, HTMLElement style, String def) {
        loadVaadinTheme(container, el, style, def, postThemeLoad);
    }

    private static void loadVaadinTheme(HTMLElement container, HTMLElement el, HTMLElement style, String def, Function f) {
        String theme = getAttrValue(el, "theme", def);
        if (theme == null) {
            return;
        }
        if ($(style).text().contains(theme)) {
            return;
        }
        loadTheme($(container), $(style), theme);
        waitUntilThemeLoaded($(style), f);
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

    private static String loadThemeAjax(String theme) {
        String url = "bower_components/vaadin-" + theme + "/styles.css";

        Ajax.get(url).done(new Function() {
            public void f() {
                super.f();
            }
        });
        return url;
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
            base += "bower_components/vaadin-";
        }
        base += theme + "/styles.css";
        container.addClass(theme);
        style.text("@import url('" + base + "')");
    }

    public static native void observe(JavaScriptObject jso, EventListener ev) /*-{
      if (! ('observe' in Object) ) {
        $wnd.console.log("This browser does not support Object.observe, consider to load a polyfill.");
        return;
      }
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
