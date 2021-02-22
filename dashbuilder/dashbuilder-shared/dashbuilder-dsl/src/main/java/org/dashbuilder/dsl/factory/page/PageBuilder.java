package org.dashbuilder.dsl.factory.page;

import org.dashbuilder.dsl.model.Page;
import org.dashbuilder.dsl.model.Row;
import org.uberfire.ext.layout.editor.api.editor.LayoutTemplate;
import org.uberfire.ext.layout.editor.api.editor.LayoutTemplate.Style;

public class PageBuilder extends AbstractLayoutBuilder<PageBuilder> {

    LayoutTemplate layoutTemplate;

    private PageBuilder(String name) {
        this.layoutTemplate = new LayoutTemplate(name);
    }

    public static PageBuilder newBuilder(String name) {
        return new PageBuilder(name);
    }

    public PageBuilder name(String name) {
        this.layoutTemplate.setName(name);
        return this;
    }

    public PageBuilder style(Style style) {
        this.layoutTemplate.setStyle(style);
        return this;
    }

    public PageBuilder row(Row row) {
        this.layoutTemplate.addRow(row.getLayoutRow());
        return this;
    }

    public PageBuilder rows(Row... rows) {
        for (Row row : rows) {
            this.row(row);
        }
        return this;
    }

    public Page build() {
        return Page.create(this.layoutTemplate);
    }

    @Override
    protected void _addProperty(String key, String value) {
        this.layoutTemplate.addLayoutProperty(key, value);
    }

}