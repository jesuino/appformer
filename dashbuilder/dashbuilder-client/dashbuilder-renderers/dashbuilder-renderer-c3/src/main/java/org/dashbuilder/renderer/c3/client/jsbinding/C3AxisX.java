package org.dashbuilder.renderer.c3.client.jsbinding;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class C3AxisX {
    
    @JsOverlay
    public static C3AxisX create(String type, String[] categories) {
        C3AxisX instance = new C3AxisX();
        instance.setType(type);
        instance.setCategories(categories);
        return instance;
    }

    @JsProperty
    public native void setType(String type);
    
    @JsProperty
    public native void setCategories(String categories[]);

}
