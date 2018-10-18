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
import org.dashbuilder.dataset.DataSet;
import org.dashbuilder.dataset.DataSetLookupConstraints;
import org.dashbuilder.displayer.ColumnSettings;
import org.dashbuilder.displayer.DisplayerAttributeDef;
import org.dashbuilder.displayer.DisplayerAttributeGroupDef;
import org.dashbuilder.displayer.DisplayerConstraints;
import org.dashbuilder.displayer.client.AbstractGwtDisplayer;
import org.dashbuilder.renderer.c3.client.jsbinding.C3ChartConf;
import org.dashbuilder.renderer.c3.client.jsbinding.C3ChartData;
import org.dashbuilder.renderer.c3.client.jsbinding.C3ChartSize;
import org.jboss.errai.ioc.client.api.InitBallot;

import com.google.gwt.user.client.Window;

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
        String[][] columns = retrieveData();
        double width = displayerSettings.getChartWidth();
        double height = displayerSettings.getChartHeight();
        return C3ChartConf.create(
                    C3ChartSize.create(width, height),
                    C3ChartData.create(columns, type)
                );
    }

    
    protected String[][] retrieveData() {
        DataSet dataset = displayerSettings.getDataSet();
        List<DataColumn> columns = dataset.getColumns();
        String[][] data = new String[columns.size()][dataset.getRowCount() + 1];
        for (int i = 0; i < dataset.getColumns().size(); i++) {
            ColumnSettings settings = displayerSettings.getColumnSettings(columns.get(i));
            data[i][0] = settings.getColumnName();
            for (int j = 0; j < dataset.getRowCount(); j++) {
                data[i][j + 1] = dataset.getValueAt(j, i).toString(); 
            }
        }
        return data;
    }

}
