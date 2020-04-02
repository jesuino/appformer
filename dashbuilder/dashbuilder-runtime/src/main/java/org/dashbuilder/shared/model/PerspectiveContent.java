package org.dashbuilder.shared.model;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class PerspectiveContent {

    private String id;
    private String content;

    public PerspectiveContent() {}

    public PerspectiveContent(@MapsTo("id") String id, 
                              @MapsTo("content") String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

}
