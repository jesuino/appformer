package org.dashbuilder.client.editor.external;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.dashbuilder.displayer.client.widgets.ExternalComponentPropertiesEditor;
import org.uberfire.ext.widgets.common.client.common.popups.BaseModal;
import org.uberfire.ext.widgets.common.client.common.popups.footers.ModalFooterOKCancelButtons;
import org.uberfire.mvp.Command;

@Dependent
public class ExternalComponentEditorPopUp extends BaseModal {

    @Inject
    ExternalComponentPropertiesEditor externalComponentEditor;

    private Command onCancel;

    private Command onSuccess;

    private Map<String, String> componentProps;

    @PostConstruct
    public void init() {
        ModalFooterOKCancelButtons footer = createModalFooterOKCancelButtons();
        footer.enableCancelButton(true);
        footer.enableOkButton(true);
        setBody(externalComponentEditor);
        setWidth(1200 + "px");
        add(footer);
        setTitle("Edit Component Properties");
    }

    public void onCancelCommand(Command onCancel) {
        this.onCancel = onCancel;
    }

    public void onSuccessCommand(Command onSuccess) {
        this.onSuccess = onSuccess;
    }

    public void withComponentProperties(String componentId, Map<String, String> props) {
        this.componentProps = props;
        externalComponentEditor.withComponentId(componentId, props);
    }

    protected ModalFooterOKCancelButtons createModalFooterOKCancelButtons() {
        return new ModalFooterOKCancelButtons(() -> {
            componentProps.putAll(externalComponentEditor.getProperties());
            if (onSuccess != null) {
                onSuccess.execute();
            }
            hide();
        }, () -> {
            if (onCancel != null) {
                onCancel.execute();
            }
            hide();
        });
    }

}