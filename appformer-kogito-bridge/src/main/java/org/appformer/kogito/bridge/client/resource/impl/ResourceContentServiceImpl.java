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

package org.appformer.kogito.bridge.client.resource.impl;

import org.appformer.kogito.bridge.client.resource.ResourceContentService;
import org.appformer.kogito.bridge.client.resource.interop.ResourceContentEditorServiceWrapper;

import elemental2.promise.Promise;

public class ResourceContentServiceImpl implements ResourceContentService {

    @Override
    public Promise<String> get(String uri) {
        return ResourceContentEditorServiceWrapper.get().get(uri);
    }

    @Override
    public Promise<String[]> list(String pattern) {
        return ResourceContentEditorServiceWrapper.get().list(pattern);
    }

}
