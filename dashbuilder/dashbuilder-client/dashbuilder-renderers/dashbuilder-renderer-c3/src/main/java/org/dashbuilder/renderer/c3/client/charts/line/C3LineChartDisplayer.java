package org.dashbuilder.renderer.c3.client.charts.line;

import org.dashbuilder.renderer.c3.client.C3Displayer;

public class C3LineChartDisplayer extends C3Displayer {
    
    public C3LineChartDisplayer() {
        super(new C3LineChartView());
    }

    public C3LineChartDisplayer(View view) {
        super(view);
    }

}
