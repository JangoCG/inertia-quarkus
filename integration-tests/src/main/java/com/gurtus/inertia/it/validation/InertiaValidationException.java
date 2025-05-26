package com.gurtus.inertia.it.validation;

import java.util.Map;

public class InertiaValidationException extends RuntimeException {
    private final String component;
    private final Map<String, Object> props;
    private final Map<String, String> errors;
    private final Map<String, Object> old;
    
    public InertiaValidationException(String component, Map<String, Object> props, 
                                    Map<String, String> errors, Map<String, Object> old) {
        super("Validation failed for component: " + component);
        this.component = component;
        this.props = props;
        this.errors = errors;
        this.old = old;
    }
    
    public String getComponent() { 
        return component; 
    }
    
    public Map<String, Object> getProps() { 
        return props; 
    }
    
    public Map<String, String> getErrors() { 
        return errors; 
    }
    
    public Map<String, Object> getOld() { 
        return old; 
    }
} 