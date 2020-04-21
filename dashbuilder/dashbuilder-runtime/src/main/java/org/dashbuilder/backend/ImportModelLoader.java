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

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.dashbuilder.shared.model.DashbuilderRuntimeMode;
import org.dashbuilder.shared.service.ImportModelRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uberfire.commons.services.cdi.Startup;

@Startup
@ApplicationScoped
public class ImportModelLoader {

    Logger logger = LoggerFactory.getLogger(ImportModelLoader.class);

    @Inject
    ImportModelRegistry importModelRegistry;

    @Inject
    RuntimeOptions runtimeOptions;

    @PostConstruct
    private void doInitialImport() {
        runtimeOptions.importFileLocation().ifPresent(importFile -> {
            logger.info("Importing file {}", importFile);
            importModelRegistry.setMode(DashbuilderRuntimeMode.STATIC);
            importModelRegistry.registerFile(importFile);
        });

        if (runtimeOptions.isMultipleImport() && !runtimeOptions.importFileLocation().isPresent()) {
            importModelRegistry.setMode(DashbuilderRuntimeMode.MULTIPLE_IMPORT);
        }
    }

}