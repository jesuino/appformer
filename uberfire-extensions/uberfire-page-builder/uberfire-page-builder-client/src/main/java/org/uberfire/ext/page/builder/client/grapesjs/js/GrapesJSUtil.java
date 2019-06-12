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

public class GrapesJSUtil {
    
    public static native void addCssClassPrefix(GrapesJS.Editor editor, String prefix) /*-{
            editor.on('selector:add', function(selector) {
                var name = selector.get('name');
        
                if (selector.get('type') === editor.SelectorManager.Selector.TYPE_CLASS && 
                    name.indexOf(prefix) !== 0) {
                    selector.set('name', prefix + name);
                }
            });
    }-*/;
    
    public static native GrapesJS.Model buildExtendedModel(GrapesJS.Model defaultModel, 
                                                           ModelDefaultProperties baseModel,
                                                           GrapesJS.ModelTypeRecognizer recognizer) /*-{
        return defaultModel.extend({
            // Extend default properties
            defaults: Object.assign({}, defaultModel.prototype.defaults, baseModel),
        }, recognizer);
    }-*/;
    
}