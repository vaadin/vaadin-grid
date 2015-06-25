package com.vaadin.components.grid;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.vaadin.client.BrowserInfo;
import com.vaadin.client.WidgetUtil;

public class ViolatedGrid extends com.vaadin.client.widgets.Grid<Object> {

    public ViolatedGrid() {
        super();
        // This is needed for detecting the correct native scrollbar size in
        // (OS X) Chrome [https://github.com/vaadin/components/issues/30]
        if (BrowserInfo.get().isChrome()) {
            setWidgetUtilNativeScrollbarSize(getWidgetUtilNativeScrollbarSize());
        }

        // This hack is needed to fix scrollbars for OS X Safari
        // [https://github.com/vaadin/components/issues/28]
        //
        // Dynamic scrollbars refuse to work on the stacking root directly so
        // the wrapper is needed for creating an additional stacking context.
        if (BrowserInfo.get().isSafari()
                && WidgetUtil.getNativeScrollbarSize() == 0) {
            GQuery scrollers = GQuery.$(".v-grid-scroller", this)
                    .css("position", "relative")
                    .wrap("<div style='position: absolute; z-index: 10' />");
            scrollers.filter(".v-grid-scroller-vertical").parent()
                    .css("right", "0");
            scrollers.filter(".v-grid-scroller-horizontal").parent()
                    .css("bottom", "0");
        }
    }

    public native boolean refreshHeader()
    /*-{
        this.@com.vaadin.client.widgets.Grid::refreshHeader()();
    }-*/;

    public native boolean refreshFooter()
    /*-{
      this.@com.vaadin.client.widgets.Grid::refreshFooter()();
    }-*/;

    public void refreshStaticSection(StaticSection.StaticCell cell) {
        if (cell instanceof HeaderCell) {
            refreshHeader();
        } else {
            refreshFooter();
        }
    }

    /**
     * The method is overridden for now to avoid IE related bugs and performance
     * issues.
     */
    @Override
    public void setHeightByRows(double bodyRows)
            throws IllegalArgumentException {
        // Header height
        double headerRowHeight = getEscalator().getHeader()
                .getDefaultRowHeight();
        int headerRows = isHeaderVisible() ? getHeaderRowCount() : 0;
        double headerHeight = headerRowHeight * headerRows;

        // Body height
        double bodyRowHeight = getEscalator().getBody().getDefaultRowHeight();
        double bodyHeight = bodyRowHeight * bodyRows;

        // Footer height
        double footerRowHeight = getEscalator().getFooter()
                .getDefaultRowHeight();
        int footerRows = isFooterVisible() ? getFooterRowCount() : 0;
        double footerHeight = footerRowHeight * footerRows;

        setHeight(headerHeight + bodyHeight + footerHeight + "px");
    }

    private static native void setWidgetUtilNativeScrollbarSize(int size)
    /*-{
      @com.vaadin.client.WidgetUtil::detectedScrollbarSize = size;
    }-*/;

    private static int getWidgetUtilNativeScrollbarSize() {
        // Using an iFrame to calculate the native scrollbar size
        IFrameElement iFrame = IFrameElement.as(DOM.createIFrame());
        iFrame.getStyle().setProperty("position", "absolute");
        iFrame.getStyle().setProperty("marginLeft", "-5000px");
        RootPanel.getBodyElement().appendChild(iFrame);
        Document contentDocument = iFrame.getContentDocument();

        Element scroller = contentDocument.createElement("div");
        scroller.getStyle().setProperty("width", "50px");
        scroller.getStyle().setProperty("height", "50px");
        scroller.getStyle().setProperty("overflow", "scroll");
        contentDocument.getBody().appendChild(scroller);

        int detectedScrollbarSize = scroller.getOffsetWidth()
                - scroller.getPropertyInt("clientWidth");
        RootPanel.getBodyElement().removeChild(iFrame);

        return detectedScrollbarSize;
    }
}
