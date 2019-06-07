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

import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.uberfire.ext.page.builder.api.model.PageModel;

import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLStyleElement;

@Templated
@Dependent
public class GrapesJSPageDisplayerViewImpl implements GrapesJSPageDisplayer.View {
    
    @Inject
    @DataField
    public HTMLDivElement root;
    
    @Inject
    @DataField
    public HTMLDivElement grapesJSPageContainer;

    private GrapesJSPageDisplayer presenter;

    private HTMLStyleElement style;

    @Override
    public HTMLElement getElement() {
        return root;
    }

    @Override
    public void init(GrapesJSPageDisplayer presenter) {
        this.presenter = presenter;
    }

    @Override
    public void displayContent(PageModel pageModel) {
        style = (HTMLStyleElement) DomGlobal.document.createElement("style");
        style.type = "text/css";
        style.innerHTML = pageModel.getCss();
        DomGlobal.document.body.appendChild(style);
        grapesJSPageContainer.innerHTML = pageModel.getHtml();
    }

    @Override
    public void error(String msg) {
        DomGlobal.alert(msg);
    }

    @Override
    public void clearContent() {
        DomGlobal.document.removeChild(style);
        grapesJSPageContainer.innerHTML = "";
    }

}
