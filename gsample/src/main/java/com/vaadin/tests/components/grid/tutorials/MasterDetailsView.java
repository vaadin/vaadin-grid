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

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.tests.widgetset.client.grid.tutorials.Task;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Slider;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.Grid;
import com.vaadin.ui.components.grid.Grid.SelectionMode;
import com.vaadin.ui.components.grid.selection.SelectionChangeEvent;
import com.vaadin.ui.components.grid.selection.SelectionChangeListener;

public class MasterDetailsView extends VerticalLayout {
    private final Grid grid = new Grid(new FormattingData().createDataSource());

    private final BeanFieldGroup<Task> fields = new BeanFieldGroup<Task>(
            Task.class);
    private final Button submitButton = new Button("Save",
            new Button.ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                    try {
                        fields.commit();
                    } catch (CommitException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

    public MasterDetailsView() {
        addComponent(grid);
        grid.getColumn("id").setVisible(false);
        grid.setWidth("650px");

        grid.setSelectionMode(SelectionMode.SINGLE);
        grid.addSelectionChangeListener(new SelectionChangeListener() {
            @Override
            public void selectionChange(SelectionChangeEvent event) {
                Object selectedRow = grid.getSelectedRow();
                if (selectedRow == null) {
                    submitButton.setEnabled(false);
                    fields.setEnabled(false);
                    // Is this actually the best way to clear all fields?
                    fields.setItemDataSource(new Task());
                } else {
                    submitButton.setEnabled(true);
                    fields.setEnabled(true);
                    fields.setItemDataSource(grid.getContainerDatasource()
                            .getItem(selectedRow));
                }
            }
        });

        // Silly that I have to bind to an item for it to find the properties
        fields.setItemDataSource(new Task());
        Slider progressSlider = new Slider(0d, 1d, 3);
        progressSlider.setCaption("progress");
        progressSlider.setWidth("200px");
        fields.bind(progressSlider, "progress");

        for (Object propertyId : fields.getUnboundPropertyIds()) {
            fields.buildAndBind(propertyId);
        }
        for (Object propertyId : fields.getBoundPropertyIds()) {
            addComponent(fields.getField(propertyId));
        }

        fields.setEnabled(false);

        addComponent(submitButton);
        submitButton.setEnabled(false);
    }
}
