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
package com.vaadin.tests.components.grid.tutorials;

import com.vaadin.data.Container.Indexed;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.tests.widgetset.client.grid.tutorials.Task;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.components.grid.Grid;

/**
 * 
 * @since
 * @author Vaadin Ltd
 */
public class AbstractGridSample extends CustomComponent {
    public static final String APPROVED_COL = "approved";
    public static final String ID_COL = "id";
    public static final String NAME_COL = "name";
    public static final String OWNER_COL = "owner";
    public static final String PRIORITY_COL = "priority";
    public static final String PROGRESS_COL = "progress";
    public static final String VALUE_COL = "value";

    protected final Grid grid = new Grid(createDataSource());

    public AbstractGridSample() {
        grid.getColumn(ID_COL).setVisible(false);
        grid.setWidth("650px");
        setCompositionRoot(grid);
    }

    /**
     * @since
     * @return
     */
    protected Indexed createDataSource() {
        BeanItemContainer<Task> container = new BeanItemContainer<Task>(
                Task.class, Task.createRandomTasks(100));

        return container;
    }
}
