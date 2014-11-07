package com.vaadin.prototype.wc.gwt.client.widgets.grid;

import static com.google.gwt.query.client.GQuery.console;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQ;
import com.google.gwt.query.client.builders.JsonBuilder;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GColumn.GHeader;

public interface GData extends JsonBuilder {
    
    public interface GResponse extends JsonBuilder {
        public interface GCfg extends JsonBuilder {
            String name();
            String title();
        }
        int error();
        int start();
        int rows();
        int size();
        JsArray<JavaScriptObject> data();
        List<GCfg>columns();
    }
    
    public interface GAjax extends JsonBuilder {
        GAjax setUrl(String url);
        String getUrl();
    }
    
    public interface GColumn extends JsonBuilder {

        public interface GHeader extends JsonBuilder {
            public static enum Format {
                TEXT, HTML, WIDGET
            }

            Object content();
            GHeader setContent(Object content);
            Format format();
            GHeader setFormat(Format format);
            int colSpan();
            GHeader setColSpan(int colSpans);
        }

        String type();
        GColumn setType(String s);
        JavaScriptObject renderer();
        GColumn setRenderer(JavaScriptObject o);
        Object value();
        List<GHeader> headerData();
        GColumn setHeaderData(List<GHeader> headers);
        String template();
        GColumn setTemplate(String template);
        String name();
        GColumn setName(String name);
    }

    List<GColumn> columns();

    GData setColumns(List<GColumn> l);

    List<JsArrayMixed> values();

    GData setValues(List<JsArrayMixed> l);

    int defaultRowIndex = 0;

    public static class MockData {
        private static String[] actions = { "Fix", "Implement", "Disable",
                "Activate", "Design", "Export", "Import", "Produce", "Invent",
                "Establish", "Feed", "Launch", "Deliver", "Polish" };
        private static String[] targets = { "Grid", "world peace",
                "teleportation", "Vaadin", "the dog", "the weather", "soup",
                "results" };
        private static String[] forenames = { "Teemu", "Johannes", "Patrik",
                "John", "Henrik", "Leif", "Artur", "Joonas" };
        private static String[] surnames = { "Suo-Anttila", "Dahlström",
                "Lindström", "Ahlroos", "Paul", "Åstrand", "Signell",
                "Lehtinen" };

        private static String pick(Random random, String[] values) {
            return values[random.nextInt(values.length)];
        }

        public static GData createRandomData(Random random) {

            GData ret = GQ.create(GData.class);
            List<GColumn> cols = new ArrayList<GColumn>();
            List<GHeader> header = new ArrayList<GHeader>();
            header.add(GQ.create(GHeader.class).setColSpan(1)
                    .setContent("Name").setFormat(GHeader.Format.TEXT));
            cols.add(GQ.create(GColumn.class).setHeaderData(header)
                    .setType("string"));
            header = new ArrayList<GHeader>();
            header.add(GQ.create(GHeader.class).setColSpan(1)
                    .setContent("Surname").setFormat(GHeader.Format.TEXT));
            cols.add(GQ.create(GColumn.class).setHeaderData(header)
                    .setType("string"));
            header = new ArrayList<GHeader>();
            header.add(GQ.create(GHeader.class).setColSpan(1)
                    .setContent("Action").setFormat(GHeader.Format.TEXT));
            cols.add(GQ.create(GColumn.class).setHeaderData(header)
                    .setType("string"));
            header = new ArrayList<GHeader>();
            header.add(GQ.create(GHeader.class).setColSpan(1)
                    .setContent("Target").setFormat(GHeader.Format.TEXT));
            cols.add(GQ.create(GColumn.class).setHeaderData(header)
                    .setType("string"));

            List<JsArrayMixed> vals = new ArrayList<JsArrayMixed>();
            for (int i = 0, l = random.nextInt(100) + 300; i < l; i++) {
                JsArrayMixed s = JsArrayMixed.createArray().cast();
                s.push(pick(random, forenames));
                s.push(pick(random, surnames));
                s.push(pick(random, actions));
                s.push(pick(random, targets));
                vals.add(s);
            }

            ret.setColumns(cols).setValues(vals);
            return ret;
        }

        public static void loadExternalDataSource(HTMLElement elm, Function f) {
            final String dsource = elm.getAttribute("dataSource");
            if (dsource != null) {
                Ajax.get(dsource).done(f).fail(new Function() {
                    @Override
                    public void f() {
                        console.log("Error loading data: " + dsource);
                    }
                });
            }
        }
        
        public static GData parseJso(JavaScriptObject o) {
            GData r = GQ.create(GData.class);
            if (JsUtils.isArray(o)) {
                r.set("values", o);
            } else {
                r.load(o);
            }
            return r;
        }
    }
}
