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

import java.text.NumberFormat;
import java.util.Locale;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.components.grid.renderers.HtmlRenderer;
import com.vaadin.ui.components.grid.renderers.NumberRenderer;
import com.vaadin.ui.components.grid.renderers.ProgressBarRenderer;

/**
 * 
 * @since
 * @author Vaadin Ltd
 */
public class FormattingData extends AbstractGridSample {
    public FormattingData() {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        grid.getColumn(VALUE_COL).setRenderer(new NumberRenderer(format));

        Converter<String, Boolean> approvedBooleanConverter = new Converter<String, Boolean>() {
            @Override
            public Boolean convertToModel(String value,
                    Class<? extends Boolean> targetType, Locale locale)
                    throws Converter.ConversionException {
                throw new ConversionException("Not supported");
            }

            @Override
            public String convertToPresentation(Boolean value,
                    Class<? extends String> targetType, Locale locale)
                    throws Converter.ConversionException {
                if (value.booleanValue()) {
                    return "Approved";
                } else {
                    return "<b style='color:red'>Not approved</b>";
                }
            }

            @Override
            public Class<Boolean> getModelType() {
                return Boolean.class;
            }

            @Override
            public Class<String> getPresentationType() {
                return String.class;
            }
        };

        grid.getColumn(APPROVED_COL).setRenderer(new HtmlRenderer(),
                approvedBooleanConverter);

        grid.getColumn(PROGRESS_COL).setRenderer(new ProgressBarRenderer());
    }
}
