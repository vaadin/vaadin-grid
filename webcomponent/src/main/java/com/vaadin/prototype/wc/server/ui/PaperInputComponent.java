package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.PaperInputState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class PaperInputComponent extends CoreInputComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }
  
    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"label","floatingLabel","maxRows"});
    }
  
    @Override
    protected PaperInputState getState() {
        return (PaperInputState) super.getState();
    }


  /**
   * The label for this input. It normally appears as grey text inside
   * the text input and disappears once the user enters text.
   *
   * @attribute label
   * @type string
   */
    public void label(String val) {
        getState().label = val;
    }

  /**
   * The label for this input. It normally appears as grey text inside
   * the text input and disappears once the user enters text.
   *
   * @attribute label
   * @type string
   */
    public String label() {
        return getState().label;
    }

  /**
   * If true, the label will "float" above the text input once the
   * user enters text instead of disappearing.
   *
   * @attribute floatingLabel
   * @type boolean
   */
    public void floatingLabel(boolean val) {
        getState().floatingLabel = val;
    }

  /**
   * If true, the label will "float" above the text input once the
   * user enters text instead of disappearing.
   *
   * @attribute floatingLabel
   * @type boolean
   */
    public boolean floatingLabel() {
        return getState().floatingLabel;
    }

  /**
   * (multiline only) If set to a non-zero value, the height of this
   * text input will grow with the value changes until it is maxRows
   * rows tall. If the maximum size does not fit the value, the text
   * input will scroll internally.
   *
   * @attribute maxRows
   * @type number
   */
    public void maxRows(double val) {
        getState().maxRows = val;
    }

  /**
   * (multiline only) If set to a non-zero value, the height of this
   * text input will grow with the value changes until it is maxRows
   * rows tall. If the maximum size does not fit the value, the text
   * input will scroll internally.
   *
   * @attribute maxRows
   * @type number
   */
    public double maxRows() {
        return getState().maxRows;
    }

}