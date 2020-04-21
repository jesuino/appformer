/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dashbuilder.backend.remote.services.dummy;

import java.util.Collection;
import java.util.Collections;

import javax.enterprise.context.ApplicationScoped;

import org.dashbuilder.navigation.layout.LayoutTemplateContext;
import org.dashbuilder.navigation.layout.LayoutTemplateInfo;
import org.dashbuilder.navigation.service.PerspectivePluginServices;
import org.jboss.errai.bus.server.annotations.Service;
import org.uberfire.ext.layout.editor.api.editor.LayoutTemplate;
import org.uberfire.ext.plugin.model.Plugin;

@Service
@ApplicationScoped
public class DummyPerspectivePluginServices implements PerspectivePluginServices {

    @Override
    public Collection<Plugin> listPlugins() {
        return Collections.emptyList();
    }

    @Override
    public Plugin getPerspectivePlugin(String perspectiveName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LayoutTemplate getLayoutTemplate(String perspectiveName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LayoutTemplate getLayoutTemplate(Plugin perspectivePlugin) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LayoutTemplateInfo getLayoutTemplateInfo(String perspectiveName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LayoutTemplateInfo getLayoutTemplateInfo(Plugin perspectivePlugin, LayoutTemplateContext layoutCtx) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LayoutTemplateInfo getLayoutTemplateInfo(LayoutTemplate layoutTemplate) {
        // TODO Auto-generated method stub
        return null;
    }

}
