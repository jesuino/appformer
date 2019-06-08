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

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJS;
import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJS.Type;

/**
 * TODO This could be done using grapesjs plugins
 */
@ApplicationScoped
public class CustomComponentsLoader {
    
    @Inject
    SyncBeanManager beanManager;
    
    public void applyPlugins(GrapesJS.Editor editor) {
        beanManager.lookupBeans(CustomComponent.class).forEach(i -> {
            Type defaultType = editor.getDomComponents().getType("default");
            CustomComponent customComponent = i.getInstance();
            ComponentType type = customComponent.getType(defaultType);
            ComponentBlock block = customComponent.getBlock();
            editor.getDomComponents().addType(type.getTypeId(), type.getType());
            editor.getBlockManager().add(block.getName(), block.getBlock());
        });
    }

}
