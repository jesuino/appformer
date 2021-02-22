package org.dashbuilder.dsl.factory.component;

import org.dashbuilder.displayer.DisplayerSettings;
import org.dashbuilder.displayer.json.DisplayerSettingsJSONMarshaller;
import org.dashbuilder.dsl.model.Component;

public class DisplayerBuilder extends AbstractComponentBuilder<DisplayerBuilder> {

    private static final String JSON_PROP = "json";
    DisplayerSettings settings;

    DisplayerBuilder(DisplayerSettings settings) {
        this.settings = settings;
    }

    public static DisplayerBuilder create(DisplayerSettings settings) {
        return new DisplayerBuilder(settings);
    }

    @Override
    public Component build() {
        addProperty(JSON_PROP, DisplayerSettingsJSONMarshaller.get().toJsonString(this.settings));
        return super.build();
    }

    @Override
    String getDragType() {
        return "org.dashbuilder.client.editor.DisplayerDragComponent";
    }

}
