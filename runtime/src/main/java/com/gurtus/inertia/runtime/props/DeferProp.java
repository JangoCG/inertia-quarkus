package com.gurtus.inertia.runtime.props;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A prop that is deferred and loaded separately from the initial page load.
 * Deferred props can be grouped together and loaded in batches.
 */
public class DeferProp extends IgnoreOnFirstLoadProp {
    
    private final String group;
    private final boolean merge;
    private final boolean deepMerge;
    
    public DeferProp(Function<Object, Object> evaluator) {
        this(evaluator, "default", false, false);
    }
    
    public DeferProp(Function<Object, Object> evaluator, String group) {
        this(evaluator, group, false, false);
    }
    
    public DeferProp(Function<Object, Object> evaluator, String group, boolean merge, boolean deepMerge) {
        super(evaluator);
        
        if (deepMerge && merge) {
            throw new IllegalArgumentException("Cannot set both 'deepMerge' and 'merge' to true");
        }
        
        this.group = group != null ? group : "default";
        this.merge = merge || deepMerge;
        this.deepMerge = deepMerge;
    }
    
    public DeferProp(Supplier<Object> supplier) {
        this(context -> supplier.get());
    }
    
    public DeferProp(Supplier<Object> supplier, String group) {
        this(context -> supplier.get(), group);
    }
    
    public DeferProp(Supplier<Object> supplier, String group, boolean merge, boolean deepMerge) {
        this(context -> supplier.get(), group, merge, deepMerge);
    }
    
    public DeferProp(Object value) {
        this(context -> value);
    }
    
    public DeferProp(Object value, String group) {
        this(context -> value, group);
    }
    
    public DeferProp(Object value, String group, boolean merge, boolean deepMerge) {
        this(context -> value, group, merge, deepMerge);
    }
    
    @Override
    public boolean isDeferred() {
        return true;
    }
    
    @Override
    public String getDeferGroup() {
        return group;
    }
    
    @Override
    public boolean isMergeable() {
        return merge;
    }
    
    @Override
    public boolean isDeepMergeable() {
        return deepMerge;
    }
} 