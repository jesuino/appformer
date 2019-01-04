package org.dashbuilder.renderer.c3.client.jsbinding;

import org.dashbuilder.displayer.Position;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class C3LegendInset {
    
    @JsOverlay
    static C3LegendInset create(String anchor, int x, int y, int step) {
        C3LegendInset instance = new C3LegendInset();
        instance.setAnchor(anchor);
        instance.setX(x);
        instance.setY(y);
        instance.setStep(step);
        return instance;
    }    
    
    /**
     * Must generate the correct insets for types not supported by default on C3 (TOP and LEFT)
     * 
     * @param legendPosition
     * @return
     */
    @JsOverlay
    public static C3LegendInset getInsetForPosition(Position legendPosition) {
        switch(legendPosition) {
        case TOP: 
            return C3LegendInset.create("top-left", 20, 0, 0);
        case LEFT:
            return C3LegendInset.create("bottom-left", 0, 0, 0);
        default: 
            return null;
        }
    }

    @JsProperty
    public native void setAnchor(String anchor);
    
    @JsProperty
    public native void setX(int x);
    
    @JsProperty
    public native void setY(int y);
    
    @JsProperty
    public native void setStep(int step);

}