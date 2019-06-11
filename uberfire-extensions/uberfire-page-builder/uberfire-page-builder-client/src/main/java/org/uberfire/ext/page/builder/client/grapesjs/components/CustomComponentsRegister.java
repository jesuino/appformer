/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.uberfire.ext.page.builder.client.grapesjs.components;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.errai.ioc.client.container.SyncBeanManager;

@ApplicationScoped
public class CustomComponentsRegister {

    Map<String, CustomComponent> componentsRegistry = new HashMap<>();
    
    @Inject
    SyncBeanManager beanManager;
    
    @PostConstruct
    public void registerComponents() {
        beanManager.lookupBeans(CustomComponent.class).forEach(i -> {
            CustomComponent customComponent = i.getInstance();
            componentsRegistry.put(customComponent.getTypeId(), customComponent);
        });
    }
    
    public Collection<CustomComponent> registeredComponents() {
        return componentsRegistry.values();
    }
    
    public Collection<String> registeredComponentsIds() {
        return componentsRegistry.keySet();
    }


    public Optional<CustomComponent> get(String componentType) {
        return Optional.ofNullable(componentsRegistry.get(componentType));
    }
    
}
