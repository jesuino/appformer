package org.dashbuilder.renderer.c3.client.jsbinding;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class C3ChartData {
    
    @JsOverlay
    public static C3ChartData create(String[][] columns, String type) {
        C3ChartData data = new C3ChartData();
        data.setColumns(columns);
        data.setType(type);
        return data;
    }
    
    @JsProperty
    public native void setColumns(String columns[][]);

    @JsProperty
    public native void setType(String type);

}
