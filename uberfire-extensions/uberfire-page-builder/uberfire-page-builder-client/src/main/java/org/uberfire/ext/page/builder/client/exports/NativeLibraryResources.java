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

package org.uberfire.ext.page.builder.client.exports;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.resources.client.TextResource;

/**
 *
 */
public interface NativeLibraryResources extends ClientBundle {
    
    NativeLibraryResources INSTANCE = GWT.create(NativeLibraryResources.class);
    
    @Source("js/grapes.min.js")
    TextResource grapesjs();

    @NotStrict
    @Source("css/grapes.css")
    CssResource grapescss(); 
    
    @Source("js/grapesjs-preset-webpage.min.js")
    TextResource grapespresetwebpagejs();

    @NotStrict
    @Source("css/grapesjs-preset-webpage.min.css")
    CssResource grapespresetwebpagecss(); 
    
    
}
