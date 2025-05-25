package com.gurtus.inertia.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

/**
 * Base controller class for Inertia.js integration.
 * Provides convenient methods for Inertia responses and shared data management.
 */
public abstract class InertiaController {

    @Inject
    protected InertiaService inertiaService;

    @Inject
    protected InertiaSession inertiaSession;

    /**
     * Render an Inertia component.
     */
    protected Response inertia(String component) {
        return inertiaService.buildResponse(component, new HashMap<>(), this, getCurrentAction());
    }

    /**
     * Render an Inertia component with props.
     */
    protected Response inertia(String component, Map<String, Object> props) {
        return inertiaService.buildResponse(component, props, this, getCurrentAction());
    }

    /**
     * Create an Inertia response builder for fluent API.
     */
    protected InertiaResponseBuilder inertia(String component, String key, Object value) {
        return inertiaService.inertia(component).with(key, value);
    }

    /**
     * Share data globally.
     */
    protected void share(String key, Object value) {
        inertiaService.share(key, value);
    }

    /**
     * Share data with action filters.
     */
    protected void shareData(Map<String, Object> data, String... onlyActions) {
        inertiaService.shareData(data, onlyActions);
    }

    /**
     * Share conditional data.
     */
    protected void shareDataIf(Map<String, Object> data, Supplier<Boolean> condition) {
        inertiaService.shareDataIf(data, condition);
    }

    /**
     * Share dynamic data.
     */
    protected void shareData(Supplier<Map<String, Object>> dataSupplier) {
        inertiaService.shareData(dataSupplier);
    }

    /**
     * Share contextual data.
     */
    protected void shareData(Function<Object, Map<String, Object>> dataFunction) {
        inertiaService.shareData(dataFunction);
    }

    /**
     * Share data only for specific actions.
     */
    protected void shareOnly(Map<String, Object> data, String... actions) {
        InertiaSharedData sharedData = InertiaSharedData.builder()
                .withStaticData(data)
                .only(actions)
                .build();
        inertiaService.shareData(sharedData);
    }

    /**
     * Share data except for specific actions.
     */
    protected void shareExcept(Map<String, Object> data, String... actions) {
        InertiaSharedData sharedData = InertiaSharedData.builder()
                .withStaticData(data)
                .except(actions)
                .build();
        inertiaService.shareData(sharedData);
    }

    /**
     * Get the current action name.
     * Override this method to provide the actual action name.
     */
    protected String getCurrentAction() {
        // Default implementation - should be overridden by subclasses
        // or determined through reflection/annotations
        return "index";
    }

    /**
     * Helper method to create a map with a single key-value pair.
     */
    protected Map<String, Object> props(String key, Object value) {
        Map<String, Object> props = new HashMap<>();
        props.put(key, value);
        return props;
    }

    /**
     * Helper method to create a map with multiple key-value pairs.
     */
    protected Map<String, Object> props(Object... keyValuePairs) {
        if (keyValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException("Key-value pairs must be even number of arguments");
        }
        
        Map<String, Object> props = new HashMap<>();
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            String key = keyValuePairs[i].toString();
            Object value = keyValuePairs[i + 1];
            props.put(key, value);
        }
        return props;
    }

    /**
     * Redirect with Inertia session data.
     */
    protected Response redirectWithErrors(String location, Map<String, Object> errors) {
        // TODO: Implement redirect with session errors
        // This would require integration with the web framework's redirect mechanism
        return Response.status(Response.Status.SEE_OTHER)
                .header("Location", location)
                .build();
    }

    /**
     * Redirect with clear history flag.
     */
    protected Response redirectWithClearHistory(String location) {
        // TODO: Implement redirect with clear history
        return Response.status(Response.Status.SEE_OTHER)
                .header("Location", location)
                .build();
    }
} 