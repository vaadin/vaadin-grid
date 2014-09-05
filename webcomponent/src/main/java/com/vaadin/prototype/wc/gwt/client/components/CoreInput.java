package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.js.JsArray;
import com.google.gwt.core.client.js.JsObject;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.user.client.EventListener;

/**
 * core-input is an unstyled single- or multi-line text field where user can
 * enter input.
 *
 * Example:
 *
 *     <core-input placeholder="Placeholder text here"></core-input>
 *
 *     <core-input multiline placeholder="Enter multiple lines here"></core-input>
 *
 * The text input's value is considered "committed" if the user hits the `enter`
 * key or blurs the input after changing the value. The "change" event is fired
 * when the value becomes committed, and the committed value is stored in the
 * "value" property. The current value of the input is stored in the "inputValue"
 * property.
 *
 * core-input also can optionally validate the value by providing it with a
 * regular expression to match against, or a validation function. The
 * "input-invalid" event is fired if the input value changes and is invalid.
 * The "invalid" property is also available for observation.
 *
 * Example:
 *
 *     // valid only if the value is a number
 *     <core-input validate="^[0-9]*$" on-input-invalid="{{inputInvalidAction}}"></core-input>
 *
 *     this.$.input.validate = /^[0-9]*$/;  // valid only if the value is a number
 *
 *     this.$.input2.validate = function(value) {
 *       return value === 'foo';  // valid only if the value is 'foo'
 *     }
 *
 * @group Polymer Core Elements
 * @element core-input
 * @homepage github.io
 */
@JsType(prototype = "HTMLElement", isNative = true)
public interface CoreInput {
/**
 * Fired when the inputValue of this text input changes and fails validation.
 *
 * @event input-invalid
 * @param {Object} detail
 * @param {string} value The text input's inputValue. * Fired when the user commits the value of the input, either by the hitting the
 * `enter` key or blurring the input after the changing the inputValue. Also see the
 * DOM "change" event.
 *
 * @event change * Fired when the inputValue of is changed. This is the same event as the DOM
 * "input" event.
 *
 * @event input
 */
  void addEventListener(String event, EventListener listener);

  /**
   * Placeholder text that hints to the user what can be entered in
   * the input.
   *
   * @attribute placeholder
   * @type string
   * @default ''
   */
  @JsProperty CoreInput placeholder(String val);
  @JsProperty String placeholder();

  /**
   * If true, this input cannot be focused and the user cannot change
   * its value.
   *
   * @attribute disabled
   * @type boolean
   * @default false
   */
  @JsProperty CoreInput disabled(boolean val);
  @JsProperty boolean disabled();

  /**
   * Set the input type. Not supported for `multiline`.
   *
   * @attribute type
   * @type string
   * @default text
   */
  @JsProperty CoreInput type(String val);
  @JsProperty String type();

  /**
   * If true, this input accepts multi-line input like a `<textarea>`
   *
   * @attribute multiline
   * @type boolean
   * @default false
   */
  @JsProperty CoreInput multiline(boolean val);
  @JsProperty boolean multiline();

  /**
   * (multiline only) The height of this text input in rows. The input
   * will scroll internally if more input is entered beyond the size
   * of the component. This property is meaningless if multiline is
   * false. You can also set this property to "fit" and size the
   * component with CSS to make the input fit the CSS size.
   *
   * @attribute rows
   * @type number|'fit'
   * @default 'fit'
   */
  @JsProperty CoreInput rows(double val);
  @JsProperty double rows();

  /**
   * The current value of this input.
   *
   * @attribute inputValue
   * @type string
   * @default ''
   */
  @JsProperty CoreInput inputValue(String val);
  @JsProperty String inputValue();

  /**
   * The value of the input committed by the user, either by changing the
   * inputValue and blurring the input, or by hitting the `enter` key.
   *
   * @attribute value
   * @type string
   * @default ''
   */
  @JsProperty CoreInput value(String val);
  @JsProperty String value();

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
   * @default null
   */
  @JsProperty CoreInput validate(String val);
  @JsProperty String validate();

  /**
   * If this property is true, the text input's inputValue failed validation.
   *
   * @attribute invalid
   * @type boolean
   * @default false
   */
  @JsProperty CoreInput invalid(boolean val);
  @JsProperty boolean invalid();
}
