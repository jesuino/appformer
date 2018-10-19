/*
 * Copyright 2014 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dashbuilder.renderer.c3.client;

import java.util.List;

import org.dashbuilder.dataset.ColumnType;
import org.dashbuilder.dataset.DataColumn;
import org.dashbuilder.dataset.DataSetLookupConstraints;
import org.dashbuilder.displayer.DisplayerAttributeDef;
import org.dashbuilder.displayer.DisplayerAttributeGroupDef;
import org.dashbuilder.displayer.DisplayerConstraints;
import org.dashbuilder.displayer.client.AbstractGwtDisplayer;
import org.dashbuilder.renderer.c3.client.jsbinding.C3Axis;
import org.dashbuilder.renderer.c3.client.jsbinding.C3AxisX;
import org.dashbuilder.renderer.c3.client.jsbinding.C3ChartConf;
import org.dashbuilder.renderer.c3.client.jsbinding.C3ChartData;
import org.dashbuilder.renderer.c3.client.jsbinding.C3ChartSize;

public class C3Displayer extends AbstractGwtDisplayer<C3Displayer.View> {
    
    View view;

    public interface View extends AbstractGwtDisplayer.View<C3Displayer> {

        void updateChart(C3ChartConf conf);
        
        String getId();
        
        String getType();
    }
    
    public C3Displayer(View view) {
        super();
        this.view = view;
        view.init(this);
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public DisplayerConstraints createDisplayerConstraints() {
        DataSetLookupConstraints lookupConstraints = new DataSetLookupConstraints()
                .setGroupRequired(true)
                .setGroupColumn(true)
                .setMaxColumns(10)
                .setMinColumns(2)
                .setExtraColumnsAllowed(true)
                .setExtraColumnsType(ColumnType.NUMBER)
                .setColumnTypes(new ColumnType[]{
                        ColumnType.LABEL,
                        ColumnType.NUMBER});

        return new DisplayerConstraints(lookupConstraints)
                .supportsAttribute(DisplayerAttributeDef.TYPE)
                .supportsAttribute(DisplayerAttributeDef.RENDERER)
                .supportsAttribute(DisplayerAttributeGroupDef.COLUMNS_GROUP)
                .supportsAttribute(DisplayerAttributeGroupDef.FILTER_GROUP)
                .supportsAttribute(DisplayerAttributeGroupDef.REFRESH_GROUP)
                .supportsAttribute(DisplayerAttributeGroupDef.GENERAL_GROUP)
                .supportsAttribute(DisplayerAttributeDef.CHART_WIDTH)
                .supportsAttribute(DisplayerAttributeDef.CHART_HEIGHT)
                .supportsAttribute(DisplayerAttributeDef.CHART_BGCOLOR)
                .supportsAttribute(DisplayerAttributeGroupDef.CHART_MARGIN_GROUP)
                .supportsAttribute(DisplayerAttributeGroupDef.CHART_LEGEND_GROUP)
                .supportsAttribute(DisplayerAttributeGroupDef.AXIS_GROUP);      
    }

    @Override
    protected void createVisualization() {
        updateVisualization();
    }

    @Override
    protected void updateVisualization() {
        C3ChartConf conf = buildConfiguration();
        getView().updateChart(conf);
    }

    protected C3ChartConf buildConfiguration() {
        String type = getView().getType();
        String[][] series = retrieveSeries();
        double width = displayerSettings.getChartWidth();
        double height = displayerSettings.getChartHeight();
        C3Axis axis = createAxis();
        C3ChartData data = createData(type, series);
        return C3ChartConf.create(
                    C3ChartSize.create(width, height),
                    data,
                    axis
                );
    }

    protected C3ChartData createData(String type, String[][] series) {
        return C3ChartData.create(series, type);
    }

    protected C3Axis createAxis() {
        C3AxisX axisX = createAxisX();
        return C3Axis.create(false, axisX);
    }
    
    protected C3AxisX createAxisX() {
       String[] categories = retrieveCategories();
       return C3AxisX.create("category", categories);
    }

    protected String[] retrieveCategories() {
        List<DataColumn> columns = dataSet.getColumns();
        String[] categories = null;
        if(columns.size() > 0) {
            List<?> values = columns.get(0).getValues();
            categories = new String[values.size()];
            for (int i = 0; i < categories.length; i++) {
                categories[i] = values.get(i).toString();
            }
        }
        return categories;
    }

    protected String[][] retrieveSeries() {
        List<DataColumn> columns = dataSet.getColumns();
        String[][] data  = null;
        if(columns.size() > 1) {
            data = new String[columns.size() - 1][];
            for (int i = 1; i < columns.size(); i++) {
                DataColumn dataColumn = columns.get(i);
                List<?> values = dataColumn.getValues();
                String[] seriesValues = new String[values.size() + 1];
                seriesValues[0] = columns.get(i).getId();
                for (int j = 0; j < values.size(); j++) {
                    seriesValues[j + 1] = values.get(j).toString(); 
                }
                data[i - 1] = seriesValues;
            }
        }
        return data;
    }

}
