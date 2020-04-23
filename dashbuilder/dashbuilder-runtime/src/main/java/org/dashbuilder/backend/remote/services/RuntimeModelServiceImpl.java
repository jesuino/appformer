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

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.dashbuilder.backend.RuntimeOptions;
import org.dashbuilder.backend.navigation.RuntimeNavigationBuilder;
import org.dashbuilder.shared.model.RuntimeModel;
import org.dashbuilder.shared.service.RuntimeModelRegistry;
import org.dashbuilder.shared.service.RuntimeModelService;
import org.jboss.errai.bus.server.annotations.Service;

@Service
@ApplicationScoped
public class RuntimeModelServiceImpl implements RuntimeModelService {

    @Inject
    RuntimeModelRegistry importModelRegistry;

    @Inject
    RuntimeNavigationBuilder runtimeNavigationBuilder;
    
    @Inject
    RuntimeOptions runtimeOptions;

    @Override
    public Optional<RuntimeModel> getRuntimeModel(String exportId) {
        Optional<RuntimeModel> runtimeModelOp = importModelRegistry.get(exportId);

        if (!runtimeModelOp.isPresent() && exportId != null) {
            String fileName = String.join("/", runtimeOptions.getImportsBaseDir(), exportId).concat(".zip");
            runtimeModelOp = importModelRegistry.registerFile(fileName);
        }
        return runtimeModelOp;
    }

}