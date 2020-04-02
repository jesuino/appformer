package org.dashbuilder.backend.services.impl;

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

import static org.dashbuilder.shared.model.DashbuilderRuntimeMode.MULTIPLE_IMPORT;

@ApplicationScoped
public class ImportModelRegistryImpl implements ImportModelRegistry {

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
        if (mode == MULTIPLE_IMPORT) {
            return Optional.ofNullable(importModels.get(id));
        }
        return importModels.values().stream().findFirst();
    }

    @Override
    public void store(String id, InputStream fileStream) {
        if (!acceptingNewImports()) {
            throw new IllegalArgumentException("New imports are not allowed in mode " + mode);
        }
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        ImportModel importModel = parser.parse(fileStream);
        importModels.put(id, importModel);
        newDataSetContentEvent.fire(new NewDataSetContentEvent(importModel.getDatasets()));
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

}
