package org.dashbuilder.backend.remote.services.dummy;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.errai.bus.server.annotations.Service;
import org.uberfire.backend.plugin.RuntimePlugin;
import org.uberfire.backend.plugin.RuntimePluginService;

@Service
@ApplicationScoped
public class DummyRuntimePluginService implements RuntimePluginService {

    @Override
    public Collection<String> listFrameworksContent() {
        return Collections.emptyList();
    }

    @Override
    public Collection<String> listPluginsContent() {
        return Collections.emptyList();

    }

    @Override
    public String getTemplateContent(String url) {
        return "";
    }

    @Override
    public String getRuntimePluginTemplateContent(String url) {
        return "";
    }

    @Override
    public List<RuntimePlugin> getRuntimePlugins() {
        return Collections.emptyList();
    }

}
