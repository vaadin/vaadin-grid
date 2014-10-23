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

import java.util.ArrayList;
import java.util.Random;

import com.google.gwt.user.client.Timer;
import com.vaadin.client.data.AbstractRemoteDataSource;
import com.vaadin.client.data.DataSource;

/**
 * 
 * @since
 * @author Vaadin Ltd
 */
public class UsingLazyData extends AbstractGridSample {
    private static final int containerSize = 10000;

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.tests.widgetset.client.grid.tutorials.AbstractGridSample#
     * createDataSource()
     */
    @Override
    protected DataSource<Task> createDataSource() {
        return new AbstractRemoteDataSource<Task>() {
            @Override
            protected void requestRows(final int firstRowIndex,
                    final int numberOfRows) {
                // Simulate a remote database by generating items with a delay
                new Timer() {
                    @Override
                    public void run() {
                        ArrayList<Task> list = new ArrayList<Task>(numberOfRows);

                        for (int i = 0; i < numberOfRows; i++) {
                            int index = i + firstRowIndex;
                            Task task = Task
                                    .createRandomTask(new Random(index));
                            task.setId(index);
                            list.add(task);
                        }

                        setRowData(firstRowIndex, list);
                    }
                }.schedule(1000);
            }

            @Override
            public Object getRowKey(Task row) {
                // Help Grid identify distinct Task instances representing the
                // same data
                return Integer.valueOf(row.getId());
            }

            @Override
            public int size() {
                return containerSize;
            }
        };
    }
}
