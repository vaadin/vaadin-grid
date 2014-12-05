package com.vaadin.prototype.wc.gwt.client.util;

import com.vaadin.prototype.wc.gwt.client.components.CoreIcon;
import com.vaadin.prototype.wc.gwt.client.components.CoreIcons;
import com.vaadin.prototype.wc.gwt.client.components.CoreSubmenu;
import com.vaadin.prototype.wc.gwt.client.components.CoreTransitionCss;
import com.vaadin.prototype.wc.gwt.client.components.PaperDialog;
import com.vaadin.prototype.wc.gwt.client.components.PaperDialogTransition;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;

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

    @SuppressWarnings("unchecked")
    public static <T extends HTMLElement> T create(Class<T> clazz) {
        return (T)create(load(clazz));
    }

    public static HTMLElement create(String tag) {
        return Elements.create(tag);
    }

    /**
     * Import the WebComponent of the provided class if it wasn't done already.
     */
    public static <T extends HTMLElement> String load(Class<T> clazz) {
        String tag = Elements.computeTag(clazz);
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
                Elements.importTemplate(tag + "/iconsets", s + "icons");
            }
            return tag;
        }
        if (clazz == CoreSubmenu.class) {
            path = "core-menu";
        }
        if (clazz == PaperDialog.class) {
            Elements.importTemplate(tag, Elements.computeTag(PaperDialogTransition.class));
        }
        if (clazz == PaperDialogTransition.class) {
            path = "paper-dialog";
        }
        if (clazz == CoreTransitionCss.class) {
            path = "core-transition";
        }

        Elements.importTemplate(path, tag);
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

    public static void register(String tag, HTMLElement elem) {
        Elements.registerElement(tag, elem);
    }

}
