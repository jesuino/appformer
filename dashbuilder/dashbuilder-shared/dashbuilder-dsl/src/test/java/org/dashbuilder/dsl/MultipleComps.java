package org.dashbuilder.dsl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.dashbuilder.dsl.model.Page;
import org.dashbuilder.dsl.serialization.DashboardExporter;
import org.dashbuilder.dsl.serialization.DashboardExporter.ExportType;
import org.junit.Test;

import static org.dashbuilder.dsl.factory.component.ComponentFactory.external;
import static org.dashbuilder.dsl.factory.dashboard.DashboardFactory.dashboardBuilder;
import static org.dashbuilder.dsl.factory.page.PageFactory.column;
import static org.dashbuilder.dsl.factory.page.PageFactory.page;
import static org.dashbuilder.dsl.factory.page.PageFactory.row;

public class MultipleComps {

    @Test
    public void test() throws Exception {

        Page page = page("My Page",
                         row("Love"),
                         row(external("mycomp")),
                         row(column(external("comp1")),
                             column(external("comp2"))));

        Path componentsPath = Paths.get(this.getClass().getResource("/components").getFile());
        DashboardExporter.export(dashboardBuilder(Arrays.asList(page)).componentsPath(componentsPath)
                                                                      .build(),
                                 "/home/wsiqueir/projects/dashbuilder-docker/demos/dashbuilder_dev_mode/models/comp-compose.zip",
                                 ExportType.ZIP);

    }
}