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

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true)
public interface GrapesJS {
    
    @JsMethod
    Editor init(GrapesJSConfig config);
    
    class Builder {

        @JsProperty(name = "grapesjs", namespace = JsPackage.GLOBAL)
        public static native GrapesJS get();
        
    }
    
    @JsType(isNative = true)
    interface DomComponents {
        
        @JsMethod
        Type getType(String type);
        
        @JsMethod
        void addType(String id, Type type);
    }
    
    @JsType(isNative = true)
    interface BlockManager {
        
        @JsMethod
        Type add(String id, Block block);
    }
    
    @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
    class Type {
        
        @JsOverlay
        public static Type create(Model model, View view) {
            Type instance = new Type();
            instance.setModel(model);
            instance.setView(view);
            return instance;
        }
        
        @JsProperty
        public native Model getModel();
        
        @JsProperty
        public native void setModel(Model model);
        
        @JsProperty
        public native View getView();
        
        @JsProperty
        public native void setView(View view);
        
    }
    
    @JsType(isNative = true)
    interface View {
        
    }
    
    @JsType(isNative = true)
    interface Model {
        
        @JsMethod
        Model extend(ModelDefaultProperties other);
    }
    
    @JsType(isNative = true)
    interface Editor {
        
        @JsMethod
        String getHtml();
        
        @JsMethod
        String getCss();

        @JsMethod
        void setStyle(String css);

        @JsMethod
        void setComponents(String html);
        
        @JsProperty(name="DomComponents")
        DomComponents getDomComponents();
        
        @JsProperty(name="BlockManager")
        BlockManager getBlockManager();        
        
    }

}