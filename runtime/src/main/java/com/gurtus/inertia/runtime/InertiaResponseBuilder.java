package com.gurtus.inertia.runtime;

import java.util.HashMap;
import java.util.Map;

import jakarta.ws.rs.core.Response;

/**
 * Builder class for creating Inertia responses with a fluent API.
 * No need to pass HttpHeaders and UriInfo anymore!
 * 
 * Usage:
 * return inertiaService.inertia("ComponentName")
 *     .with("key", "value")
 *     .with("user", userObject)
 *     .build();
 */
public class InertiaResponseBuilder {
    
    private final InertiaService inertiaService;
    private final String component;
    private final Map<String, Object> props = new HashMap<>();
    
    public InertiaResponseBuilder(InertiaService inertiaService, String component) {
        this.inertiaService = inertiaService;
        this.component = component;
    }
    
    /**
     * Add a single prop to the response.
     */
    public InertiaResponseBuilder with(String key, Object value) {
        this.props.put(key, value);
        return this;
    }
    
    /**
     * Add multiple props to the response.
     */
    public InertiaResponseBuilder with(Map<String, Object> props) {
        this.props.putAll(props);
        return this;
    }
    
    /**
     * Add props using method chaining.
     */
    public InertiaResponseBuilder props(Map<String, Object> props) {
        return with(props);
    }
    
    /**
     * Build the JAX-RS Response without needing to pass context.
     */
    public Response build() {
        return inertiaService.buildResponse(component, props);
    }
} 