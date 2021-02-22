package org.dashbuilder.dsl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;

import org.dashbuilder.dataset.ColumnType;
import org.dashbuilder.dataset.DataSet;
import org.dashbuilder.displayer.DisplayerSettings;
import org.dashbuilder.dsl.model.Page;
import org.dashbuilder.dsl.serialization.DashboardExporter;
import org.dashbuilder.dsl.serialization.DashboardExporter.ExportType;
import org.junit.Test;

import static org.dashbuilder.dataset.DataSetFactory.newDataSetBuilder;
import static org.dashbuilder.displayer.DisplayerSettingsFactory.newBarChartSettings;
import static org.dashbuilder.displayer.DisplayerSettingsFactory.newLineChartSettings;
import static org.dashbuilder.dsl.factory.component.ComponentFactory.displayer;
import static org.dashbuilder.dsl.factory.component.ComponentFactory.external;
import static org.dashbuilder.dsl.factory.component.ComponentFactory.html;
import static org.dashbuilder.dsl.factory.component.ComponentFactory.logoBuilder;
import static org.dashbuilder.dsl.factory.dashboard.DashboardFactory.dashboardBuilder;
import static org.dashbuilder.dsl.factory.page.PageFactory.column;
import static org.dashbuilder.dsl.factory.page.PageFactory.columnBuilder;
import static org.dashbuilder.dsl.factory.page.PageFactory.page;
import static org.dashbuilder.dsl.factory.page.PageFactory.row;

public class Playground {

    private static final String LOGO_URL = "https://www.redhat.com/cms/managed-files/styles/xlarge/s3/red-hat-logo-a-sample.png?itok=PJP_urGJ";

    @Test
    public void test() throws Exception {

        DataSet mockDS = newDataSetBuilder().column("name", ColumnType.LABEL)
                                            .column("value", ColumnType.NUMBER)
                                            .row("V1", 5)
                                            .row("V2", 1)
                                            .row("V3", 1)
                                            .row("V4", 10)
                                            .buildDataSet();
        DisplayerSettings settings;

        settings = newLineChartSettings().subType_Line()
                                         .dataset(mockDS)
                                         .width(500).height(300)
                                         .buildSettings();

        settings = newBarChartSettings().subType_Column()
                                        .dataset(mockDS)
                                        .width(800).height(300)
                                        .buildSettings();

        Page page = page("My Page",
                         row(column(logoBuilder(LOGO_URL).width("300px")
                                                         .height("auto")
                                                         .build())),
                         row(column(html("<h1>Page Title</h1>"))),
                         row(column(html("<hr />"))),
                         row(columnBuilder().row(row(column(html("<h3>Column 1</h3>"))))
                                            .row(row(column(html("<p>Column 1 content</p>"))))
                                            .property("color", "blue")
                                            .property("width", "300px")
                                            .property("height", "100px")
                                            .property("background-color", "gray")
                                            .build(),
                             columnBuilder().row(row(column(html("<h3>Column 2</h3>"))))
                                            .row(row(column(html("<p>Column 2 content</p>"))))
                                            .property("color", "red")
                                            .property("width", "300px")
                                            .property("height", "100px")
                                            .property("background-color", "lightgray")
                                            .build()),
                         row(column(displayer(settings))),
                         row(column(external("mycomp"))),
                         row(column(html("<em> Generated at " + new Date() + "</em>"))));

        Path componentsPath = Paths.get(this.getClass().getResource("/components").getFile());
        DashboardExporter.export(dashboardBuilder(Arrays.asList(page)).componentsPath(componentsPath)
                                                                      .build(),
                                 "/tmp/dashbuilder/models/test.zip",
                                 ExportType.ZIP);

        // TODO: Deployer
        // COmponents config
        // DEMO: With components in the project

    }
}