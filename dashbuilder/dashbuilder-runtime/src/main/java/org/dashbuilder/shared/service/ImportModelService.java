package org.dashbuilder.shared.service;

import java.util.Optional;

import org.dashbuilder.shared.model.ImportModel;
import org.dashbuilder.shared.model.RuntimeModel;
import org.jboss.errai.bus.server.annotations.Remote;

@Remote
public interface ImportModelService {

    public Optional<ImportModel> getImportModel(String importModelId);

    public Optional<RuntimeModel> getRuntimeModel(String runtimeModelId);

}
