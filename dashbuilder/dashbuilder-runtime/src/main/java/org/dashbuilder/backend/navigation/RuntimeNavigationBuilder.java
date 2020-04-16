package org.dashbuilder.backend.navigation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;

import org.dashbuilder.navigation.NavDivider;
import org.dashbuilder.navigation.NavGroup;
import org.dashbuilder.navigation.NavItem;
import org.dashbuilder.navigation.NavItemContext;
import org.dashbuilder.navigation.NavItemVisitor;
import org.dashbuilder.navigation.NavTree;
import org.dashbuilder.navigation.impl.NavTreeBuilder;
import org.dashbuilder.navigation.json.NavTreeJSONMarshaller;
import org.dashbuilder.navigation.workbench.NavWorkbenchCtx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uberfire.ext.layout.editor.api.editor.LayoutTemplate;

/**
 * Builds the navigation for Dashbuilder Runtime
 *
 */
@ApplicationScoped
public class RuntimeNavigationBuilder {

    Logger logger = LoggerFactory.getLogger(RuntimeNavigationBuilder.class);

    public NavTree build(Optional<String> navTreeJson, List<LayoutTemplate> layoutTemplates) {
        if (navTreeJson.isPresent()) {
            return buildPrunedTree(navTreeJson, layoutTemplates);
        }

        return navTreeForTemplates(layoutTemplates);
    }

    private NavTree navTreeForTemplates(List<LayoutTemplate> layoutTemplates) {
        NavTreeBuilder treeBuilder = new NavTreeBuilder();
        treeBuilder.group("dashboards", "Runtime Dashboards", "Dashboards", false);
        layoutTemplates.forEach(lt -> {
            NavItemContext ctx = NavWorkbenchCtx.perspective(lt.getName());
            treeBuilder.item(lt.getName(), lt.getName(), "", true, ctx);
        });
        treeBuilder.endGroup();
        return treeBuilder.build();
    }

    private NavTree buildPrunedTree(Optional<String> navTreeJson, List<LayoutTemplate> layoutTemplates) {
        NavTree navTree = NavTreeJSONMarshaller.get().fromJson(navTreeJson.get());
        NavTree runtimeNavTree = navTree.cloneTree();
        RuntimeNavigationVisitor visitor = new RuntimeNavigationVisitor(layoutTemplates, runtimeNavTree);
        
        navTree.accept(visitor);
        removeEmptyGroups(runtimeNavTree);
        
        List<LayoutTemplate> orphanTemplates = visitor.getOrphanItems();
        if (!orphanTemplates.isEmpty()) {
            logger.info("Found {} layout components without a group", orphanTemplates.size());
            navTreeForTemplates(orphanTemplates).getRootItems()
                                                .forEach(runtimeNavTree.getRootItems()::add);
        }
        return runtimeNavTree;
    }

    List<LayoutTemplate> checkOrphansLayoutTemplates(NavTree navTree,
                                                     List<LayoutTemplate> layoutTemplates) {
        return layoutTemplates.stream()
                              .filter(lt -> navTree.getItemById(lt.getName()) == null)
                              .collect(Collectors.toList());
    }

    void removeEmptyGroups(NavTree target) {
        List<String> itemsToRemove = new ArrayList<>();
        filteringGroups(target.getRootItems()).forEach(group -> {
            removedEmptyNestedGroups(group, itemsToRemove);
        });
        itemsToRemove.forEach(target::deleteItem);
    }

    /**
     * 
     * Remove groups with child groups that have no children
     * @param navGroup
     * @param originTree
     */
    void removedEmptyNestedGroups(NavGroup navGroup, List<String> itemsToRemove) {

        filteringGroups(navGroup.getChildren()).forEach(group -> removedEmptyNestedGroups(group, itemsToRemove));

        if (navGroup.getChildren().isEmpty()) {
            logger.info("Removing groups {}", navGroup.getName());
            itemsToRemove.add(navGroup.getId());
        }

    }

    Stream<NavGroup> filteringGroups(List<NavItem> items) {
        return items.stream()
                    .filter(item -> item instanceof NavGroup)
                    .map(item -> (NavGroup) item);
    }

    /**
     * Visitor responsible to collect orphan items and clean unused groups
     *
     */
    class RuntimeNavigationVisitor implements NavItemVisitor {

        List<LayoutTemplate> orphanItems;
        List<LayoutTemplate> layoutTemplates;
        NavTree targetTree;

        private RuntimeNavigationVisitor(List<LayoutTemplate> layoutTemplates, NavTree targetTree) {
            this.layoutTemplates = layoutTemplates;
            this.orphanItems = new ArrayList<>(layoutTemplates);
            this.targetTree = targetTree;
        }

        @Override
        public void visitGroup(NavGroup group) {
            if (group.getChildren().isEmpty()) {
                targetTree.deleteItem(group.getId());
            }
        }

        @Override
        public void visitItem(NavItem item) {
            String resourceId = NavWorkbenchCtx.get(item.getContext()).getResourceId();
            if (layoutTemplates.stream().noneMatch(lt -> lt.getName().equals(resourceId))) {
                targetTree.deleteItem(item.getId());
            }

            orphanItems.stream()
                       .filter(lt -> lt.getName().equals(resourceId))
                       .findFirst().ifPresent(notOrphanItem -> orphanItems.remove(notOrphanItem));

        }

        @Override
        public void visitDivider(NavDivider divider) {
            // do nothing
        }

        public List<LayoutTemplate> getOrphanItems() {
            return orphanItems;
        }

    }

}