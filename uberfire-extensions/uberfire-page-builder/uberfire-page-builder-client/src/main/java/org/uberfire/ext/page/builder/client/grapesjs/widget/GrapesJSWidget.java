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

package org.uberfire.ext.page.builder.client.grapesjs.widget;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.errai.common.client.api.Caller;
import org.uberfire.client.mvp.UberElemental;
import org.uberfire.ext.page.builder.api.model.PageModel;
import org.uberfire.ext.page.builder.api.service.PageBuilderService;

@Dependent
public class GrapesJSWidget {
    
    public interface View extends UberElemental<GrapesJSWidget> {
        
        void loadEditor(PageModel pageModel);
        
        void sucessSaving();

        /**
         * @param string
         */
        void error(String string);
        
    }
    
    View view;
    private Caller<PageBuilderService> pageBuilderService;

    @Inject
    public GrapesJSWidget(View view, 
                          Caller<PageBuilderService> pageBuilderService) {
        this.view = view;
        this.pageBuilderService = pageBuilderService;
        view.init(this);
    }

    public View getView() {
        return view;
    }
    
    
    public void load() {
        pageBuilderService.call(pageModel -> view.loadEditor((PageModel) pageModel), 
                               (msg, error) -> {
                                    view.error("Error loading initial content: " + error.getMessage());
                                    return true;
                                }).get();
        
    }

    /**
     * @param html
     * @param css
     */
    public void saveContent(String html, String css) {
        pageBuilderService.call(pageModel -> view.sucessSaving(), (msg, error) -> {
            view.error("Error saving content: " + error.getMessage());
            return true;
        }).save(html, css);
    }

}