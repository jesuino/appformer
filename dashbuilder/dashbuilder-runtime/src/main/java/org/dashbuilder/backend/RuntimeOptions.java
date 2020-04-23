/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dashbuilder.backend;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Holds Runtime System properties
 *
 */
@ApplicationScoped
public class RuntimeOptions {

    Logger logger = LoggerFactory.getLogger(RuntimeOptions.class);

    private static final String IMPORTS_BASE_DIR_PROP = "org.dashbuilder.import.base.dir";

    private static final String IMPORT_FILE_LOCATION_PROP = "dashbuilder.runtime.import";

    private static final String UPLOAD_SIZE_PROP = "dashbuilder.runtime.upload.size";

    private static final String DASHBUILDER_RUNTIME_MULTIPLE_IMPORT = "dashbuilder.runtime.multiple";

    private static final int DEFAULT_UPLOAD_SIZE = 96 * 1024;

    private boolean multipleImport;
    private String importFileLocation;
    private String importsBaseDir;
    private int uploadSize;

    @PostConstruct
    public void init() {
        String multipleImportStr = System.getProperty(DASHBUILDER_RUNTIME_MULTIPLE_IMPORT, "false");
        importFileLocation = System.getProperty(IMPORT_FILE_LOCATION_PROP);
        importsBaseDir = System.getProperty(IMPORTS_BASE_DIR_PROP, "/tmp/dashbuilder");
        multipleImport = Boolean.parseBoolean(multipleImportStr);
        uploadSize = DEFAULT_UPLOAD_SIZE;
        
        String uploadSizeStr = System.getProperty(UPLOAD_SIZE_PROP);
        if (uploadSizeStr != null) {
            try {
                uploadSize = 1024 * Integer.parseInt(uploadSizeStr);
            } catch (Exception e) {
                logger.warn("Not able to parse upload size {}", uploadSizeStr);
                logger.debug("Not able to parse upload size {}", uploadSizeStr, e);
            }
        }
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

    public int getUploadSize() {
        return uploadSize;
    }

}