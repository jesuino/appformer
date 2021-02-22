package org.dashbuilder.dsl.factory.component;

import org.dashbuilder.displayer.DisplayerSettings;

public class AllProcessesHeatmapBuilder extends ExternalDisplayerBuilder {

    private static final String COMPONENT_ID = "processes-heatmaps-provided";
    private static final String SERVER_TEMPLATE_PARAM = "serverTemplate";

    public AllProcessesHeatmapBuilder(String serverTemplate, DisplayerSettings settings) {
        super(COMPONENT_ID, settings);
        this.componentProperty(SERVER_TEMPLATE_PARAM, serverTemplate);
    }

    public static AllProcessesHeatmapBuilder create(String serverTemplate,
                                                    DisplayerSettings settings) {
        return new AllProcessesHeatmapBuilder(serverTemplate, settings);
    }

}