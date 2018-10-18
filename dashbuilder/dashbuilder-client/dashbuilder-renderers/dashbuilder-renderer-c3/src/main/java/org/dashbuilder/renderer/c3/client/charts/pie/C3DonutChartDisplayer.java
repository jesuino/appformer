package org.dashbuilder.renderer.c3.client.charts.pie;

import org.dashbuilder.renderer.c3.client.C3Displayer;

public class C3DonutChartDisplayer extends C3Displayer {
    
    public C3DonutChartDisplayer() {
        super(new C3DonutChartView());
    }

    public C3DonutChartDisplayer(View view) {
        super(view);
    }

}
