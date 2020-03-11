package org.dashbuilder.client.perspective;

import javax.enterprise.context.ApplicationScoped;

import org.dashbuilder.client.screens.EmptyScreen;
import org.uberfire.client.annotations.Perspective;
import org.uberfire.client.annotations.WorkbenchPerspective;
import org.uberfire.client.workbench.panels.impl.StaticWorkbenchPanelPresenter;
import org.uberfire.mvp.PlaceRequest;
import org.uberfire.mvp.impl.DefaultPlaceRequest;
import org.uberfire.workbench.model.PerspectiveDefinition;
import org.uberfire.workbench.model.impl.PartDefinitionImpl;
import org.uberfire.workbench.model.impl.PerspectiveDefinitionImpl;

@ApplicationScoped
@WorkbenchPerspective(identifier = DashboardPerspective.ID, isDefault = true, isTransient = true)
public class DashboardPerspective {

    static final String ID = "DashboardPerspective";

    @Perspective
    public PerspectiveDefinition buildPerspective() {
        final PlaceRequest place = new DefaultPlaceRequest(EmptyScreen.ID);
        final PerspectiveDefinition perspective = new PerspectiveDefinitionImpl(StaticWorkbenchPanelPresenter.class.getName());
        perspective.setName("Dashboards");
        perspective.getRoot().addPart(new PartDefinitionImpl(place));
        return perspective;
    }

}
