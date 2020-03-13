package org.dashbuilder.renderer.c3.client.jsbinding;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class C3LoadData {
    
    @JsOverlay
    public static C3LoadData create(String[][] columns) {
        C3LoadData loadData = new C3LoadData();
        loadData.setColumns(columns);
        return loadData;
    }
    
    @JsProperty
    public native void setColumns(String columns[][]);

}
