package org.dashbuilder.client.screens;

import javax.enterprise.context.Dependent;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchScreen;

@Dependent
@Templated
@WorkbenchScreen(identifier = EmptyScreen.ID)
public class EmptyScreen extends Composite {

    public static final String ID = "EmptyScreen";

    @WorkbenchPartTitle
    public String getScreenTitle() {
        return "Welcome to Dashbuilder";
    }

}
