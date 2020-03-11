/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.dashbuilder.client;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import elemental2.dom.DomGlobal;
import org.dashbuilder.client.resources.i18n.AppConstants;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.uberfire.ext.security.management.client.ClientUserSystemManager;

/**
 * Entry-point for the Dashbuilder Runtime
 */
@EntryPoint
public class RuntimeEntryPoint {

    private AppConstants constants = AppConstants.INSTANCE;
    
    @Inject
    ClientUserSystemManager userSystemManager;
    
    @PostConstruct
    public void start() {
        userSystemManager.waitForInitialization(this::hideLoading);
    }
    
    
    public void hideLoading() {
        DomGlobal.document.getElementById("loading").remove();
    }
    
}