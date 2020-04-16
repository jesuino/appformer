package org.dashbuilder.client.perspective;

import org.uberfire.client.workbench.panels.impl.StaticWorkbenchPanelPresenter;
import org.uberfire.ext.layout.editor.api.editor.LayoutTemplate;
import org.uberfire.ext.plugin.client.perspective.editor.generator.PerspectiveEditorActivity;
import org.uberfire.ext.plugin.client.perspective.editor.generator.PerspectiveEditorScreenActivity;

public class RuntimePerspectiveEditorActivity extends PerspectiveEditorActivity {

    public RuntimePerspectiveEditorActivity(LayoutTemplate editor, PerspectiveEditorScreenActivity screen) {
        super(editor, screen);
    }

    @Override
    protected String getDefaultPanelType() {
        return StaticWorkbenchPanelPresenter.class.getName();
    }

}