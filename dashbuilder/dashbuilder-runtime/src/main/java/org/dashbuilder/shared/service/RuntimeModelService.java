package org.dashbuilder.shared.service;

import java.util.Optional;

import org.dashbuilder.shared.model.RuntimeModel;
import org.jboss.errai.bus.server.annotations.Remote;

@Remote
public interface RuntimeModelService {

    public Optional<RuntimeModel> getRuntimeModel(String runtimeModelId);

}