package com.gurtus.inertia.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents shared data for Inertia with optional filters.
 * Based on Rails InertiaRails shared data functionality.
 */
public class InertiaSharedData {
    
    private final Map<String, Object> staticData;
    private final Supplier<Map<String, Object>> dynamicDataSupplier;
    private final Function<Object, Map<String, Object>> contextualDataFunction;
    private final InertiaActionFilter actionFilter;
    private final Supplier<Boolean> conditionalSupplier;
    
    // Static data constructor
    public InertiaSharedData(Map<String, Object> data) {
        this.staticData = new HashMap<>(data);
        this.dynamicDataSupplier = null;
        this.contextualDataFunction = null;
        this.actionFilter = null;
        this.conditionalSupplier = null;
    }
    
    // Dynamic data constructor
    public InertiaSharedData(Supplier<Map<String, Object>> dataSupplier) {
        this.staticData = null;
        this.dynamicDataSupplier = dataSupplier;
        this.contextualDataFunction = null;
        this.actionFilter = null;
        this.conditionalSupplier = null;
    }
    
    // Contextual data constructor (with controller context)
    public InertiaSharedData(Function<Object, Map<String, Object>> dataFunction) {
        this.staticData = null;
        this.dynamicDataSupplier = null;
        this.contextualDataFunction = dataFunction;
        this.actionFilter = null;
        this.conditionalSupplier = null;
    }
    
    // Constructor with action filter
    public InertiaSharedData(Map<String, Object> data, InertiaActionFilter filter) {
        this.staticData = data != null ? new HashMap<>(data) : null;
        this.dynamicDataSupplier = null;
        this.contextualDataFunction = null;
        this.actionFilter = filter;
        this.conditionalSupplier = null;
    }
    
    // Constructor with conditional supplier
    public InertiaSharedData(Map<String, Object> data, Supplier<Boolean> conditional) {
        this.staticData = data != null ? new HashMap<>(data) : null;
        this.dynamicDataSupplier = null;
        this.contextualDataFunction = null;
        this.actionFilter = null;
        this.conditionalSupplier = conditional;
    }
    
    // Full constructor
    public InertiaSharedData(Map<String, Object> staticData, 
                           Supplier<Map<String, Object>> dynamicDataSupplier,
                           Function<Object, Map<String, Object>> contextualDataFunction,
                           InertiaActionFilter actionFilter,
                           Supplier<Boolean> conditionalSupplier) {
        this.staticData = staticData != null ? new HashMap<>(staticData) : null;
        this.dynamicDataSupplier = dynamicDataSupplier;
        this.contextualDataFunction = contextualDataFunction;
        this.actionFilter = actionFilter;
        this.conditionalSupplier = conditionalSupplier;
    }
    
    /**
     * Evaluate the shared data for the given context and action.
     */
    public Map<String, Object> evaluate(Object controllerContext, String currentAction) {
        // Check action filter
        if (actionFilter != null && !actionFilter.match(currentAction)) {
            return new HashMap<>();
        }
        
        // Check conditional
        if (conditionalSupplier != null && !conditionalSupplier.get()) {
            return new HashMap<>();
        }
        
        // Return appropriate data
        if (staticData != null) {
            return new HashMap<>(staticData);
        } else if (dynamicDataSupplier != null) {
            Map<String, Object> result = dynamicDataSupplier.get();
            return result != null ? result : new HashMap<>();
        } else if (contextualDataFunction != null) {
            Map<String, Object> result = contextualDataFunction.apply(controllerContext);
            return result != null ? result : new HashMap<>();
        }
        
        return new HashMap<>();
    }
    
    /**
     * Check if this shared data should be included for the given action.
     */
    public boolean shouldInclude(String currentAction) {
        if (actionFilter != null && !actionFilter.match(currentAction)) {
            return false;
        }
        
        if (conditionalSupplier != null && !conditionalSupplier.get()) {
            return false;
        }
        
        return true;
    }
    
    // Builder pattern for easier construction
    public static class Builder {
        private Map<String, Object> staticData;
        private Supplier<Map<String, Object>> dynamicDataSupplier;
        private Function<Object, Map<String, Object>> contextualDataFunction;
        private InertiaActionFilter actionFilter;
        private Supplier<Boolean> conditionalSupplier;
        
        public Builder withStaticData(Map<String, Object> data) {
            this.staticData = data;
            return this;
        }
        
        public Builder withDynamicData(Supplier<Map<String, Object>> supplier) {
            this.dynamicDataSupplier = supplier;
            return this;
        }
        
        public Builder withContextualData(Function<Object, Map<String, Object>> function) {
            this.contextualDataFunction = function;
            return this;
        }
        
        public Builder withActionFilter(InertiaActionFilter filter) {
            this.actionFilter = filter;
            return this;
        }
        
        public Builder withConditional(Supplier<Boolean> conditional) {
            this.conditionalSupplier = conditional;
            return this;
        }
        
        public Builder only(String... actions) {
            this.actionFilter = new InertiaActionFilter(InertiaActionFilter.ConditionalKey.ONLY, actions);
            return this;
        }
        
        public Builder except(String... actions) {
            this.actionFilter = new InertiaActionFilter(InertiaActionFilter.ConditionalKey.EXCEPT, actions);
            return this;
        }
        
        public InertiaSharedData build() {
            return new InertiaSharedData(staticData, dynamicDataSupplier, contextualDataFunction, actionFilter, conditionalSupplier);
        }
    }
    
    public static Builder builder() {
        return new Builder();
    }
} 