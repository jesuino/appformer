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

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import org.dashbuilder.client.perspective.RuntimePerspectiveGenerator;
import org.dashbuilder.shared.model.RuntimeModel;
import org.dashbuilder.shared.service.RuntimeModelService;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.ErrorCallback;

@ApplicationScoped
public class ClientRuntimeModelLoader {

    public static final String IMPORT_ID_PARAM = "import";

    @Inject
    private Caller<RuntimeModelService> importModelServiceCaller;

    RuntimeModel modelCache = null;

    @Inject
    RuntimePerspectiveGenerator perspectiveEditorGenerator;

    public void loadModel(Consumer<RuntimeModel> modelLoaded,
                          Command emptyModel,
                          BiConsumer<Exception, Throwable> error) {
        String importID = Window.Location.getParameter(IMPORT_ID_PARAM);
        loadModel(importID, modelLoaded, emptyModel, error);

    }

    public void loadModel(String modelId,
                          Consumer<RuntimeModel> modelLoaded,
                          Command emptyModel,
                          BiConsumer<Exception, Throwable> error) {
        if (modelCache != null) {
            modelLoaded.accept(modelCache);
            return;
        }

        importModelServiceCaller.call((Optional<RuntimeModel> runtimeModelOp) -> {
            if (runtimeModelOp.isPresent()) {
                RuntimeModel runtimeModel = runtimeModelOp.get();
                runtimeModel.getLayoutTemplates().forEach(perspectiveEditorGenerator::generatePerspective);
                modelCache = runtimeModel;
                modelLoaded.accept(runtimeModel);
            } else {
                emptyModel.execute();
            }
        }, (ErrorCallback<Exception>) (Exception message, Throwable throwable) -> {
            error.accept(message, throwable);
            return false;
        }).getRuntimeModel(modelId);
    }

}