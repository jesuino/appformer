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

package org.dashbuilder.backend.remote.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.dashbuilder.backend.RuntimeOptions;
import org.dashbuilder.backend.navigation.RuntimeNavigationBuilder;
import org.dashbuilder.navigation.NavTree;
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

    @Inject
    RuntimeNavigationBuilder runtimeNavigationBuilder;
    
    @Inject
    RuntimeOptions runtimeOptions;

    Gson gson;

    @PostConstruct
    void init() {
        gson = new GsonBuilder().create();
    }

    @Override
    public Optional<RuntimeModel> getRuntimeModel(String exportId) {
        Optional<ImportModel> importModelOp = importModelRegistry.get(exportId);

        if (!importModelOp.isPresent() && exportId != null) {
            String fileName = String.join("/", runtimeOptions.getImportsBaseDir(), exportId).concat(".zip");
            importModelOp = importModelRegistry.registerFile(fileName);
        }
        return importModelOp.flatMap(this::buildRuntimeModel);
    }

    private Optional<RuntimeModel> buildRuntimeModel(ImportModel importModel) {
        List<LayoutTemplate> layoutTemplates = importModel.getPerspectives().stream()
                                                          .map(PerspectiveContent::getContent)
                                                          .map(content -> gson.fromJson(content, LayoutTemplate.class))
                                                          .collect(Collectors.toList());
        Optional<String> navTreeOp = importModel.getNavigationJSON();
        NavTree navTree = runtimeNavigationBuilder.build(navTreeOp, layoutTemplates);
        return Optional.of(new RuntimeModel(navTree, layoutTemplates));
    }

}
