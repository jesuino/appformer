package org.dashbuilder.backend.remote.services.dummy;

import java.util.Collection;
import java.util.Collections;

import org.uberfire.experimental.service.backend.BackendExperimentalFeatureDefRegistry;
import org.uberfire.experimental.service.definition.ExperimentalFeatureDefinition;

public class DummyBackendExperimentalFeatureDefRegistry implements BackendExperimentalFeatureDefRegistry {

    @Override
    public ExperimentalFeatureDefinition getFeatureById(String definitionId) {
        return null;
    }

    @Override
    public Collection<ExperimentalFeatureDefinition> getAllFeatures() {
        return Collections.emptyList();
    }

    @Override
    public Collection<ExperimentalFeatureDefinition> getGlobalFeatures() {
        return Collections.emptyList();
    }

    @Override
    public Collection<ExperimentalFeatureDefinition> getUserFeatures() {
        return Collections.emptyList();
    }

    @Override
    public Collection<ExperimentalFeatureDefinition> loadFeatureDefinitions(Collection<ExperimentalFeatureDefinition> clientDefinitions) {
        return Collections.emptyList();
    }

}
