package com.vaadin.elements.grid;

import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.RootPanel;
import com.vaadin.client.BrowserInfo;
import com.vaadin.client.WidgetUtil;
import com.vaadin.client.widget.grid.selection.SelectionModelMulti;
import com.vaadin.client.widgets.Escalator;
import com.vaadin.client.widgets.Grid;
import com.vaadin.client.widgets.Overlay;
import com.vaadin.elements.common.js.JS;
import com.vaadin.elements.grid.selection.IndexBasedSelectionModelMulti;

public class ViolatedGrid extends Grid<Object> {

    public static class ViolatedOverlay extends Overlay {
        private Element partnerElement;

        @Override
        public void addAutoHidePartner(Element partner) {
            partnerElement = partner;
        }

        @Override
        public void setPopupPositionAndShow(PositionCallback callback) {
            this.getElement().getStyle().setOpacity(0);
            show();
            adjustPosition(this.getElement(), partnerElement);
            this.getElement().getStyle().setOpacity(1);
        }

        private native void adjustPosition(Element p, Element r) /*-{
         var overlayRect = p.getBoundingClientRect();
         var partnerRect = r.getBoundingClientRect();

         p.style.left = (partnerRect.right - overlayRect.left - overlayRect.width) + 'px';
         p.style.top = (partnerRect.top - overlayRect.top + partnerRect.height) + 'px';
        }-*/;
    }

    public ViolatedGrid() {
        super();
        // This is needed for detecting the correct native scrollbar size in
        // (OS X) Chrome [https://github.com/vaadin/vaadin-grid/issues/30]
        if (BrowserInfo.get().isChrome()) {
            setWidgetUtilNativeScrollbarSize(getWidgetUtilNativeScrollbarSize());
        }

        if (WidgetUtil.getNativeScrollbarSize() == 0) {
            // "invisible" scrollbars
            if (BrowserInfo.get().isSafari()) {
                // This hack is needed to fix scrollbars for OS X Safari
                // [https://github.com/vaadin/vaadin-grid/issues/28]
                //
                // Dynamic scrollbars refuse to work on the stacking root
                // directly so
                // the wrapper is needed for creating an additional stacking
                // context.
                GQuery scrollers = GQuery
                        .$(".vaadin-grid-scroller", this)
                        .css("position", "relative")
                        .wrap("<div style='position: absolute; z-index: 10' />");
                scrollers.filter(".vaadin-grid-scroller-vertical").parent()
                        .css("right", "0");
                scrollers.filter(".vaadin-grid-scroller-horizontal").parent()
                        .css("bottom", "0");
            } else if (BrowserInfo.get().isChrome()) {
                // Fix for [https://github.com/vaadin/vaadin-grid/issues/29]
                GQuery.$(".vaadin-grid-scroller", this).attr("invisible", "");
            }
        }
    }

    @Override
    public Escalator getEscalator() {
        return super.getEscalator();
    }

    public void refreshStaticSection(StaticSection.StaticCell cell) {
        if (cell instanceof HeaderCell) {
            refreshHeader();
        } else {
            refreshFooter();
        }
    }

    public native void refreshHeader()
    /*-{
        this.@com.vaadin.client.widgets.Grid::refreshHeader();
    }-*/;

    public native void refreshFooter()
    /*-{
        this.@com.vaadin.client.widgets.Grid::refreshFooter();
    }-*/;

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

    @Override
    public void onBrowserEvent(Event event) {
        Element target = event.getEventTarget().cast();
        if (target != getElement() && event.getType().equals(BrowserEvents.CLICK)) {
            Element focused = WidgetUtil.getFocusedElement();
            if (focused != getElement()) {
                if (isSelectAll(target)) {
                    // clicking on the select all checkbox moves focus away from the
                    // grid causing the :focus transition effect to be reapplied.
                    // Forcing focus on the grid will mitigate the issue.
                    getElement().focus();
                } else {
                    boolean focusable = !JS.ISIE || isFocusable(focused);
                    if (focusable && focused.isOrHasChild(target)) {
                        return;
                    }
                }
            }
        }
        super.onBrowserEvent(event);
    }

    private boolean isFocusable(Element focused) {
        // In IE11 flex items can also receive focus and be the
        // value of document.activeElement.
        String fTag = focused.getTagName().toLowerCase();
        return !focused.hasAttribute("disabled") && (focused.hasAttribute("tabindex")
                || fTag.matches("button|input|select|option|textarea|object|iframe|label")
                || fTag.matches("a|area") && focused.hasAttribute("href"));
    }

    private boolean isSelectAll(Element element) {
        return this.getSelectionModel() instanceof IndexBasedSelectionModelMulti
                && element.getParentNode() == JS.querySelector(getElement(), ".vaadin-grid-select-all-checkbox");
    }

    @Override
    public void removeColumn(
            com.vaadin.client.widgets.Grid.Column<?, Object> column) {
        int initialFrozenColumnCount = getFrozenColumnCount();
        int maxFrozenColumnCount = getColumnCount() - 1;
        if (getSelectionModel() instanceof SelectionModelMulti) {
            maxFrozenColumnCount--;
        }

        // Make sure that frozen column count does not exceed the total column
        // count. This is currently not checked by Grid.
        if (maxFrozenColumnCount < initialFrozenColumnCount) {
            setFrozenColumnCount(maxFrozenColumnCount);
        }

        try {
            super.removeColumn(column);
        } catch (Exception e) {
            // If column removal fails for any reason, we need to set the frozen
            // column count to the initial value.
            setFrozenColumnCount(initialFrozenColumnCount);
        }
    }
}
