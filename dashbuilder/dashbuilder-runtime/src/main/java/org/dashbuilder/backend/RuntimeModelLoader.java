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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.dashbuilder.shared.model.DashbuilderRuntimeMode;
import org.dashbuilder.shared.service.RuntimeModelRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uberfire.commons.services.cdi.Startup;

import static org.dashbuilder.backend.RuntimeOptions.DASHBOARD_EXTENSION;

/**
 * Responsible for runtime model files loading.
 *
 */
@Startup
@ApplicationScoped
public class RuntimeModelLoader {

    Logger logger = LoggerFactory.getLogger(RuntimeModelLoader.class);

    @Inject
    RuntimeModelRegistry runtimeModelRegistry;

    @Inject
    RuntimeOptions runtimeOptions;

    @PostConstruct
    private void doInitialImport() {
        createBaseDir();
        runtimeOptions.importFileLocation().ifPresent(importFile -> {
            logger.info("Importing file {}", importFile);
            runtimeModelRegistry.registerFile(importFile);
            runtimeModelRegistry.setMode(DashbuilderRuntimeMode.STATIC);
        });

        if (runtimeOptions.isMultipleImport() && !runtimeOptions.importFileLocation().isPresent()) {
            runtimeModelRegistry.setMode(DashbuilderRuntimeMode.MULTIPLE_IMPORT);
            loadAvailableModels();
        }
    }

    /**
     * Create, if do not exist, the base directory for runtime models
     */
    private void createBaseDir() {
        java.nio.file.Path baseDirPath = Paths.get(runtimeOptions.getImportsBaseDir());
        if (!baseDirPath.toFile().exists()) {
            try {
                Files.createDirectory(baseDirPath);
            } catch (IOException e) {
                logger.debug("Error creating base directory for dashboards: {}", baseDirPath, e);
                throw new RuntimeException("Base directory for dashboards could not be created: " + baseDirPath, e);
            }
        } else {
            logger.info("Base directory for dashboards already exist: {}", runtimeOptions.getImportsBaseDir());
        }
    }

    private void loadAvailableModels() {
        logger.info("Registering existing models");
        try (Stream<java.nio.file.Path> walk = Files.walk(Paths.get(runtimeOptions.getImportsBaseDir()), 1)) {
            walk.filter(p -> p.toFile().isFile())
                .filter(p -> p.toString().toLowerCase().endsWith(DASHBOARD_EXTENSION))
                .map(p -> p.toString())
                .peek(p -> logger.info("Registering {}", p))
                .peek(runtimeModelRegistry::registerFile)
                .forEach(p -> logger.info("Sucessfully Registered {}", p));

        } catch (Exception e) {
            logger.info("Error Registering existing models");
            logger.debug("Error Registering existing models.", e);
            throw new RuntimeException("Error registering existing models.", e);
        }

    }

}