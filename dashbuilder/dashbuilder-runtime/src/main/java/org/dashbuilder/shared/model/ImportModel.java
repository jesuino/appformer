package org.dashbuilder.shared.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jboss.errai.common.client.api.annotations.Portable;

/**
 * Text representation of dashbuilder dashboards required information
 *
 */
@Portable
public class ImportModel {

    List<DataSetContent> datasets;
    List<PerspectiveContent> perspectives;

    String navigationJSON;

    public ImportModel() {
        datasets = new ArrayList<>();
        perspectives = new ArrayList<>();
    }

    public Optional<String> getNavigationJSON() {
        return Optional.ofNullable(navigationJSON);
    }

    public void setNavigationJSON(String navigationJSON) {
        this.navigationJSON = navigationJSON;
    }

    public List<DataSetContent> getDatasets() {
        return datasets;
    }

    public List<PerspectiveContent> getPerspectives() {
        return perspectives;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((datasets == null) ? 0 : datasets.hashCode());
        result = prime * result + ((navigationJSON == null) ? 0 : navigationJSON.hashCode());
        result = prime * result + ((perspectives == null) ? 0 : perspectives.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ImportModel other = (ImportModel) obj;
        
        if (datasets == null && other.datasets != null ||
            datasets != null && other.datasets == null) {
            return false;
        } else if (!datasets.equals(other.datasets)) {
            return false;
        }

        if ((navigationJSON == null && other.navigationJSON != null) ||
            (navigationJSON != null && other.navigationJSON == null)) {
            return false;
        } else if (!navigationJSON.equals(other.navigationJSON)) {
            return false;
        }

        if (perspectives == null && other.perspectives != null ||
            perspectives != null && other.perspectives == null) {
            return false;
        } else if (!perspectives.equals(other.perspectives)) {
            return false;
        }
        return true;
    }

}
