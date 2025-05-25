package com.gurtus.inertia.runtime;

import java.util.HashMap;
import java.util.Map;

/**
 * Builder class for creating Inertia responses.
 */
public class InertiaResponse {
    
    private String component;
    private Map<String, Object> props = new HashMap<>();
    
    private InertiaResponse(String component) {
        this.component = component;
    }
    
    /**
     * Create a new Inertia response with the specified component.
     */
    public static InertiaResponse render(String component) {
        return new InertiaResponse(component);
    }
    
    /**
     * Add a prop to the response.
     */
    public InertiaResponse with(String key, Object value) {
        this.props.put(key, value);
        return this;
    }
    
    /**
     * Add multiple props to the response.
     */
    public InertiaResponse with(Map<String, Object> props) {
        this.props.putAll(props);
        return this;
    }
    
    /**
     * Get the component name.
     */
    public String getComponent() {
        return component;
    }
    
    /**
     * Get the props.
     */
    public Map<String, Object> getProps() {
        return props;
    }
} 