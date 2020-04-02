/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dashbuilder.client.screens;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLDivElement;
import org.dashbuilder.client.navbar.NavBarHelper;
import org.dashbuilder.client.navigation.NavigationManager;
import org.dashbuilder.navigation.NavTree;
import org.dashbuilder.shared.model.RuntimeModel;
import org.dashbuilder.shared.service.ImportModelService;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchScreen;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.client.workbench.widgets.menu.megamenu.WorkbenchMegaMenuPresenter;
import org.uberfire.ext.layout.editor.client.generator.LayoutGenerator;
import org.uberfire.ext.plugin.client.perspective.editor.generator.PerspectiveEditorGenerator;
import org.uberfire.lifecycle.OnOpen;
import org.uberfire.workbench.model.menu.Menus;

@Templated
@ApplicationScoped
@WorkbenchScreen(identifier = RuntimeScreen.ID)
public class RuntimeScreen extends Composite {

    public static final String ID = "RuntimeScreen";
    private static final String IMPORT_ID_PARAM = "import";

    @Inject
    @DataField
    HTMLDivElement root;

    @Inject
    private Caller<ImportModelService> importModelServiceCaller;

    @Inject
    NavigationManager navigationManager;

    @Inject
    PerspectiveEditorGenerator perspectiveEditorGenerator;

    @Inject
    NavBarHelper menusHelper;

    @Inject
    WorkbenchMegaMenuPresenter menuBar;

    @Inject
    PlaceManager placeManager;

    @Inject
    LayoutGenerator layoutGenerator;

    @OnOpen
    public void onOpen() {
        this.hideLoading();
        String importID = Window.Location.getParameter(IMPORT_ID_PARAM);
        importModelServiceCaller.call((Optional<RuntimeModel> runtimeModelOp) -> {
            if (runtimeModelOp.isPresent()) {
                loadDashboards(runtimeModelOp.get());
            } else {
                showEmptyContent();
            }
        }).getRuntimeModel(importID);
    }

    @WorkbenchPartTitle
    public String getScreenTitle() {
        return "Welcome to Dashboards";
    }

    private void showEmptyContent() {
        placeManager.goTo(UploadDashboardsScreen.ID);
    }

    private void loadDashboards(RuntimeModel runtimeModel) {
        NavTree navTree = runtimeModel.getNavTree();
        Menus menus = menusHelper.buildMenusFromNavTree(navTree).build();

        navigationManager.setDefaultNavTree(navTree);
        runtimeModel.getLayoutTemplates().forEach(perspectiveEditorGenerator::generatePerspective);

        menuBar.clear();
        menuBar.addMenus(menus);
    }

    private void hideLoading() {
        DomGlobal.document.getElementById("loading").remove();
    }

}