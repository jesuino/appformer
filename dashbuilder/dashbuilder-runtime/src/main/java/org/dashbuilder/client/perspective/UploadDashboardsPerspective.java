package org.dashbuilder.client.perspective;

import javax.enterprise.context.ApplicationScoped;

import org.dashbuilder.client.screens.UploadDashboardsScreen;
import org.uberfire.client.annotations.Perspective;
import org.uberfire.client.annotations.WorkbenchPerspective;
import org.uberfire.client.workbench.panels.impl.StaticWorkbenchPanelPresenter;
import org.uberfire.mvp.PlaceRequest;
import org.uberfire.mvp.impl.DefaultPlaceRequest;
import org.uberfire.workbench.model.PerspectiveDefinition;
import org.uberfire.workbench.model.impl.PartDefinitionImpl;
import org.uberfire.workbench.model.impl.PerspectiveDefinitionImpl;

@ApplicationScoped
@WorkbenchPerspective(identifier = UploadDashboardsPerspective.ID)
public class UploadDashboardsPerspective {

    public static final String ID = "EmptyPerspective";

    @Perspective
    public PerspectiveDefinition buildPerspective() {
        PerspectiveDefinition perspective = new PerspectiveDefinitionImpl(StaticWorkbenchPanelPresenter.class.getName());
        final PlaceRequest place = new DefaultPlaceRequest(UploadDashboardsScreen.ID);
        PartDefinitionImpl dashboardScreen = new PartDefinitionImpl(place);
        perspective.getRoot().addPart(dashboardScreen);
        perspective.setName("Upload Dashboard");
        return perspective;
    }

}
