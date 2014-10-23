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

import com.vaadin.client.ui.grid.GridColumn;
import com.vaadin.client.ui.grid.renderers.HtmlRenderer;
import com.vaadin.client.ui.grid.renderers.TextRenderer;

/**
 * 
 * @since
 * @author Vaadin Ltd
 */
public class DynamicColumns extends AbstractGridSample {
    @Override
    protected void initColumns() {
        GridColumn<String, Task> descriptionColumn = new GridColumn<String, Task>(
                new TextRenderer()) {
            @Override
            public String getValue(Task row) {
                return row.getName() + " by " + row.getOwner();
            }
        };
        descriptionColumn.setWidth(250);

        GridColumn<String, Task> priorityColumn = new GridColumn<String, Task>(
                new HtmlRenderer()) {
            @Override
            public String getValue(Task row) {
                switch (row.getPriority()) {
                case 1:
                    return "Low";
                case 2:
                    return "<b>Medium</b>";
                case 3:
                    return "<b style='color: red'>High</b>";
                default:
                    return "Unknown";
                }
            }
        };

        grid.addColumn(descriptionColumn);
        grid.addColumn(priorityColumn);

        descriptionColumn.setHeader("Description");
        priorityColumn.setHeader("Priority");

    }
}
