package com.gurtus.inertia.runtime;

import java.util.function.Function;
import java.util.function.Supplier;

import com.gurtus.inertia.runtime.props.AlwaysProp;
import com.gurtus.inertia.runtime.props.DeferProp;
import com.gurtus.inertia.runtime.props.MergeProp;
import com.gurtus.inertia.runtime.props.OptionalProp;

/**
 * Helper class for creating different types of Inertia props.
 * Provides a fluent API similar to the Rails Inertia adapter.
 */
public class InertiaProps {
    
    /**
     * Create an optional prop that is only included during partial reloads.
     * 
     * @param evaluator Function to evaluate the prop value
     * @return OptionalProp instance
     */
    public static OptionalProp optional(Function<Object, Object> evaluator) {
        return new OptionalProp(evaluator);
    }
    
    /**
     * Create an optional prop with a supplier.
     * 
     * @param supplier Supplier for the prop value
     * @return OptionalProp instance
     */
    public static OptionalProp optional(Supplier<Object> supplier) {
        return new OptionalProp(supplier);
    }
    
    /**
     * Create an optional prop with a static value.
     * 
     * @param value Static value for the prop
     * @return OptionalProp instance
     */
    public static OptionalProp optional(Object value) {
        return new OptionalProp(value);
    }
    
    /**
     * Create an always prop that is included in every request.
     * 
     * @param evaluator Function to evaluate the prop value
     * @return AlwaysProp instance
     */
    public static AlwaysProp always(Function<Object, Object> evaluator) {
        return new AlwaysProp(evaluator);
    }
    
    /**
     * Create an always prop with a supplier.
     * 
     * @param supplier Supplier for the prop value
     * @return AlwaysProp instance
     */
    public static AlwaysProp always(Supplier<Object> supplier) {
        return new AlwaysProp(supplier);
    }
    
    /**
     * Create an always prop with a static value.
     * 
     * @param value Static value for the prop
     * @return AlwaysProp instance
     */
    public static AlwaysProp always(Object value) {
        return new AlwaysProp(value);
    }
    
    /**
     * Create a merge prop that supports client-side merging.
     * 
     * @param evaluator Function to evaluate the prop value
     * @return MergeProp instance
     */
    public static MergeProp merge(Function<Object, Object> evaluator) {
        return new MergeProp(evaluator);
    }
    
    /**
     * Create a merge prop with a supplier.
     * 
     * @param supplier Supplier for the prop value
     * @return MergeProp instance
     */
    public static MergeProp merge(Supplier<Object> supplier) {
        return new MergeProp(supplier);
    }
    
    /**
     * Create a merge prop with a static value.
     * 
     * @param value Static value for the prop
     * @return MergeProp instance
     */
    public static MergeProp merge(Object value) {
        return new MergeProp(value);
    }
    
    /**
     * Create a deep merge prop that supports deep client-side merging.
     * 
     * @param evaluator Function to evaluate the prop value
     * @return MergeProp instance with deep merge enabled
     */
    public static MergeProp deepMerge(Function<Object, Object> evaluator) {
        return new MergeProp(evaluator, true);
    }
    
    /**
     * Create a deep merge prop with a supplier.
     * 
     * @param supplier Supplier for the prop value
     * @return MergeProp instance with deep merge enabled
     */
    public static MergeProp deepMerge(Supplier<Object> supplier) {
        return new MergeProp(supplier, true);
    }
    
    /**
     * Create a deep merge prop with a static value.
     * 
     * @param value Static value for the prop
     * @return MergeProp instance with deep merge enabled
     */
    public static MergeProp deepMerge(Object value) {
        return new MergeProp(value, true);
    }
    
    /**
     * Create a deferred prop with default group.
     * 
     * @param evaluator Function to evaluate the prop value
     * @return DeferProp instance
     */
    public static DeferProp defer(Function<Object, Object> evaluator) {
        return new DeferProp(evaluator);
    }
    
    /**
     * Create a deferred prop with a supplier.
     * 
     * @param supplier Supplier for the prop value
     * @return DeferProp instance
     */
    public static DeferProp defer(Supplier<Object> supplier) {
        return new DeferProp(supplier);
    }
    
    /**
     * Create a deferred prop with a static value.
     * 
     * @param value Static value for the prop
     * @return DeferProp instance
     */
    public static DeferProp defer(Object value) {
        return new DeferProp(value);
    }
    
    /**
     * Create a deferred prop with a specific group.
     * 
     * @param evaluator Function to evaluate the prop value
     * @param group The defer group name
     * @return DeferProp instance
     */
    public static DeferProp defer(Function<Object, Object> evaluator, String group) {
        return new DeferProp(evaluator, group);
    }
    
    /**
     * Create a deferred prop with a supplier and specific group.
     * 
     * @param supplier Supplier for the prop value
     * @param group The defer group name
     * @return DeferProp instance
     */
    public static DeferProp defer(Supplier<Object> supplier, String group) {
        return new DeferProp(supplier, group);
    }
    
    /**
     * Create a deferred prop with a static value and specific group.
     * 
     * @param value Static value for the prop
     * @param group The defer group name
     * @return DeferProp instance
     */
    public static DeferProp defer(Object value, String group) {
        return new DeferProp(value, group);
    }
    
    /**
     * Create a deferred merge prop.
     * 
     * @param evaluator Function to evaluate the prop value
     * @param group The defer group name
     * @param merge Whether to enable merging
     * @param deepMerge Whether to enable deep merging
     * @return DeferProp instance
     */
    public static DeferProp defer(Function<Object, Object> evaluator, String group, boolean merge, boolean deepMerge) {
        return new DeferProp(evaluator, group, merge, deepMerge);
    }
} 