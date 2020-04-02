package org.dashbuilder.shared.event;

import java.util.List;

import org.dashbuilder.shared.model.DataSetContent;

public class NewDataSetContentEvent {

    List<DataSetContent> content;

    public NewDataSetContentEvent(List<DataSetContent> content) {
        this.content = content;
    }

    public List<DataSetContent> getContent() {
        return content;
    }

}