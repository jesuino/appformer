package org.dashbuilder.backend.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Predicate;

import org.dashbuilder.backend.services.impl.ImportModelParserImpl;
import org.dashbuilder.shared.model.DataSetContent;
import org.dashbuilder.shared.model.ImportModel;
import org.dashbuilder.shared.model.PerspectiveContent;
import org.junit.Test;

import static org.dashbuilder.shared.model.DataSetContentType.CSV;
import static org.dashbuilder.shared.model.DataSetContentType.DEFINITION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DefaultImportModelParserTest {

    @Test
    public void testGetEntriesMapEmpty() throws IOException {
        ImportModelParserImpl parser = new ImportModelParserImpl();
        InputStream emptyImport = this.getClass().getResourceAsStream("/empty.zip");
        ImportModel importModel = parser.retrieveImportModel(emptyImport);
        assertTrue(importModel.getDatasets().isEmpty());
        assertTrue(importModel.getPerspectives().isEmpty());
        assertFalse(importModel.getNavigationJSON().isPresent());
    }

    @Test
    public void testGetEntriesMap() throws IOException {
        ImportModelParserImpl parser = new ImportModelParserImpl();
        InputStream validImport = this.getClass().getResourceAsStream("/import.zip");
        ImportModel importModel = parser.retrieveImportModel(validImport);

        List<DataSetContent> datasets = importModel.getDatasets();
        assertEquals(2, datasets.size());
        assertEquals("ds1", datasets.get(0).getId());
        assertEquals("ds1", datasets.get(1).getId());
        Predicate<DataSetContent> csvMatcher = ds -> ds.getContentType().equals(CSV);
        Predicate<DataSetContent> defMatcher = ds -> ds.getContentType().equals(DEFINITION);
        assertTrue(datasets.stream().anyMatch(csvMatcher));
        assertTrue(datasets.stream().anyMatch(defMatcher));
        String ds1Content = datasets.stream().filter(defMatcher).findAny().get().getContent();
        String csv1Content = datasets.stream().filter(csvMatcher).findAny().get().getContent();
        assertEquals("ds1content", ds1Content);
        assertEquals("csv1content", csv1Content);
        
        List<PerspectiveContent> perspectives = importModel.getPerspectives();
        assertEquals(1, perspectives.size());
        
        PerspectiveContent perspectiveContent = perspectives.get(0);
        assertEquals("page1", perspectiveContent.getId());
        assertEquals("page1_content", perspectiveContent.getContent());
        
        assertEquals("navigation", importModel.getNavigationJSON().get());
        
    }

}
