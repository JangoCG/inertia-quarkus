package com.gurtus.inertia.runtime;

/**
 * Inertia.js HTTP headers constants.
 */
public final class InertiaHeaders {
    
    /**
     * Header to indicate an Inertia request.
     */
    public static final String X_INERTIA = "X-Inertia";
    
    /**
     * Header for redirects in Inertia.
     */
    public static final String X_INERTIA_LOCATION = "X-Inertia-Location";
    
    /**
     * Header for asset version checking.
     */
    public static final String X_INERTIA_VERSION = "X-Inertia-Version";
    
    /**
     * Header for partial component reloads.
     */
    public static final String X_INERTIA_PARTIAL_COMPONENT = "X-Inertia-Partial-Component";
    
    /**
     * Header for partial data specification.
     */
    public static final String X_INERTIA_PARTIAL_DATA = "X-Inertia-Partial-Data";
    
    private InertiaHeaders() {
        // Utility class
    }
} 