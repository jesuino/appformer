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

package org.dashbuilder.external;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.activation.MimetypesFileTypeMap;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.dashbuilder.external.service.ExternalComponentAssetProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.stream.Collectors.joining;

public class ExternalComponentServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(ExternalComponentServlet.class);

    private static final long serialVersionUID = 1L;

    @Inject
    ExternalComponentAssetProvider assetProvider;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    private void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.reset();
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        if (pathParts.length < 3) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String componentId = pathParts[1];
        String assetPath = Arrays.stream(pathParts).skip(2).collect(joining(File.separator));

        logger.info("Retrieving asset {} for component.", componentId, assetPath);

        try {
            InputStream assetStream = assetProvider.openAsset(componentId, assetPath);
            int size = IOUtils.copy(assetStream, resp.getOutputStream());
            assetStream.close();
            String mimeType = new MimetypesFileTypeMap().getContentType(pathInfo);
            resp.setContentType(mimeType);
            resp.setContentLength(size);

        } catch (Exception e) {
            logger.info("Not able to find resource {} for component {}", assetPath, componentId);
            logger.debug("Error opening external component asset", e);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    }

}