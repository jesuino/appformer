package org.dashbuilder.dsl.model;

import org.uberfire.ext.layout.editor.api.editor.LayoutTemplate;

public class Page {

    LayoutTemplate layoutTemplate;

    Page(LayoutTemplate layoutTemplate) {
        this.layoutTemplate = layoutTemplate;
    }

    public static Page create(LayoutTemplate layoutTemplate) {
        return new Page(layoutTemplate);
    }

    public LayoutTemplate getLayoutTemplate() {
        return layoutTemplate;
    }

}