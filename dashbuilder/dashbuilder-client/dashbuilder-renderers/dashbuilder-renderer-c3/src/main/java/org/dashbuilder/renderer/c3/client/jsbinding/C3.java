package org.dashbuilder.renderer.c3.client.jsbinding;

import jsinterop.annotations.JsType;

@JsType(name="c3", isNative = true)
public class C3 {
    
    public static native void generate(C3ChartConf conf);
    
}
