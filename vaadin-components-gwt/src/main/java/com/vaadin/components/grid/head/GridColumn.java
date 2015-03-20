package com.vaadin.components.grid.head;

import java.util.Arrays;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.js.JsUtils;
import com.vaadin.client.renderers.Renderer;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.client.widgets.Grid;
import com.vaadin.client.widgets.Grid.Column;
import com.vaadin.components.common.js.JS;
import com.vaadin.components.common.js.JS.PropertyValueSetter;
import com.vaadin.components.grid.GridComponent;
import com.vaadin.components.grid.config.JSColumn;
import com.vaadin.components.grid.data.DataItemContainer;

public final class GridColumn extends Column<Object, Object> implements
        PropertyValueSetter {

    private JSColumn jsColumn;
    private Grid<Object> grid;
    private GridComponent gridComponent;
    private JavaScriptObject valueGenerator;
    private String name;

    public static GridColumn addColumn(JSColumn jsColumn,
            GridComponent gridComponent) {

        GridColumn result = new GridColumn();
        result.grid = gridComponent.getGrid();
        result.gridComponent = gridComponent;
        result.jsColumn = jsColumn;

        gridComponent.getGrid().addColumn(result);

        // Define the properties
        for (String propertyName : Arrays.asList("flex", "name", "headerHtml",
                "sortable", "readOnly", "renderer", "generatedValue",
                "minWidth", "maxWidth", "width")) {
            JS.defineSetter(jsColumn, result, propertyName, result);
        }

        return result;

    }

    private GridColumn() {
        super();
    }

    public JSColumn getJsColumn() {
        return jsColumn;
    }

    private void setColumnRenderer(JavaScriptObject renderer) {
        setRenderer(new Renderer<Object>() {
            @Override
            public void render(RendererCellReference cell, Object data) {
                JavaScriptObject cellJso = JavaScriptObject.createObject();
                JsUtils.prop(cellJso, "element", cell.getElement());
                JsUtils.prop(cellJso, "data", data);
                JsUtils.prop(cellJso, "index", cell.getColumnIndex());
                JsUtils.prop(cellJso, "rowIndex", cell.getRowIndex());

                JsUtils.jsni(renderer, "call", renderer, cellJso);
            }
        });
    }

    @Override
    public void setValue(Object target, String propertyName,
            JavaScriptObject jso) {
        switch (propertyName) {
        case "flex":
            double value = JsUtils.prop(jso, VALUE);
            setExpandRatio((int) value);
            break;
        case "name":
            name = JsUtils.prop(jso, VALUE);
            break;
        case "headerHtml":
            grid.getDefaultHeaderRow().getCell(this)
                    .setHtml(JsUtils.prop(jso, VALUE));
            break;
        case "sortable":
            setSortable(JsUtils.prop(jso, VALUE));
            break;
        case "readOnly":
            setEditable(!JsUtils.<Boolean> prop(jso, VALUE));
            break;
        case "renderer":
            setColumnRenderer(JsUtils.prop(jso, VALUE));
            break;
        case "generatedValue":
            valueGenerator = JsUtils.prop(jso, VALUE);
            break;
        case "minWidth":
            String minWidth = JsUtils.prop(jso, VALUE);
            if (minWidth != null && !minWidth.isEmpty()) {
                setMinimumWidth(parseDouble(minWidth));
            }
            break;
        case "maxWidth":
            String maxWidth = JsUtils.prop(jso, VALUE);
            if (maxWidth != null && !maxWidth.isEmpty()) {
                setMaximumWidth(parseDouble(maxWidth));
            }
            break;
        case "width":
            String width = JsUtils.prop(jso, VALUE);
            if (width != null && !width.isEmpty()) {
                setWidth(parseDouble(width));
            }
            break;
        default:
            break;
        }
        gridComponent.redraw();
    }

    private double parseDouble(String cssPixelValue) {
        return Double.parseDouble(cssPixelValue.replaceAll("[^\\d]", ""));
    }

    @Override
    public Object getValue(Object dataItem) {
        if (dataItem instanceof DataItemContainer) {
            dataItem = ((DataItemContainer) dataItem).getDataItem();
        }
        Object result = null;
        if (valueGenerator != null) {
            result = JsUtils.jsni(valueGenerator, "call", valueGenerator,
                    dataItem);
        } else {
            if (JS.isPrimitiveType(dataItem)) {
                if (getColumnIndex() == 0) {
                    result = dataItem;
                }
            } else {
                if (JsUtils.isArray((JavaScriptObject) dataItem)) {
                    result = ((JsArrayMixed) dataItem)
                            .getObject(getColumnIndex());
                } else {
                    Properties p = ((JavaScriptObject) dataItem).cast();
                    result = p.getObject(name);
                }
            }
        }

        return result;
    }

    private int getColumnIndex() {
        return gridComponent.getColumns().indexOf(jsColumn);
    }

}
