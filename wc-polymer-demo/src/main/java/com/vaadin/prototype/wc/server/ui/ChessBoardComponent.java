package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.ChessBoardState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class ChessBoardComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }
  
    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"fen","frame"});
    }
  
    @Override
    protected ChessBoardState getState() {
        return (ChessBoardState) super.getState();
    }


  /**
   * @attribute fen
   * @type string
   */
    public void fen(String val) {
        getState().fen = val;
    }

  /**
   * @attribute fen
   * @type string
   */
    public String fen() {
        return getState().fen;
    }

  /**
   * @attribute frame
   * @type boolean
   */
    public void frame(boolean val) {
        getState().frame = val;
    }

  /**
   * @attribute frame
   * @type boolean
   */
    public boolean frame() {
        return getState().frame;
    }

}