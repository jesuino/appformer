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

import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.dashbuilder.shared.model.DashbuilderRuntimeMode;
import org.dashbuilder.shared.service.ImportModelRegistry;
import org.jboss.logging.Logger;
import org.uberfire.commons.services.cdi.Startup;

@Startup
@ApplicationScoped
public class ImportModelLoader {

    private static final String DASHBUILDER_RUNTIME_MULTIPLE_IMPORT = "dashbuilder.runtime.multiple";

    private static final String IMPORT_FILE_LOCATION_PROP = "dashbuilder.runtime.import";
    
    Logger logger = Logger.getLogger(ImportModelLoader.class);

    @Inject
    ImportModelRegistry importModelRegistry;

    @PostConstruct
    private void doImport() {
        String importFile = System.getProperty(IMPORT_FILE_LOCATION_PROP, "/home/wsiqueir/Downloads/export_full.zip");
        if (importFile != null) {
            importModelRegistry.setMode(DashbuilderRuntimeMode.SINGLE_IMPORT);
            try (FileInputStream fis = new FileInputStream(importFile)) {
                importModelRegistry.store(importFile, fis);
            } catch (IOException e) {
                logger.error("Not able to load file {}", importFile, e);
            }
        }
        if (Boolean.getBoolean(DASHBUILDER_RUNTIME_MULTIPLE_IMPORT)) {
            importModelRegistry.setMode(DashbuilderRuntimeMode.MULTIPLE_IMPORT);
        }
    }

}
