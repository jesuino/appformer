package org.dashbuilder.renderer.c3.client.charts.pie;

import java.util.Iterator;
import java.util.List;

import org.dashbuilder.dataset.DataColumn;
import org.dashbuilder.dataset.DataSet;
import org.dashbuilder.renderer.c3.client.C3Displayer;
import org.dashbuilder.renderer.c3.client.jsbinding.C3AxisX;

public class C3PieChartDisplayer extends C3Displayer {
    
    public C3PieChartDisplayer() {
        super(new C3PieChartView());
    }

    public C3PieChartDisplayer(View view) {
        super(view);
    }
    
    // In C3 we only need the series for PieCharts
    
    @Override
    protected String[][] retrieveSeries() {
        List<DataColumn> columns = dataSet.getColumns();
        String[][] data  = null;
        // first columns hold the pie series name
        DataColumn categoriesColumn = columns.get(0);
        // First column will contains the pie series information
        List<?> values = categoriesColumn.getValues();
        data = new String[values.size()][];
        // next columns hold the values for each series
        // needs to get the column value for each serie
        for (int i = 0; i < values.size(); i++) {
            String[] seriesValues = new String[columns.size()];
            seriesValues[0] = values.get(i).toString();
            for (int j = 1; j < columns.size(); j++) {
                DataColumn dataColumn = columns.get(j);
                seriesValues[j] = dataColumn.getValues().get(i).toString();
            }
            data[i] = seriesValues;
        }
        return data;
    }
    
    @Override
    protected C3AxisX createAxisX() {
        return null;
     }

    @Override
    protected String[] retrieveCategories() {
        return null;
    }

}
