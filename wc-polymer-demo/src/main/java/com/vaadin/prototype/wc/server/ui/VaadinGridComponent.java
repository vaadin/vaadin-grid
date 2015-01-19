package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.VaadinGridState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class VaadinGridComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{"select"});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"shadow","selectedRow","selectedRows","rowCount","type","url","dataSource","theme"});
    }

    @Override
    protected VaadinGridState getState() {
        return (VaadinGridState) super.getState();
    }

/**
 * @attribute shadow
 * @type boolean
 */
    public void shadow(boolean val) {
        getState().shadow = val;
    }
/**
 * @attribute shadow
 * @type boolean
 */
    public boolean shadow() {
        return getState().shadow;
    }
/**
 * @attribute selectedRow
 * @type number
 */ 
    public void selectedRow(double val) {
        getState().selectedRow = val;
    }
/**
 * @attribute selectedRow
 * @type number
 */ 
    public double selectedRow() {
        return getState().selectedRow;
    }
/**
 * @attribute rowCount
 * @type number
 */ 
    public void rowCount(double val) {
        getState().rowCount = val;
    }
/**
 * @attribute rowCount
 * @type number
 */ 
    public double rowCount() {
        return getState().rowCount;
    }
/**
 * @attribute type
 * @type string
 */ 
    public void type(String val) {
        getState().type = val;
    }
/**
 * @attribute type
 * @type string
 */ 
    public String type() {
        return getState().type;
    }
/**
 * @attribute url
 * @type string
 */ 
    public void url(String val) {
        getState().url = val;
    }
/**
 * @attribute url
 * @type string
 */ 
    public String url() {
        return getState().url;
    }
/**
 * @attribute theme
 * @type string
 */ 
    public void theme(String val) {
        getState().theme = val;
    }
/**
 * @attribute theme
 * @type string
 */ 
    public String theme() {
        return getState().theme;
    }

}