package com.vaadin.elements.grid.config;

import static com.vaadin.shared.ui.grid.GridStaticCellType.HTML;
import static com.vaadin.shared.ui.grid.GridStaticCellType.WIDGET;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.ui.SimplePanel;
import com.vaadin.client.widgets.Grid.StaticSection.StaticCell;
import com.vaadin.elements.common.js.JS;
import com.vaadin.elements.grid.GridElement;
import com.vaadin.elements.grid.table.GridColumn;
import com.vaadin.shared.util.SharedUtil;

/**
 * This class is a JsInterop wrapper for the JS object representing a grid
 * header/footer cell configuration
 */
@JsType(namespace = JS.NAMESPACE_API)
public class JSStaticCell {

    private final StaticCell cell;
    private Object content;
    private final GridElement gridElement;
    private final static String CONTENT_WRAPPER = "<span style='overflow: hidden;text-overflow: ellipsis;'>%CONTENT%</span>";

    public JSStaticCell(StaticCell staticCell, GridElement gridElement) {
        this.gridElement = gridElement;
        cell = staticCell;
        content = cell.getType() == HTML ? cell.getHtml()
                : cell.getType() == WIDGET ? cell.getWidget().getElement()
                        : !"".equals(cell.getText()) ? cell.getText() : null;
    }

    @JsProperty
    public Object getContent() {
        return content;
    }

    @JsProperty
    public void setContent(Object content) {
        this.content = content;
        contentChanged(content);
        cellChanged();
    }

    @JsProperty
    public int getColspan() {
        return cell.getColspan();
    }

    @JsProperty
    public void setColspan(int colspan) {
        cell.setColspan(colspan);
        cellChanged();
    }

    @JsProperty
    public String getClassName() {
        return cell.getStyleName();
    }

    @JsProperty
    public void setClassName(String className) {
        cell.setStyleName(className);
        cellChanged();
    }

    private void cellChanged() {
        gridElement.updateWidth();
        gridElement.getGrid().refreshStaticSection(cell);
    }

    private void contentChanged(Object content) {
        // "column" is non-null only if the given cell is on default header row
        GridColumn column = getColumnByDefaultHeaderCell();
        if (content == null) {
            contentCleared(column);
        } else if (JS.isPrimitiveType(content) || content instanceof Number) {
            applyStringContent(String.valueOf(content), column);
        } else if (JsUtils.isElement(content)) {
            applyElementContent((Element) content, column);
        }
    }

    private void contentCleared(GridColumn column) {
        if (column != null) {
            // Default header cell content is empty, use
            // column name as the header caption and cell html instead
            String name = column.getJsColumn().getName();
            name = name != null ? name : "";
            // Remove until last dot
            name = name.replaceFirst(".*\\.", "");
            // Remove certain characters used for separate words
            name = name.replaceAll("[_+,;:-]", " ");
            // Capitalize words
            name = SharedUtil.camelCaseToHumanFriendly(name);

            column.setHeaderCaption(name);
            cell.setHtml(CONTENT_WRAPPER.replace("%CONTENT%", name));
        } else {
            cell.setHtml(null);
        }
    }

    private void applyStringContent(String content, GridColumn column) {
        // Primitive content
        if (column != null) {
            column.setHeaderCaption(content);
        }
        cell.setHtml(CONTENT_WRAPPER.replace("%CONTENT%", content));
    }

    private void applyElementContent(Element content, GridColumn column) {
        if (column != null) {
            String name = column.getJsColumn().getName();
            if (name != null) {
                column.setHeaderCaption(name);
            } else {
                column.setHeaderCaption(String.valueOf(content));
            }
        }
        cell.setWidget(new SimplePanel(content) {
        });
    }

    private GridColumn getColumnByDefaultHeaderCell() {
        GridColumn result = null;
        for (GridColumn col : gridElement.getDataColumns()) {
            if (col.getDefaultHeaderCellReference() == this) {
                result = col;
                break;
            }
        }
        return result;
    }
}
