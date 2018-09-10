/*
 * Copyright 2014 Red Hat, Inc. and/or its affiliates.
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
package org.dashbuilder.renderer.c3.client;

import static org.dashbuilder.displayer.DisplayerSubType.LINE;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.dashbuilder.displayer.DisplayerSettings;
import org.dashbuilder.displayer.DisplayerSubType;
import org.dashbuilder.displayer.DisplayerType;
import org.dashbuilder.displayer.client.AbstractRendererLibrary;
import org.dashbuilder.displayer.client.Displayer;
import org.dashbuilder.renderer.c3.client.charts.bar.C3BarChartDisplayer;
import org.dashbuilder.renderer.c3.client.charts.line.C3LineChartDisplayer;
import org.dashbuilder.renderer.c3.client.exports.ResourcesInjector;
import org.jboss.errai.ioc.client.container.SyncBeanManager;

@ApplicationScoped
public class C3Renderer extends AbstractRendererLibrary {

    public static final String UUID = "c3";

    @Inject
    protected SyncBeanManager beanManager;

    @Override
    public String getUUID() {
        return UUID;
    }

    @Override
    public String getName() {
        return "C3 Charts";
    }

    @Override
    public List<DisplayerSubType> getSupportedSubtypes(DisplayerType displayerType) {
        switch (displayerType) {
        case LINECHART:
            return Arrays.asList(LINE);
        default:
            return Arrays.asList();
        }
    }

    public Displayer lookupDisplayer(DisplayerSettings displayerSettings) {
        ResourcesInjector.ensureInjected();
        DisplayerType displayerType = displayerSettings.getType();
        C3Displayer displayer;
        switch (displayerType) {
        case LINECHART:
            displayer = new C3LineChartDisplayer();
            break;
        case BARCHART:
            displayer = new C3BarChartDisplayer();
            break;
        default:
            return null;
        }
        return displayer;
    }

    @Override
    public List<DisplayerType> getSupportedTypes() {
        return Arrays.asList(DisplayerType.LINECHART);
    }
}
