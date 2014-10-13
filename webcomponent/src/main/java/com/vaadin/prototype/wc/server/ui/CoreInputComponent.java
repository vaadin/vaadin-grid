package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreInputState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreInputComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{"input","change","input-invalid"});
    }
  
    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"placeholder","disabled","type","multiline","rows","inputValue","value","validate","invalid"});
    }
  
    @Override
    protected CoreInputState getState() {
        return (CoreInputState) super.getState();
    }


  /**
   * Placeholder text that hints to the user what can be entered in
   * the input.
   *
   * @attribute placeholder
   * @type string
   */
    public void placeholder(String val) {
        getState().placeholder = val;
    }

  /**
   * Placeholder text that hints to the user what can be entered in
   * the input.
   *
   * @attribute placeholder
   * @type string
   */
    public String placeholder() {
        return getState().placeholder;
    }

  /**
   * If true, this input cannot be focused and the user cannot change
   * its value.
   *
   * @attribute disabled
   * @type boolean
   */
    public void disabled(boolean val) {
        getState().disabled = val;
    }

  /**
   * If true, this input cannot be focused and the user cannot change
   * its value.
   *
   * @attribute disabled
   * @type boolean
   */
    public boolean disabled() {
        return getState().disabled;
    }

  /**
   * Set the input type. Not supported for `multiline`.
   *
   * @attribute type
   * @type string
   */
    public void type(String val) {
        getState().type = val;
    }

  /**
   * Set the input type. Not supported for `multiline`.
   *
   * @attribute type
   * @type string
   */
    public String type() {
        return getState().type;
    }

  /**
   * If true, this input accepts multi-line input like a `<textarea>`
   *
   * @attribute multiline
   * @type boolean
   */
    public void multiline(boolean val) {
        getState().multiline = val;
    }

  /**
   * If true, this input accepts multi-line input like a `<textarea>`
   *
   * @attribute multiline
   * @type boolean
   */
    public boolean multiline() {
        return getState().multiline;
    }

  /**
   * (multiline only) The height of this text input in rows. The input
   * will scroll internally if more input is entered beyond the size
   * of the component. This property is meaningless if multiline is
   * false. You can also set this property to "fit" and size the
   * component with CSS to make the input fit the CSS size.
   *
   * @attribute rows
   * @type number|'fit'
   */
    public void rows(double val) {
        getState().rows = val;
    }

  /**
   * (multiline only) The height of this text input in rows. The input
   * will scroll internally if more input is entered beyond the size
   * of the component. This property is meaningless if multiline is
   * false. You can also set this property to "fit" and size the
   * component with CSS to make the input fit the CSS size.
   *
   * @attribute rows
   * @type number|'fit'
   */
    public double rows() {
        return getState().rows;
    }

  /**
   * The current value of this input.
   *
   * @attribute inputValue
   * @type string
   */
    public void inputValue(String val) {
        getState().inputValue = val;
    }

  /**
   * The current value of this input.
   *
   * @attribute inputValue
   * @type string
   */
    public String inputValue() {
        return getState().inputValue;
    }

  /**
   * The value of the input committed by the user, either by changing the
   * inputValue and blurring the input, or by hitting the `enter` key.
   *
   * @attribute value
   * @type string
   */
    public void value(String val) {
        getState().value = val;
    }

  /**
   * The value of the input committed by the user, either by changing the
   * inputValue and blurring the input, or by hitting the `enter` key.
   *
   * @attribute value
   * @type string
   */
    public String value() {
        return getState().value;
    }

  /**
   * If this property is not null, the text input's inputValue will be
   * validated. You can validate the value with either a regular expression
   * or a custom function.
   *
   * To use a regular expression, set this property to a RegExp object or
   * a string containing the regular expression to match against. To use a
   * custom validator, set this property to a function with the signature
   * function(value) that returns a boolean. The input is valid if the
   * function returns true.
   *
   * Example:
   *
   *     // valid only if the value is a number
   *     <core-input validate="^[0-9]*$"></core-input> 
   * 
   *     // valid only if the value is a number
   *     this.$.input.validate = /^[0-9]*$/;
   *
   *     this.$.input2.validate = function(value) {
   *         // valid only if the value is 'foo'
   *         return value === 'foo';  
   *     }
   *
   * @attribute validate
   * @type string|RegExp|Function(value)
   */
    public void validate(String val) {
        getState().validate = val;
    }

  /**
   * If this property is not null, the text input's inputValue will be
   * validated. You can validate the value with either a regular expression
   * or a custom function.
   *
   * To use a regular expression, set this property to a RegExp object or
   * a string containing the regular expression to match against. To use a
   * custom validator, set this property to a function with the signature
   * function(value) that returns a boolean. The input is valid if the
   * function returns true.
   *
   * Example:
   *
   *     // valid only if the value is a number
   *     <core-input validate="^[0-9]*$"></core-input> 
   * 
   *     // valid only if the value is a number
   *     this.$.input.validate = /^[0-9]*$/;
   *
   *     this.$.input2.validate = function(value) {
   *         // valid only if the value is 'foo'
   *         return value === 'foo';  
   *     }
   *
   * @attribute validate
   * @type string|RegExp|Function(value)
   */
    public String validate() {
        return getState().validate;
    }

  /**
   * If this property is true, the text input's inputValue failed validation.
   *
   * @attribute invalid
   * @type boolean
   */
    public void invalid(boolean val) {
        getState().invalid = val;
    }

  /**
   * If this property is true, the text input's inputValue failed validation.
   *
   * @attribute invalid
   * @type boolean
   */
    public boolean invalid() {
        return getState().invalid;
    }

}