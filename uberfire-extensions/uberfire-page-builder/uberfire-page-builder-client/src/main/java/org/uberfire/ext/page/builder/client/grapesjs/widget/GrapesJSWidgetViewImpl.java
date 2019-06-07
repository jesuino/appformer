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

package org.uberfire.ext.page.builder.client.grapesjs.widget;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.errai.common.client.api.elemental2.IsElement;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.uberfire.ext.page.builder.api.model.PageModel;
import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJS;
import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJS.Editor;
import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJSConfig;
import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJSUtil;

import com.google.gwt.event.dom.client.ClickEvent;

import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLButtonElement;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;

/**
 *
 */
@Templated
@Dependent
public class GrapesJSWidgetViewImpl implements GrapesJSWidget.View, IsElement {
    
    @Inject
    @DataField
    public HTMLDivElement root;
    
    @Inject
    @DataField
    public HTMLDivElement grapesJSContainer;

    @Inject
    @DataField
    public HTMLButtonElement btnSave;
    
    private GrapesJSWidget presenter;
    
    final static String PLUGINS[] = {"grapesjs-preset-webpage"};

    private Editor editor; 

    @Override
    public void init(GrapesJSWidget presenter) {
        this.presenter = presenter;
    }
    
    @Override
    public HTMLElement getElement() {
        return root;
    }
    
    @Override
    public void loadEditor(PageModel pageModel) {
        GrapesJSConfig conf = GrapesJSConfig.create(grapesJSContainer, PLUGINS);
        editor = GrapesJS.Builder.get().init(conf);
        editor.setComponents(pageModel.getHtml());
        editor.setStyle(pageModel.getCss());
        GrapesJSUtil.addCssClassPrefix(editor, "appformer-page-");
    }
    
    @EventHandler("btnSave")
    public void saveHandler(final ClickEvent clickEvent) {
        btnSave.disabled = true;
        presenter.saveContent(editor.getHtml(), editor.getCss());
    }

    @Override
    public void sucessSaving() {
        btnSave.disabled = false;
        DomGlobal.window.alert("Content Saved with success");
    }
    @Override
    public void error(String message) {
        btnSave.disabled = false;
        DomGlobal.window.alert(message);
    }

}