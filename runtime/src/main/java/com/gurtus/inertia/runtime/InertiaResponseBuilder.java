package com.gurtus.inertia.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import com.gurtus.inertia.runtime.props.AlwaysProp;
import com.gurtus.inertia.runtime.props.DeferProp;
import com.gurtus.inertia.runtime.props.MergeProp;
import com.gurtus.inertia.runtime.props.OptionalProp;

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
    private Object controllerContext;
    
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
     * Set the controller context for prop evaluation.
     */
    public InertiaResponseBuilder withContext(Object controllerContext) {
        this.controllerContext = controllerContext;
        return this;
    }
    
    /**
     * Add an optional prop (only included during partial reloads).
     */
    public InertiaResponseBuilder withOptional(String key, Function<Object, Object> evaluator) {
        this.props.put(key, new OptionalProp(evaluator));
        return this;
    }
    
    public InertiaResponseBuilder withOptional(String key, Supplier<Object> supplier) {
        this.props.put(key, new OptionalProp(supplier));
        return this;
    }
    
    public InertiaResponseBuilder withOptional(String key, Object value) {
        this.props.put(key, new OptionalProp(value));
        return this;
    }
    
    /**
     * Add an always prop (always included).
     */
    public InertiaResponseBuilder withAlways(String key, Function<Object, Object> evaluator) {
        this.props.put(key, new AlwaysProp(evaluator));
        return this;
    }
    
    public InertiaResponseBuilder withAlways(String key, Supplier<Object> supplier) {
        this.props.put(key, new AlwaysProp(supplier));
        return this;
    }
    
    public InertiaResponseBuilder withAlways(String key, Object value) {
        this.props.put(key, new AlwaysProp(value));
        return this;
    }
    
    /**
     * Add a merge prop (supports client-side merging).
     */
    public InertiaResponseBuilder withMerge(String key, Function<Object, Object> evaluator) {
        this.props.put(key, new MergeProp(evaluator));
        return this;
    }
    
    public InertiaResponseBuilder withMerge(String key, Supplier<Object> supplier) {
        this.props.put(key, new MergeProp(supplier));
        return this;
    }
    
    public InertiaResponseBuilder withMerge(String key, Object value) {
        this.props.put(key, new MergeProp(value));
        return this;
    }
    
    /**
     * Add a deep merge prop (supports deep client-side merging).
     */
    public InertiaResponseBuilder withDeepMerge(String key, Function<Object, Object> evaluator) {
        this.props.put(key, new MergeProp(evaluator, true));
        return this;
    }
    
    public InertiaResponseBuilder withDeepMerge(String key, Supplier<Object> supplier) {
        this.props.put(key, new MergeProp(supplier, true));
        return this;
    }
    
    public InertiaResponseBuilder withDeepMerge(String key, Object value) {
        this.props.put(key, new MergeProp(value, true));
        return this;
    }
    
    /**
     * Add a deferred prop (loaded separately).
     */
    public InertiaResponseBuilder withDefer(String key, Function<Object, Object> evaluator) {
        this.props.put(key, new DeferProp(evaluator));
        return this;
    }
    
    public InertiaResponseBuilder withDefer(String key, Function<Object, Object> evaluator, String group) {
        this.props.put(key, new DeferProp(evaluator, group));
        return this;
    }
    
    public InertiaResponseBuilder withDefer(String key, Supplier<Object> supplier) {
        this.props.put(key, new DeferProp(supplier));
        return this;
    }
    
    public InertiaResponseBuilder withDefer(String key, Supplier<Object> supplier, String group) {
        this.props.put(key, new DeferProp(supplier, group));
        return this;
    }
    
    public InertiaResponseBuilder withDefer(String key, Object value) {
        this.props.put(key, new DeferProp(value));
        return this;
    }
    
    public InertiaResponseBuilder withDefer(String key, Object value, String group) {
        this.props.put(key, new DeferProp(value, group));
        return this;
    }
    
    /**
     * Build the JAX-RS Response without needing to pass context.
     */
    public Response build() {
        return inertiaService.buildResponse(component, props, controllerContext);
    }
} 