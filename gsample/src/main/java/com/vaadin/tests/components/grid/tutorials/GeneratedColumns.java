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
import com.vaadin.data.Item;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;

/**
 * 
 * @since
 * @author Vaadin Ltd
 */
public class GeneratedColumns extends AbstractGridSample {

    private static final String SUMMARY_COL = "summary";

    public GeneratedColumns() {
        grid.getColumn(NAME_COL).setVisible(false);
        grid.getColumn(OWNER_COL).setVisible(false);

        grid.getColumn(SUMMARY_COL).setWidth(200);
    }

    @Override
    protected Indexed createDataSource() {
        GeneratedPropertyContainer container = new GeneratedPropertyContainer(
                super.createDataSource());

        container.addGeneratedProperty(SUMMARY_COL,
                new PropertyValueGenerator<String>() {
                    @Override
                    public String getValue(Item item, Object itemId,
                            Object propertyId) {
                        return item.getItemProperty(NAME_COL).getValue()
                                + " by "
                                + item.getItemProperty(OWNER_COL).getValue();
                    }

                    @Override
                    public Class<String> getType() {
                        return String.class;
                    }
                });

        container.addGeneratedProperty(PRIORITY_COL,
                new PropertyValueGenerator<String>() {

                    @Override
                    public String getValue(Item item, Object itemId,
                            Object propertyId) {
                        Integer value = (Integer) item.getItemProperty(
                                propertyId).getValue();
                        switch (value.intValue()) {
                        case 1:
                            return "Low";
                        case 2:
                            return "Medium";
                        case 3:
                            return "High";
                        default:
                            return "???";
                        }
                    }

                    @Override
                    public Class<String> getType() {
                        return String.class;
                    }
                });

        return container;
    }
}
