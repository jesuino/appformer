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

import com.google.gwt.user.client.ui.Composite;
import elemental2.dom.DomGlobal;
import elemental2.dom.FormData;
import elemental2.dom.HTMLButtonElement;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLFormElement;
import elemental2.dom.HTMLInputElement;
import elemental2.dom.RequestInit;
import elemental2.dom.Response;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchScreen;
import org.uberfire.client.workbench.widgets.menu.megamenu.WorkbenchMegaMenuPresenter;
import org.uberfire.lifecycle.OnOpen;

@Templated
@Dependent
@WorkbenchScreen(identifier = UploadDashboardsScreen.ID)
public class UploadDashboardsScreen extends Composite {

    public static final String ID = "UploadDashboardsScreen";

    @Inject
    @DataField
    HTMLButtonElement btnImport;

    @Inject
    @DataField
    HTMLFormElement uploadForm;

    @Inject
    @DataField
    HTMLInputElement inputFile;

    @Inject
    @DataField
    HTMLDivElement emptyImport;

    @Inject
    WorkbenchMegaMenuPresenter menuBar;

    @PostConstruct
    public void build() {
        btnImport.onclick = e -> {
            inputFile.click();
            return null;
        };

        inputFile.onchange = e -> {
            RequestInit request = RequestInit.create();
            request.setMethod("POST");
            request.setBody(new FormData(uploadForm));
            DomGlobal.window.fetch("/rest/upload", request)
                            .then((Response response) -> response.text().then(id -> {
                                if (response.status == 200) {
                                    DomGlobal.window.location.assign("/dashbuilder.html?import=" + id);
                                } else {
                                    showError("Error uploading file: " + response.status);
                                }
                                return null;
                            }), error -> {
                                DomGlobal.console.log(error);
                                DomGlobal.window.alert("Error uploading file: " + error);
                                return null;
                            });
            return null;
        };
    }

    private void showError(String error) {
        DomGlobal.window.alert(error);
    }

    @OnOpen
    public void onOpen() {
        menuBar.clear();
    }

    @WorkbenchPartTitle
    public String title() {
        return "Upload Dashboards";
    }

}