package org.dashbuilder.dsl.factory.page;

import org.dashbuilder.dsl.model.Column;
import org.dashbuilder.dsl.model.Component;
import org.dashbuilder.dsl.model.Page;
import org.dashbuilder.dsl.model.Row;

import static org.dashbuilder.dsl.factory.component.ComponentFactory.html;

public class PageFactory {

    private PageFactory() {
        // empty
    }

    public static PageBuilder pageBuilder(String name) {
        return PageBuilder.newBuilder(name);
    }

    public static RowBuilder rowBuilder(String height) {
        return RowBuilder.newBuilder(height);
    }

    public static ColumnBuilder columnBuilder() {
        return ColumnBuilder.newBuilder();
    }

    public static ColumnBuilder columnBuilder(String span) {
        return ColumnBuilder.newBuilder(span);
    }

    public static ColumnBuilder columnBuilder(String span, Component... components) {
        return ColumnBuilder.newBuilder(span).components(components);
    }

    public static Page page(String name, Row... rows) {
        return PageBuilder.newBuilder(name).rows(rows).build();
    }

    public static Column column(String span, Component... components) {
        return ColumnBuilder.newBuilder(span).components(components).build();
    }
    
    public static Column column(Component... components) {
        return ColumnBuilder.newBuilder().components(components).build();
    }
    
    public static Column column(Component component) {
        return ColumnBuilder.newBuilder().components(component).build();
    }

    public static Column column(Row row) {
        return ColumnBuilder.newBuilder().row(row).build();
    }

    public static Column column(Row... rows) {
        return ColumnBuilder.newBuilder().rows(rows).build();
    }

    public static Row row(Column... columns) {
        return RowBuilder.newBuilder().columns(columns).build();
    }

    /**
     * 
     * Creates a column with a single html component
     * @param html
     * @return
     */
    public static Column column(String html) {
        return ColumnBuilder.newBuilder().component(html(html)).build();
    }

    /**
     * 
     * Creates a row with a single colunm and a single html component
     * @param html
     * @return
     */
    public static Row row(String html) {
        return row(html(html));
    }

    /**
     * 
     * Creates a row with a single colunm with the provided component
     * @param html
     * @return
     */
    public static Row row(Component component) {
        return row(column(component));
    }

}