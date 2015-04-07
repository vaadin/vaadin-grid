package com.vaadin.components.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsNamespace;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.widget.grid.EditorHandler;
import com.vaadin.client.widget.grid.EditorHandler.EditorRequest;
import com.vaadin.client.widgets.Grid;
import com.vaadin.client.widgets.Grid.Column;
import com.vaadin.components.common.js.JS;
import com.vaadin.components.common.js.JSArray;
import com.vaadin.components.grid.config.JSColumn;
import com.vaadin.components.grid.config.JSEditorHandler;
import com.vaadin.components.grid.config.JSEditorRequest;
import com.vaadin.components.grid.data.DataItemContainer;
import com.vaadin.components.grid.table.GridColumn;

@JsNamespace(JS.VAADIN_JS_NAMESPACE)
@JsExport
@JsType
public class GridEditor {

    private final Grid<Object> grid;
    private final GridComponent gridComponent;
    private Element container;
    private JSEditorHandler handler;
    private boolean enabled;

    private final Map<JSColumn, Element> editors = new HashMap<>();

    public GridEditor(GridComponent gridComponent) {
        this.gridComponent = gridComponent;
        this.grid = gridComponent.getGrid();
    }

    public void setContainer(Element containerElement) {
        this.container = containerElement;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (handler != null) {
            grid.setEditorEnabled(enabled);
        }
    }

    public String getSaveButtonText() {
        return grid.getEditorSaveCaption();
    }

    public void setSaveButtonText(String saveButtonText) {
        grid.setEditorSaveCaption(saveButtonText);
    }

    public String getCancelButtonText() {
        return grid.getEditorCancelCaption();
    }

    public void setCancelButtonText(String cancelButtonText) {
        grid.setEditorCancelCaption(cancelButtonText);
    }

    public void editRow(int row) {
        grid.editRow(row);
    }

    public void save() {
        grid.saveEditor();
    }

    public void cancel() {
        grid.cancelEditor();
    }

    public void setHandler(JSEditorHandler handler) {
        this.handler = handler;
        grid.setEditorHandler(new EditorHandler<Object>() {
            @Override
            public void bind(EditorRequest<Object> request) {
                JsUtils.jsni(handler.getBind(), "call", handler.getBind(),
                        createJSEditorRequest(request, false));
            }

            @Override
            public void cancel(EditorRequest<Object> request) {
                JsUtils.jsni(handler.getCancel(), "call", handler.getCancel(),
                        createJSEditorRequest(request, true));
                editors.clear();
            }

            @Override
            public void save(EditorRequest<Object> request) {
                JsUtils.jsni(handler.getSave(), "call", handler.getSave(),
                        createJSEditorRequest(request, true));
                gridComponent.refresh();
            }

            @Override
            public Widget getWidget(Column<?, Object> column) {
                Element editor = getEditor(((GridColumn) column).getJsColumn());
                return editor != null ? new SimplePanel(editor) {
                } : null;
            }
        });
        grid.setEditorEnabled(enabled);
    }

    public JSEditorHandler getHandler() {
        return handler;
    }

    private JSEditorRequest createJSEditorRequest(
            EditorRequest<Object> request, final boolean clearEditorsOnSuccess) {
        JSEditorRequest result = JS.createJsType(JSEditorRequest.class);
        result.setRowIndex(request.getRowIndex());

        Object dataItem = request.getRow();
        if (dataItem instanceof DataItemContainer) {
            dataItem = ((DataItemContainer) dataItem).getDataItem();
        }
        result.setDataItem(dataItem);
        result.setGrid(container);
        result.setSuccess(JsUtils.wrapFunction(new Function() {
            @Override
            public void f() {
                request.success();
                if (clearEditorsOnSuccess) {
                    editors.clear();
                }
            }
        }));
        result.setFailure(JsUtils.wrapFunction(new Function() {
            @Override
            public void f() {
                JSArray<JSColumn> jsErrorColumns = arguments(1);
                Collection<Column<?, Object>> errorColumns = new ArrayList<>();
                for (Column<?, Object> column : gridComponent.getDataColumns()) {
                    if (jsErrorColumns != null
                            && jsErrorColumns.indexOf(((GridColumn) column)
                                    .getJsColumn()) != -1) {
                        errorColumns.add(column);
                    }
                }
                request.failure(arguments(0), errorColumns);
            }
        }));
        result.setGetCellEditor(JsUtils.wrapFunction(new Function() {
            @Override
            public Object f(Object... args) {
                return getEditor(arguments(0));
            }
        }));
        return result;
    }

    private Element getEditor(JSColumn jsColumn) {
        if (!editors.containsKey(jsColumn)) {
            editors.put(
                    jsColumn,
                    JsUtils.jsni(handler.getGetCellEditor(), "call",
                            handler.getGetCellEditor(), jsColumn));
        }
        return editors.get(jsColumn);
    }

}
