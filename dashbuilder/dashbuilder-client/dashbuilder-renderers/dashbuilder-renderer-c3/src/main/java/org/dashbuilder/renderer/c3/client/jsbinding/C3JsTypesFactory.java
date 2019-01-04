package org.dashbuilder.renderer.c3.client.jsbinding;

import javax.enterprise.context.ApplicationScoped;

import org.dashbuilder.renderer.c3.client.jsbinding.C3Point.RadiusCallback;
import org.dashbuilder.renderer.c3.client.jsbinding.C3Tick.FormatterCallback;

import elemental2.core.JsObject;

/**
 * Build C3 JS Types
 */
@ApplicationScoped
public class C3JsTypesFactory {
    
    public C3Tick createC3Tick(FormatterCallback callback) {
        C3Tick instance = new C3Tick();
        instance.setFormat(callback);
        return instance;
    }

    public C3Grid c3Grid(boolean showX, boolean showY) {
        return C3Grid.create(C3GridConf.create(showX), 
                             C3GridConf.create(showY));
    }

    public C3Padding c3Padding(int top, int right, 
                               int bottom, int left) {
        return C3Padding.create(top, right, bottom, left);
    }

    public C3ChartSize c3ChartSize(double width, double height) {
        return C3ChartSize.create(width, height);
    }

    public C3Selection c3Selection(boolean enabled, boolean multiple, 
                                   boolean grouped) {
        return C3Selection.create(enabled, multiple, grouped);
    }

    public C3AxisY c3AxisY(boolean show, C3Tick tickY) {
        return C3AxisY.create(show, tickY);
    }
    public C3AxisX c3AxisX(String type, String[] categories, C3Tick tick) {
        return C3AxisX.create(type, categories, tick);
    }
    
    
    public C3Legend c3Legend(boolean show, String position, C3LegendInset inset) {
        return C3Legend.create(show, position, inset);
    }

    public C3Point c3Point(RadiusCallback r) {
        return C3Point.create(r);
    }

    public C3ChartData c3ChartData(String[][] columns, String type, 
                                   String[][] groups, JsObject xs,
                                   C3Selection selection) {
        return C3ChartData.create(columns, type, groups, xs, selection);
    }

    public C3AxisInfo c3AxisInfo(boolean rotated, C3AxisX axisX, C3AxisY axisY) {
        return C3AxisInfo.create(rotated, axisX, axisY);
    }



    public C3ChartConf c3ChartConf(C3ChartSize size, 
                                   C3ChartData data, 
                                   C3AxisInfo axis, 
                                   C3Grid grid,
                                   C3Transition transition, 
                                   C3Point point, 
                                   C3Padding padding, 
                                   C3Legend legend) {
        return C3ChartConf.create(size, data, axis, grid, transition, point, padding, legend);
    }

    public C3Transition c3Transition(int duration) {
        return C3Transition.create(duration);
    }

    public C3AxisLabel createC3Label(String text, String position) {
        return C3AxisLabel.create(text, position);
    }
    
}