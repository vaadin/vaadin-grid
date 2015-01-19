package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreMetaState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreMetaComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"type","list"});
    }

    @Override
    protected CoreMetaState getState() {
        return (CoreMetaState) super.getState();
    }


  /**
   * The type of meta-data.  All meta-data with the same type with be
   * stored together.
   * 
   * @attribute type
   * @type string
   */
    public void type(String val) {
        getState().type = val;
    }

  /**
   * The type of meta-data.  All meta-data with the same type with be
   * stored together.
   * 
   * @attribute type
   * @type string
   */
    public String type() {
        return getState().type;
    }

}