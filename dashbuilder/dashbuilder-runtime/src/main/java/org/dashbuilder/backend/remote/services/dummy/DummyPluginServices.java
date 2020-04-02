package org.dashbuilder.backend.remote.services.dummy;

import java.util.Collection;
import java.util.Collections;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.errai.bus.server.annotations.Service;
import org.uberfire.backend.vfs.Path;
import org.uberfire.ext.editor.commons.file.DefaultMetadata;
import org.uberfire.ext.plugin.model.DynamicMenu;
import org.uberfire.ext.plugin.model.LayoutEditorModel;
import org.uberfire.ext.plugin.model.Media;
import org.uberfire.ext.plugin.model.Plugin;
import org.uberfire.ext.plugin.model.PluginContent;
import org.uberfire.ext.plugin.model.PluginType;
import org.uberfire.ext.plugin.model.RuntimePlugin;
import org.uberfire.ext.plugin.service.PluginServices;

/**
 * This should removed as soon as PluginServices cliend side mocked service starts working.
 *
 */
@Service
@ApplicationScoped
public class DummyPluginServices implements PluginServices {

    @Override
    public void delete(Path path, String comment) {

    }

    @Override
    public Path copy(Path path, String newName, String comment) {
        return null;
    }

    @Override
    public Path copy(Path path, String newName, Path targetDirectory, String comment) {
        return null;
    }

    @Override
    public Path saveAndRename(Path path, String newFileName, DefaultMetadata metadata, Plugin content, String comment) {
        return null;
    }

    @Override
    public Path rename(Path path, String newName, String comment) {
        return null;
    }

    @Override
    public Path save(Path path, Plugin content, DefaultMetadata metadata, String comment) {
        return null;
    }

    @Override
    public String getMediaServletURI() {
        return null;
    }

    @Override
    public Collection<RuntimePlugin> listRuntimePlugins() {
        return Collections.emptyList();
    }

    @Override
    public Collection<RuntimePlugin> listPluginRuntimePlugins(Path pluginPath) {
        return Collections.emptyList();
    }

    @Override
    public Collection<Plugin> listPlugins() {
        return Collections.emptyList();
    }

    @Override
    public Collection<Plugin> listPlugins(PluginType type) {
        return Collections.emptyList();
    }

    @Override
    public Plugin createNewPlugin(String name, PluginType type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PluginContent getPluginContent(Path path) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteMedia(Media media) {
        // TODO Auto-generated method stub

    }

    @Override
    public DynamicMenu getDynamicMenuContent(Path path) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Path save(Plugin plugin, String commitMessage) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LayoutEditorModel getLayoutEditor(Path path, PluginType pluginType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Path saveMenu(DynamicMenu menu, String commitMessage) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Path saveLayout(LayoutEditorModel layoutContent, String commitMessage) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<DynamicMenu> listDynamicMenus() {
        return Collections.emptyList();
    }

    @Override
    public Collection<LayoutEditorModel> listLayoutEditor(PluginType pluginType) {
        return Collections.emptyList();
    }

}
