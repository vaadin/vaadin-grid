package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreTooltipState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreTooltipComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }
  
    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"label","show","position","noarrow","tipAttribute"});
    }
  
    @Override
    protected CoreTooltipState getState() {
        return (CoreTooltipState) super.getState();
    }


  /**
   * A simple string label for the tooltip to display. To display a rich
   * HTML tooltip instead, omit `label` and include the `tip` attribute
   * on a child node of `core-tooltip`.
   *
   * @attribute label
   * @type string
   */
    public void label(String val) {
        getState().label = val;
    }

  /**
   * A simple string label for the tooltip to display. To display a rich
   * HTML tooltip instead, omit `label` and include the `tip` attribute
   * on a child node of `core-tooltip`.
   *
   * @attribute label
   * @type string
   */
    public String label() {
        return getState().label;
    }

  /**
   * If true, the tooltip displays by default.
   *
   * @attribute show
   * @type boolean
   */
    public void show(boolean val) {
        getState().show = val;
    }

  /**
   * If true, the tooltip displays by default.
   *
   * @attribute show
   * @type boolean
   */
    public boolean show() {
        return getState().show;
    }

  /**
   * Positions the tooltip to the top, right, bottom, left of its content.
   *
   * @attribute position
   * @type string
   */
    public void position(String val) {
        getState().position = val;
    }

  /**
   * Positions the tooltip to the top, right, bottom, left of its content.
   *
   * @attribute position
   * @type string
   */
    public String position() {
        return getState().position;
    }

  /**
   * If true, the tooltip an arrow pointing towards the content.
   *
   * @attribute noarrow
   * @type boolean
   */
    public void noarrow(boolean val) {
        getState().noarrow = val;
    }

  /**
   * If true, the tooltip an arrow pointing towards the content.
   *
   * @attribute noarrow
   * @type boolean
   */
    public boolean noarrow() {
        return getState().noarrow;
    }

  /**
   * Customizes the attribute used to specify which content
   * is the rich HTML tooltip.
   *
   * @attribute tipAttribute
   * @type string
   */
    public void tipAttribute(String val) {
        getState().tipAttribute = val;
    }

  /**
   * Customizes the attribute used to specify which content
   * is the rich HTML tooltip.
   *
   * @attribute tipAttribute
   * @type string
   */
    public String tipAttribute() {
        return getState().tipAttribute;
    }

}