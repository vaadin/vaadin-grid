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

import com.google.gwt.user.client.ui.SimplePanel;
import com.vaadin.client.data.DataSource;
import com.vaadin.client.ui.grid.Grid;
import com.vaadin.client.ui.grid.GridColumn;
import com.vaadin.client.ui.grid.datasources.ListDataSource;
import com.vaadin.client.ui.grid.renderers.TextRenderer;

/**
 * 
 * @since
 * @author Vaadin Ltd
 */
public abstract class AbstractGridSample extends SimplePanel {

    protected Grid<Task> grid = new Grid<Task>();

    public AbstractGridSample() {
        grid.setDataSource(createDataSource());
        initColumns();

        add(grid);
    }

    protected void initColumns() {
        // XXX Feels kind of boilerplatey to always define a TextRenderer
        GridColumn<String, Task> nameColumn = new GridColumn<String, Task>(
                new TextRenderer()) {
            @Override
            public String getValue(Task row) {
                return row.getName();
            }
        };
        nameColumn.setHeader("Task");

        GridColumn<String, Task> ownerColumn = new GridColumn<String, Task>(
                new TextRenderer()) {
            @Override
            public String getValue(Task row) {
                return row.getOwner();
            }
        };
        ownerColumn.setHeader("Owner");

        GridColumn<String, Task> priorityColumn = new GridColumn<String, Task>(
                new TextRenderer()) {
            @Override
            public String getValue(Task row) {
                return Integer.toString(row.getPriority());
            }
        };
        priorityColumn.setHeader("Priority");

        grid.addColumns(nameColumn, ownerColumn, priorityColumn);
    }

    protected DataSource<Task> createDataSource() {
        ListDataSource<Task> dataSource = new ListDataSource<Task>(
                Task.createRandomTasks(200));
        return dataSource;
    }

}