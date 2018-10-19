package org.dashbuilder.renderer.c3.client.jsbinding;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class C3Axis {
    
    @JsOverlay
    public static C3Axis create(boolean rotated, C3AxisX x) {
        C3Axis instance = new C3Axis();
        instance.setRotated(rotated);
        instance.setX(x);
        return instance;
    }
    
    @JsProperty
    public native void setRotated(boolean rotated);

    
    @JsProperty
    public native void setX(C3AxisX x);
}
