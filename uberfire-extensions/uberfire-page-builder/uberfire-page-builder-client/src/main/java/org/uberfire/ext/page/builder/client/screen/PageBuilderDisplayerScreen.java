/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.uberfire.ext.page.builder.client.screen;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.errai.common.client.ui.ElementWrapperWidget;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchPartView;
import org.uberfire.client.annotations.WorkbenchScreen;
import org.uberfire.ext.page.builder.client.exports.ResourcesInjector;
import org.uberfire.ext.page.builder.client.grapesjs.widget.GrapesJSPageDisplayer;
import org.uberfire.lifecycle.OnClose;
import org.uberfire.lifecycle.OnOpen;
import org.uberfire.lifecycle.OnStartup;

import com.google.gwt.user.client.ui.IsWidget;

import elemental2.dom.HTMLElement;

/**
 *
 */
@ApplicationScoped
@WorkbenchScreen(identifier = PageBuilderDisplayerScreen.ID)
public class PageBuilderDisplayerScreen {
    
    public static final String ID = "PageBuilderDisplayerScreen";
    
    GrapesJSPageDisplayer grapesJSPageDisplayer;
    
    public PageBuilderDisplayerScreen() {
    }

    @Inject
    public PageBuilderDisplayerScreen(GrapesJSPageDisplayer grapesJSPageDisplayer) {
        this.grapesJSPageDisplayer = grapesJSPageDisplayer;
    }
    
    @WorkbenchPartTitle
    public String title() {
        return "Page Builder Displayer";
    }
    
    @WorkbenchPartView
    public IsWidget part() {
        HTMLElement element = grapesJSPageDisplayer.getView().getElement();
        return ElementWrapperWidget.getWidget(element);
    }
    
    @OnOpen
    public void onOpen() {
        grapesJSPageDisplayer.loadContent();
    }
    
    @OnClose
    public void onClose() {
    }
    
    @OnStartup
    public void init() {
        ResourcesInjector.ensureGrapesJsInjected();
    }
    
}