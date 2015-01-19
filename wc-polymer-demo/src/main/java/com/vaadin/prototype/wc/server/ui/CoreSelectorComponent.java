package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreSelectorState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreSelectorComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"selected","multi","valueattr","selectedClass","selectedProperty","selectedAttribute","selectedItem","selectedModel","selectedIndex","target","itemSelector","activateEvent","notap"});
    }

    @Override
    protected CoreSelectorState getState() {
        return (CoreSelectorState) super.getState();
    }


  /**
   * If true, multiple selections are allowed.
   *
   * @attribute multi
   * @type boolean
   */
    public void multi(boolean val) {
        getState().multi = val;
    }

  /**
   * If true, multiple selections are allowed.
   *
   * @attribute multi
   * @type boolean
   */
    public boolean multi() {
        return getState().multi;
    }

  /**
   * Specifies the attribute to be used for "selected" attribute.
   *
   * @attribute valueattr
   * @type string
   */
    public void valueattr(String val) {
        getState().valueattr = val;
    }

  /**
   * Specifies the attribute to be used for "selected" attribute.
   *
   * @attribute valueattr
   * @type string
   */
    public String valueattr() {
        return getState().valueattr;
    }

  /**
   * Specifies the CSS class to be used to add to the selected element.
   * 
   * @attribute selectedClass
   * @type string
   */
    public void selectedClass(String val) {
        getState().selectedClass = val;
    }

  /**
   * Specifies the CSS class to be used to add to the selected element.
   * 
   * @attribute selectedClass
   * @type string
   */
    public String selectedClass() {
        return getState().selectedClass;
    }

  /**
   * Specifies the property to be used to set on the selected element
   * to indicate its active state.
   *
   * @attribute selectedProperty
   * @type string
   */
    public void selectedProperty(String val) {
        getState().selectedProperty = val;
    }

  /**
   * Specifies the property to be used to set on the selected element
   * to indicate its active state.
   *
   * @attribute selectedProperty
   * @type string
   */
    public String selectedProperty() {
        return getState().selectedProperty;
    }

  /**
   * Specifies the attribute to set on the selected element to indicate
   * its active state.
   *
   * @attribute selectedAttribute
   * @type string
   */
    public void selectedAttribute(String val) {
        getState().selectedAttribute = val;
    }

  /**
   * Specifies the attribute to set on the selected element to indicate
   * its active state.
   *
   * @attribute selectedAttribute
   * @type string
   */
    public String selectedAttribute() {
        return getState().selectedAttribute;
    }

  /**
   * In single selection, this returns the selected index.
   *
   * @attribute selectedIndex
   * @type number
   */
    public void selectedIndex(double val) {
        getState().selectedIndex = val;
    }

  /**
   * In single selection, this returns the selected index.
   *
   * @attribute selectedIndex
   * @type number
   */
    public double selectedIndex() {
        return getState().selectedIndex;
    }

  /**
   * This can be used to query nodes from the target node to be used for 
   * selection items.  Note this only works if the 'target' property is set.
   *
   * Example:
   *
   *     <core-selector target="{{$.myForm}}" itemsSelector="input[type=radio]"></core-selector>
   *     <form id="myForm">
   *       <label><input type="radio" name="color" value="red"> Red</label> <br>
   *       <label><input type="radio" name="color" value="green"> Green</label> <br>
   *       <label><input type="radio" name="color" value="blue"> Blue</label> <br>
   *       <p>color = {{color}}</p>
   *     </form>
   * 
   * @attribute itemSelector
   * @type string
   */
    public void itemSelector(String val) {
        getState().itemSelector = val;
    }

  /**
   * This can be used to query nodes from the target node to be used for 
   * selection items.  Note this only works if the 'target' property is set.
   *
   * Example:
   *
   *     <core-selector target="{{$.myForm}}" itemsSelector="input[type=radio]"></core-selector>
   *     <form id="myForm">
   *       <label><input type="radio" name="color" value="red"> Red</label> <br>
   *       <label><input type="radio" name="color" value="green"> Green</label> <br>
   *       <label><input type="radio" name="color" value="blue"> Blue</label> <br>
   *       <p>color = {{color}}</p>
   *     </form>
   * 
   * @attribute itemSelector
   * @type string
   */
    public String itemSelector() {
        return getState().itemSelector;
    }

  /**
   * The event that would be fired from the item element to indicate
   * it is being selected.
   *
   * @attribute activateEvent
   * @type string
   */
    public void activateEvent(String val) {
        getState().activateEvent = val;
    }

  /**
   * The event that would be fired from the item element to indicate
   * it is being selected.
   *
   * @attribute activateEvent
   * @type string
   */
    public String activateEvent() {
        return getState().activateEvent;
    }

  /**
   * Set this to true to disallow changing the selection via the
   * `activateEvent`.
   *
   * @attribute notap
   * @type boolean
   */
    public void notap(boolean val) {
        getState().notap = val;
    }

  /**
   * Set this to true to disallow changing the selection via the
   * `activateEvent`.
   *
   * @attribute notap
   * @type boolean
   */
    public boolean notap() {
        return getState().notap;
    }

}