package org.dashbuilder.dsl.model;

import org.uberfire.ext.layout.editor.api.editor.LayoutColumn;

public class Column {

    LayoutColumn layoutColumn;

    Column(LayoutColumn layoutColumn) {
        this.layoutColumn = layoutColumn;
    }

    public static Column create(LayoutColumn layoutColumn) {
        return new Column(layoutColumn);
    }

    public LayoutColumn getLayoutColumn() {
        return layoutColumn;
    }

}