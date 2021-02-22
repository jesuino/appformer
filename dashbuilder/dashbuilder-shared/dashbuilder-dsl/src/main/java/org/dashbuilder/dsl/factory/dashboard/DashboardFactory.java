package org.dashbuilder.dsl.factory.dashboard;

import java.util.List;

import org.dashbuilder.dataset.def.DataSetDef;
import org.dashbuilder.dsl.model.Dashboard;
import org.dashbuilder.dsl.model.Navigation;
import org.dashbuilder.dsl.model.Page;

public class DashboardFactory {

    private DashboardFactory() {
        // empty
    }

    public static Dashboard dashboard(List<Page> pages) {
        return DashboardBuilder.newBuilder(pages).build();
    }

    public static DashboardBuilder dashboardBuilder(List<Page> pages) {
        return DashboardBuilder.newBuilder(pages);
    }

    public static Dashboard dashboard(List<Page> pages, List<DataSetDef> dataSets) {
        return DashboardBuilder.newBuilder(pages).dataSets(dataSets).build();
    }

    public static Dashboard dashboard(List<Page> pages, List<DataSetDef> dataSets, Navigation navigation) {
        return DashboardBuilder.newBuilder(pages).dataSets(dataSets).navigation(navigation).build();
    }

}
