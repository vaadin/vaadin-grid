package com.vaadin.components.grid.selection;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.user.client.DOM;
import com.vaadin.client.renderers.Renderer;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.client.widget.grid.selection.MultiSelectionRenderer;
import com.vaadin.client.widget.grid.selection.SelectionModelMulti;
import com.vaadin.client.widgets.Grid;

public abstract class IndexBasedSelectionModelMultiAbstract extends
        SelectionModelMulti<Object> implements IndexBasedSelectionModel {

    private Renderer<Boolean> renderer;
    protected Grid<Object> grid;
    private boolean allowSelection = true;

    @Override
    public void setGrid(Grid<Object> grid) {
        super.setGrid(grid);
        this.grid = grid;
        this.renderer = new MultiSelectionRenderer<Object>(grid) {
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
}
