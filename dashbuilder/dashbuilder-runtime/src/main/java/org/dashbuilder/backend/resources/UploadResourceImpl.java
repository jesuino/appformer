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

package org.dashbuilder.backend.resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.dashbuilder.backend.RuntimeOptions;
import org.dashbuilder.shared.resources.ResourceDefinitions;
import org.dashbuilder.shared.service.ImportValidationService;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Resource to receive new imports
 *
 */
@ApplicationScoped
@Path(ResourceDefinitions.UPLOAD_RESOURCE)
public class UploadResourceImpl {

    Logger logger = LoggerFactory.getLogger(UploadResourceImpl.class);

    @Inject
    RuntimeOptions runtimeOptions;

    @Inject
    ImportValidationService importValidationService;

    @PostConstruct
    public void createBaseDir() throws IOException {
        java.nio.file.Path baseDirPath = Paths.get(runtimeOptions.getImportsBaseDir());
        if (!Files.exists(baseDirPath)) {
            Files.createDirectory(baseDirPath);
        }
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String uploadFile(@MultipartForm FileUploadModel form) throws IOException {
        checkUploadSize(form);

        logger.info("Uploading file with size {} bytes", form.getFileData().length);

        String fileId = System.currentTimeMillis() + "";
        String filePath = String.join("/", runtimeOptions.getImportsBaseDir(), fileId).concat(".zip");
        java.nio.file.Path path = Paths.get(filePath);

        Files.write(path, form.getFileData());

        validateImportContent(path);

        return fileId;
    }

    private void validateImportContent(java.nio.file.Path path) throws IOException {
        String filePath = path.toFile().getPath();
        if (!importValidationService.validate(filePath)) {
            Files.delete(path);
            throw new WebApplicationException("Not a valid file structure.", Status.BAD_REQUEST);
        }
    }

    private void checkUploadSize(FileUploadModel form) {
        int uploadSize = form.getFileData().length;
        if (uploadSize > runtimeOptions.getUploadSize()) {
            logger.warn("Stopping upload of size {}. Max size is {}", uploadSize, runtimeOptions.getUploadSize());
            throw new WebApplicationException("Upload is too big.", Status.BAD_REQUEST);
        }
    }

}