package org.dashbuilder.backend.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.dashbuilder.shared.event.NewDataSetContentEvent;
import org.dashbuilder.shared.model.DashbuilderRuntimeMode;
import org.dashbuilder.shared.model.ImportModel;
import org.dashbuilder.shared.service.ImportModelParser;
import org.dashbuilder.shared.service.ImportModelRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ImportModelRegistryImpl implements ImportModelRegistry {

    Logger logger = LoggerFactory.getLogger(ImportModelRegistryImpl.class);

    Map<String, ImportModel> importModels;

    DashbuilderRuntimeMode mode = DashbuilderRuntimeMode.SINGLE_IMPORT;

    @Inject
    ImportModelParser parser;

    @Inject
    Event<NewDataSetContentEvent> newDataSetContentEvent;

    @PostConstruct
    public void init() {
        importModels = new HashMap<>();
    }

    @Override
    public Optional<ImportModel> get(String id) {
        if (mode != DashbuilderRuntimeMode.MULTIPLE_IMPORT) {
            return importModels.values().stream().findFirst();
        }
        return Optional.ofNullable(importModels.get(id));
    }

    @Override
    public Optional<ImportModel> registerFile(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return Optional.empty();
        }
        if (!new File(filePath).exists()) {
            logger.debug("File does not exist {}", filePath);
            throw new IllegalArgumentException("File does not exist: " + filePath);
        }
        try (FileInputStream fis = new FileInputStream(filePath)) {
            return register(filePath, fis);
        } catch (IOException e) {
            logger.error("Not able to load file {}", filePath, e);
            throw new IllegalArgumentException("Error loading import file: " + filePath, e);
        }
    }

    @Override
    public void setMode(DashbuilderRuntimeMode mode) {
        this.mode = mode;
    }

    @Override
    public boolean isEmpty() {
        return importModels.isEmpty();
    }

    @Override
    public DashbuilderRuntimeMode getMode() {
        return mode;
    }

    public Optional<ImportModel> register(String id, InputStream fileStream) {
        if (!acceptingNewImports()) {
            throw new IllegalArgumentException("New imports are not allowed in mode " + mode);
        }
        try {
            ImportModel importModel = parser.parse(fileStream);
            if (id == null) {
                id = UUID.randomUUID().toString();
            }
            importModels.put(id, importModel);
            newDataSetContentEvent.fire(new NewDataSetContentEvent(importModel.getDatasets()));
            return Optional.of(importModel);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error parsing import model.");
        }
    }

}