package org.dashbuilder.renderer.c3.client.jsbinding;

import elemental2.core.JsObject;
import elemental2.dom.Element;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class C3ChartData {
    
    @JsOverlay
    public static C3ChartData create(String[][] columns, 
                                     String type, 
                                     String[][] groups, 
                                     JsObject xs, 
                                     C3Selection selection) {
        C3ChartData data = new C3ChartData();
        data.setColumns(columns);
        data.setType(type);
        data.setGroups(groups);
        data.setXs(xs);
        data.setSelection(selection);
        return data;
    }
    
    @JsProperty
    public native void setColumns(String columns[][]);

    @JsProperty
    public native void setType(String type);
    
    @JsProperty
    public native void setGroups(String groups[][]); 
    
    @JsProperty
    public native void setXs(JsObject xs);
    
    @JsProperty
    public native void setOrder(String order);
    
    @JsProperty
    public native void setOnselected(SelectCallback callback);
    
    @JsProperty
    public native void setOnunselected(SelectCallback callback);
    
    @JsProperty
    public native void setSelection(C3Selection selection);
    
    @JsFunction
    @FunctionalInterface
    public static interface SelectCallback {
        
        void callback(C3DataInfo data);
    
    }
    

}