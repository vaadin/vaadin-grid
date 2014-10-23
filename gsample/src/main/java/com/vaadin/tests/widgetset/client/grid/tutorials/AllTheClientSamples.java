/*
 * Copyright 2000-2014 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.tests.widgetset.client.grid.tutorials;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class AllTheClientSamples extends FlowPanel {
    public AllTheClientSamples() {
        addSample(new UsingData(), "Using a client-side DataSource");
        addSample(new UsingLazyData(),
                "Using a lazy loading client-side DataSource");
        addSample(new Formatting(), "Formatting data in a grid");
        addSample(new DynamicColumns(), "Dynamic / generated grid columns");
        addSample(new CustomRenderer(), "Creating a custom renderer (simple)");
        addSample(new InlineDataEditing(), "Inline data editing");
        addSample(new MasterDetailEditing(), "Master details view");
    }

    private void addSample(Widget widget, String label) {
        if (false && getWidgetCount() != 0) {
            return;
        }
        add(new Label(label));
        add(widget);
    }
}
