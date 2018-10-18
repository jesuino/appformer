package org.dashbuilder.renderer.c3.client.charts.area;

import org.dashbuilder.renderer.c3.client.C3Displayer;

public class C3AreaChartDisplayer extends C3Displayer {
    
    public C3AreaChartDisplayer() {
        super(new C3AreaChartView());
    }

    public C3AreaChartDisplayer(View view) {
        super(view);
    }
    
}
