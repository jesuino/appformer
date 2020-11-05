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

package org.dashbuilder.external.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.dashbuilder.external.model.ExternalComponent;
import org.dashbuilder.external.service.ExternalComponentLoader;
import org.dashbuilder.external.service.ExternalComponentService;
import org.jboss.errai.bus.server.annotations.Service;

@Service
@ApplicationScoped
public class ExternalComponentServiceImpl implements ExternalComponentService {

    @Inject
    ExternalComponentLoader loader;

    @Override
    public List<ExternalComponent> listExternalComponents() {
        return loader.loadExternal();
    }

    @Override
    public List<ExternalComponent> listInternalComponents() {
        return loader.loadInternal();
    }

    @Override
    public Optional<ExternalComponent> byId(String componentId) {
        return Stream.concat(loader.loadExternal().stream(),
                             loader.loadInternal().stream())
                     .filter(c -> componentId.equals(c.getId()))
                     .findFirst();
    }

}
