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

package org.uberfire.ext.page.builder.service.impl;

import java.net.URI;
import java.util.HashMap;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.errai.bus.server.annotations.Service;
import org.jboss.errai.marshalling.server.ServerMarshalling;
import org.uberfire.ext.page.builder.api.model.PageModel;
import org.uberfire.ext.page.builder.api.service.PageBuilderService;
import org.uberfire.io.IOService;
import org.uberfire.java.nio.file.FileSystem;
import org.uberfire.java.nio.file.FileSystemAlreadyExistsException;
import org.uberfire.java.nio.file.NoSuchFileException;
import org.uberfire.java.nio.file.Path;
import org.uberfire.spaces.Space;
import org.uberfire.spaces.SpacesAPI;

/**
 *
 */
@Service
@Dependent
public class PageBuilderServiceImpl implements PageBuilderService {
    
    @Inject
    @Named("ioStrategy")
    IOService ioService;
    
    @Inject SpacesAPI spacesAPI;
    
    private final static String SPACE_PROP = "org.uberfire.demo.defaultspace";
    
    public final static String DEFAULT_SPACE_NAME = System.getProperty(SPACE_PROP, SpacesAPI.DEFAULT_SPACE_NAME);
    
    public static final Space DEFAULT_SPACE = new Space(DEFAULT_SPACE_NAME);

    public static final String DEFAULT_REPOSITORY = "page_builder";
    
    public static final String LOCATION_PATH = "pages/page";

    @Override
    public PageModel save(String htmlContent, String cssContent) {
        Path pagePath = getLocationPath();
        PageModel pageModel = new PageModel(htmlContent, cssContent);
        String content = ServerMarshalling.toJSON(pageModel);
        ioService.write(pagePath, content);
        return pageModel;
    }

    @Override
    public PageModel get() {
        Path pagePath = getLocationPath();
        String pageJSON = "";
        try {
            pageJSON = ioService.readAllString(pagePath);
        } catch (NoSuchFileException e) {
            System.out.println("Creating new file");
            PageModel pageModel = new PageModel();
            pageJSON = ServerMarshalling.toJSON(pageModel);
            ioService.write(pagePath, pageJSON);
        }
        return ServerMarshalling.fromJSON(pageJSON, PageModel.class);
    }
    
    private Path getLocationPath() {
        FileSystem fileSystem = getFileSystem();
        Path root = fileSystem.getRootDirectories().iterator().next();
        return root.resolve(LOCATION_PATH);
    }
    
    private FileSystem getFileSystem() {
        URI fileSystemURI = spacesAPI.resolveFileSystemURI(SpacesAPI.Scheme.DEFAULT,
                                                           DEFAULT_SPACE,
                                                           DEFAULT_REPOSITORY);
        try {
            return ioService.newFileSystem(fileSystemURI, getEnv());
        } catch (FileSystemAlreadyExistsException e) {
            return ioService.getFileSystem(fileSystemURI);
        }
    }
    
    @SuppressWarnings("serial")
    private HashMap<String, Object> getEnv() {
        return new HashMap<String, Object>() {{
             put("init",
                 Boolean.TRUE);
             put("internal",
                 Boolean.TRUE);
         }};

    }
}
