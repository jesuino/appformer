package org.dashbuilder.shared.model;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class DataSetContent {

    private String id;
    private String content;
    private DataSetContentType contentType;

    public DataSetContent() {}

    public DataSetContent(@MapsTo("id") String id,
                          @MapsTo("content") String content,
                          @MapsTo("contentType") DataSetContentType contentType) {
        this.id = id;
        this.content = content;
        this.contentType = contentType;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public DataSetContentType getContentType() {
        return contentType;
    }

}
