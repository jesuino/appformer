package org.dashbuilder.dsl.factory.component;

public class HtmlComponentBuilder extends AbstractComponentBuilder<HtmlComponentBuilder> {

    private static final String DRAG_TYPE = "org.uberfire.ext.plugin.client.perspective.editor.layout.editor.HTMLLayoutDragComponent";

    private static final String HTML_CODE_PROP = "HTML_CODE";

    public static HtmlComponentBuilder create() {
        return new HtmlComponentBuilder();
    }

    public HtmlComponentBuilder html(String html) {
        addProperty(HTML_CODE_PROP, html);
        return this;
    }

    @Override
    String getDragType() {
        return DRAG_TYPE;
    }

}