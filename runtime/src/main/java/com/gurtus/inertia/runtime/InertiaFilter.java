package com.gurtus.inertia.runtime;

import java.util.Arrays;
import java.util.List;

import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Enhanced Inertia filter that handles advanced middleware functionality.
 * Based on the Rails InertiaRails::Middleware implementation.
 */
@ApplicationScoped
public class InertiaFilter {

    @Inject
    InertiaConfig config;

    @Inject
    InertiaSession inertiaSession;

    private static final List<Integer> REDIRECT_STATUS_CODES = Arrays.asList(301, 302);
    private static final List<String> NON_GET_REDIRECTABLE_METHODS = Arrays.asList("PUT", "PATCH", "DELETE");

    /**
     * Handle the enhanced Inertia middleware logic.
     * This should be called before processing the actual request.
     */
    public boolean handle(RoutingContext context) {
        // Copy XSRF token to CSRF token for Inertia requests
        copyXsrfToCsrf(context);
        
        // If this is not an Inertia request, continue normally
        if (!isInertiaRequest(context)) {
            return true; // Continue processing
        }

        // Check for stale version on GET requests
        if (isGetRequest(context) && isVersionStale(context)) {
            return forceRefresh(context);
        }

        return true; // Continue processing
    }

    /**
     * Handle response processing after the request has been processed.
     * This should be called after the response is ready.
     */
    public void handleResponse(RoutingContext context, int statusCode) {
        // Clean up session data unless we should keep it
        if (!shouldKeepInertiaSessionOptions(statusCode, context)) {
            cleanupInertiaSession(context);
        }

        // Convert redirects for non-GET methods to 303 See Other
        if (shouldConvertToSeeOther(context, statusCode)) {
            context.response().setStatusCode(303);
        }
    }

    private void copyXsrfToCsrf(RoutingContext context) {
        String xsrfToken = context.request().getHeader(InertiaHeaders.X_XSRF_TOKEN);
        if (xsrfToken != null && isInertiaRequest(context)) {
            // Set CSRF token header for framework compatibility
            context.request().headers().set(InertiaHeaders.X_CSRF_TOKEN, xsrfToken);
        }
    }

    private boolean isInertiaRequest(RoutingContext context) {
        return "true".equals(context.request().getHeader(InertiaHeaders.X_INERTIA));
    }

    private boolean isGetRequest(RoutingContext context) {
        return context.request().method() == HttpMethod.GET;
    }

    private boolean isVersionStale(RoutingContext context) {
        String clientVersion = context.request().getHeader(InertiaHeaders.X_INERTIA_VERSION);
        String serverVersion = config.version().orElse("1");
        
        return !coerceVersion(clientVersion, serverVersion).equals(coerceVersion(serverVersion, serverVersion));
    }

    /**
     * Coerce version to proper type for comparison.
     * If server version is numeric, convert client version to numeric too.
     */
    private String coerceVersion(String version, String serverVersion) {
        if (version == null) {
            return "null";
        }
        
        // Try to determine if server version is numeric
        try {
            Double.parseDouble(serverVersion);
            // Server version is numeric, try to convert client version
            try {
                return String.valueOf(Double.parseDouble(version));
            } catch (NumberFormatException e) {
                return version; // Keep as string if conversion fails
            }
        } catch (NumberFormatException e) {
            // Server version is not numeric, keep as string
            return version;
        }
    }

    private boolean forceRefresh(RoutingContext context) {
        // Preserve flash messages (if any session mechanism is available)
        preserveFlashMessages(context);
        
        // Send 409 Conflict with location header
        String location = getFullUrl(context);
        context.response()
            .putHeader(InertiaHeaders.X_INERTIA_LOCATION, location)
            .setStatusCode(409)
            .end();
        
        return false; // Stop processing
    }

    private void preserveFlashMessages(RoutingContext context) {
        // In a real implementation, this would preserve flash messages
        // For now, we'll just log that we should preserve them
        Session session = context.session();
        if (session != null) {
            // Keep flash messages by not removing them
            // This is framework-specific implementation
        }
    }

    private String getFullUrl(RoutingContext context) {
        String scheme = context.request().isSSL() ? "https" : "http";
        String host = context.request().getHeader("Host");
        String uri = context.request().uri();
        
        if (host == null) {
            host = "localhost:8080"; // Fallback
        }
        
        return scheme + "://" + host + uri;
    }

    private boolean shouldKeepInertiaSessionOptions(int statusCode, RoutingContext context) {
        return isRedirectStatus(statusCode) || isStaleInertiaRequest(context);
    }

    private boolean isRedirectStatus(int statusCode) {
        return REDIRECT_STATUS_CODES.contains(statusCode);
    }

    private boolean isStaleInertiaRequest(RoutingContext context) {
        return isInertiaRequest(context) && isVersionStale(context);
    }

    private void cleanupInertiaSession(RoutingContext context) {
        Session session = context.session();
        inertiaSession.cleanup(session);
    }

    private boolean shouldConvertToSeeOther(RoutingContext context, int statusCode) {
        return isInertiaRequest(context) && 
               isRedirectStatus(statusCode) && 
               isNonGetRedirectableMethod(context);
    }

    private boolean isNonGetRedirectableMethod(RoutingContext context) {
        String method = context.request().method().name();
        return NON_GET_REDIRECTABLE_METHODS.contains(method);
    }
} 