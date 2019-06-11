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

import java.util.List;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.uberfire.client.mvp.UberElemental;
import org.uberfire.mvp.Command;

import elemental2.dom.Element;

@Dependent
public class ComponentConfigurationPresenter {
    
    public interface View extends UberElemental<ComponentConfigurationPresenter> {
        
        void withProperties(List<String> properties);

        void clear();
 
        Map<String, String> propertiesValues();
    }
    
    View view;
    private Command onCloseCommand;
    
    @Inject
    public ComponentConfigurationPresenter(View view) {
        this.view = view;
        view.init(this);
    }
    
    public Map<String, String> allProperties() {
        return view.propertiesValues();
    }
    
    public Element getViewContent(List<String> properties) {
        view.clear();
        view.withProperties(properties);
        return view.getElement();
    }
    
    public void closeListener(Command onCloseCommand) {
        this.onCloseCommand = onCloseCommand;
    }
    
    void close() {
        if (onCloseCommand != null) {
            onCloseCommand.execute();
        }
    }

}
