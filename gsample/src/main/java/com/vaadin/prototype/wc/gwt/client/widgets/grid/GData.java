package com.vaadin.prototype.wc.gwt.client.widgets.grid;

import static com.google.gwt.query.client.GQuery.console;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQ;
import com.google.gwt.query.client.builders.JsonBuilder;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.user.client.Command;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;

public interface GData extends JsonBuilder {
    public interface GColumn extends JsonBuilder {
        String name();
        GColumn setName(String s);
        String type();
        GColumn setType(String s);
        String format();
        GColumn setFormat(String s);
    }

    List<GColumn> columns();
    GData setColumns(List<GColumn> l);
    List<JsArrayMixed> values();
    GData setValues(List<JsArrayMixed> l);

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
            cols.add(GQ.create(GColumn.class).setName("Name").setType("string"));
            cols.add(GQ.create(GColumn.class).setName("Surname").setType("string"));
            cols.add(GQ.create(GColumn.class).setName("Action").setType("string"));
            cols.add(GQ.create(GColumn.class).setName("Target").setType("string"));

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
    }


}
