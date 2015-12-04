package com.vaadin.elements.grid.config;

import static com.vaadin.shared.ui.grid.GridStaticCellType.HTML;
import static com.vaadin.shared.ui.grid.GridStaticCellType.WIDGET;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import com.vaadin.client.widgets.Grid.StaticSection.StaticCell;
import com.vaadin.elements.grid.table.GridStaticSection;

/**
 * This class is a JsInterop wrapper for the JS object representing a grid
 * header/footer cell configuration
 */
@JsType()
public class JSStaticCell {

    private GridStaticSection section;
    private StaticCell cell;
    private Object content;

    public JSStaticCell(GridStaticSection staticSection, StaticCell staticCell) {
        section = staticSection;
        cell = staticCell;
        content = cell.getType() == HTML ? cell.getHtml()
                : cell.getType() == WIDGET ? cell.getWidget().getElement()
                        : !"".equals(cell.getText()) ? cell.getText() : null;
    }

    @JsProperty public Object getContent() {
        return content;
    }

    @JsProperty public void setContent(Object content) {
        this.content = content;
        section.contentChanged(content, cell);
        section.cellChanged(cell);
    }

    @JsProperty public int getColspan() {
        return cell.getColspan();
    }

    @JsProperty public void setColspan(int colspan) {
        cell.setColspan(colspan);
        section.cellChanged(cell);
    }

    @JsProperty public String getClassName() {
        return cell.getStyleName();
    }

    @JsProperty public void setClassName(String className) {
        cell.setStyleName(className);
        section.cellChanged(cell);
    }
}
