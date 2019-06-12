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
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.uberfire.ext.page.builder.client.grapesjs.components.configuration.ComponentConfigurationPresenter;
import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJS;
import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJS.Modal;
import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJS.Model;
import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJS.Type;

import elemental2.core.JsObject;
import elemental2.dom.Element;
import jsinterop.base.Any;
import jsinterop.base.Js;

/**
 * TODO This could be done using grapesjs plugins
 */
@ApplicationScoped
public class GrapesJSPluginsLoader {
    
    @Inject
    SyncBeanManager beanManager;
    
    @Inject
    ComponentConfigurationPresenter componentConfigurationPresenter;
    
    @Inject
    CustomComponentsRegister customComponentsRegister;
    
    public void applyPlugins(GrapesJS.Editor editor) {
        componentConfigurationPresenter.closeListener(editor.getModal()::close);
        customComponentsRegister.registeredComponents().forEach(customComponent -> {
            Type baseType = editor.getDomComponents().getType("default");
            Type type = customComponent.getType(baseType);
            ComponentBlock block = customComponent.getBlock();
            editor.getDomComponents().addType(customComponent.getTypeId(), type);
            editor.getBlockManager().add(block.getName(), block.getBlock());
        });
        editor.on("component:mount", model -> {
            getCustomComponentForModel(model).ifPresent(customComponent -> customComponent.build(model.getEl()));
        });
    }

    public void registerNewComponentsListeners(GrapesJS.Editor editor) {
        editor.on("component:add", model -> {
            getCustomComponentForModel(model).ifPresent(customComponent -> {
                List<String> props = customComponent.getComponentProperties();
                if (!props.isEmpty()) {
                    Modal modal = editor.getModal();
                    Element element = componentConfigurationPresenter.getViewContent(props);
                    modal.setContent(element);
                    modal.open();
                    modal.setTitle("Properties for " + customComponent.getBlock().block.getLabel());
                    modal.onceClose(e -> {
                        Map<String, String> allProps = componentConfigurationPresenter.allProperties();
                        applyPropertiesToComponent(model, allProps);
                        customComponent.build(model.getEl());
                    });
                }
            });
        });
        
    }

    private Optional<CustomComponent> getCustomComponentForModel(Model model) {
        Element modelEl = model.getEl();
        String componentType = modelEl.getAttribute("data-appformer-type");
        if (componentType != null) {
            return customComponentsRegister.get(componentType);
        }
        return Optional.empty();
    }

    private void applyPropertiesToComponent(Model model, Map<String, String> allProps) {
        allProps.forEach((prop, val) -> {
            JsObject attrs = JsObject.create(null);
            Js.<Any>cast(attrs).asPropertyMap().set("data-" + prop, val);
            model.addAttributes(attrs);
        });
    }

}
