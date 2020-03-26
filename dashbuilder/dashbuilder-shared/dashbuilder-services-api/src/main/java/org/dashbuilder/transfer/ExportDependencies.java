package org.dashbuilder.transfer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dashbuilder.dataset.def.DataSetDef;

/**
 * Hold dependencies for an export
 *
 */
public class ExportDependencies {

    private String pageName;

    private Set<String> datasets = new HashSet<>();

    private Set<String> pages = new HashSet<>();

    public ExportDependencies() {}

    public static ExportDependencies navigationDependencies() {
        return new ExportDependencies(null);
    }

    public static ExportDependencies pageDependencies(String pageName) {
        return new ExportDependencies(pageName);
    }

    private ExportDependencies(String pageName) {
        this.pageName = pageName;
    }

    public String getPageName() {
        return pageName;
    }

    public Set<String> getDatasets() {
        return datasets;
    }

    public Set<String> getPages() {
        return pages;
    }

}
