package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.js.JsArray;
import com.google.gwt.core.client.js.JsObject;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.user.client.EventListener;
import com.vaadin.prototype.wc.gwt.client.html.*;

@JsType(prototype = "HTMLElement", isNative = true)
public interface PaperElements extends HTMLElement {

  Class<?>[] dependencies = new Class<?>[]{PaperButton.class, PaperCheckbox.class, PaperDialog.class, PaperFab.class, PaperIconButton.class, PaperInput.class, PaperProgress.class, PaperRadioButton.class, PaperRadioGroup.class, PaperRipple.class, PaperShadow.class, PaperSlider.class, PaperTabs.class, PaperToast.class, PaperToggleButton.class};
}
