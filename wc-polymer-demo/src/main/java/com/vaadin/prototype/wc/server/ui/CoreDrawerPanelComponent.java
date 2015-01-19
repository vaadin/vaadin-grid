package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreDrawerPanelState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreDrawerPanelComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{"core-responsive-change"});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"responsiveWidth","selected","defaultSelected","narrow"});
    }

    @Override
    protected CoreDrawerPanelState getState() {
        return (CoreDrawerPanelState) super.getState();
    }


  /**
   * Max-width when the panel changes to narrow layout.
   *
   * @attribute responsiveWidth
   * @type string
   */
    public void responsiveWidth(String val) {
        getState().responsiveWidth = val;
    }

  /**
   * Max-width when the panel changes to narrow layout.
   *
   * @attribute responsiveWidth
   * @type string
   */
    public String responsiveWidth() {
        return getState().responsiveWidth;
    }

  /**
   * The panel that is being selected. `drawer` for the drawer panel and
   * `main` for the main panel.
   *
   * @attribute selected
   * @type string
   */
    public void selected(String val) {
        getState().selected = val;
    }

  /**
   * The panel that is being selected. `drawer` for the drawer panel and
   * `main` for the main panel.
   *
   * @attribute selected
   * @type string
   */
    public String selected() {
        return getState().selected;
    }

  /**
   * The panel to be selected when `core-drawer-panel` changes to narrow 
   * layout.
   *
   * @attribute defaultSelected
   * @type string
   */
    public void defaultSelected(String val) {
        getState().defaultSelected = val;
    }

  /**
   * The panel to be selected when `core-drawer-panel` changes to narrow 
   * layout.
   *
   * @attribute defaultSelected
   * @type string
   */
    public String defaultSelected() {
        return getState().defaultSelected;
    }

  /**
   * Returns true if the panel is in narrow layout.  This is useful if you
   * need to show/hide elements based on the layout.
   *
   * @attribute narrow
   * @type boolean
   */
    public void narrow(boolean val) {
        getState().narrow = val;
    }

  /**
   * Returns true if the panel is in narrow layout.  This is useful if you
   * need to show/hide elements based on the layout.
   *
   * @attribute narrow
   * @type boolean
   */
    public boolean narrow() {
        return getState().narrow;
    }

}