package org.dashbuilder.backend;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.dashbuilder.backend.services.dataset.RuntimeCSVFileStorage;
import org.dashbuilder.backend.services.dataset.provider.RuntimeDataSetProviderRegistry;
import org.dashbuilder.dataset.def.DataSetDef;
import org.dashbuilder.dataset.def.DataSetDefRegistry;
import org.dashbuilder.dataset.json.DataSetDefJSONMarshaller;
import org.dashbuilder.shared.event.NewDataSetContentEvent;
import org.dashbuilder.shared.model.DataSetContent;
import org.dashbuilder.shared.model.DataSetContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class DataSetContentListener {
    
    Logger logger = LoggerFactory.getLogger(DataSetContentListener.class);

    @Inject
    DataSetDefRegistry registry;

    @Inject
    RuntimeCSVFileStorage storage;

    @Inject 
    RuntimeDataSetProviderRegistry runtimeDataSetProviderRegistry;

    DataSetDefJSONMarshaller defMarshaller;

    @PostConstruct
    public void init() {
        defMarshaller = runtimeDataSetProviderRegistry.getDataSetDefJSONMarshaller();
    }

    public void register(@Observes NewDataSetContentEvent newDataSetContentEvent) {
        newDataSetContentEvent.getContent().forEach(this::registerDataSetContent);
    }

    public void registerDataSetContent(DataSetContent content) {
        try {
            DataSetContentType contentType = content.getContentType();
            switch (contentType) {
                case CSV:
                    storage.storeCSV(content.getId(), content.getContent());
                    break;
                case DEFINITION:
                    DataSetDef dataSetDef = defMarshaller.fromJson(content.getContent());
                    registry.registerDataSetDef(dataSetDef);
                    break;
                default:
                    logger.error("Unknow DataSet Content Type: {}", contentType.name(), null);
                    break;
            }
        } catch (Exception e) {
            logger.error("Error registering dataset", e);
        }
    }
}
