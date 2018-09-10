package org.dashbuilder.renderer.c3.client.exports;

import com.google.gwt.core.client.ScriptInjector;
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
        // is this the right way to inject CSS?
        StyleInjector.inject(NativeLibraryResources.INSTANCE.c3css().getText());
        ScriptInjector.fromString(NativeLibraryResources.INSTANCE.d3js().getText());
        ScriptInjector.fromString(NativeLibraryResources.INSTANCE.c3js().getText());

    }

}
