package org.dashbuilder.renderer.c3.client.jsbinding;

import com.google.gwt.user.client.Element;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * Bind the type that should be passed to c3.generate
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class C3ChartConf {  
    
    @JsOverlay
    public static C3ChartConf create(C3ChartSize size, C3ChartData data) {
        C3ChartConf instance = new C3ChartConf();
        instance.setSize(size);
        instance.setData(data);
        return instance;
    }
    
    @JsProperty
    public native void setBindto(Element element);
    @JsProperty
    public native void setSize(C3ChartSize size);
    @JsProperty
    public native void setData(C3ChartData data);

}
