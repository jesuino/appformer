package org.dashbuilder.dsl.factory.component;

import org.dashbuilder.dsl.model.Component;
import org.uberfire.ext.layout.editor.api.editor.LayoutComponent;

public abstract class AbstractComponentBuilder<T> {

    private LayoutComponent layoutComponent;

    AbstractComponentBuilder() {
        String dragType = getDragType();
        this.layoutComponent = new LayoutComponent(dragType);
    }

    public Component build() {
        return Component.create(this.layoutComponent);
    }

    @SuppressWarnings("unchecked")
    protected T addProperty(String key, String value) {
        this.layoutComponent.addProperty(key, value);
        return (T) this;
    }

    abstract String getDragType();

}