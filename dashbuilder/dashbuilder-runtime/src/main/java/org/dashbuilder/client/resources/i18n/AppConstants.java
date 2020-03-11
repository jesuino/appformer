package org.dashbuilder.client.resources.i18n;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface AppConstants extends Messages {

    public static final AppConstants INSTANCE = GWT.create(AppConstants.class);

}
