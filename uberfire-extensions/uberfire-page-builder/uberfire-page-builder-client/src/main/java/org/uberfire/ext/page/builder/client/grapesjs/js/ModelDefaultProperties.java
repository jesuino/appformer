/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.uberfire.ext.page.builder.client.grapesjs.js;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class ModelDefaultProperties {
    
    @JsOverlay
    public static ModelDefaultProperties create(String extend, Trait[] traits) {
        ModelDefaultProperties instance = new ModelDefaultProperties();
        instance.setExtend(extend);
        instance.setTraits(traits);
        // no time, brother - default values
        instance.setDroppable(false);
        instance.setHoverable(true);
        instance.setResizable(true);
        instance.setLayerable(true);
        instance.setLayerable(true);
        instance.setHighlightable(true);
        return instance;
    }
    
    @JsProperty
    public native void setExtend(String extend);
    
    @JsProperty
    public native void setDroppable(boolean droppable);
    
    @JsProperty
    public native void setHoverable(boolean hoverable);
    
    @JsProperty
    public native void setResizable(boolean resizable);
    
    @JsProperty
    public native void setLayerable(boolean layerable);
    
    @JsProperty
    public native void setHighlightable(boolean highlightable);
    
    @JsProperty
    public native void setTraits(Trait[] traits);   
    
}