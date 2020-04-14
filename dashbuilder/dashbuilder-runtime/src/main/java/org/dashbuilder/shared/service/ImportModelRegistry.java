package org.dashbuilder.shared.service;

import java.io.InputStream;
import java.util.Optional;

import org.dashbuilder.shared.model.DashbuilderRuntimeMode;
import org.dashbuilder.shared.model.ImportModel;

import static org.dashbuilder.shared.model.DashbuilderRuntimeMode.MULTIPLE_IMPORT;

/**
 * Provides access to a saved ImportModelService
 *
 */
public interface ImportModelRegistry {

    public default boolean acceptingNewImports() {
        return getMode() == MULTIPLE_IMPORT ||
               (getMode() == DashbuilderRuntimeMode.SINGLE_IMPORT && isEmpty());
    }

    /**
     * Returns if this registry has at least one model.
     * @return
     */
    public boolean isEmpty();

    /**
     * Returns the registry mode
     * 
     * @return
     */
    public DashbuilderRuntimeMode getMode();

    /**
     * Get a previously registered import model
     * @param id
     * @return
     */
    public Optional<ImportModel> get(String id);

    /**
     * Sets this runtime mode.
     * 
     * @param mode
     * The mode to be used.
     */
    public void setMode(DashbuilderRuntimeMode mode);

    /**
     * Store the import from a File path;
     * @param filePath
     * The path to the file
     */
    public Optional<ImportModel> registerFile(String filePath);

}
