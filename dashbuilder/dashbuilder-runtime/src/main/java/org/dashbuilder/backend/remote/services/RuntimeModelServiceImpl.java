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

package org.dashbuilder.backend.remote.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.dashbuilder.backend.RuntimeOptions;
import org.dashbuilder.backend.navigation.RuntimeNavigationBuilder;
import org.dashbuilder.shared.model.RuntimeModel;
import org.dashbuilder.shared.service.RuntimeModelRegistry;
import org.dashbuilder.shared.service.RuntimeModelService;
import org.jboss.errai.bus.server.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uberfire.commons.data.Pair;

@Service
@ApplicationScoped
public class RuntimeModelServiceImpl implements RuntimeModelService {

    Logger logger = LoggerFactory.getLogger(RuntimeModelServiceImpl.class);

    @Inject
    RuntimeModelRegistry importModelRegistry;

    @Inject
    RuntimeNavigationBuilder runtimeNavigationBuilder;

    @Inject
    RuntimeOptions runtimeOptions;

    @Override
    public Optional<RuntimeModel> getRuntimeModel(String exportId) {
        if (exportId == null) {
            return importModelRegistry.single();
        }

        Optional<RuntimeModel> runtimeModelOp = importModelRegistry.get(exportId);
        if (runtimeModelOp.isPresent()) {
            return runtimeModelOp;
        }

        // if it is an existing file
        Optional<String> modelPath = runtimeOptions.modelPath(exportId);
        if (modelPath.isPresent()) {
            return importModelRegistry.registerFile(modelPath.get());
        }

        // if it is an external file
        if (runtimeOptions.isAllowExternal()) {
            // This logic could move to a new file like modelIO which could be used in registry instead here
            String newImportPath = null;
            try {
                URL url = new URL(exportId);
                newImportPath = downloadFile(url);
                return importModelRegistry.registerFile(newImportPath);
            } catch (Exception e) {
                removeTempFile(newImportPath);
                throw new IllegalArgumentException("Error downloading file " + exportId, e);
            }
        }

        return Optional.empty();
    }

    private String downloadFile(URL url) throws Exception {
        // consider generate better model ids for URLs
        final String modelId = Math.abs(url.toURI().hashCode()) + "";
        final String filePath = runtimeOptions.buildFilePath(modelId);
        int totalBytes = 0;
        final int pageSize = 1024;
        try (BufferedInputStream in = new BufferedInputStream(url.openStream());
                FileOutputStream fos = new FileOutputStream(filePath)) {
            byte[] dataBuffer = new byte[pageSize];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, pageSize)) != -1) {
                fos.write(dataBuffer, 0, bytesRead);
                totalBytes += pageSize;
                if (totalBytes > runtimeOptions.getUploadSize()) {
                    Files.deleteIfExists(Paths.get(filePath));
                    logger.error("Size file is bigger than max upload size {}", runtimeOptions.getUploadSize());
                    throw new IllegalArgumentException("External file size is too big.");
                }
            }
            return filePath;
        } catch (IOException e) {
            throw new IllegalArgumentException("Not able to download file", e);
        }
    }

    private void removeTempFile(String filePath) {
        if (filePath != null) {
            try {
                Files.deleteIfExists(Paths.get(filePath));
            } catch (Exception e) {
                logger.error("Error deleting file", e);
            }
        }
    }

}