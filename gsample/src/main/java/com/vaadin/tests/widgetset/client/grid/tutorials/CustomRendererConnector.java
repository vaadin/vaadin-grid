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

import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.vaadin.client.ui.grid.Cell;
import com.vaadin.client.ui.grid.FlyweightCell;
import com.vaadin.client.ui.grid.Renderer;
import com.vaadin.client.ui.grid.renderers.AbstractRendererConnector;
import com.vaadin.client.ui.grid.renderers.ComplexRenderer;
import com.vaadin.shared.ui.Connect;

@Connect(com.vaadin.tests.components.grid.tutorials.CustomRenderer.class)
public class CustomRendererConnector extends AbstractRendererConnector<Boolean> {
    @Override
    protected Renderer<Boolean> createRenderer() {
        return new ComplexRenderer<Boolean>() {
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
                boolean checked = event.getEventTarget().<InputElement> cast()
                        .isChecked();
                String rowKey = getRowKey(cell.getRow());
                getRpcProxy(CustomRendererConenctorRpc.class).change(rowKey,
                        checked);
                return true;
            }
        };
    }
}
