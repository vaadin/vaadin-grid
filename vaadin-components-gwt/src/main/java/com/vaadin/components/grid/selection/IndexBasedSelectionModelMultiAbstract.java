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

    @Override
    public void setGrid(Grid<Object> grid) {
        super.setGrid(grid);
        this.renderer = new MultiSelectionRenderer(grid) {
            @Override
            public void init(RendererCellReference cell) {
                InputElement checkbox = Document.get()
                        .createCheckInputElement();
                LabelElement label = Document.get().createLabelElement();
                checkbox.addClassName("v-grid style-scope");
                checkbox.setId(DOM.createUniqueId());
                label.setHtmlFor(checkbox.getId());
                cell.getElement().removeAllChildren();
                cell.getElement().appendChild(checkbox);
                cell.getElement().appendChild(label);
            }
        };
    }

    public Renderer<Boolean> getSelectionColumnRenderer() {
        return renderer;
    }
}
