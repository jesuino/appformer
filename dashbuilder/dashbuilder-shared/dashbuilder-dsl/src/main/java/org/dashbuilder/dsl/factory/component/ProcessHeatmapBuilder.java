package org.dashbuilder.dsl.factory.component;

import org.dashbuilder.displayer.DisplayerSettings;

public class ProcessHeatmapBuilder extends ExternalDisplayerBuilder {

    private static final String COMPONENT_ID = "process-heatmap-provided";
    private static final String SERVER_TEMPLATE_PARAM = "serverTemplate";
    private static final String CONTAINER_ID_PARAM = "containerId";
    private static final String PROCESS_ID_PARAM = "processId";

    public ProcessHeatmapBuilder(String serverTemplate, String container, String processId, DisplayerSettings settings) {
        super(COMPONENT_ID, settings);
        this.componentProperty(SERVER_TEMPLATE_PARAM, serverTemplate);
        this.componentProperty(CONTAINER_ID_PARAM, container);
        this.componentProperty(PROCESS_ID_PARAM, processId);
    }

    public static ProcessHeatmapBuilder create(String serverTemplate,
                                               String container,
                                               String processId,
                                               DisplayerSettings settings) {
        return new ProcessHeatmapBuilder(serverTemplate, container, processId, settings);
    }

}