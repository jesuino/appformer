package org.dashbuilder.renderer.c3.client.charts.bar;

import org.dashbuilder.renderer.c3.client.C3Displayer;

public class C3BarChartDisplayer extends C3Displayer {
    
    public C3BarChartDisplayer() {
        super(new C3BarChartView());
    }

    public C3BarChartDisplayer(View view) {
        super(view);
    }

}
