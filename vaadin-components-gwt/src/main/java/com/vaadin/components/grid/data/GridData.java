package com.vaadin.components.grid.data;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.query.client.builders.JsonBuilder;

public interface GridData extends JsonBuilder {

    public interface GridAjaxConf extends JsonBuilder {

        public interface GridAjaxResponse extends JsonBuilder {
            public interface GridAjaxColumn extends JsonBuilder {
                String name();
                String title();
            }

            int error();
            int start();
            int rows();
            int size();
            JsArray<JavaScriptObject> data();
            List<GridAjaxColumn>columns();
        }

        GridAjaxConf setUrl(String url);
        String getUrl();
    }

    public interface GridColumn extends JsonBuilder {
        public interface GridHeader extends JsonBuilder {
            public static enum Format {
                TEXT, HTML, WIDGET
            }

            Object content();
            GridHeader setContent(Object content);
            Format format();
            GridHeader setFormat(Format format);
            int colSpan();
            GridHeader setColSpan(int colSpans);
        }

        Object content();
        GridColumn setContent(Object o);
        String type();
        GridColumn setType(String s);
        JavaScriptObject renderer();
        GridColumn setRenderer(JavaScriptObject o);
        Object value();
        GridColumn setValue(Object o);
        List<GridHeader> headerData();
        GridColumn setHeaderData(List<GridHeader> headers);
        String template();
        GridColumn setTemplate(String template);
    }

    List<GridColumn> columns();

    GridData setColumns(List<GridColumn> l);

    List<JsArrayMixed> values();

    GridData setValues(List<JsArrayMixed> l);

    int defaultRowIndex = 0;
}
