package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class ChessBoardWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public ChessBoardWidget() {
      super(WC.create(ChessBoard.class));
    }
    
    public ChessBoardWidget(ChessBoard element) {
      super(element);
    }
    
    protected ChessBoard element() {
      return (ChessBoard)super.getElement();
    }

    public void fen(String val) {
        element().fen(val);
    }
    public String fen() {
        return element().fen();
    }
    public void frame(boolean val) {
        element().frame(val);
    }
    public boolean frame() {
        return element().frame();
    }

}
