package com.vaadin.components.grid.selection;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.DOM;
import com.vaadin.client.data.DataSource.RowHandle;
import com.vaadin.client.renderers.Renderer;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.client.widget.grid.events.SelectAllEvent;
import com.vaadin.client.widget.grid.selection.MultiSelectionRenderer;
import com.vaadin.client.widget.grid.selection.SelectionEvent;
import com.vaadin.client.widget.grid.selection.SelectionModelMulti;
import com.vaadin.client.widgets.Grid;
import com.vaadin.components.common.js.JS;
import com.vaadin.components.common.js.JSArray;
import com.vaadin.components.common.js.JSValidate;

/**
 * An {@link IndexBasedSelectionModel} for multiple selection.
 */
public class IndexBasedSelectionModelMulti extends SelectionModelMulti<Object>
        implements IndexBasedSelectionModel {

    private Renderer<Boolean> renderer;
    private Grid<Object> grid;
    private boolean allowSelection = true;

    private final JSArray<Double> indexes = JS.createArray();
    private boolean invertedSelection = false;
    private boolean dataSizeUpdated = false;

    @Override
    public void setGrid(Grid<Object> grid) {
        super.setGrid(grid);
        this.grid = grid;
        renderer = new MultiSelectionRenderer<Object>(grid) {
            @Override
            public void init(RendererCellReference cell) {
                InputElement checkbox = Document.get()
                        .createCheckInputElement();
                LabelElement label = Document.get().createLabelElement();
                checkbox.setId(DOM.createUniqueId());
                checkbox.setTabIndex(-1);
                label.setHtmlFor(checkbox.getId());
                cell.getElement().removeAllChildren();
                cell.getElement().appendChild(checkbox);
                cell.getElement().appendChild(label);
                checkbox.addClassName("v-grid style-scope");
                label.addClassName("v-grid style-scope");
            }

            @Override
            protected void setSelected(int logicalRow, boolean select) {
                if (allowSelection) {
                    super.setSelected(logicalRow, select);
                    allowSelection = false;
                }
            }
        };
    }

    @Override
    public Renderer<Boolean> getSelectionColumnRenderer() {
        return renderer;
    }

    @Override
    public void startBatchSelect() {
        allowSelection = true;
    }

    @Override
    public void commitBatchSelect() {
        allowSelection = true;
    }

    public boolean isIndeterminate() {
        return (size() > 0) ? size() != grid.getDataSource().size() : false;
    }

    public boolean isChecked() {
        return (size() > 0) ? size() == grid.getDataSource().size() : false;
    }

    public IndexBasedSelectionModelMulti(boolean invertedSelection) {
        this.invertedSelection = invertedSelection;
    }

    @Override
    protected boolean selectByHandle(RowHandle<Object> handle) {
        return select(SelectionUtil.getRowIndex(grid, handle), true);
    }

    @Override
    protected boolean deselectByHandle(RowHandle<Object> handle) {
        return deselect(SelectionUtil.getRowIndex(grid, handle), true);
    }

    @Override
    public boolean isSelected(Object row) {
        return invertedSelection ? indexes.indexOf((double) SelectionUtil
                .getRowIndexByRow(grid, row)) == -1
                : indexes.indexOf((double) SelectionUtil.getRowIndexByRow(grid,
                        row)) != -1;
    }

    @Override
    public void reset() {
        invertedSelection = false;
        indexes.setLength(0);
        grid.fireEvent(new SelectionEvent<Object>(grid, null, null, true));
    }

    @Override
    public JSArray<Object> selected(JavaScriptObject mapper, Integer from,
            Integer to) {
        JSArray<Object> result = JS.createArray();
        mapper = SelectionUtil.verifyMapper(mapper);
        if (invertedSelection) {

            int size = size();

            int fromIndex = JSValidate.Integer.val(from, 0, 0);
            fromIndex = Math.min(Math.max(fromIndex, 0), size - 1);

            int defaultTo = size() - 1;
            int toIndex = JSValidate.Integer.val(to, defaultTo, defaultTo);
            toIndex = Math.min(Math.max(toIndex, 0), size - 1);

            int count = toIndex - fromIndex + 1;

            int index = 0;
            int selectedIndexCount = 0;
            int addedSelectedIndexCount = 0;
            while (addedSelectedIndexCount < count) {
                if (indexes.indexOf((double) index) == -1) {
                    if (selectedIndexCount++ >= fromIndex) {
                        addedSelectedIndexCount++;
                        Object mappedValue = JsUtils.jsni(mapper, "call",
                                mapper, index);
                        if (mappedValue != null) {
                            result.add(mappedValue);
                        }
                    }
                }
                index++;
            }
        } else {
            int fromIndex = JSValidate.Integer.val(from, 0, 0);
            fromIndex = Math.min(fromIndex, indexes.length() - 1);
            int defaultTo = indexes.length() - 1;
            int toIndex = JSValidate.Integer.val(to, defaultTo, defaultTo);
            toIndex = Math.min(toIndex, indexes.length() - 1);

            for (int i = fromIndex; i <= toIndex; i++) {
                Object mappedValue = JsUtils.jsni(mapper, "call", mapper,
                        indexes.get(i));
                if (mappedValue != null) {
                    result.add(mappedValue);
                }
            }
        }
        return result;
    }

    @Override
    public JSArray<Object> deselected(JavaScriptObject mapper, Integer from,
            Integer to) {
        if (invertedSelection) {
            JSArray<Object> result = JS.createArray();
            mapper = SelectionUtil.verifyMapper(mapper);

            int fromIndex = JSValidate.Integer.val(from, 0, 0);
            fromIndex = Math.min(fromIndex, indexes.length() - 1);
            int defaultTo = indexes.length() - 1;
            int toIndex = JSValidate.Integer.val(to, defaultTo, defaultTo);
            toIndex = Math.min(toIndex, indexes.length() - 1);

            for (int i = fromIndex; i <= toIndex; i++) {
                Object mappedValue = JsUtils.jsni(mapper, "call", mapper,
                        indexes.get(i));
                if (mappedValue != null) {
                    result.add(mappedValue);
                }
            }
            return result;
        } else {
            return JS.createArray();
        }
    }

    @Override
    public int size() {
        return invertedSelection ? grid.getDataSource().size()
                - indexes.length() : indexes.length();
    }

    @Override
    public boolean select(int index, boolean skipOwnEvents) {
        if (invertedSelection) {
            return removeIndex(index, skipOwnEvents);
        } else {
            return addIndex(index, skipOwnEvents);
        }
    }

    @Override
    public boolean deselect(int index, boolean skipOwnEvents) {
        if (invertedSelection) {
            return addIndex(index, skipOwnEvents);
        } else {
            return removeIndex(index, skipOwnEvents);

        }
    }

    private boolean addIndex(int index, boolean skipOwnEvents) {
        if (index >= 0
                && (!dataSizeUpdated || index < grid.getDataSource().size())
                && indexes.indexOf((double) index) == -1) {
            indexes.add((double) index);

            skipOwnEvents = JSValidate.Boolean.val(skipOwnEvents, false, false);
            if (!skipOwnEvents) {
                grid.fireEvent(new SelectionEvent<Object>(grid, null, null,
                        false));
            }
            if (invertedSelection && size() == 0) {
                clear();
                return false;
            } else if (!invertedSelection && isChecked()) {
                selectAll();
                return false;
            }

            return true;
        }
        return false;
    }

    private boolean removeIndex(int index, boolean skipOwnEvents) {
        if (indexes.indexOf((double) index) != -1) {
            indexes.remove((double) index);
            skipOwnEvents = JSValidate.Boolean.val(skipOwnEvents, false, false);
            if (!skipOwnEvents) {
                grid.fireEvent(new SelectionEvent<Object>(grid, null, null,
                        false));
            }
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        reset();
    }

    @Override
    public void selectAll() {
        indexes.setLength(0);
        invertedSelection = true;
        grid.fireEvent(new SelectionEvent<Object>(grid, null, null, true));
    }

    @Override
    public boolean deselectAll() {
        indexes.setLength(0);
        invertedSelection = false;
        grid.fireEvent(new SelectAllEvent<Object>(this));
        return true;
    }

    @Override
    public IndexBasedSelectionMode getMode() {
        return invertedSelection ? IndexBasedSelectionMode.ALL
                : IndexBasedSelectionMode.MULTI;
    }

    @Override
    public void dataSizeUpdated(int newSize) {
        dataSizeUpdated = true;
        // If row indexes contain values that are out of bounds, remove
        // them.
        boolean changed = false;
        for (int i = 0; i < indexes.length(); i++) {
            if (indexes.get(i) >= newSize) {
                indexes.remove(indexes.get(i--));
                changed = true;
            }
        }
        if (changed) {
            grid.fireEvent(new SelectionEvent<Object>(grid, null, null, true));
        }
    }
}
