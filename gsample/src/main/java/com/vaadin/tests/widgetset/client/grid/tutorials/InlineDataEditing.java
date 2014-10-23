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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ui.grid.EditorRow;
import com.vaadin.client.ui.grid.EditorRowHandler;
import com.vaadin.client.ui.grid.GridColumn;

/**
 * 
 * @since
 * @author Vaadin Ltd
 */
public class InlineDataEditing extends AbstractGridSample {
    public InlineDataEditing() {
        EditorRow<Task> editorRow = grid.getEditorRow();
        editorRow.setHandler(new EditorRowHandler<Task>() {
            private Map<GridColumn<?, Task>, Widget> widgets = new HashMap<GridColumn<?, Task>, Widget>();

            @Override
            public void bind(EditorRowHandler.EditorRowRequest request) {
                // XXX Would be convenient with the data object available here
                // XXX Would be convenient with a grid reference here
                Task data = grid.getDataSource().getRow(request.getRowIndex());

                // XXX Would be convenient if request could give me the columns
                // XXX Would be convenient if request could give me the current
                // widget for a column
                for (Entry<GridColumn<?, Task>, Widget> entry : widgets
                        .entrySet()) {
                    GridColumn<?, Task> column = entry.getKey();
                    Widget widget = entry.getValue();

                    // XXX This could be shorthand in the request
                    // public <T> T getValue(GridColumn<T, ?> column);
                    Object value = column.getValue(data);

                    // XXX HasValueWidgetEditorRowHandler helper?
                    ((HasValue<Object>) widget).setValue(value);
                }

                // XXX Why do I need to do this?
                request.invokeCallback();
            }

            @Override
            public void cancel(EditorRowHandler.EditorRowRequest request) {
                // XXX What should I do here? Javadoc gives no hints.

                // XXX Why????
                request.invokeCallback();
            }

            @Override
            public void commit(EditorRowHandler.EditorRowRequest request) {
                Task data = grid.getDataSource().getRow(request.getRowIndex());
                for (Entry<GridColumn<?, Task>, Widget> entry : widgets
                        .entrySet()) {
                    GridColumn<?, Task> column = entry.getKey();
                    HasValue<?> widget = (HasValue<?>) entry.getValue();

                    // I guess it's my own fault I don't have the column
                    // references here
                    if (column == grid.getColumn(1)) {
                        data.setName((String) widget.getValue());
                    } else if (column == grid.getColumn(2)) {
                        data.setOwner((String) widget.getValue());
                    } else if (column == grid.getColumn(3)) {
                        data.setPriority(Integer.valueOf((String) widget
                                .getValue()));
                    }
                }

                request.invokeCallback();
            }

            @Override
            public void discard(EditorRowHandler.EditorRowRequest request) {
                // XXX What should I do here? Javadoc gives no hints.

                // XXX Why????
                request.invokeCallback();
            }

            @Override
            public Widget getWidget(GridColumn<?, Task> column) {
                // Not editable (doh)
                if (column == grid.getColumn(3)) {
                    return null;
                }

                Widget widget = widgets.get(column);
                if (widget == null) {
                    widget = new TextBox();
                    widgets.put(column, widget);
                }
                return widget;
            }
        });

        editorRow.setEnabled(true);
    }
}
