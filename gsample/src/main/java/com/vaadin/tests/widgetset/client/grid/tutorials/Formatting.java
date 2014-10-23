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

import com.google.gwt.i18n.client.NumberFormat;
import com.vaadin.client.ui.grid.GridColumn;
import com.vaadin.client.ui.grid.renderers.NumberRenderer;
import com.vaadin.client.ui.grid.renderers.ProgressBarRenderer;

/**
 * 
 * @since
 * @author Vaadin Ltd
 */
public class Formatting extends AbstractGridSample {

    @Override
    protected void initColumns() {
        super.initColumns();

        NumberRenderer valueRenderer = new NumberRenderer();
        valueRenderer.setFormat(NumberFormat.getCurrencyFormat("EUR"));
        GridColumn<Number, Task> valueColumn = new GridColumn<Number, Task>(
                valueRenderer) {
            @Override
            public Number getValue(Task row) {
                return Integer.valueOf(row.getValue());
            }
        };

        GridColumn<Double, Task> progressColumn = new GridColumn<Double, Task>(
                new ProgressBarRenderer()) {
            @Override
            public Double getValue(Task row) {
                return Double.valueOf(row.getProgress());
            }
        };

        grid.addColumn(valueColumn);
        grid.addColumn(progressColumn);

        // Poor kitten!
        valueColumn.setHeader("Value");
        progressColumn.setHeader("Progress");

    }
}
