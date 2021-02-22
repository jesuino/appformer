package org.dashbuilder.dsl;

import java.util.Arrays;

import org.dashbuilder.dataset.ColumnType;
import org.dashbuilder.dataset.DataSet;
import org.dashbuilder.dsl.model.Page;
import org.dashbuilder.dsl.serialization.DashboardExporter;
import org.dashbuilder.dsl.serialization.DashboardExporter.ExportType;
import org.junit.Test;

import static org.dashbuilder.dataset.DataSetFactory.newDataSetBuilder;
import static org.dashbuilder.displayer.DisplayerSettingsFactory.newExternalDisplayerSettings;
import static org.dashbuilder.dsl.factory.component.ComponentFactory.allProcessesHeatmap;
import static org.dashbuilder.dsl.factory.component.ComponentFactory.processHeatmap;
import static org.dashbuilder.dsl.factory.dashboard.DashboardFactory.dashboard;
import static org.dashbuilder.dsl.factory.page.PageFactory.page;
import static org.dashbuilder.dsl.factory.page.PageFactory.row;

public class HeatmapDashboard {

    @Test
    public void test() throws Exception {
        DataSet heatmapDS = newDataSetBuilder().column("nid", ColumnType.LABEL)
                                               .column("value", ColumnType.NUMBER)
                                               .row("_06FE5C4E-B2EF-4FD8-A389-1BFAD566FE59", "1")
                                               .row("_AB431E82-86BC-460F-9D8B-7A7617565B36", "1")
                                               .buildDataSet();

        DataSet heatmapsDS = newDataSetBuilder().column("containerid", ColumnType.LABEL)
                                                .column("processid", ColumnType.LABEL)
                                                .column("nid", ColumnType.LABEL)
                                                .column("value", ColumnType.NUMBER)
                                                .row("evaluation_1.0.0-SNAPSHOT", "evaluation", "_06FE5C4E-B2EF-4FD8-A389-1BFAD566FE59", "1")
                                                .row("evaluation_1.0.0-SNAPSHOT", "evaluation", "_AB431E82-86BC-460F-9D8B-7A7617565B36", "1")
                                                .row("mortgage-process_1.0.0-SNAPSHOT", "Mortgage_Process.MortgageApprovalProcess", "_06FE5C4E-B2EF-4FD8-A389-1BFAD566FE59", "1")
                                                .row("mortgage-process_1.0.0-SNAPSHOT", "Mortgage_Process.MortgageApprovalProcess", "_AB431E82-86BC-460F-9D8B-7A7617565B36", "1")
                                                .buildDataSet();

        Page page = page("Heatmap Test",
                         row(processHeatmap("sample-server",
                                            "evaluation_1.0.0-SNAPSHOT",
                                            "evaluation",
                                            newExternalDisplayerSettings().dataset(heatmapDS)
                                                                          .height(500)
                                                                          .width(300)
                                                                          .buildSettings())),
                         row(allProcessesHeatmap("sample-server",
                                                 newExternalDisplayerSettings().dataset(heatmapsDS)
                                                                               .height(500)
                                                                               .width(300)
                                                                               .buildSettings())));

        DashboardExporter.export(dashboard(Arrays.asList(page)),
                                 "/tmp/dashbuilder/models/heatmap-test.zip",
                                 ExportType.ZIP);

    }
}