package org.dashbuilder.dsl.model;

import org.uberfire.ext.layout.editor.api.editor.LayoutComponent;

public class Component {

    LayoutComponent layoutComponent;

    Component(LayoutComponent layoutComponent) {
        this.layoutComponent = layoutComponent;
    }

    public static Component create(LayoutComponent layoutComponent) {
        return new Component(layoutComponent);
    }

    public LayoutComponent getLayoutComponent() {
        return layoutComponent;
    }

}