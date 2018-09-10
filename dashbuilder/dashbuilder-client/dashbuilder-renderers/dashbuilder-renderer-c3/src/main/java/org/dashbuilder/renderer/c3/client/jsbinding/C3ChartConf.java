package org.dashbuilder.renderer.c3.client.jsbinding;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * Bind the type that should be passed to c3.generate
 */
@JsType(isNative = true, name = "Object")
public class C3ChartConf {  
    
    @JsOverlay
    public static C3ChartConf create(String bindto) {
        C3ChartConf instance = new C3ChartConf();
        instance.setBindto(bindto);
        return instance;
    }
    
    @JsProperty
    public native void setBindto(String bindto);
    @JsProperty
    public native void setSize(C3ChartSize size);
    @JsProperty
    public native void setData(C3ChartData data);

}
