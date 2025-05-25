package com.gurtus.inertia.runtime.props;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A prop that supports merging with previous values on the client side.
 * This is useful for props that represent collections or objects that should
 * be merged rather than replaced entirely.
 */
public class MergeProp implements BaseProp {
    
    private final Function<Object, Object> evaluator;
    private final boolean deepMerge;
    
    public MergeProp(Function<Object, Object> evaluator) {
        this(evaluator, false);
    }
    
    public MergeProp(Function<Object, Object> evaluator, boolean deepMerge) {
        this.evaluator = evaluator;
        this.deepMerge = deepMerge;
    }
    
    public MergeProp(Supplier<Object> supplier) {
        this(supplier, false);
    }
    
    public MergeProp(Supplier<Object> supplier, boolean deepMerge) {
        this.evaluator = context -> supplier.get();
        this.deepMerge = deepMerge;
    }
    
    public MergeProp(Object value) {
        this(value, false);
    }
    
    public MergeProp(Object value, boolean deepMerge) {
        this.evaluator = context -> value;
        this.deepMerge = deepMerge;
    }
    
    @Override
    public Object evaluate(Object context) {
        return evaluator.apply(context);
    }
    
    @Override
    public boolean isMergeable() {
        return true;
    }
    
    @Override
    public boolean isDeepMergeable() {
        return deepMerge;
    }
} 