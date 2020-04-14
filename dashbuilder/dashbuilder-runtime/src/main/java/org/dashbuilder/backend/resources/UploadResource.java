package org.dashbuilder.backend.resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

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

    private static final String BASE_PATH = System.getProperty("org.dashbuilder.base.dir", "/tmp/dashbuilder/");

    @PostConstruct
    public void createBaseDir() throws IOException {
        java.nio.file.Path baseDirPath = Paths.get(BASE_PATH);
        if (!Files.exists(baseDirPath)) {
            Files.createDirectory(baseDirPath);
        }
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String uploadFile(@MultipartForm FileUploadModel form) throws IOException {
        logger.info("Uploading file with size {} bytes", form.getFileData().length);
        String fileName = System.currentTimeMillis() + ".zip";
        String filePath = BASE_PATH.concat(fileName);

        // TODO: validate file?
        // TODO: limit size?

        Files.write(Paths.get(filePath), form.getFileData());
        return filePath;
    }

}
