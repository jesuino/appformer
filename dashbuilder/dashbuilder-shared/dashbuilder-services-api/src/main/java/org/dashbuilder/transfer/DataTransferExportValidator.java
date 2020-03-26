package org.dashbuilder.transfer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.dashbuilder.dataset.def.DataSetDef;
import org.dashbuilder.displayer.json.DisplayerSettingsJSONMarshaller;
import org.dashbuilder.navigation.NavDivider;
import org.dashbuilder.navigation.NavGroup;
import org.dashbuilder.navigation.NavItem;
import org.dashbuilder.navigation.NavItem.Type;
import org.dashbuilder.navigation.NavItemVisitor;
import org.dashbuilder.navigation.NavTree;
import org.dashbuilder.navigation.service.NavigationServices;
import org.dashbuilder.navigation.service.PerspectivePluginServices;
import org.dashbuilder.navigation.workbench.NavWorkbenchCtx;
import org.uberfire.ext.layout.editor.api.editor.LayoutComponent;
import org.uberfire.ext.layout.editor.api.editor.LayoutTemplate;

import static org.dashbuilder.navigation.layout.NavDragComponentSettings.NAV_GROUP_ID;
import static org.dashbuilder.navigation.layout.NavDragComponentSettings.PERSPECTIVE_ID;

@ApplicationScoped
public class DataTransferExportValidator {

    @Inject
    PerspectivePluginServices perspectivePluginServices;

    @Inject
    DisplayerSettingsJSONMarshaller marshaller;

    @Inject
    NavigationServices navigationServices;

    PageVisitor pageVisitor = new PageVisitor();

    public List<ExportDependencies> validate(DataTransferExportModel exportModel) {
        if (exportModel.isExportAll()) {
            return Collections.emptyList();
        }

        List<ExportDependencies> allDependencies = new ArrayList<>();

        if (exportModel.isExportNavigation()) {
            ExportDependencies deps = ExportDependencies.navigationDependencies();
            navigationDependencies(deps);
            allDependencies.add(deps);
        }

        exportModel.getPages().forEach(p -> {
            LayoutTemplate layoutTemplate = perspectivePluginServices.getLayoutTemplate(p);
            ExportDependencies dependencies = ExportDependencies.pageDependencies(p);
            fillUnsatisfiedDependencies(layoutTemplate, dependencies, exportModel);
            allDependencies.add(dependencies);
        });
        // clean resolved dependencies
        allDependencies.removeIf(dep -> dep.getDatasets().isEmpty() && dep.getPages().isEmpty());

        return allDependencies;
    }

    private void fillUnsatisfiedDependencies(LayoutTemplate layoutTemplate,
                                             ExportDependencies dependencies,
                                             DataTransferExportModel exportModel) {
        layoutTemplate.getRows().stream()
                      .flatMap(r -> r.getLayoutColumns().stream())
                      .flatMap(c -> c.getLayoutComponents().stream())
                      .forEach(component -> fillMissingDependencies(component, dependencies, exportModel));
    }

    private void fillMissingDependencies(LayoutComponent component, ExportDependencies dependencies, DataTransferExportModel exportModel) {
        Map<String, String> props = component.getProperties();
        String displayerJson = props.get("json");
        String navigationGroupProperty = props.get(NAV_GROUP_ID);
        String perspectiveId = props.get(PERSPECTIVE_ID);
        if (displayerJson != null) {
            checkDataSetDependency(dependencies, exportModel, displayerJson);
        } else if (navigationGroupProperty != null) {
            navigationDependencies(navigationGroupProperty, dependencies);
        } else if (perspectiveId != null) {
            checkPageDependency(dependencies, exportModel, perspectiveId);
        }
    }

    private void checkPageDependency(ExportDependencies dependencies,
                                     DataTransferExportModel exportModel,
                                     String pageId) {
        boolean missingPage = exportModel.getPages().stream().noneMatch(pageId::equals);
        if (missingPage) {
            dependencies.getPages().add(pageId);
        }
    }

    private void checkDataSetDependency(ExportDependencies dependencies,
                                        DataTransferExportModel exportModel,
                                        String displayerJson) {
        // retrieve dataset and add to dependencies 
        String datasetUUID = marshaller.fromJsonString(displayerJson)
                                       .getDataSetLookup()
                                       .getDataSetUUID();
        // change to ifAbsent when updating to later java version
        boolean missingDs = exportModel.getDatasetDefinitions()
                                       .stream()
                                       .map(DataSetDef::getUUID)
                                       .noneMatch(datasetUUID::equals);
        if (missingDs) {
            dependencies.getDatasets().add(datasetUUID);
        }
    }

    private void navigationDependencies(ExportDependencies pageDependencies) {
        navigationDependencies(navigationServices.loadNavTree(), pageDependencies);
    }

    private void navigationDependencies(String groupId, ExportDependencies pageDependencies) {
        NavTree navTree = navigationServices.loadNavTree().getItemAsTree(groupId);
        navigationDependencies(navTree, pageDependencies);
    }

    private void navigationDependencies(NavTree navTree, ExportDependencies pageDependencies) {
        pageVisitor.with(pageDependencies.getPages());
        navTree.accept(pageVisitor);
    }

    private class PageVisitor implements NavItemVisitor {

        Set<String> pagesRecorder;

        public void with(Set<String> pagesRecorder) {
            this.pagesRecorder = pagesRecorder;
        }

        @Override
        public void visitGroup(NavGroup group) {

        }

        @Override
        public void visitItem(NavItem item) {
            if (pagesRecorder != null && item.getType() == Type.ITEM) {
                String pageId = NavWorkbenchCtx.get(item).getResourceId();
                pagesRecorder.add(pageId);
            }

        }

        @Override
        public void visitDivider(NavDivider divider) {

        }

    }
}
