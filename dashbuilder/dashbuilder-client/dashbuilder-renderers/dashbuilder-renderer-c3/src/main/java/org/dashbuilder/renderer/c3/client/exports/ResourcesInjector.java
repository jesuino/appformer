package org.dashbuilder.renderer.c3.client.exports;

import com.google.gwt.dom.client.StyleInjector;

public class ResourcesInjector {

    static boolean injected;

    public static void ensureInjected() {
        if (!injected) {
            injectAllResources();
            injected = true;
        }
    }

    private static void injectAllResources() {
        StyleInjector.inject(NativeLibraryResources.INSTANCE.c3css().getText());
        JavaScriptInjector.inject(NativeLibraryResources.INSTANCE.d3js().getText());
        JavaScriptInjector.inject(NativeLibraryResources.INSTANCE.c3js().getText());
    }

}
