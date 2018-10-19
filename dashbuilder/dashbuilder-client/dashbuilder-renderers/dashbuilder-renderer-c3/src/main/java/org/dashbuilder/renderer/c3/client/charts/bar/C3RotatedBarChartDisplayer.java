package org.dashbuilder.renderer.c3.client.charts.bar;

import org.dashbuilder.renderer.c3.client.C3Displayer;
import org.dashbuilder.renderer.c3.client.jsbinding.C3Axis;

public class C3RotatedBarChartDisplayer extends C3Displayer {
    
    public C3RotatedBarChartDisplayer() {
        super(new C3BarChartView());
    }

    public C3RotatedBarChartDisplayer(View view) {
        super(view);
    }
    
    @Override
    protected C3Axis createAxis() {
        C3Axis axis = super.createAxis();
        axis.setRotated(true);
        return axis;
    }

}
