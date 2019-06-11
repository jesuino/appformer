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

package org.uberfire.ext.page.builder.client.grapesjs.components;

import java.util.Collections;
import java.util.List;

import org.uberfire.ext.page.builder.client.grapesjs.js.GrapesJS.Type;

import elemental2.dom.Element;


public interface CustomComponent {
    
    ComponentBlock getBlock();

    Type getType(Type defaultType);
    
    default List<String> getComponentProperties() {
        return Collections.emptyList();
    }
    
    default void build(Element parent) {
        parent.innerHTML = "<em>Preview</em>";
    }

    String getTypeId();

}