package org.dashbuilder.shared.model;

/**
 * Execution for dashbuilder Runtime
 *
 */
public enum DashbuilderRuntimeMode {

    /**
     * Used when only the first import is used
     */
    STATIC,

    /**
     * Allow users to import once
     */
    SINGLE_IMPORT,

    /**
     * Users can import how many dashboards they want and reopen them later.
     */
    MULTIPLE_IMPORT;

}
