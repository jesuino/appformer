package org.dashbuilder.dsl.model;

import org.uberfire.ext.layout.editor.api.editor.LayoutRow;

public class Row {

    LayoutRow layoutRow;

    Row(LayoutRow layoutRow) {
        this.layoutRow = layoutRow;
    }

    public static Row create(LayoutRow layoutRow) {
        return new Row(layoutRow);
    }

    public LayoutRow getLayoutRow() {
        return layoutRow;
    }

}