package org.dashbuilder.backend.remote.services.dummy;

import java.util.Collection;
import java.util.Collections;

import javax.enterprise.context.ApplicationScoped;

import org.dashbuilder.navigation.layout.LayoutTemplateContext;
import org.dashbuilder.navigation.layout.LayoutTemplateInfo;
import org.dashbuilder.navigation.service.PerspectivePluginServices;
import org.jboss.errai.bus.server.annotations.Service;
import org.uberfire.ext.layout.editor.api.editor.LayoutTemplate;
import org.uberfire.ext.plugin.model.Plugin;

@Service
@ApplicationScoped
public class DummyPerspectivePluginServices implements PerspectivePluginServices {

    @Override
    public Collection<Plugin> listPlugins() {
        return Collections.emptyList();
    }

    @Override
    public Plugin getPerspectivePlugin(String perspectiveName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LayoutTemplate getLayoutTemplate(String perspectiveName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LayoutTemplate getLayoutTemplate(Plugin perspectivePlugin) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LayoutTemplateInfo getLayoutTemplateInfo(String perspectiveName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LayoutTemplateInfo getLayoutTemplateInfo(Plugin perspectivePlugin, LayoutTemplateContext layoutCtx) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LayoutTemplateInfo getLayoutTemplateInfo(LayoutTemplate layoutTemplate) {
        // TODO Auto-generated method stub
        return null;
    }

}
