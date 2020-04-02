package org.dashbuilder.backend.remote.services.dummy;

import java.util.Collections;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.errai.bus.server.annotations.Service;
import org.uberfire.experimental.service.backend.BackendExperimentalFeaturesRegistryService;
import org.uberfire.experimental.service.backend.ExperimentalFeaturesSession;
import org.uberfire.experimental.service.backend.impl.ExperimentalFeaturesSessionImpl;
import org.uberfire.experimental.service.registry.impl.ExperimentalFeaturesRegistryImpl;

@Service
@ApplicationScoped
public class DummyBackendExperimentalFeaturesRegistryService implements BackendExperimentalFeaturesRegistryService{

    @Override
    public ExperimentalFeaturesSession getExperimentalFeaturesSession() {
        ExperimentalFeaturesSessionImpl session = new ExperimentalFeaturesSessionImpl();
        session.setRegistry(new ExperimentalFeaturesRegistryImpl(Collections.emptyList()));
        return session;
    }

}
