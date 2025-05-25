package com.gurtus.inertia.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.qute.Engine;
import io.quarkus.runtime.LaunchMode;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

/**
 * Main service for handling Inertia.js requests and responses.
 */
@ApplicationScoped
public class InertiaService {

    @Inject
    InertiaConfig config;

    @Inject
    ObjectMapper objectMapper;

    @Inject
    Engine quteEngine;

    @Inject
    InertiaContext context;

    private final Map<String, Object> sharedProps = new ConcurrentHashMap<>();
    private final Map<String, Object> sharedViewData = new ConcurrentHashMap<>();

    /**
     * Share a prop globally across all Inertia responses.
     */
    public void share(String key, Object value) {
        sharedProps.put(key, value);
    }

    /**
     * Share view data globally across all Inertia responses.
     */
    public void shareViewData(String key, Object value) {
        sharedViewData.put(key, value);
    }

    /**
     * Create an Inertia response builder for fluent API.
     * No need to pass context anymore!
     * Usage: return inertiaService.inertia("ComponentName").with("key", "value").build();
     */
    public InertiaResponseBuilder inertia(String component) {
        return new InertiaResponseBuilder(this, component);
    }

    /**
     * Build a JAX-RS Response for the given component and props.
     * Context is now automatically injected.
     */
    public Response buildResponse(String component, Map<String, Object> props) {
        // Merge props with shared props
        Map<String, Object> mergedProps = new HashMap<>(sharedProps);
        if (props != null) {
            mergedProps.putAll(props);
        }

        // Create the page object
        InertiaPage page = new InertiaPage(
            component,
            mergedProps,
            context.getRequestUri(),
            config.version().orElse("1")
        );

        // Check if this is an Inertia request
        if (context.isInertiaRequest()) {
            return createJsonResponse(page);
        } else {
            return createHtmlResponse(page);
        }
    }

    private Response createJsonResponse(InertiaPage page) {
        try {
            String json = objectMapper.writeValueAsString(page);
            return Response.ok(json)
                    .header("Content-Type", "application/json")
                    .header("Vary", "Accept")
                    .header(InertiaHeaders.X_INERTIA, "true")
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Error serializing Inertia page: " + e.getMessage())
                    .build();
        }
    }

    private Response createHtmlResponse(InertiaPage page) {
        try {
            String pageJson = objectMapper.writeValueAsString(page);
            
            // Load the configured template
            String templateName = config.rootTemplate();
            var template = quteEngine.getTemplate(templateName);
            
            if (template == null) {
                return Response.serverError()
                    .entity("Inertia template '" + templateName + "' not found. Please create the template file.")
                    .build();
            }
            
            // Check if we're in development mode
            boolean isDevelopment = LaunchMode.current() == LaunchMode.DEVELOPMENT;
            
            String html = template.data("page", page)
                                .data("pageJson", pageJson)
                                .data("isDevelopment", isDevelopment)
                                .render();
            
            return Response.ok(html)
                    .header("Content-Type", "text/html")
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Error rendering Inertia template: " + e.getMessage())
                    .build();
        }
    }
} 