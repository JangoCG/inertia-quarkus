package com.gurtus.inertia.runtime.props;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A prop that is always included, regardless of partial reload settings.
 * This is useful for props that should always be available on every request.
 */
public class AlwaysProp implements BaseProp {
    
    private final Function<Object, Object> evaluator;
    
    public AlwaysProp(Function<Object, Object> evaluator) {
        this.evaluator = evaluator;
    }
    
    public AlwaysProp(Supplier<Object> supplier) {
        this.evaluator = context -> supplier.get();
    }
    
    public AlwaysProp(Object value) {
        this.evaluator = context -> value;
    }
    
    @Override
    public Object evaluate(Object context) {
        return evaluator.apply(context);
    }
    
    @Override
    public boolean isAlways() {
        return true;
    }
    
    @Override
    public boolean includeOnFirstLoad() {
        return true;
    }
    
    @Override
    public boolean includeOnPartialReload() {
        return true;
    }
} 