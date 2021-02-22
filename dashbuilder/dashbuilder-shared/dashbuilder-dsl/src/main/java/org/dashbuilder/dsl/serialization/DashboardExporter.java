package org.dashbuilder.dsl.serialization;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.dashbuilder.dsl.model.Dashboard;
import org.dashbuilder.dsl.serialization.impl.DashboardZipSerializer;

public class DashboardExporter {

    public enum ExportType {
        ZIP
    }

    public static void export(Dashboard dashboard, String path, ExportType type) {
        DashboardZipSerializer zip = new DashboardZipSerializer();
        // TODO: validate dashboard
        try (FileOutputStream fos = new FileOutputStream(path)) {
            zip.serialize(dashboard, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found: " + path);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file " + path);
        }

    }

}