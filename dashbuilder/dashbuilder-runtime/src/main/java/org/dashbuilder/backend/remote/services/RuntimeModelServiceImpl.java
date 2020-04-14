package org.dashbuilder.backend.remote.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.dashbuilder.navigation.NavDivider;
import org.dashbuilder.navigation.NavGroup;
import org.dashbuilder.navigation.NavItem;
import org.dashbuilder.navigation.NavItemContext;
import org.dashbuilder.navigation.NavItemVisitor;
import org.dashbuilder.navigation.NavTree;
import org.dashbuilder.navigation.impl.NavItemContextImpl;
import org.dashbuilder.navigation.impl.NavTreeBuilder;
import org.dashbuilder.navigation.json.NavTreeJSONMarshaller;
import org.dashbuilder.navigation.workbench.NavWorkbenchCtx;
import org.dashbuilder.shared.model.ImportModel;
import org.dashbuilder.shared.model.PerspectiveContent;
import org.dashbuilder.shared.model.RuntimeModel;
import org.dashbuilder.shared.service.ImportModelRegistry;
import org.dashbuilder.shared.service.RuntimeModelService;
import org.jboss.errai.bus.server.annotations.Service;
import org.uberfire.ext.layout.editor.api.editor.LayoutTemplate;

@Service
@ApplicationScoped
public class RuntimeModelServiceImpl implements RuntimeModelService {

    @Inject
    ImportModelRegistry importModelRegistry;

    Gson gson;

    @PostConstruct
    void init() {
        gson = new GsonBuilder().create();
    }

    @Override
    public Optional<RuntimeModel> getRuntimeModel(String exportPath) {
        Optional<ImportModel> importModelOp = importModelRegistry.get(exportPath);

        if (!importModelOp.isPresent()) {
            importModelOp = importModelRegistry.registerFile(exportPath);
        }
        return importModelOp.flatMap(this::buildRuntimeModel);
    }

    private Optional<RuntimeModel> buildRuntimeModel(ImportModel importModel) {
        List<LayoutTemplate> layoutTemplates = importModel.getPerspectives().stream()
                                                          .map(PerspectiveContent::getContent)
                                                          .map(content -> gson.fromJson(content, LayoutTemplate.class))
                                                          .collect(Collectors.toList());
        Optional<String> navTreeOp = importModel.getNavigationJSON();
        NavTree navTree = buildNavigation(navTreeOp, layoutTemplates);
        return Optional.of(new RuntimeModel(navTree, layoutTemplates));
    }

    private NavTree buildNavigation(Optional<String> navTreeJson, List<LayoutTemplate> layoutTemplates) {
        if (navTreeJson.isPresent()) {
            NavTree fullNavTree = NavTreeJSONMarshaller.get().fromJson(navTreeJson.get());
            NavTree cleanedNavTree = fullNavTree.cloneTree();
            fullNavTree.accept(new NavItemVisitor() {

                @Override
                public void visitItem(NavItem item) {
                    String resourceId = NavWorkbenchCtx.get(item.getContext()).getResourceId();
                    if (layoutTemplates.stream().noneMatch(lt -> lt.getName().equals(resourceId))) {
                        cleanedNavTree.deleteItem(item.getId());
                    }
                }

                @Override
                public void visitGroup(NavGroup group) {
                    if (group.getChildren().isEmpty()) {
                        cleanedNavTree.deleteItem(group.getId());
                    }

                }

                @Override
                public void visitDivider(NavDivider divider) {

                }
            });
            return cleanedNavTree;
        } else {
            NavTreeBuilder treeBuilder = new NavTreeBuilder();
            treeBuilder.group("dashboards", "Dashboards", "Dashboards", false);
            layoutTemplates.forEach(lt -> {
                NavItemContext ctx = NavWorkbenchCtx.perspective(lt.getName());
                treeBuilder.item(lt.getName(), lt.getName(), "", true, ctx);
            });
            treeBuilder.endGroup();
            return treeBuilder.build();
        }

    }

}
