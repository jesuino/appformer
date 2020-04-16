package org.dashbuilder.backend;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

/**
 * Holds Runtime System properties
 *
 */
@ApplicationScoped
public class RuntimeOptions {

    private static final String IMPORTS_BASE_DIR_PROP = "org.dashbuilder.import.base.dir";

    private static final String DASHBUILDER_RUNTIME_MULTIPLE_IMPORT = "dashbuilder.runtime.multiple";

    private static final String IMPORT_FILE_LOCATION_PROP = "dashbuilder.runtime.import";

    private boolean multipleImport;
    private String importFileLocation;
    private String importsBaseDir;

    @PostConstruct
    public void init() {
        String multipleImportStr = System.getProperty(DASHBUILDER_RUNTIME_MULTIPLE_IMPORT, "false");
        importFileLocation = System.getProperty(IMPORT_FILE_LOCATION_PROP);
        importsBaseDir = System.getProperty(IMPORTS_BASE_DIR_PROP, "/tmp/dashbuilder");
        multipleImport = Boolean.parseBoolean(multipleImportStr);
    }

    public boolean isMultipleImport() {
        return multipleImport;
    }

    public Optional<String> importFileLocation() {
        return Optional.ofNullable(importFileLocation);
    }

    public String getImportsBaseDir() {
        return importsBaseDir;
    }

}