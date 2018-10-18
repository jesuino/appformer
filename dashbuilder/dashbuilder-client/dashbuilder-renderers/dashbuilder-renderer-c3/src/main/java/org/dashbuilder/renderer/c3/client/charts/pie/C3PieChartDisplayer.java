package org.dashbuilder.renderer.c3.client.charts.pie;

import org.dashbuilder.displayer.DisplayerSubType;
import org.dashbuilder.renderer.c3.client.C3Displayer;

public class C3PieChartDisplayer extends C3Displayer {
    
    public C3PieChartDisplayer() {
        super(new C3PieChartView());
    }

    public C3PieChartDisplayer(View view) {
        super(view);
    }

}
