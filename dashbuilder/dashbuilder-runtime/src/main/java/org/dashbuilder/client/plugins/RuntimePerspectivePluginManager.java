package org.dashbuilder.client.plugins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import com.google.gwt.user.client.ui.IsWidget;
import org.apache.xalan.xsltc.dom.LoadDocument;
import org.dashbuilder.client.navigation.plugin.PerspectivePluginManager;
import org.dashbuilder.navigation.NavGroup;
import org.dashbuilder.navigation.NavItem;
import org.dashbuilder.navigation.layout.LayoutRecursionIssue;
import org.dashbuilder.navigation.layout.LayoutTemplateContext;
import org.dashbuilder.navigation.layout.LayoutTemplateInfo;
import org.dashbuilder.navigation.workbench.NavWorkbenchCtx;
import org.dashbuilder.shared.event.RuntimeModelEvent;
import org.jboss.errai.common.client.ui.ElementWrapperWidget;
import org.uberfire.ext.layout.editor.api.editor.LayoutInstance;
import org.uberfire.ext.layout.editor.api.editor.LayoutTemplate;
import org.uberfire.ext.layout.editor.client.generator.LayoutGenerator;
import org.uberfire.ext.plugin.model.Plugin;
import org.uberfire.ext.plugin.model.PluginType;
import org.uberfire.mvp.ParameterizedCommand;

@Alternative
@ApplicationScoped
public class RuntimePerspectivePluginManager implements PerspectivePluginManager {
    
    @Inject
    LayoutGenerator layoutGenerator;

    List<LayoutTemplate> templates = new ArrayList<>();

    @Override
    public void loadPlugins() {

    }

    @Override
    public void getPerspectivePlugins(ParameterizedCommand<Collection<Plugin>> callback) {
        List<Plugin> plugins = templates.stream()
                                        .map(lt -> new Plugin(lt.getName(),
                                                              PluginType.PERSPECTIVE,
                                                              null))
                                        .collect(Collectors.toList());
        callback.execute(plugins);
    }

    @Override
    public boolean isRuntimePerspective(Plugin plugin) {
        return searchLayoutTemplate(plugin.getName());
    }

    @Override
    public boolean isRuntimePerspective(NavItem navItem) {
        NavWorkbenchCtx navCtx = NavWorkbenchCtx.get(navItem);
        String resourceId = navCtx.getResourceId();
        return searchLayoutTemplate(resourceId);
    }

    @Override
    public boolean isRuntimePerspective(String perspectiveId) {
        return searchLayoutTemplate(perspectiveId);
    }

    @Override
    public String getRuntimePerspectiveId(NavItem navItem) {
        NavWorkbenchCtx navCtx = NavWorkbenchCtx.get(navItem);
        return navCtx.getResourceId();
    }

    @Override
    public boolean existsPerspectivePlugin(String perspectiveName) {
        return searchLayoutTemplate(perspectiveName);
    }

    @Override
    public void getLayoutTemplateInfo(String perspectiveName, ParameterizedCommand<LayoutTemplateInfo> callback) {
        // not used in runtime
    }

    @Override
    public void getLayoutTemplateInfo(LayoutTemplate layoutTemplate, ParameterizedCommand<LayoutTemplateInfo> callback) {
        // not used in runtime
    }

    @Override
    public void buildPerspectiveWidget(String perspectiveName, LayoutTemplateContext layoutCtx, ParameterizedCommand<IsWidget> afterBuild, ParameterizedCommand<LayoutRecursionIssue> onInfiniteRecursion) {
        // LAYOUT RECURSION SHOULD BE HANDLED HERE
        templates.stream().filter(lt -> lt.getName().equals(perspectiveName)).findFirst().ifPresent(lt -> {
            LayoutInstance result = layoutGenerator.build(lt);
            IsWidget widget = ElementWrapperWidget.getWidget(result.getElement());
            afterBuild.execute(widget);
        });
    }

    @Override
    public NavGroup getLastBuildPerspectiveNavGroup() {
        return null;
    }

    public void onRuntimeModelLoaded(@Observes RuntimeModelEvent runtimeModelEvent) {
        templates = runtimeModelEvent.getRuntimeModel().getLayoutTemplates();
    }
    
    private boolean searchLayoutTemplate(String name) {
        return templates.stream().anyMatch(lt -> lt.getName().equals(name));
    }

}