package org.dashbuilder.displayer.client.widgets;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import org.jboss.errai.common.client.dom.elemental2.Elemental2DomUtil;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

@Dependent
@Templated
public class ExternalComponentPropertiesEditorView implements ExternalComponentPropertiesEditor.View {

    @Inject
    @DataField
    HTMLDivElement root;

    @Inject
    @DataField
    HTMLDivElement externalComponentPreviewRoot;

    @Inject
    @DataField
    HTMLDivElement externalComponentPropertiesRoot;

    @Inject
    Elemental2DomUtil elementalUtil;

    @Override
    public HTMLElement getElement() {
        return root;
    }

    @Override
    public void init(ExternalComponentPropertiesEditor presenter) {
        elementalUtil.appendWidgetToElement(externalComponentPropertiesRoot, presenter.getPropertyEditorWidget());
        elementalUtil.appendWidgetToElement(externalComponentPreviewRoot, presenter.getExternalComponentWidget());
    }

    @Override
    public void componentNotFound() {
        // TODO: build a not found for component, which should never happen
    }

}