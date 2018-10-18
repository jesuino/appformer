package org.dashbuilder.renderer.c3.client.charts.line;

import org.dashbuilder.displayer.DisplayerSubType;
import org.dashbuilder.renderer.c3.client.C3Displayer;

public class C3SmoothChartDisplayer extends C3Displayer {
    
    public C3SmoothChartDisplayer() {
        super(new C3SmoothChartView());
    }

    public C3SmoothChartDisplayer(View view) {
        super(view);
    }

}
