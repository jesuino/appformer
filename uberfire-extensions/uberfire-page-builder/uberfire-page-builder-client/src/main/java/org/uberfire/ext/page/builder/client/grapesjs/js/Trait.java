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
public class Trait {
    
    @JsOverlay
    public static Trait create(String type, String label, String name) {
        Trait instance = new Trait();
        instance.setType(type);
        instance.setLabel(label);
        instance.setName(name);
        return instance;
    }
    
    @JsProperty
    public native void setType(String type);
    
    @JsProperty
    public native void setLabel(String label);
    
    @JsProperty
    public native void setName(String name); 

}
