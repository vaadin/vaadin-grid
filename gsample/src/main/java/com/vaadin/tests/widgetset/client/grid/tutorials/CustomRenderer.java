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

import java.util.Arrays;
import java.util.Collection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.vaadin.client.ui.grid.Cell;
import com.vaadin.client.ui.grid.FlyweightCell;
import com.vaadin.client.ui.grid.GridColumn;
import com.vaadin.client.ui.grid.Renderer;
import com.vaadin.client.ui.grid.renderers.ComplexRenderer;
import com.vaadin.client.ui.grid.renderers.WidgetRenderer;

/**
 * 
 * @since
 * @author Vaadin Ltd
 */
public class CustomRenderer extends AbstractGridSample {
    @Override
    protected void initColumns() {
        super.initColumns();

        Renderer<Boolean> simpleCheckboxRenderer = new Renderer<Boolean>() {
            @Override
            public void render(FlyweightCell cell, Boolean data) {
                String html = "<input type='checkbox' disabled "
                        + (data == Boolean.TRUE ? "checked " : "") + ">";
                cell.getElement().setInnerHTML(html);
            }
        };

        Renderer<Boolean> complexCheckboxRenderer = new ComplexRenderer<Boolean>() {
            @Override
            public void init(FlyweightCell cell) {
                InputElement element = Document.get().createCheckInputElement();
                cell.getElement().appendChild(element);
            }

            @Override
            public void render(FlyweightCell cell, Boolean data) {
                InputElement element = cell.getElement().getChild(0).cast();
                element.setChecked(Boolean.TRUE == data);
            }

            @Override
            public Collection<String> getConsumedEvents() {
                return Arrays.asList(BrowserEvents.CHANGE);
            }

            @Override
            public boolean onBrowserEvent(Cell cell, NativeEvent event) {
                // One-liner ftw
                grid.getDataSource()
                        .getRow(cell.getRow())
                        .setApproved(
                                event.getEventTarget().<InputElement> cast()
                                        .isChecked());

                return true;
            }
        };

        WidgetRenderer<Boolean, CheckBox> widgetCheckboxRenderer = new WidgetRenderer<Boolean, CheckBox>() {
            private final ValueChangeHandler<Boolean> handler = new ValueChangeHandler<Boolean>() {
                @Override
                public void onValueChange(ValueChangeEvent<Boolean> event) {
                    CheckBox widget = (CheckBox) event.getSource();
                    Integer rowIndex = (Integer) widget.getLayoutData();
                    if (rowIndex == null) {
                        return;
                    }

                    Task task = grid.getDataSource()
                            .getRow(rowIndex.intValue());
                    task.setApproved(event.getValue().booleanValue());

                    // XXX How do I make other columnns in the row update?
                }
            };

            @Override
            public CheckBox createWidget() {
                CheckBox widget = GWT.create(CheckBox.class);
                widget.addValueChangeHandler(handler);
                return widget;
            }

            @Override
            public void render(FlyweightCell cell, Boolean data, CheckBox widget) {
                widget.setValue(data);
                widget.setLayoutData(Integer.valueOf(cell.getRow()));
            }
        };

        GridColumn<Boolean, Task> approvedColumn1 = new GridColumn<Boolean, Task>(
                simpleCheckboxRenderer) {
            @Override
            public Boolean getValue(Task row) {
                return Boolean.valueOf(row.isApproved());
            }

        };
        GridColumn<Boolean, Task> approvedColumn2 = new GridColumn<Boolean, Task>(
                complexCheckboxRenderer) {
            @Override
            public Boolean getValue(Task row) {
                return Boolean.valueOf(row.isApproved());
            }
        };
        GridColumn<Boolean, Task> approvedColumn3 = new GridColumn<Boolean, Task>(
                widgetCheckboxRenderer) {
            @Override
            public Boolean getValue(Task row) {
                return Boolean.valueOf(row.isApproved());
            }
        };

        grid.addColumn(approvedColumn1);
        grid.addColumn(approvedColumn2);
        grid.addColumn(approvedColumn3);

        approvedColumn1.setHeader("Approved 1");
        approvedColumn2.setHeader("Approved 2");
        approvedColumn3.setHeader("Approved 3");

    }
}
