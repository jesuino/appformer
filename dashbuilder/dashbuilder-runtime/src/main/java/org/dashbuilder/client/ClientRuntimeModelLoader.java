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

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import org.dashbuilder.client.navigation.NavigationManager;
import org.dashbuilder.client.perspective.RuntimePerspectiveGenerator;
import org.dashbuilder.client.plugins.RuntimePerspectivePluginManager;
import org.dashbuilder.shared.model.RuntimeModel;
import org.dashbuilder.shared.service.RuntimeModelService;
import org.jboss.errai.common.client.api.Caller;
import org.uberfire.mvp.Command;

@ApplicationScoped
public class ClientRuntimeModelLoader {

    public static final String IMPORT_ID_PARAM = "import";

    private Caller<RuntimeModelService> runtimeModelServiceCaller;

    RuntimeModel modelCache = null;

    RuntimePerspectiveGenerator perspectiveEditorGenerator;
    
    RuntimePerspectivePluginManager runtimePerspectivePluginManager;
    
    NavigationManager navigationManager;

    public ClientRuntimeModelLoader() {
        // do nothing
    }

    @Inject
    public ClientRuntimeModelLoader(Caller<RuntimeModelService> importModelServiceCaller,
                                    RuntimePerspectiveGenerator perspectiveEditorGenerator,
                                    RuntimePerspectivePluginManager runtimePerspectivePluginManager,
                                    NavigationManager navigationManager) {
        this.runtimeModelServiceCaller = importModelServiceCaller;
        this.perspectiveEditorGenerator = perspectiveEditorGenerator;
        this.runtimePerspectivePluginManager = runtimePerspectivePluginManager;
        this.navigationManager = navigationManager;
    }


    

    public void loadModel(Consumer<RuntimeModel> modelLoaded,
                          Command emptyModel,
                          BiConsumer<Object, Throwable> error) {
        String importID = Window.Location.getParameter(IMPORT_ID_PARAM);
        loadModel(importID, modelLoaded, emptyModel, error);

    }

    public void loadModel(String importId,
                          Consumer<RuntimeModel> modelLoaded,
                          Command emptyModel,
                          BiConsumer<Object, Throwable> error) {
        if (modelCache != null) {
            modelLoaded.accept(modelCache);
            return;
        }

        if (importId == null || importId.trim().isEmpty()) {
            runtimeModelServiceCaller.call((Optional<RuntimeModel> runtimeModelOp) -> handleResponse(modelLoaded, emptyModel, runtimeModelOp),
                                           (msg, t) -> handleError(error, msg, t))
                                     .getRuntimeModel();
        } else {
            runtimeModelServiceCaller.call((Optional<RuntimeModel> runtimeModelOp) -> handleResponse(modelLoaded, emptyModel, runtimeModelOp),
                                           (msg, t) -> handleError(error, msg, t))
                                     .getRuntimeModel(importId);
        }

    }

    private boolean handleError(BiConsumer<Object, Throwable> error, Object message, Throwable throwable) {
        error.accept(message, throwable);
        return false;
    }

    private void handleResponse(Consumer<RuntimeModel> modelLoaded, Command emptyModel, Optional<RuntimeModel> runtimeModelOp) {
        if (runtimeModelOp.isPresent()) {
            RuntimeModel runtimeModel = runtimeModelOp.get();
            
            runtimeModel.getLayoutTemplates().forEach(perspectiveEditorGenerator::generatePerspective);
            runtimePerspectivePluginManager.setTemplates(runtimeModel.getLayoutTemplates());
            navigationManager.setDefaultNavTree(runtimeModel.getNavTree());
            
            modelCache = runtimeModel;
            modelLoaded.accept(runtimeModel);
        } else {
            emptyModel.execute();
        }
    }

}