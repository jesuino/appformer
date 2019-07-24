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

package org.dashbuilder.data.screens;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.errai.common.client.api.elemental2.IsElement;
import org.jboss.errai.common.client.dom.elemental2.Elemental2DomUtil;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.uberfire.ext.editor.commons.client.file.popups.CommonModalBuilder;
import org.uberfire.ext.widgets.common.client.common.popups.BaseModal;

import com.google.gwt.user.client.DOM;

import org.dashbuilder.data.resources.i18n.DataTransferConstants;

import com.google.gwt.dom.client.Element;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLParagraphElement;
import elemental2.dom.HTMLUListElement;
import elemental2.dom.Node;
import jsinterop.base.Js;

@Templated
@Dependent
public class DataTransferPopUpView implements DataTransferPopUp.View, IsElement {

	private DataTransferPopUp presenter;
	private BaseModal modal;
	private DataTransferConstants i18n = DataTransferConstants.INSTANCE;
    private HTMLDivElement root;
    private HTMLDivElement body;
    private HTMLParagraphElement filesImportedCount;
    private HTMLUListElement filesImportedList;
    private Elemental2DomUtil elem2Dom;
    
    public DataTransferPopUpView() {

    }
    
    @Inject
    public DataTransferPopUpView(
    		final @DataField HTMLDivElement root,
    		final @DataField HTMLDivElement body,
    		final @DataField HTMLParagraphElement filesImportedCount,
    		final @DataField HTMLUListElement filesImportedList,
    		final Elemental2DomUtil elem2Dom) {
    
    	this.root = root;
    	this.body = body;
    	this.filesImportedCount = filesImportedCount;
    	this.filesImportedList = filesImportedList;
    	this.elem2Dom = elem2Dom;
    }

    @Override
    public void init(DataTransferPopUp presenter) {
        this.presenter = presenter;

        modal = new CommonModalBuilder()
        		.addHeader(i18n.dataTransferPopUpViewTitle())
                .addBody(body)
                .build();
    }

	@Override
	public HTMLElement getElement() {
		return root;
	}

    public void show(List<String> filesImported) {
    	modal.setTitle(i18n.dataTransferPopUpViewTitle());
    	elem2Dom.removeAllElementChildren(filesImportedList);
    	filesImportedCount.textContent = i18n.importResultMessage(filesImported.size());

    	filesImported.forEach(fileImported -> {
    		Element element = DOM.createElement("li");
    		element.setClassName("list-group-item");
    		element.setInnerText(fileImported);
        	filesImportedList.appendChild((Node) Js.cast(element));
    	});

        modal.show();
    }
}