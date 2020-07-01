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

package org.dashbuilder.displayer.client.widgets;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import jsinterop.base.Js;
import org.dashbuilder.external.model.ComponentParameter;
import org.dashbuilder.external.model.ExternalComponent;
import org.dashbuilder.external.service.ExternalComponentService;
import org.jboss.errai.common.client.api.Caller;
import org.uberfire.client.mvp.UberElemental;
import org.uberfire.ext.properties.editor.client.PropertyEditorWidget;
import org.uberfire.ext.properties.editor.model.PropertyEditorCategory;
import org.uberfire.ext.properties.editor.model.PropertyEditorChangeEvent;
import org.uberfire.ext.properties.editor.model.PropertyEditorEvent;
import org.uberfire.ext.properties.editor.model.PropertyEditorFieldInfo;
import org.uberfire.ext.properties.editor.model.PropertyEditorType;
import org.uberfire.ext.widgets.common.client.common.BusyIndicatorView;

@Dependent
public class ExternalComponentPropertiesEditor extends Widget {

    private final String EXTERNAL_COMP_EDITOR_ID = Document.get().createUniqueId();

    public interface View extends UberElemental<ExternalComponentPropertiesEditor> {

        void componentNotFound();

    }

    @Inject
    View view;

    @Inject
    Caller<ExternalComponentService> externalComponentService;

    @Inject
    BusyIndicatorView loading;

    @Inject
    ExternalComponentPresenter externalComponentPresenter;

    private ExternalComponent currentComp;

    private PropertyEditorWidget propertyEditorWidget;

    private Map<String, String> props;

    @PostConstruct
    public void init() {
        propertyEditorWidget = new PropertyEditorWidget();
        super.setElement(Js.<Element> uncheckedCast(view.getElement()));
        super.setWidth("100%");
        super.setHeight("100%");
        view.init(this);
    }

    public void withComponentId(String componentId, Map<String, String> props) {
        this.props = new HashMap<String, String>(props);
        loading.showBusyIndicator("Loading component");
        externalComponentService.call((Optional<ExternalComponent> comp) -> this.loadProperties(comp))
                                .byId(componentId);

    }

    public ExternalComponentPresenter getExternalComponentWidget() {
        return externalComponentPresenter;
    }

    public PropertyEditorWidget getPropertyEditorWidget() {
        return propertyEditorWidget;
    }

    public Map<String, String> getProperties() {
        return props;
    }

    private void loadProperties(Optional<ExternalComponent> compOp) {
        loading.hideBusyIndicator();
        if (compOp.isPresent()) {
            currentComp = compOp.get();
            loadProperties();
            externalComponentPresenter.withComponent(currentComp.getId());
            externalComponentPresenter.setComponentProperties(props);
        } else {
            view.componentNotFound();
        }
    }

    private void loadProperties() {
        PropertyEditorCategory category = new PropertyEditorCategory("Component Properties");
        currentComp.getParameters()
                   .stream()
                   .map(p -> buildField(p, props))
                   .forEach(category::withField);
        propertyEditorWidget.handle(new PropertyEditorEvent(EXTERNAL_COMP_EDITOR_ID, Arrays.asList(category)));
    }

    protected void onPropertyEditorChange(@Observes PropertyEditorChangeEvent event) {
        PropertyEditorFieldInfo property = event.getProperty();
        if (property.getEventId().equalsIgnoreCase(EXTERNAL_COMP_EDITOR_ID)) {
            props.put(property.getKey(), property.getCurrentStringValue());
            externalComponentPresenter.setComponentProperties(props);
        }
    }

    private PropertyEditorFieldInfo buildField(ComponentParameter p, Map<String, String> props) {
        String fieldKey = p.getName();
        String currentValue = props.get(fieldKey);
        PropertyEditorType type = getType(p.getType());
        PropertyEditorFieldInfo field = new PropertyEditorFieldInfo(p.getLabel(), p.getDefaultValue(), type);
        field.withKey(fieldKey);
        if (field.getType() == PropertyEditorType.COMBO) {
            field.withComboValues(p.getComboValues());
        }
        if (currentValue != null) {
            field.setCurrentStringValue(currentValue);
        }
        return field;
    }

    private PropertyEditorType getType(String type) {
        return Stream.of(PropertyEditorType.values())
                     .filter(t -> t.name().equalsIgnoreCase(type))
                     .findFirst().orElse(PropertyEditorType.TEXT);
    }

}