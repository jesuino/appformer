package org.dashbuilder.dsl.factory.page;

import org.dashbuilder.dsl.model.Column;
import org.dashbuilder.dsl.model.Component;
import org.dashbuilder.dsl.model.Row;
import org.uberfire.ext.layout.editor.api.editor.LayoutColumn;

public class ColumnBuilder extends AbstractLayoutBuilder<ColumnBuilder> {

    private static final String DEFAULT_SPAN = "0";

    LayoutColumn layoutColumn;

    ColumnBuilder(String span) {
        this.layoutColumn = new LayoutColumn(span);
    }

    public static ColumnBuilder newBuilder() {
        return newBuilder(DEFAULT_SPAN);
    }

    public static ColumnBuilder newBuilder(String span) {
        return new ColumnBuilder(span);
    }

    public ColumnBuilder row(Row row) {
        this.layoutColumn.addRow(row.getLayoutRow());
        return this;
    }

    public ColumnBuilder rows(Row... rows) {
        for (Row row : rows) {
            this.row(row);
        }
        return this;
    }

    public ColumnBuilder component(Component component) {
        this.layoutColumn.add(component.getLayoutComponent());
        return this;
    }

    public ColumnBuilder components(Component... components) {
        for (Component component : components) {
            component(component);
        }
        return this;
    }

    public Column build() {
        return Column.create(this.layoutColumn);
    }

    @Override
    protected void _addProperty(String key, String value) {
        this.layoutColumn.getProperties().put(key, value);
    }

}