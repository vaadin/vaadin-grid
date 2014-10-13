package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreIconsetState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreIconsetComponent extends CoreMetaComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }
  
    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"src","width","icons","iconSize","offsetX","offsetY"});
    }
  
    @Override
    protected CoreIconsetState getState() {
        return (CoreIconsetState) super.getState();
    }


  /**
   * The URL of the iconset image.
   *
   * @attribute src
   * @type string
   */
    public void src(String val) {
        getState().src = val;
    }

  /**
   * The URL of the iconset image.
   *
   * @attribute src
   * @type string
   */
    public String src() {
        return getState().src;
    }

  /**
   * The width of the iconset image. This must only be specified if the
   * icons are arranged into separate rows inside the image.
   *
   * @attribute width
   * @type number
   */
    public void width(double val) {
        getState().width = val;
    }

  /**
   * The width of the iconset image. This must only be specified if the
   * icons are arranged into separate rows inside the image.
   *
   * @attribute width
   * @type number
   */
    public double width() {
        return getState().width;
    }

  /**
   * A space separated list of names corresponding to icons in the iconset
   * image file. This list must be ordered the same as the icon images
   * in the image file.
   *
   * @attribute icons
   * @type string
   */
    public void icons(String val) {
        getState().icons = val;
    }

  /**
   * A space separated list of names corresponding to icons in the iconset
   * image file. This list must be ordered the same as the icon images
   * in the image file.
   *
   * @attribute icons
   * @type string
   */
    public String icons() {
        return getState().icons;
    }

  /**
   * The size of an individual icon. Note that icons must be square.
   *
   * @attribute iconSize
   * @type number
   */
    public void iconSize(double val) {
        getState().iconSize = val;
    }

  /**
   * The size of an individual icon. Note that icons must be square.
   *
   * @attribute iconSize
   * @type number
   */
    public double iconSize() {
        return getState().iconSize;
    }

  /**
   * The horizontal offset of the icon images in the inconset src image.
   * This is typically used if the image resource contains additional images
   * beside those intended for the iconset.
   *
   * @attribute offsetX
   * @type number
   */
    public void offsetX(double val) {
        getState().offsetX = val;
    }

  /**
   * The horizontal offset of the icon images in the inconset src image.
   * This is typically used if the image resource contains additional images
   * beside those intended for the iconset.
   *
   * @attribute offsetX
   * @type number
   */
    public double offsetX() {
        return getState().offsetX;
    }

  /**
   * The vertical offset of the icon images in the inconset src image.
   * This is typically used if the image resource contains additional images
   * beside those intended for the iconset.
   *
   * @attribute offsetY
   * @type number
   */
    public void offsetY(double val) {
        getState().offsetY = val;
    }

  /**
   * The vertical offset of the icon images in the inconset src image.
   * This is typically used if the image resource contains additional images
   * beside those intended for the iconset.
   *
   * @attribute offsetY
   * @type number
   */
    public double offsetY() {
        return getState().offsetY;
    }

}