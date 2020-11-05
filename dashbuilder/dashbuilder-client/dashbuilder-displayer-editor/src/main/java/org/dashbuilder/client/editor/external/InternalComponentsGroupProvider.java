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

package org.dashbuilder.client.editor.external;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.dashbuilder.client.editor.resources.i18n.Constants;
import org.dashbuilder.external.model.ExternalComponent;
import org.dashbuilder.external.service.ExternalComponentService;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.ioc.client.container.SyncBeanManager;

@EntryPoint
@ApplicationScoped
public class InternalComponentsGroupProvider extends ComponentsGroupProvider {
    
    private final Constants i18n = Constants.INSTANCE;

    Caller<ExternalComponentService> externalComponentService;

    @Inject
    public InternalComponentsGroupProvider(Caller<ExternalComponentService> externalComponentService, 
                                          SyncBeanManager beanManager) {
        this.externalComponentService = externalComponentService;
        this.beanManager = beanManager;
    }

    @Override
    @AfterInitialization
    public void loadComponents() {
        externalComponentService.call((List<ExternalComponent> components) -> loadedComponents = components)
                                .listInternalComponents();
    }

    @Override
    public String getName() {
        return i18n.internalComponentsGroupName();
    }

}