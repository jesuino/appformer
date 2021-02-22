package org.dashbuilder.dsl.factory.component;

import org.dashbuilder.external.model.ExternalComponent;

public class ExternalComponentBuilder extends AbstractComponentBuilder<ExternalComponentBuilder> {

    private String componentId;

    ExternalComponentBuilder(String componentId) {
        this.componentId = componentId;
        addProperty(ExternalComponent.COMPONENT_ID_KEY, this.componentId);
    }

    public static ExternalComponentBuilder create(String componentId) {
        return new ExternalComponentBuilder(componentId);
    }

    public ExternalComponentBuilder componentProperty(String key, String value) {
        addProperty(this.componentId + "." + key, value);
        return this;
    }
    
    public ExternalComponentBuilder partition(String value) {
        addProperty(ExternalComponent.COMPONENT_PARTITION_KEY, value);
        return this;
    }

    @Override
    String getDragType() {
        return "org.dashbuilder.client.editor.external.ExternalDragComponent";
    }

}
