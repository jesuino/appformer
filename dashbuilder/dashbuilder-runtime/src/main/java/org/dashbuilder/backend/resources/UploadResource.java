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
import javax.ws.rs.core.MediaType;

import org.dashbuilder.backend.RuntimeOptions;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Resource to receive new imports
 *
 */
@Path("upload")
@ApplicationScoped
public class UploadResource {

    Logger logger = LoggerFactory.getLogger(UploadResource.class);

    @Inject
    RuntimeOptions runtimeOptions;

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
        logger.info("Uploading file with size {} bytes", form.getFileData().length);

        // TODO: validate file?
        // TODO: limit size?

        String fileId = System.currentTimeMillis() + "";
        String filePath = String.join("/", runtimeOptions.getImportsBaseDir(), fileId).concat(".zip");
        Files.write(Paths.get(filePath), form.getFileData());
        return fileId;
    }

}
