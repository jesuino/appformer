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

package org.dashbuilder.displayer.client.widgets;

import java.util.function.Consumer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import elemental2.dom.DomGlobal;
import org.dashbuilder.displayer.external.ExternalComponentMessage;
import org.uberfire.client.mvp.UberElemental;

@Dependent
public class ExternalComponentPresenter {

    private static final String COMPONENT_ID = "component_id";

    final String COMPONENT_SERVER_PATH = "dashbuilder/component";

    final String componentId = DOM.createUniqueId();

    private Consumer<ExternalComponentMessage> messageConsumer;

    public interface View extends UberElemental<ExternalComponentPresenter> {

        void setComponentURL(String url);

        void postMessage(ExternalComponentMessage message);
    }

    @Inject
    View view;

    @PostConstruct
    public void init() {
        view.init(this);
    }

    public void withComponent(String componentId) {
        String url = String.join("/", GWT.getHostPageBaseURL(), COMPONENT_SERVER_PATH, componentId, "index.html");
        view.setComponentURL(url);
    }

    public void sendMessage(ExternalComponentMessage message) {
        message.setProperty(COMPONENT_ID, componentId);
        view.postMessage(message);
    }

    public void receiveMessage(ExternalComponentMessage message) {
        Object destinationId = message.getProperty(COMPONENT_ID);
        if (!componentId.equals(destinationId)) {
            return;
        }
        DomGlobal.console.log("Component received message!");
        DomGlobal.console.log(message);
        if (messageConsumer != null) {
            messageConsumer.accept(message);
        }
    }

    public View getView() {
        return view;
    }

    public void setMessageConsumer(Consumer<ExternalComponentMessage> messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    public String getComponentId() {
        return componentId;
    }

}