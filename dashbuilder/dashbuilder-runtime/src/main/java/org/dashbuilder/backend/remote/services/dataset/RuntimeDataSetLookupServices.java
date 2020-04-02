package org.dashbuilder.backend.remote.services.dataset;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.dashbuilder.dataset.DataSet;
import org.dashbuilder.dataset.DataSetLookup;
import org.dashbuilder.dataset.DataSetManager;
import org.dashbuilder.dataset.DataSetMetadata;
import org.dashbuilder.dataset.def.DataSetDef;
import org.dashbuilder.dataset.service.DataSetLookupServices;
import org.jboss.errai.bus.server.annotations.Service;

@Service
@ApplicationScoped
public class RuntimeDataSetLookupServices implements DataSetLookupServices {

    @Inject
    DataSetManager manager;
    

    public RuntimeDataSetLookupServices() {
    }

    @Override
    public DataSet lookupDataSet(DataSetLookup lookup) throws Exception {
        return manager.lookupDataSet(lookup);
    }

    @Override
    public DataSet lookupDataSet(DataSetDef def, DataSetLookup lookup) throws Exception {
        return manager.lookupDataSet(lookup);
    }

    @Override
    public DataSetMetadata lookupDataSetMetadata(String uuid) throws Exception {
        return manager.getDataSetMetadata(uuid);
    }

}