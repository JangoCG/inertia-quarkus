package com.gurtus.inertia.runtime.props;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Abstract base class for props that should be ignored on the first page load
 * but included during partial reloads.
 */
public abstract class IgnoreOnFirstLoadProp implements BaseProp {
    
    protected final Function<Object, Object> evaluator;
    
    protected IgnoreOnFirstLoadProp(Function<Object, Object> evaluator) {
        this.evaluator = evaluator;
    }
    
    protected IgnoreOnFirstLoadProp(Supplier<Object> supplier) {
        this.evaluator = context -> supplier.get();
    }
    
    protected IgnoreOnFirstLoadProp(Object value) {
        this.evaluator = context -> value;
    }
    
    @Override
    public Object evaluate(Object context) {
        return evaluator.apply(context);
    }
    
    @Override
    public boolean includeOnFirstLoad() {
        return false;
    }
    
    @Override
    public boolean includeOnPartialReload() {
        return true;
    }
} 