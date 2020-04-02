package org.dashbuilder.backend.services.dataset;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.dashbuilder.dataprovider.csv.CSVFileStorage;
import org.dashbuilder.dataset.def.CSVDataSetDef;

@ApplicationScoped
public class RuntimeCSVFileStorage implements CSVFileStorage {
    
    Map<String, String> csvStorage;
    
    
    public RuntimeCSVFileStorage() {
    }


    @PostConstruct
    public void init() {
        csvStorage = new HashMap<>();
    }

    @Override
    public InputStream getCSVInputStream(CSVDataSetDef def) {
        String csvStr = getCSVString(def);
        return new ByteArrayInputStream(csvStr.getBytes());
    }

    @Override
    public String getCSVString(CSVDataSetDef def) {
        return csvStorage.get(def.getUUID());
    }
    
    public void storeCSV(String uuid, String csvContent) {
        csvStorage.put(uuid, csvContent);
    }

    @Override
    public void saveCSVFile(CSVDataSetDef def) {
        // not going to save

    }

    @Override
    public void deleteCSVFile(CSVDataSetDef def) {
        // Ignored
    }

}
