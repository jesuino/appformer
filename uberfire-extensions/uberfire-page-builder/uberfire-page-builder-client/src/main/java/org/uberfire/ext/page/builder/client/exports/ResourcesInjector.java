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

import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.dom.client.StyleInjector;

/**
 *
 */
public class ResourcesInjector {
    
    static boolean grapesJsInjected;
    
    public static void ensureGrapesJsInjected() {
        if (!grapesJsInjected) {
            injectGrapesJsResources();
            grapesJsInjected = true;
        }
    }

    private static void injectGrapesJsResources() {
        NativeLibraryResources instance = NativeLibraryResources.INSTANCE;
        // SUPER WORKAROUND THAT MUST BE CHANGED! Downloading same font awesome used by pf
        String replacedCSS = instance.grapescss().getText().replaceAll("v=4.7.0", "v=4.6.3");
        StyleInjector.inject(replacedCSS);
        ScriptInjector.fromString(instance.grapesjs().getText())
                      .setWindow(ScriptInjector.TOP_WINDOW)
                      .inject();
    }

}
