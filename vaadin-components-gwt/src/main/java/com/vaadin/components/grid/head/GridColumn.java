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
import com.vaadin.components.grid.GridComponent;
import com.vaadin.components.grid.config.JSColumn;

public final class GridColumn extends Column<Object, JsArrayMixed> {

    private static final String VALUE = "value";
    private JSColumn jsColumn;
    private Grid<JsArrayMixed> grid;
    private GridComponent gridComponent;
    protected JavaScriptObject valueGenerator;
    private JavaScriptObject renderer;
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
                "sortable", "editable", "renderer", "generatedValue",
                "minWidth", "maxWidth", "width")) {
            result.defineProperty(jsColumn, propertyName);
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
        this.renderer = renderer;
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

    private void setValue(String propertyName, JavaScriptObject jso) {
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
        case "editable":
            setEditable(JsUtils.prop(jso, VALUE));
            break;
        case "renderer":
            setColumnRenderer(JsUtils.prop(jso, VALUE));
            break;
        case "generatedValue":
            valueGenerator = JsUtils.prop(jso, VALUE);
            break;
        case "minWidth":
            setMinimumWidth(parseDouble(JsUtils.prop(jso, VALUE)));
            break;
        case "maxWidth":
            setMaximumWidth(parseDouble(JsUtils.prop(jso, VALUE)));
            break;
        case "width":
            setWidth(parseDouble(JsUtils.prop(jso, VALUE)));
            break;
        default:
            break;
        }
        gridComponent.redraw();
    }

    private native void defineProperty(JSColumn jsColumn, String propertyName)
    /*-{
      var _this = this;
      var _value = jsColumn[propertyName];
      Object.defineProperty(jsColumn, propertyName, {
        get: function() {
            return _value;
        },
        set: function(value) {
            _this.@com.vaadin.components.grid.head.GridColumn::setValue(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(propertyName, {value: value});
            _value = value;
        }
      });
      
      if (_value !== undefined){
          jsColumn[propertyName] = _value;
      }
    }-*/;

    private double parseDouble(String cssPixelValue) {
        return Double.parseDouble(cssPixelValue.replaceAll("[^\\d]", ""));
    }

    @Override
    public Object getValue(JsArrayMixed row) {
        int idx = grid.getColumns().indexOf(this);

        Object o = null;

        if (valueGenerator != null) {
            o = JsUtils.jsni(valueGenerator, "call", valueGenerator, row);
        } else {
            if (o instanceof JavaScriptObject
                    && JsUtils.isFunction((JavaScriptObject) o)) {
                o = JsUtils.jsni((JavaScriptObject) o, "call", o, row, idx);
            } else if (o instanceof String
            // For some reason JsInterop returns empty
                    && "" != o) {
                o = JsUtils.prop(row, o);
            } else {
                if (JsUtils.isArray(row)) {
                    o = row.getObject(idx);
                } else {
                    Properties p = row.cast();
                    o = p.getObject(p.keys()[idx]);
                }
            }
        }
        return o;
    }
}
