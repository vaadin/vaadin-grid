package org.vaadin.demo.client;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.vaadin.prototype.wc.gwt.client.components.VaadinGridWidget;
import com.vaadin.prototype.wc.gwt.client.components.VaadinProgressBarWidget;
import com.vaadin.prototype.wc.gwt.client.components.VaadinSliderWidget;

/**
 * Before running this code you should install vaadin bower components
 * in your public folder:
 *  <pre>
       $ cd src/.../public
       $ bower install vaadin-grid vaadin-slider vaadin-progress-bar
 *  </pre>
 *  
 *  Your html file should look like:
 *  <pre>
        <!doctype html>
        <html>
          <head>
            <script src="demo/bower_components/platform/platform.js"></script>
            <script src="demo/demo.nocache.js"></script>
          </head>
          <body>
          </body>
        </html>
 *  </pre>
 */
public class Demo implements EntryPoint {
  public void onModuleLoad() {
    
    final VaadinSliderWidget slider = new VaadinSliderWidget();
    slider.getElement().setAttribute("theme", "valo");
    RootPanel.get().add(slider);
    
    final Label value = new Label();
    RootPanel.get().add(value);

    slider.addChangeHandler(new EventListener() {
        public void onBrowserEvent(Event event) {
            value.setText("" + slider.value());
        }
    });
    
    final VaadinProgressBarWidget progress = new VaadinProgressBarWidget();
    progress.getElement().setAttribute("theme", "valo");
    RootPanel.get().add(progress);
    
    final VaadinGridWidget grid = new VaadinGridWidget();
    grid.getElement().setAttribute("theme", "valo");
    RootPanel.get().add(grid);
    
    $(grid).delay(100, new Function(){
        public void f() {
            // FIXME:  why do we need to delay this.
            String data = "[{\"name\":\"Grid\", \"value\":1000, \"progress\":0.1}]";
            grid.dataSource(JsUtils.parseJSON(data));
            grid.redraw();
        }
    });

    grid.addEventHandler("select", new EventListener() {
        public void onBrowserEvent(Event event) {
            progress.value(grid.selectedRow() / 10);
        }
    });
  }
}
