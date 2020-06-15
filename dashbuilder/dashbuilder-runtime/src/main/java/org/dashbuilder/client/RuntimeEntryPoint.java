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

package org.dashbuilder.client;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import org.dashbuilder.client.screens.RuntimeScreen;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.uberfire.client.workbench.Workbench;

@EntryPoint
@ApplicationScoped
@Bundle("resources/i18n/AppConstants.properties")
public class RuntimeEntryPoint {

    @Inject
    Workbench workbench;

    @Inject
    ClientRuntimeModelLoader modelLoader;

    @Inject
    RuntimeScreen runtimeScreen;

    @PostConstruct
    public void startup() {
        workbench.addStartupBlocker(RuntimeEntryPoint.class);
        modelLoader.loadModel(model -> hideLoading(),
                              this::hideLoading,
                              (e, t) -> this.hideLoading());
    }

    private void hideLoading() {
        workbench.removeStartupBlocker(RuntimeEntryPoint.class);
        Element loading = DomGlobal.document.getElementById("loading");
        if (loading != null) {
            loading.remove();
        }
    }

}