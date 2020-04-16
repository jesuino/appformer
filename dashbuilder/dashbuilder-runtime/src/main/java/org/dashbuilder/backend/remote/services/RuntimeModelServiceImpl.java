package org.dashbuilder.backend.remote.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.dashbuilder.backend.RuntimeOptions;
import org.dashbuilder.backend.navigation.RuntimeNavigationBuilder;
import org.dashbuilder.navigation.NavTree;
import org.dashbuilder.shared.model.ImportModel;
import org.dashbuilder.shared.model.PerspectiveContent;
import org.dashbuilder.shared.model.RuntimeModel;
import org.dashbuilder.shared.service.ImportModelRegistry;
import org.dashbuilder.shared.service.RuntimeModelService;
import org.jboss.errai.bus.server.annotations.Service;
import org.uberfire.ext.layout.editor.api.editor.LayoutTemplate;

@Service
@ApplicationScoped
public class RuntimeModelServiceImpl implements RuntimeModelService {

    @Inject
    ImportModelRegistry importModelRegistry;

    @Inject
    RuntimeNavigationBuilder runtimeNavigationBuilder;
    
    @Inject
    RuntimeOptions runtimeOptions;

    Gson gson;

    @PostConstruct
    void init() {
        gson = new GsonBuilder().create();
    }

    @Override
    public Optional<RuntimeModel> getRuntimeModel(String exportId) {
        Optional<ImportModel> importModelOp = importModelRegistry.get(exportId);

        if (!importModelOp.isPresent() && exportId != null) {
            String fileName = String.join("/", runtimeOptions.getImportsBaseDir(), exportId).concat(".zip");
            importModelOp = importModelRegistry.registerFile(fileName);
        }
        return importModelOp.flatMap(this::buildRuntimeModel);
    }

    private Optional<RuntimeModel> buildRuntimeModel(ImportModel importModel) {
        List<LayoutTemplate> layoutTemplates = importModel.getPerspectives().stream()
                                                          .map(PerspectiveContent::getContent)
                                                          .map(content -> gson.fromJson(content, LayoutTemplate.class))
                                                          .collect(Collectors.toList());
        Optional<String> navTreeOp = importModel.getNavigationJSON();
        NavTree navTree = runtimeNavigationBuilder.build(navTreeOp, layoutTemplates);
        return Optional.of(new RuntimeModel(navTree, layoutTemplates));
    }

}
