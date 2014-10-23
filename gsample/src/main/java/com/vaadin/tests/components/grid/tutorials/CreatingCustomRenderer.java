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

import com.vaadin.data.Item;
import com.vaadin.ui.Notification;

/**
 * 
 * @since
 * @author Vaadin Ltd
 */
public class CreatingCustomRenderer extends AbstractGridSample {
    public CreatingCustomRenderer() {
        grid.getColumn(APPROVED_COL).setRenderer(new CustomRenderer() {
            @Override
            protected void onChange(Object itemId, boolean checked) {
                Item item = grid.getContainerDatasource().getItem(itemId);
                item.getItemProperty(APPROVED_COL).setValue(
                        Boolean.valueOf(checked));

                Notification.show("Event for "
                        + item.getItemProperty(NAME_COL).getValue());
            }
        });
    }
}
