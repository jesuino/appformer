package org.dashbuilder.backend.resources;

import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class FileUploadModel {

    private byte[] fileData;

    public byte[] getFileData() {
        return fileData;
    }

    @FormParam("selectedFile")
    @PartType("application/octet-stream")
    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}