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

package org.dashbuilder.backend.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.enterprise.context.ApplicationScoped;

import org.dashbuilder.shared.model.DataSetContent;
import org.dashbuilder.shared.model.DataSetContentType;
import org.dashbuilder.shared.model.ImportModel;
import org.dashbuilder.shared.model.PerspectiveContent;
import org.dashbuilder.shared.service.ImportModelParser;

import static org.dashbuilder.shared.model.ImportDefinitions.DATASET_PREFIX;
import static org.dashbuilder.shared.model.ImportDefinitions.NAVIGATION_FILE;
import static org.dashbuilder.shared.model.ImportDefinitions.PERSPECTIVE_SUFFIX;

/**
 * Parses a exported zip file from Transfer Services into ImportModel.
 *
 */
@ApplicationScoped
public class ImportModelParserImpl implements ImportModelParser {

    @Override
    public ImportModel parse(InputStream is) {
        try {
            return retrieveImportModel(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected ImportModel retrieveImportModel(InputStream is) throws IOException {
        ImportModel importModel = new ImportModel();
        try (ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry = zis.getNextEntry();
            while (entry != null) {
                if (!entry.isDirectory()) {
                    String entryName = entry.getName();
                    if (entryName.startsWith(DATASET_PREFIX)) {
                        DataSetContent dataSetContent = retrieveDataSetContent(entry, zis);
                        importModel.getDatasets().add(dataSetContent);
                    }

                    if (entryName.endsWith(PERSPECTIVE_SUFFIX)) {
                        PerspectiveContent perspectiveContent = retrievePerspectiveContent(entry, zis);
                        importModel.getPerspectives().add(perspectiveContent);
                    }

                    if (entryName.equalsIgnoreCase(NAVIGATION_FILE)) {
                        String navigationJSON = entryContent(entry, zis);
                        importModel.setNavigationJSON(navigationJSON);
                    }
                }
                entry = zis.getNextEntry();
            }
        }
        return importModel;
    }

    private PerspectiveContent retrievePerspectiveContent(ZipEntry entry, ZipInputStream zis) {
        String id = entry.getName().split("/")[2];
        String content = entryContent(entry, zis);
        return new PerspectiveContent(id, content);
    }

    private DataSetContent retrieveDataSetContent(ZipEntry entry, ZipInputStream zis) {
        String fileName = entry.getName().split("/")[3];
        String[] nameParts = fileName.split("\\.");
        String id = nameParts[0];
        String ext = nameParts[1];
        String content = entryContent(entry, zis);
        return new DataSetContent(id, content, DataSetContentType.fromFileExtension(ext));
    }

    private String entryContent(ZipEntry entry, ZipInputStream zis) {
        try {
            final int BUFFER_SIZE = 1024;
            byte[] buffer = new byte[BUFFER_SIZE];
            int read = 0;
            String output = "";
            while ((read = zis.read(buffer, 0, BUFFER_SIZE)) >= 0) {
                output = output.concat(new String(buffer, 0, read));
            }
            return output.trim();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
