package com.vaadin.components.grid.head;

import static com.google.gwt.query.client.GQuery.$;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQuery;
import com.vaadin.components.grid.GridComponent;
import com.vaadin.components.grid.config.JS;
import com.vaadin.components.grid.config.JSArray;
import com.vaadin.components.grid.config.JSColumn;
import com.vaadin.components.grid.config.JSHeaderCell;
import com.vaadin.components.grid.config.JSHeaderCell.Format;

/**
 * This class represents a grid header configuration based on a DOM structure.
 */
public class GridDomTableHead extends GridJsTableHead {

    private String lastHeaders = null;

    private GQuery lightDom;
    private GQuery $rows;

    public GridDomTableHead(Element tableElement, GridComponent grid) {
        super(grid);
        setLightDom(tableElement.cast());
    }

    public void setLightDom(JavaScriptObject tableElement) {
        if (lightDom == null || lightDom.isEmpty()
                || lightDom.get(0) != tableElement) {
            lightDom = $(tableElement);
            loadHeaders();
        }
    }

    public JSArray<JSColumn> loadHeaders() {
        $rows = lightDom.find("thead tr");
        String txt;
        if ($rows.isEmpty() || (txt = $rows.toString()).equals(lastHeaders)) {
            return jsColumns;
        }
        lastHeaders = txt;

        jsColumns.setLength(0);

        Map<JSColumn, JSArray<JSHeaderCell>> contentsMap =
                new HashMap<JSColumn, JSArray<JSHeaderCell>>();

        int defaultRow = 0;
        for (int i = 0; i < $rows.size(); i++) {
            GQuery $ths = $rows.eq(i).children("th");
            while (jsColumns.size() < $ths.size()) {
                JSColumn column = JS.createJsType(JSColumn.class);
                contentsMap.put(column, JS.<JSHeaderCell> createArray());
                jsColumns.add(column);
            }
            // The default row should be the last row in the header which has
            // the largest number of th elements
            if ($ths.size() == jsColumns.size()) {
                defaultRow = i;
            }
        }

        for (int i = 0; i < $rows.size(); i++) {
            GQuery $ths = $rows.eq(i).children("th");
            for (int j = 0, colOffset = 0; j + colOffset < $ths.size(); j++) {
                JSColumn column = jsColumns.get(j + colOffset);
                JSHeaderCell header = JS.createJsType(JSHeaderCell.class);
                GQuery $th = $ths.eq(j);
                column.setValue($th.attr("name"));
                int colSpan = 1;
                String colString = $th.attr("colspan");
                if (!colString.isEmpty()) {
                    colSpan = Integer.parseInt(colString);
                    colOffset += colSpan - 1;
                }
                header.setColSpan(colSpan);
                header.setContent($th.html()).setFormat(Format.HTML.name());
                contentsMap.get(column).add(header);
            }
        }

        GQuery $templateRow = lightDom.find("tr[template] td");
        Iterator<JSColumn> iterator = contentsMap.keySet().iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            JSColumn column = iterator.next();
            column.setHeaderData(contentsMap.get(column));
            if (i < $templateRow.size()) {
                String html = $templateRow.eq(i).html();
                column.setTemplate(html);
            }
        }

        updateGridColumns(defaultRow);
        return jsColumns;
    }

}
