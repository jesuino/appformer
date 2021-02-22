package org.dashbuilder.dsl.factory.page;

import java.util.HashMap;

import org.dashbuilder.dsl.model.Column;
import org.dashbuilder.dsl.model.Row;
import org.uberfire.ext.layout.editor.api.editor.LayoutRow;

public class RowBuilder extends AbstractLayoutBuilder<RowBuilder> {
    
    private static final String DEFAULT_HEIGHT = "12";
    

    LayoutRow layoutRow;

    private RowBuilder(String height) {
        this.layoutRow = new LayoutRow(height, new HashMap<>());
    }

    public static RowBuilder newBuilder() {
        return newBuilder(DEFAULT_HEIGHT);
    }
    
    public static RowBuilder newBuilder(String height) {
        return new RowBuilder(height);
    }

    public RowBuilder property(String key, String value) {
        this.layoutRow.getProperties().put(key, value);
        return this;
    }

    public RowBuilder columns(Column... columns) {
        for (Column column : columns) {
            this.column(column);
        }
        return this;
    }

    public RowBuilder column(Column column) {
        this.layoutRow.add(column.getLayoutColumn());
        return this;
    }

    public Row build() {
        return Row.create(this.layoutRow);
    }

    @Override
    protected void _addProperty(String key, String value) {
        this.layoutRow.getProperties().put(key, value);
    }

}
