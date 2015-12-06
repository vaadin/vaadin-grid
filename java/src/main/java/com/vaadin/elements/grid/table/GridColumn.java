package com.vaadin.elements.grid.table;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.TextOverflow;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.vaadin.client.widgets.Grid.Column;
import com.vaadin.elements.common.js.JS;
import com.vaadin.elements.common.js.JSArray;
import com.vaadin.elements.grid.GridElement;
import com.vaadin.elements.grid.config.JSColumn;
import com.vaadin.elements.grid.config.JSStaticCell;
import com.vaadin.elements.grid.data.GridDataSource;
import com.vaadin.elements.grid.data.GridDomTableDataSource;

public final class GridColumn extends Column<Object, Object> {

    private final JSColumn jsColumn;
    private final GridElement gridElement;

    /**
     * Create a new GridColumn associated with a JSColumn configuration.
     *
     * @param cfg it accepts either a JSO or a JSColumn, the object is
     *   promoted to a JSColumn to add customised setters/getters.
     */
    public static GridColumn createColumn(Object cfg, GridElement gridElement) {
        JSColumn jsColumn = JSColumn.promote(cfg);
        GridColumn column = new GridColumn(jsColumn, gridElement);
        gridElement.getGrid().addColumn(column,
                gridElement.getGrid().getVisibleColumns().size());
        jsColumn.configure(gridElement, column);
        return column;
    }

    private GridColumn(JSColumn jsColumn, GridElement gridElement) {
        this.jsColumn = jsColumn;
        this.gridElement = gridElement;

        // Default renderer
        setRenderer((cell, data) -> {
            Element element = cell.getElement();
            String content = JS.isUndefinedOrNull(data) ? "" : data.toString();

            if (gridElement.getDataSource() instanceof GridDomTableDataSource
                    && new HTML(content).getElement().getFirstChildElement() != null) {
                element.setInnerHTML(content);
            } else {
                Element wrapper = element.getFirstChildElement();
                if (wrapper == null || !wrapper.getPropertyBoolean("iswrapper")) {
                    // Need to create a new wrapper
                    wrapper = DOM.createSpan();
                    wrapper.getStyle().setOverflow(Overflow.HIDDEN);
                    wrapper.getStyle().setTextOverflow(TextOverflow.ELLIPSIS);
                    wrapper.setPropertyBoolean("iswrapper", true);
                    element.removeAllChildren();
                    element.appendChild(wrapper);
                }
                wrapper.setInnerText(content);
            }
        });
    }

    public JSStaticCell getDefaultHeaderCellReference() {
        GridStaticSection staticSection = gridElement.getStaticSection();
        return staticSection.getHeaderCellByColumn(
                staticSection.getDefaultHeader(), this);
    }

    public void nameChanged(String name) {
        // Need to invoke the logic that determines whether default header cell
        // should show content or name. Invocation must be deferred because
        // the logic happens synchronously and the actual name property hasn't
        // been yet applied
        JSStaticCell reference = getDefaultHeaderCellReference();
        Scheduler.get().scheduleDeferred(
                () -> reference.setContent(reference.getContent()));

    }

    public JSColumn getJsColumn() {
        return jsColumn;
    }

    @Override
    public Object getValue(Object dataItem) {
        dataItem = GridDataSource.extractDataItem(dataItem);

        Object result = null;
        if (JS.isPrimitiveType(dataItem)) {
            if (getColumnIndex() == 0) {
                result = dataItem;
            }
        } else {
            if (JsUtils.isArray((JavaScriptObject) dataItem)) {
                result = ((JSArray<Object>) dataItem).get(getColumnIndex());
            } else {
                result = getNestedProperty(dataItem,
                        Arrays.asList(jsColumn.getName().split("\\.")));
            }
        }
        return result;
    }

    private Object getNestedProperty(Object o, List<String> props) {
        Object result = null;
        if (props.isEmpty()) {
            result = o;
        } else if (JS.isObject(o)) {
            result = getNestedProperty(
                    JsUtils.prop((JavaScriptObject) o, props.get(0)),
                    props.subList(1, props.size()));
        }
        return result;
    }

    private int getColumnIndex() {
        return gridElement.getColumns().indexOf(jsColumn);
    }
}
