package org.dashbuilder.dsl.serialization.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.dashbuilder.dataset.def.DataSetDef;
import org.dashbuilder.dataset.json.DataSetDefJSONMarshaller;
import org.dashbuilder.dsl.model.Dashboard;
import org.dashbuilder.dsl.model.Navigation;
import org.dashbuilder.dsl.model.Page;
import org.dashbuilder.dsl.serialization.DashboardModel;
import org.dashbuilder.dsl.serialization.DashboardSerializer;
import org.dashbuilder.external.model.ExternalComponent;
import org.dashbuilder.external.service.ComponentLoader;
import org.dashbuilder.navigation.json.NavTreeJSONMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uberfire.ext.layout.editor.api.editor.LayoutComponent;
import org.uberfire.ext.layout.editor.api.editor.LayoutRow;
import org.uberfire.ext.layout.editor.api.editor.LayoutTemplate;

public class DashboardZipSerializer implements DashboardSerializer {

    Logger logger = LoggerFactory.getLogger(DashboardZipSerializer.class);

    private static String BASE_PATH = "dashbuilder/";

    private static final String LAYOUTS_PATH = BASE_PATH + "perspectives/";
    private static final String DATA_SETS_BASE = BASE_PATH + "datasets/";
    private static final String COMPONENTS_BASE = BASE_PATH + "components/";
    private static final String DATA_SETS_PATH = DATA_SETS_BASE + "definitions/";
    private static final String NAVIGATION_PATH = BASE_PATH + "navigation/navigation/navtree.json";

    private static final DataSetDefJSONMarshaller DATA_SET_MARSHALLER = new DataSetDefJSONMarshaller(null);

    private static final Gson gson = new GsonBuilder().create();

    @Override
    public Dashboard deserialize(DashboardModel model) {
        return null;
    }

    @Override
    public DashboardModel serialize(Dashboard dashboard, OutputStream os) {
        ZipOutputStream zos = new ZipOutputStream(os);

        dashboard.getDataSets()
                 .stream()
                 .forEach(def -> writeDataSetDef(zos, def));

        dashboard.getPages()
                 .stream()
                 .forEach(page -> writePage(zos, page));

        writeNavigation(zos, dashboard.getNavigation());

        writeContent(zos, DATA_SETS_BASE + "readme.md", "");

        if (dashboard.getComponentsPath().isPresent()) {
            Path componentsPath = dashboard.getComponentsPath().get();
            List<ExternalComponent> components = listComponents(componentsPath);
            List<String> usedComponents = findComponents(dashboard.getPages());
            usedComponents.stream()
                          .filter(c -> {
                              Optional<ExternalComponent> compOp = components.stream()
                                                                             .filter(comp -> comp.getId().equals(c))
                                                                             .findFirst();
                              if (!compOp.isPresent()) {
                                  logger.info("Not able to find component {}", c);
                              }
                              return compOp.isPresent();
                          })
                          .forEach(c -> writeComponent(componentsPath, c, zos));
        }

        try {
            zos.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing ZIP", e);
        }
        return DashboardModel.of(zos);
    }

    private void writeComponent(Path componentsPath, String componentId, ZipOutputStream zos) {
        final Path componentPath = componentsPath.resolve(componentId);
        final String componentZipPathBase = COMPONENTS_BASE + componentId + "/";
        try (Stream<Path> walker = Files.walk(componentPath, 1)) {
            walker.filter(p -> !p.toFile().isDirectory())
                  .forEach(file -> {
                      String fileName = componentZipPathBase + file.toFile().getName();
                      writeContent(zos, fileName, file);
                  });
        } catch (IOException e) {
            logger.debug("Error loading external component files.", e);
            throw new RuntimeException("Error loading components from " + componentsPath + ". Error: " + e.getMessage());
        }
    }

    private void writeNavigation(ZipOutputStream zos, Navigation navigation) {
        String content = NavTreeJSONMarshaller.get().toJson(navigation.getNavTree()).toString();
        writeContent(zos, NAVIGATION_PATH, content);
    }

    private void writePage(ZipOutputStream zos, Page page) {
        LayoutTemplate lt = page.getLayoutTemplate();
        String path = LAYOUTS_PATH + lt.getName() + "/perspective_layout";
        String pluginPath = path + ".plugin";
        String content = gson.toJson(lt);
        writeContent(zos, path, content);
        writeContent(zos, pluginPath, new Date().toString());
    }

    private void writeDataSetDef(ZipOutputStream zos, DataSetDef def) {
        String path = DATA_SETS_PATH + def.getUUID() + ".dset";
        String content = DATA_SET_MARSHALLER.toJsonString(def);
        writeContent(zos, path, content);
    }

    private void writeContent(ZipOutputStream zos, String path, byte[] content) {
        try {
            zos.putNextEntry(new ZipEntry(path));
            zos.write(content);
            zos.closeEntry();
        } catch (IOException e) {
            logger.warn("Error writing content on path {}", path);
            logger.debug("Error writing content. ", e);
        }
    }

    private void writeContent(ZipOutputStream zos, String path, String content) {
        this.writeContent(zos, path, content.getBytes());
    }

    private void writeContent(ZipOutputStream zos, String path, Path file) {
        try {
            this.writeContent(zos, path, Files.readAllBytes(file));
        } catch (IOException e) {
            logger.info("Error reading component file {}: {}", file, e.getMessage());
            logger.debug("Error reading component file", e);
        }
    }

    private List<ExternalComponent> listComponents(Path componentsPath) {
        try (Stream<Path> walker = Files.walk(componentsPath, 1)) {
            return walker.filter(p -> p.toFile().isDirectory())
                         .map(p -> Paths.get(p.toString(), ComponentLoader.DESCRIPTOR_FILE))
                         .filter(f -> Files.exists(f))
                         .map(f -> readComponent(f))
                         .filter(Objects::nonNull)
                         .collect(Collectors.toList());

        } catch (IOException e) {
            logger.debug("Error loading external components.", e);
            throw new RuntimeException("Error loading components from " + componentsPath + ". Error: " + e.getMessage());
        }
    }

    private ExternalComponent readComponent(Path file) {
        String id = file.getParent().toFile().getName();
        try {
            ExternalComponent component = gson.fromJson(new FileReader(file.toFile()),
                                                        ExternalComponent.class);
            component.setId(id);
            return component;
        } catch (FileNotFoundException e) {
            logger.error("Not able to read component manifest file {}. Error: {}", file, e.getMessage());
            logger.debug("Error reading component file.", e);
            return null;
        }
    }

    public List<String> findComponents(List<Page> pages) {
        return pages.stream()
                    .map(Page::getLayoutTemplate)
                    .map(LayoutTemplate::getRows)
                    .flatMap(this::allComponentsStream)
                    .map(lt -> lt.getProperties().get(ExternalComponent.COMPONENT_ID_KEY))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
    }

    private Stream<LayoutComponent> allComponentsStream(List<LayoutRow> row) {
        return row.stream()
                  .flatMap(r -> r.getLayoutColumns().stream())
                  .flatMap(cl -> Stream.concat(cl.getLayoutComponents().stream(),
                                               allComponentsStream(cl.getRows())));
    }

}
