package org.dashbuilder.client.editor.external;

import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.user.client.ui.IsWidget;
import org.gwtbootstrap3.client.ui.Modal;
import org.uberfire.ext.layout.editor.client.api.HasModalConfiguration;
import org.uberfire.ext.layout.editor.client.api.ModalConfigurationContext;
import org.uberfire.ext.layout.editor.client.api.RenderingContext;

@Dependent
public class ExternalDragComponentWithProps extends ExternalDragComponent implements HasModalConfiguration {

    @Inject
    ExternalComponentEditorPopUp popUp;

    public ExternalDragComponentWithProps() {
        // empty
    }

    public ExternalDragComponentWithProps(String componentName, String componentIcon) {
        super(componentName, componentIcon);
    }
    
    @Override
    public Modal getConfigurationModal(ModalConfigurationContext ctx) {
        Map<String, String> componentProperties = ctx.getComponentProperties();
        String componentId = componentProperties.get(COMPONENT_ID_KEY);
        popUp.withComponentProperties(componentId, componentProperties);
        popUp.onCancelCommand(() -> ctx.configurationCancelled());
        popUp.onSuccessCommand(() -> ctx.configurationFinished());
        return popUp;
    }

}