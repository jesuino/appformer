package org.dashbuilder.dsl.factory.component;

import org.dashbuilder.displayer.DisplayerSettings;
import org.dashbuilder.external.model.ExternalComponent;

public class ExternalDisplayerBuilder extends DisplayerBuilder {

    ExternalDisplayerBuilder(String componentId, DisplayerSettings settings) {
        super(settings);
        addProperty(ExternalComponent.COMPONENT_ID_KEY, componentId);
        settings.setComponentId(componentId);
    }

    public static ExternalDisplayerBuilder create(String id, DisplayerSettings settings) {
        return new ExternalDisplayerBuilder(id, settings);
    }

    public ExternalDisplayerBuilder componentProperty(String key, String value) {
        settings.setComponentProperty(key, value);
        return this;
    }

}