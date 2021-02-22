package org.dashbuilder.dsl.factory.component;

import org.dashbuilder.displayer.DisplayerSettings;
import org.dashbuilder.dsl.model.Component;

public class ComponentFactory {

    private ComponentFactory() {
        // empty
    }

    public static Component html(String htmlCode) {
        return HtmlComponentBuilder.create().html(htmlCode).build();
    }

    public static Component external(String componentId) {
        return ExternalComponentBuilder.create(componentId).build();
    }

    public static Component external(String componentId, DisplayerSettings settings) {
        return ExternalDisplayerBuilder.create(componentId, settings).build();
    }

    public static Component displayer(DisplayerSettings settings) {
        return DisplayerBuilder.create(settings).build();
    }

    public static Component logo(String src) {
        return LogoBuilder.create(src).build();
    }

    public static Component processHeatmap(String serverTemplate, String container, String process, DisplayerSettings settings) {
        return ProcessHeatmapBuilder.create(serverTemplate, container, process, settings).build();
    }

    public static Component allProcessesHeatmap(String serverTemplate, DisplayerSettings settings) {
        return AllProcessesHeatmapBuilder.create(serverTemplate, settings).build();
    }

    public static HtmlComponentBuilder newHtmlComponentBuilder() {
        return HtmlComponentBuilder.create();
    }

    public static ExternalComponentBuilder externalBuilder(String componentId) {
        return ExternalComponentBuilder.create(componentId);
    }

    public static LogoBuilder logoBuilder(String src) {
        return LogoBuilder.create(src);
    }

    public static ProcessHeatmapBuilder processHeatmapBuilder(String serverTemplate, String container, String process, DisplayerSettings settings) {
        return ProcessHeatmapBuilder.create(serverTemplate, container, process, settings);
    }

    public static AllProcessesHeatmapBuilder allProcessesHeatmapBuilder(String serverTemplate, DisplayerSettings settings) {
        return AllProcessesHeatmapBuilder.create(serverTemplate, settings);
    }

}
