package org.dashbuilder.dsl.factory.component;

public class LogoBuilder extends ExternalComponentBuilder {

    private static final String LOGO_ID = "logo-provided";

    private static final String LOGO_URL_PROP = "src";
    private static final String LOGO_WIDTH_PROP = "width";
    private static final String LOGO_HEIGHT_PROP = "height";

    LogoBuilder(String src) {
        super(LOGO_ID);
        src(src);
    }

    public static LogoBuilder create(String src) {
        return new LogoBuilder(src);
    }

    public LogoBuilder src(String src) {
        componentProperty(LOGO_URL_PROP, src);
        return this;
    }

    public LogoBuilder width(String width) {
        componentProperty(LOGO_WIDTH_PROP, width);
        return this;
    }

    public LogoBuilder height(String height) {
        componentProperty(LOGO_HEIGHT_PROP, height);
        return this;
    }

}