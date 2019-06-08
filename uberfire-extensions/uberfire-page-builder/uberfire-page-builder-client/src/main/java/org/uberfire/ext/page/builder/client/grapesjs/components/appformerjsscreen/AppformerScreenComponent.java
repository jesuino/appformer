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

import javax.enterprise.context.ApplicationScoped;

import org.uberfire.ext.page.builder.client.grapesjs.components.ComponentBlock;
import org.uberfire.ext.page.builder.client.grapesjs.components.ComponentType;
import org.uberfire.ext.page.builder.client.grapesjs.components.CustomComponent;
import org.uberfire.ext.page.builder.client.grapesjs.js.Block;
import org.uberfire.ext.page.builder.client.grapesjs.js.BlockAttributes;
import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJSUtil;
import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJS.Model;
import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJS.Type;
import org.uberfire.ext.page.builder.client.grapesjs.js.ModelDefaultProperties;
import org.uberfire.ext.page.builder.client.grapesjs.js.Trait;


@ApplicationScoped
public class AppformerScreenComponent implements CustomComponent {
    
    private static final String TYPE_ID = "appformer-screen";

    private static final String BLOCK_CONTENT = "<div data-gjs-type=\""+TYPE_ID+"\" data-appformerscreen>YOUR SCREEN HERE</div>";

    @Override
    public ComponentType getType(Type defaultType) {
        Trait[] traits = {
                Trait.create("text", "Screen Id", "screen-id")
        };
        ModelDefaultProperties model = ModelDefaultProperties.create("div", traits);
        Model defaultModel = defaultType.getModel();
        Model extendedModel = GrapesJSUtil.buildExtendedModel(defaultModel, model);
        Type type = Type.create(extendedModel, defaultType.getView());
        return new ComponentType(TYPE_ID, type);
    }

    @Override
    public ComponentBlock getBlock() {
        BlockAttributes blockAttributes = BlockAttributes.create("");
        Block block = Block.create("AppFormer Screen", "AppFormer", 
                            BLOCK_CONTENT, blockAttributes);
        return new ComponentBlock("app-former-screen-block", block);
    }

}
