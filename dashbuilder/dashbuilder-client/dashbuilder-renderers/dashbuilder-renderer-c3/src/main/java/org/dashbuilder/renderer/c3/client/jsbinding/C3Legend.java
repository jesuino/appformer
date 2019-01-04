package org.dashbuilder.renderer.c3.client.jsbinding;

import org.dashbuilder.displayer.Position;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class C3Legend {
    
    @JsOverlay
    static C3Legend create(boolean show, String position, C3LegendInset inset) {
        C3Legend instance = new C3Legend();
        instance.setShow(show);
        instance.setPosition(position);
        instance.setInset(inset);
        return instance;
    }

    @JsOverlay
    public static String convertPosition(Position position) {
        // Not all positions are supported by C3, for the not supported we use inset and try to calculate the position
        switch(position) {
        case BOTTOM:
            return position.name().toLowerCase();
        case RIGHT:
            return position.name().toLowerCase();
        default:
            return "inset";
        }
    }
    
    @JsProperty
    public native void setShow(boolean show);
    
    @JsProperty
    public native void setPosition(String position);
    
    @JsProperty
    public native void setInset(C3LegendInset inset);
    
}