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

package org.uberfire.ext.page.builder.client.grapesjs.components.configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.errai.common.client.api.elemental2.IsElement;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.event.dom.client.ClickEvent;

import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLButtonElement;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLInputElement;
import elemental2.dom.HTMLLabelElement;

@Templated
@Dependent
public class ComponentConfigurationView implements ComponentConfigurationPresenter.View, IsElement {
    
    @Inject
    @DataField
    HTMLDivElement componentConfigurationViewRoot;
    
    @Inject
    @DataField
    HTMLDivElement propertiesContainer;
    
    @Inject
    @DataField
    HTMLButtonElement btnClose;
    
    Map<String, HTMLInputElement> elementsForProperties = new HashMap<>();

    private ComponentConfigurationPresenter presenter;

    @Override
    public HTMLElement getElement() {
        return componentConfigurationViewRoot;
    }

    @Override
    public void init(ComponentConfigurationPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void withProperties(List<String> properties) {
        clear();
        properties.forEach(prop -> {
            Element propContainer = DomGlobal.document.createElement("div");
            Element propLabelContainer = DomGlobal.document.createElement("div");
            Element propInputContainer = DomGlobal.document.createElement("div");
            HTMLLabelElement propLabel = (HTMLLabelElement) DomGlobal.document.createElement("label");
            HTMLInputElement propInput = (HTMLInputElement) DomGlobal.document.createElement("input");
            propInput.type = "text";
            propLabel.textContent = prop;
            
            propLabelContainer.appendChild(propLabel);
            propInputContainer.appendChild(propInput);
            
            propContainer.appendChild(propInputContainer);
            propContainer.insertBefore(propLabelContainer, propInputContainer);
            propertiesContainer.appendChild(propContainer);
            elementsForProperties.put(prop, propInput);
        });
    }

    @Override
    public void clear() {
        elementsForProperties.clear();
        propertiesContainer.innerHTML = "";
        elementsForProperties = new HashMap<>();
    }

    @Override
    public Map<String, String> propertiesValues() {
        Map<String, String> propertiesAndValues = new HashMap<>();
        elementsForProperties.forEach((prop, el) -> propertiesAndValues.put(prop, el.value));
        return Collections.unmodifiableMap(propertiesAndValues);
    }

    @EventHandler("btnClose")
    public void btnCloseListener(final ClickEvent clickEvent) {
        presenter.close();
    }

}