package com.vaadin.components.grid.table;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.js.JsUtils;
import com.vaadin.client.renderers.Renderer;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.client.widgets.Grid.Column;
import com.vaadin.components.common.js.JS;
import com.vaadin.components.grid.GridComponent;
import com.vaadin.components.grid.config.JSCell;
import com.vaadin.components.grid.config.JSColumn;
import com.vaadin.components.grid.data.DataItemContainer;

public final class GridColumn extends Column<Object, Object> {

    private final JSColumn jsColumn;
    private final GridComponent gridComponent;

    public static GridColumn addColumn(JSColumn jsColumn,
            GridComponent gridComponent) {
        GridColumn result = new GridColumn(jsColumn, gridComponent);
        gridComponent.getGrid().addColumn(result);
        result.bindProperties();
        return result;
    }

    private GridColumn(JSColumn jsColumn, GridComponent gridComponent) {
        this.jsColumn = jsColumn;
        this.gridComponent = gridComponent;
    }

    private void bindProperties() {
        bind("headerHtml", new Function() {
            @Override
            public void f() {
                gridComponent.getGrid().getDefaultHeaderRow()
                        .getCell(GridColumn.this).setHtml(arguments(0));
            }
        });
        bind("flex", new Function() {
            @Override
            public void f() {
                setExpandRatio(((Double) arguments(0)).intValue());
            }
        });
        bind("sortable", new Function() {
            @Override
            public void f() {
                setSortable(arguments(0));
            }
        });
        bind("readOnly", new Function() {
            @Override
            public void f() {
                setEditable(!(boolean) arguments(0));
            }
        });
        bind("renderer", new Function() {
            @Override
            public void f() {
                setRenderer(new Renderer<Object>() {
                    @Override
                    public void render(RendererCellReference cell, Object data) {
                        JSCell jsCell = JSCell.create(cell,
                                gridComponent.getContainer());
                        JS.exec(arguments(0), jsCell);
                    }
                });
            }
        });
        bind("minWidth", new Function() {
            @Override
            public void f() {
                String minWidth = arguments(0);
                if (minWidth != null && !minWidth.isEmpty()) {
                    setMinimumWidth(parseDouble(minWidth));
                }
            }
        });
        bind("maxWidth", new Function() {
            @Override
            public void f() {
                String maxWidth = arguments(0);
                if (maxWidth != null && !maxWidth.isEmpty()) {
                    setMaximumWidth(parseDouble(maxWidth));
                }
            }
        });
        bind("width", new Function() {
            @Override
            public void f() {
                String width = arguments(0);
                if (width != null && !width.isEmpty()) {
                    setWidth(parseDouble(width));
                }
            }
        });
    }

    private void bind(String propertyName, final Function function) {
        JS.definePropertyAccessors(getJsColumn(), propertyName, new Function() {
            @Override
            public void f() {
                Object newValue = arguments(0);
                function.f(newValue);
                gridComponent.redraw();
            }
        }, null);
    }

    public JSColumn getJsColumn() {
        return jsColumn;
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
        if (jsColumn.getGeneratedValue() != null) {
            result = JS.exec(jsColumn.getGeneratedValue(), dataItem);
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
                    result = ((Properties) dataItem).getObject(jsColumn
                            .getName());
                }
            }
        }
        return result;
    }

    private int getColumnIndex() {
        return gridComponent.getColumns().indexOf(jsColumn);
    }
}
