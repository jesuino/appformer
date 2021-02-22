package org.dashbuilder.dsl.factory.dashboard;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import org.dashbuilder.dataset.def.DataSetDef;
import org.dashbuilder.dsl.factory.navigation.NavigationFactory;
import org.dashbuilder.dsl.model.Dashboard;
import org.dashbuilder.dsl.model.Navigation;
import org.dashbuilder.dsl.model.Page;

public class DashboardBuilder {

    private List<Page> pages = Collections.emptyList();
    private List<DataSetDef> dataSets = Collections.emptyList();
    private Navigation navigation = NavigationFactory.emptyNavigation();
    private Path componentsPath;

    DashboardBuilder(List<Page> pages) {
        this.pages = pages;
    }

    public static DashboardBuilder newBuilder(List<Page> pages) {
        return new DashboardBuilder(pages);
    }

    public DashboardBuilder pages(List<Page> pages) {
        this.pages = pages;
        return this;
    }

    public DashboardBuilder dataSets(List<DataSetDef> dataSets) {
        this.dataSets = dataSets;
        return this;
    }

    public DashboardBuilder navigation(Navigation navigation) {
        this.navigation = navigation;
        return this;
    }

    public DashboardBuilder componentsPath(Path componentsPath) {
        this.componentsPath = componentsPath;
        return this;
    }

    public Dashboard build() {
        return Dashboard.of(pages, dataSets, navigation, componentsPath);
    }

}