package com.vaadin.prototype.wc.gwt.client;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.$$;
import static com.google.gwt.query.client.GQuery.console;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.vaadin.prototype.wc.gwt.client.components.PaperSliderWidget;
import com.vaadin.prototype.wc.gwt.client.components.VaadinGridWidget;
import com.vaadin.prototype.wc.gwt.client.components.VaadinProgressBarWidget;
import com.vaadin.prototype.wc.gwt.client.components.VaadinSliderWidget;

/**
 */
public class DemoVaadinComponents implements EntryPoint {

    public void onModuleLoad() {

        final VaadinSliderWidget slider = new VaadinSliderWidget();
        slider.getElement().setAttribute("theme", "valo");
        final PaperSliderWidget pslider = new PaperSliderWidget();
        RootPanel.get().add(slider);
        RootPanel.get().add(pslider);
        
        slider.addChangeHandler(new EventListener() {
            public void onBrowserEvent(Event event) {
                pslider.value(slider.value());
            }
        });
        
        final VaadinProgressBarWidget progress = new VaadinProgressBarWidget();
        progress.getElement().setAttribute("theme", "valo");

        RootPanel.get().add(progress);
        
        final VaadinGridWidget grid = new VaadinGridWidget();
        RootPanel.get().add(grid);
        
        grid.getElement().setAttribute("theme", "valo");
//        grid.getElement().setAttribute("type", "ajax");
        
        // FIXME:  why do we need to delay this.
        $(grid).delay(100, new Function(){
            public void f() {
                String data = "[{'name':'Grid', 'value':1000, 'progress':0.1}]";
                Properties p = $$("data");
                console.log(p, JsUtils.parseJSON(data.replace("'", "\"")));
                grid.dataSource(JsUtils.parseJSON(data.replace("'", "\"")));
            }
        }).delay(100, new Function(){
            public void f() {
//                grid.redraw();
            }
        });

        grid.addEventHandler("select", new EventListener() {
            public void onBrowserEvent(Event event) {
                progress.value(grid.selectedRow() / 10);
            }
        });
    }
}
