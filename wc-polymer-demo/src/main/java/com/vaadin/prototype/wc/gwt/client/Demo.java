package com.vaadin.prototype.wc.gwt.client;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.vaadin.prototype.wc.gwt.client.components.ChessBoard;
import com.vaadin.prototype.wc.gwt.client.components.CoreCollapse;
import com.vaadin.prototype.wc.gwt.client.components.CoreDrawerPanel;
import com.vaadin.prototype.wc.gwt.client.components.CoreIcon;
import com.vaadin.prototype.wc.gwt.client.components.CoreIconButton;
import com.vaadin.prototype.wc.gwt.client.components.CoreItem;
import com.vaadin.prototype.wc.gwt.client.components.CoreMenu;
import com.vaadin.prototype.wc.gwt.client.components.CoreMenuButton;
import com.vaadin.prototype.wc.gwt.client.components.CoreSplitter;
import com.vaadin.prototype.wc.gwt.client.components.CoreSubmenu;
import com.vaadin.prototype.wc.gwt.client.components.CoreToolbar;
import com.vaadin.prototype.wc.gwt.client.components.CoreTooltip;
import com.vaadin.prototype.wc.gwt.client.components.CoreTransition;
import com.vaadin.prototype.wc.gwt.client.components.PaperButton;
import com.vaadin.prototype.wc.gwt.client.components.PaperDialog;
import com.vaadin.prototype.wc.gwt.client.components.PaperFab;
import com.vaadin.prototype.wc.gwt.client.components.PaperRipple;
import com.vaadin.prototype.wc.gwt.client.components.PaperShadow;
import com.vaadin.prototype.wc.gwt.client.components.PaperSlider;
import com.vaadin.prototype.wc.gwt.client.components.PaperToast;
import com.vaadin.prototype.wc.gwt.client.util.WC;
import com.vaadin.prototype.wc.gwt.client.widgets.WCButton;
import com.vaadin.prototype.wc.gwt.client.widgets.WCDateBox;
import com.vaadin.prototype.wc.gwt.client.widgets.WCDatepicker;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVSlider;

/**
 * Example code for a GwtQuery application
 */
public class Demo implements EntryPoint {

    public void onModuleLoad() {
        if (Window.Location.getPath().contains("index.html")) {
            new DemoWrapWC().onModuleLoad();
            new DemoExportWC().onModuleLoad();
        } else if (Window.Location.getPath().contains("polymer.html")) {
            new DemoWrapWC().onModuleLoad();
        } else if (Window.Location.getPath().contains("gwt.html")) {
            new DemoExportWC().onModuleLoad();
        } else {
            loadPolymerElement();
            exportGwtWidgets();
        }
    }

    private void loadPolymerElement() {
        WC.load(ChessBoard.class);
        WC.load(CoreCollapse.class);
        WC.load(CoreDrawerPanel.class);
        WC.load(CoreIcon.class);
        WC.load(CoreIconButton.class);
        WC.load(CoreItem.class);
        WC.load(CoreMenu.class);
        WC.load(CoreMenuButton.class);
        WC.load(CoreSplitter.class);
        WC.load(CoreSubmenu.class);
        WC.load(CoreToolbar.class);
        WC.load(CoreTooltip.class);
        WC.load(CoreTransition.class);
        WC.load(PaperButton.class);
        WC.load(PaperDialog.class);
        WC.load(PaperFab.class);
        WC.load(PaperRipple.class);
        WC.load(PaperShadow.class);
        WC.load(PaperSlider.class);
        WC.load(PaperToast.class);
    }

    private void exportGwtWidgets() {
        WC.register(WCButton.TAG, WCButton.class);
        WC.register(WCVSlider.TAG, WCVSlider.class);
        WC.register(WCDateBox.TAG, WCDateBox.class);
        WC.register(WCDatepicker.TAG, WCDatepicker.class);
    }
}
