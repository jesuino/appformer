package org.dashbuilder.client.widgets.dataset.editor.prometheus;

import javax.enterprise.context.Dependent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.dashbuilder.common.client.editor.ValueBoxEditor;

/**
 * <p>The Prometheus Data Set attributes editor view.</p>
 *
 */
@Dependent
public class PrometheusDataSetDefAttributesEditorView extends Composite implements PrometheusDataSetDefAttributesEditor.View {

    interface Binder extends UiBinder<Widget, PrometheusDataSetDefAttributesEditorView> {

        Binder BINDER = GWT.create(Binder.class);
    }

    PrometheusDataSetDefAttributesEditor presenter;

    @UiField(provided = true)
    ValueBoxEditor.View serverUrlView;

    @UiField(provided = true)
    ValueBoxEditor.View queryView;

    @Override
    public void init(final PrometheusDataSetDefAttributesEditor presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initWidgets(final ValueBoxEditor.View serverUrlView,
                            final ValueBoxEditor.View queryView) {
        this.serverUrlView = serverUrlView;
        this.queryView = queryView;
        initWidget(Binder.BINDER.createAndBindUi(this));
    }
}
