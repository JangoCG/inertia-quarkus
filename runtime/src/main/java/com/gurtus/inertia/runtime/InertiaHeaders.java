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
    
    /**
     * Header for partial data exclusion.
     */
    public static final String X_INERTIA_PARTIAL_EXCEPT = "X-Inertia-Partial-Except";
    
    /**
     * Header for resetting specific props.
     */
    public static final String X_INERTIA_RESET = "X-Inertia-Reset";
    
    /**
     * Standard XMLHttpRequest header.
     */
    public static final String X_REQUESTED_WITH = "X-Requested-With";
    
    /**
     * CSRF token header.
     */
    public static final String X_CSRF_TOKEN = "X-CSRF-Token";
    
    /**
     * XSRF token header.
     */
    public static final String X_XSRF_TOKEN = "X-XSRF-Token";
    
    private InertiaHeaders() {
        // Utility class
    }
} 