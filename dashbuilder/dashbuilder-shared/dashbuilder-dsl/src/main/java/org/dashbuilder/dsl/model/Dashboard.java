package org.dashbuilder.dsl.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.dashbuilder.dataset.def.DataSetDef;

public class Dashboard {

    private List<Page> pages;
    private List<DataSetDef> dataSets;
    private Navigation navigation;
    private Path componentsPath;

    private Dashboard(List<Page> pages, List<DataSetDef> dataSets, Navigation navigation, Path componentsPath) {
        this.componentsPath = componentsPath;
        this.pages = pages;
        this.dataSets = dataSets;
        this.navigation = navigation;
    }

    public static Dashboard of(List<Page> pages, List<DataSetDef> dataSets, Navigation navigation, Path componentsPath) {
        return new Dashboard(pages, dataSets, navigation, componentsPath);
    }

    public List<Page> getPages() {
        return pages;
    }

    public List<DataSetDef> getDataSets() {
        return dataSets;
    }

    public Navigation getNavigation() {
        return navigation;
    }

    public Optional<Path> getComponentsPath() {
        return Optional.ofNullable(componentsPath);
    }

}