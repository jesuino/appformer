package org.dashbuilder.shared.service;

import java.io.InputStream;

import org.dashbuilder.shared.model.ImportModel;

/**
 * Parses a ImportModel content to the object ImportModel
 *
 */
public interface ImportModelParser {

    public ImportModel parse(InputStream is);

}