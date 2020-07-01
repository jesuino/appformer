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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.user.client.ui.IsWidget;
import org.dashbuilder.displayer.client.widgets.ExternalComponentPresenter;
import org.gwtbootstrap3.client.ui.Label;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.uberfire.ext.layout.editor.client.api.HasDragAndDropSettings;
import org.uberfire.ext.layout.editor.client.api.LayoutDragComponent;
import org.uberfire.ext.layout.editor.client.api.RenderingContext;

@Dependent
public class ExternalDragComponent implements LayoutDragComponent, HasDragAndDropSettings {

    public static final String COMPONENT_ID_KEY = "componentId";

    @Inject
    SyncBeanManager beanManager;
    
    Map<String, String> props = new HashMap<>();

    String componentId = "";
    String componentName = "";
    String componentIcon;

    public ExternalDragComponent() {
        // do nothing
    }

    public ExternalDragComponent(String componentName) {
        this(componentName, null);
    }

    public ExternalDragComponent(String componentName, String componentIcon) {
        this.componentName = componentName;
        this.componentIcon = componentIcon;
    }

    @Override
    public String getDragComponentTitle() {
        return componentName;
    }

    @Override
    public IsWidget getPreviewWidget(RenderingContext ctx) {
        return getShowWidget(ctx);
    }

    @Override
    public IsWidget getShowWidget(RenderingContext ctx) {
        String componentId = ctx.getComponent().getProperties().get(COMPONENT_ID_KEY);
        if (componentId == null || componentId.trim().isEmpty()) {
            return new Label("Component not configured.");
        }
        ExternalComponentPresenter componentPresenter = produceComponentPresenter();
        componentPresenter.withComponent(componentId);
        componentPresenter.setComponentProperties(ctx.getComponent().getProperties());
        return componentPresenter;
    }

    @Override
    public String getDragComponentIconClass() {
        return componentIcon != null ? componentIcon : LayoutDragComponent.super.getDragComponentIconClass();
    }

    @Override
    public String[] getSettingsKeys() {
        return props.keySet().stream().toArray(String[]::new);
    }

    @Override
    public String getSettingValue(String key) {
        return props.get(key);
    }

    @Override
    public void setSettingValue(String key, String value) {
        props.put(key, value);
    }

    @Override
    public Map<String, String> getMapSettings() {
        return props;
    }

    private ExternalComponentPresenter produceComponentPresenter() {
        return beanManager.lookupBean(ExternalComponentPresenter.class).getInstance();
    }
}