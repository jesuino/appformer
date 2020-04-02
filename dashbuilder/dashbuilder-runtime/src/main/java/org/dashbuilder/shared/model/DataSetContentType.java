package org.dashbuilder.shared.model;

import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public enum DataSetContentType {

    DEFINITION,
    CSV;

    public static DataSetContentType fromFileExtension(String ext) {
        if (ext.equalsIgnoreCase("dset")) {
            return DEFINITION;
        }

        if (ext.equalsIgnoreCase("csv")) {
            return CSV;
        }

        throw new IllegalArgumentException("Extension not supported: " + ext);
    }

}
