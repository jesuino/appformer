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

import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.uberfire.ext.page.builder.client.grapesjs.components.configuration.ComponentConfigurationPresenter;
import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJS;
import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJS.Modal;
import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJS.Type;

import elemental2.core.JsObject;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import jsinterop.base.Any;
import jsinterop.base.Js;

/**
 * TODO This could be done using grapesjs plugins
 */
@ApplicationScoped
public class CustomComponentsLoader {
    
    @Inject
    SyncBeanManager beanManager;
    
    @Inject
    ComponentConfigurationPresenter componentConfigurationPresenter;
    
    public void applyPlugins(GrapesJS.Editor editor) {
        componentConfigurationPresenter.closeListener(editor.getModal()::close);
        beanManager.lookupBeans(CustomComponent.class).forEach(i -> {
            Type defaultType = editor.getDomComponents().getType("default");
            CustomComponent customComponent = i.getInstance();
            ComponentType type = customComponent.getType(defaultType);
            ComponentBlock block = customComponent.getBlock();
            List<String> props = customComponent.getComponentProperties();
            editor.getDomComponents().addType(type.getTypeId(), type.getType());
            editor.getBlockManager().add(block.getName(), block.getBlock());
            if (!props.isEmpty()) {
                editor.on("component:create", model -> {
                    if (model.getAttributes().getType().equals(type.getTypeId())) {
                        Modal modal = editor.getModal();
                        Element element = componentConfigurationPresenter.getViewContent(props);
                        modal.setContent(element);
                        modal.open();
                        modal.setTitle("Properties for " + block.block.getLabel());
                        modal.onceClose(e -> {
                            DomGlobal.console.log(model);
                            Map<String, String> allProps = componentConfigurationPresenter.allProperties();
                            allProps.forEach((prop, val) -> {
                                JsObject attrs = JsObject.create(null);
                                Js.<Any>cast(attrs).asPropertyMap().set("data-" + prop, val);
                                model.addAttributes(attrs);
                            });
                            model.append(customComponent.buildPreview(model.getEl(), allProps));
                        });
                    }
                });
            }
        });
    }

}
