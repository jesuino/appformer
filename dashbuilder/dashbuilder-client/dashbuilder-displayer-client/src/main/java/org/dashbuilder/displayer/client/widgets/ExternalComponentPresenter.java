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

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.Widget;
import jsinterop.base.Js;
import org.uberfire.client.mvp.UberElemental;

@Dependent
public class ExternalComponentPresenter extends Widget {

    final String COMPONENT_SERVER_PATH = "dashbuilder/component";

    public interface View extends UberElemental<ExternalComponentPresenter> {

        void setComponentURL(String url);

        void postProperties(JavaScriptObject javaScriptObject);
    }

    @Inject
    View view;

    @PostConstruct
    public void init() {
        view.init(this);
        super.setElement(Js.<Element> uncheckedCast(view.getElement()));
    }

    public void withComponent(String componentId) {
        String url = String.join("/", GWT.getHostPageBaseURL(), COMPONENT_SERVER_PATH, componentId, "index.html");
        view.setComponentURL(url);
    }

    public void setComponentProperties(Map<String, String> props) {
        JSONObject params = new JSONObject();
        props.forEach((k, v) -> params.put(k, new JSONString(v)));
        view.postProperties(params.getJavaScriptObject());
    }

}