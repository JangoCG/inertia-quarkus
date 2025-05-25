package com.gurtus.inertia.runtime;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

/**
 * Main service for handling Inertia.js requests and responses.
 */
@ApplicationScoped
public class InertiaService {

    @Inject
    InertiaRenderer renderer;

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
        return buildResponse(component, props, null);
    }
    
    /**
     * Build a JAX-RS Response for the given component and props with controller context.
     */
    public Response buildResponse(String component, Map<String, Object> props, Object controllerContext) {
        return renderer.render(component, props, sharedProps, controllerContext);
    }


} 