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

package org.uberfire.ext.page.builder.client.grapesjs.components.appformerjsscreen;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.errai.common.client.dom.HTMLElement;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.ext.page.builder.client.grapesjs.components.ComponentBlock;
import org.uberfire.ext.page.builder.client.grapesjs.components.CustomComponent;
import org.uberfire.ext.page.builder.client.grapesjs.components.GrapesJSPluginsLoader;
import org.uberfire.ext.page.builder.client.grapesjs.js.Block;
import org.uberfire.ext.page.builder.client.grapesjs.js.BlockAttributes;
import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJS.ElementTypeTester;
import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJS.Model;
import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJS.ModelTypeRecognizer;
import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJS.Type;
import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJS.TypeDescriptor;
import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJSUtil;
import org.uberfire.ext.page.builder.client.grapesjs.js.ModelDefaultProperties;
import org.uberfire.ext.page.builder.client.grapesjs.js.Trait;
import org.uberfire.mvp.PlaceRequest;
import org.uberfire.mvp.impl.DefaultPlaceRequest;

import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLDivElement;
import jsinterop.base.Js;


@ApplicationScoped
public class AppformerScreenComponent implements CustomComponent {
    
    private static final String TYPE_ID = "appformer-screen";
    
    private static final String PARAM_SCREEN_ID = "data-screen-id";
    
    Map<String, PlaceRequest> placesRequest = new HashMap<>();

    @Inject
    private PlaceManager placeManager;

    @Override
    public Type getType(Type defaultType) {
        Trait[] traits = {
                Trait.create("text", "Screen Id", PARAM_SCREEN_ID)
        };
        ModelDefaultProperties model = ModelDefaultProperties.create("div", traits);
        Model defaultModel = defaultType.getModel();
        ElementTypeTester tester = el -> {
            if (el instanceof elemental2.dom.HTMLElement && 
                TYPE_ID.equals(el.getAttribute(GrapesJSPluginsLoader.PROP_APPFORMER_TYPE))) {
                return TypeDescriptor.create(TYPE_ID);
            }
            return null;
        };
        ModelTypeRecognizer recognizer = ModelTypeRecognizer.create(tester);
        Model extendedModel = GrapesJSUtil.buildExtendedModel(defaultModel, model, recognizer);
        return Type.create(extendedModel, defaultType.getView());
    }

    @Override
    public ComponentBlock getBlock() {
        HTMLDivElement blockWrap = (HTMLDivElement) DomGlobal.document.createElement("div");
        HTMLDivElement blockElement = (HTMLDivElement) DomGlobal.document.createElement("div");
        blockElement.setAttribute("data-gjs-type", TYPE_ID);
        blockElement.setAttribute(GrapesJSPluginsLoader.PROP_APPFORMER_TYPE, TYPE_ID);
        blockElement.innerHTML = "<em>select a screen</em>";
        blockWrap.appendChild(blockElement);
        BlockAttributes blockAttributes = BlockAttributes.create("");
        Block block = Block.create("AppFormer Screen", "AppFormer", blockWrap.innerHTML, blockAttributes);
        return new ComponentBlock("app-former-screen-block", block);
    }
    
    @Override
    public List<String> getComponentProperties() {
        return Arrays.asList(PARAM_SCREEN_ID);
    }
    
    @Override
    public void build(Element parent) {
        String screenId = parent.getAttribute(PARAM_SCREEN_ID);
        if (screenId != null && ! screenId.trim().isEmpty()) {
            parent.innerHTML = "";
            PlaceRequest screenPlaceRequest = placeRequestForScreen(screenId);
            HTMLElement erraiEl = Js.cast(parent);
            placeManager.tryClosePlace(screenPlaceRequest, () -> {});
            placeManager.goTo(screenPlaceRequest, erraiEl);
        }
    }

    private PlaceRequest placeRequestForScreen(String screenId) {
        placesRequest.computeIfAbsent(screenId, s -> {
            Map<String, String> params = new HashMap<>();
            params.put("requester", "GrapesJSEditor");
            return new DefaultPlaceRequest(screenId, params);
        });
        return placesRequest.get(screenId);
    }

    @Override
    public String getTypeId() {
        return TYPE_ID;
    }

}
