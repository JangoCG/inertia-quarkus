package com.gurtus.inertia.runtime;

import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Filter that handles Inertia.js middleware functionality.
 * This includes version checking and proper response handling.
 */
@ApplicationScoped
public class InertiaFilter {

    @Inject
    InertiaConfig config;

    /**
     * Handle the Inertia middleware logic.
     * This should be called before processing the actual request.
     */
    public boolean handle(RoutingContext context) {
        String inertiaHeader = context.request().getHeader(InertiaHeaders.X_INERTIA);
        
        // If this is not an Inertia request, continue normally
        if (!"true".equals(inertiaHeader)) {
            return true; // Continue processing
        }

        // For GET requests, check version mismatch
        if (context.request().method() == HttpMethod.GET) {
            String requestVersion = context.request().getHeader(InertiaHeaders.X_INERTIA_VERSION);
            String currentVersion = config.version().orElse("1");
            
            if (!currentVersion.equals(requestVersion)) {
                // Version mismatch - force full page reload
                context.response()
                    .putHeader(InertiaHeaders.X_INERTIA_LOCATION, config.url() + context.request().uri())
                    .setStatusCode(409)
                    .end();
                return false; // Stop processing
            }
        }

        return true; // Continue processing
    }
} 