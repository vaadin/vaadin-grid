package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreMediaQueryState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreMediaQueryComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{"core-media-change"});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"queryMatches","query"});
    }

    @Override
    protected CoreMediaQueryState getState() {
        return (CoreMediaQueryState) super.getState();
    }


  /**
   * The Boolean return value of the media query
   *
   * @attribute queryMatches
   * @type Boolean
   */
    public void queryMatches(boolean val) {
        getState().queryMatches = val;
    }

  /**
   * The Boolean return value of the media query
   *
   * @attribute queryMatches
   * @type Boolean
   */
    public boolean queryMatches() {
        return getState().queryMatches;
    }

  /**
   * The CSS media query to evaulate
   *
   * @attribute query
   * @type string
   */
    public void query(String val) {
        getState().query = val;
    }

  /**
   * The CSS media query to evaulate
   *
   * @attribute query
   * @type string
   */
    public String query() {
        return getState().query;
    }

}