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

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import elemental2.dom.HTMLButtonElement;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import org.jboss.errai.common.client.api.elemental2.IsElement;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.uberfire.client.workbench.widgets.menu.megamenu.WorkbenchMegaMenuPresenter;
import org.uberfire.lifecycle.OnOpen;

@Templated
@Dependent
public class UploadDashboardsScreen implements IsElement {

    public static final String ID = "UploadDashboardsScreen";

    @Inject
    @DataField
    HTMLButtonElement btnImport;

    @Inject
    @DataField
    HTMLDivElement emptyImport;

    @Inject
    WorkbenchMegaMenuPresenter menuBar;

    @PostConstruct
    public void build() {
        btnImport.onclick = e -> {
            return null;
        };
    }

    @OnOpen
    public void onOpen() {
        menuBar.clear();
    }

    @Override
    public HTMLElement getElement() {
        return emptyImport;
    }

}