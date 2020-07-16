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
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.dashbuilder.client.editor.DisplayerDragComponent;
import org.dashbuilder.displayer.DisplayerSettings;
import org.dashbuilder.displayer.DisplayerSettingsFactory;
import org.dashbuilder.displayer.DisplayerType;
import org.dashbuilder.displayer.client.PerspectiveCoordinator;
import org.dashbuilder.displayer.client.events.DisplayerSettingsChangedEvent;
import org.dashbuilder.displayer.client.prototypes.DisplayerPrototypes;
import org.dashbuilder.displayer.client.widgets.DisplayerEditorPopup;
import org.dashbuilder.displayer.client.widgets.DisplayerViewer;
import org.gwtbootstrap3.client.ui.Modal;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.ext.layout.editor.client.api.HasDragAndDropSettings;
import org.uberfire.ext.layout.editor.client.api.LayoutDragComponent;
import org.uberfire.ext.layout.editor.client.api.ModalConfigurationContext;

@Dependent
public class ExternalDisplayerDragComponent extends DisplayerDragComponent implements LayoutDragComponent, HasDragAndDropSettings {

    public static final String COMPONENT_ID_KEY = "componentId";

    String componentId;

    private String componentName;

    private String componentIcon;

    @Inject
    Event<DisplayerSettingsChangedEvent> displayerSettingsChangedEvent;

    @Inject
    DisplayerPrototypes displayerPrototypes;

    @Inject
    public ExternalDisplayerDragComponent(SyncBeanManager beanManager,
                                          DisplayerViewer viewer,
                                          PlaceManager placeManager,
                                          PerspectiveCoordinator perspectiveCoordinator) {
        super(beanManager, viewer, placeManager, perspectiveCoordinator);
    }

    public void setDragInfo(String componentName, String componentIcon) {
        this.componentName = componentName;
        this.componentIcon = componentIcon;
    }

    @Override
    public String getDragComponentTitle() {
        return componentName == null ? "Unknow" : componentName;
    }

    @Override
    public String getDragComponentIconClass() {
        return componentIcon != null ? componentIcon : LayoutDragComponent.super.getDragComponentIconClass();
    }

    @Override
    public String[] getSettingsKeys() {
        return new String[]{COMPONENT_ID_KEY};
    }

    @Override
    public String getSettingValue(String key) {
        if (COMPONENT_ID_KEY.equals(key)) {
            return componentId;
        }
        return componentId;
    }

    @Override
    public void setSettingValue(String key, String value) {
        if (COMPONENT_ID_KEY.equals(key)) {
            componentId = value;
        }
    }

    @Override
    public Map<String, String> getMapSettings() {
        return Collections.singletonMap(COMPONENT_ID_KEY, componentId);
    }

    @Override
    public Modal getConfigurationModal(ModalConfigurationContext ctx) {
        DisplayerEditorPopup editorPopUp = buildEditorPopUp(ctx);
        editorPopUp.setTypeSelectorEnabled(false);
        editorPopUp.setExternalDisplayerEnabled(true);
        return editorPopUp;
    }

    @Override
    protected DisplayerSettings initialSettings(ModalConfigurationContext ctx) {
        DisplayerSettings settings = displayerPrototypes.getProto(DisplayerType.EXTERNAL_COMPONENT);
        String componentId = ctx.getComponentProperty(COMPONENT_ID_KEY);
        settings.setComponentId(componentId);
        return settings;
    }

    @Override
    public DisplayerType getDisplayerType() {
        return DisplayerType.EXTERNAL_COMPONENT;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

}