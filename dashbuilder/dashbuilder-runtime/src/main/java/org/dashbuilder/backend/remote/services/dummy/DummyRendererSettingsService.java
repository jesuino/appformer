package org.dashbuilder.backend.remote.services.dummy;

import javax.enterprise.context.ApplicationScoped;

import org.dashbuilder.renderer.RendererSettings;
import org.dashbuilder.renderer.service.RendererSettingsService;
import org.jboss.errai.bus.server.annotations.Service;

@Service
@ApplicationScoped
public class DummyRendererSettingsService implements RendererSettingsService {

    @Override
    public RendererSettings getSettings() {
        return new RendererSettings();
    }
    
    

}
