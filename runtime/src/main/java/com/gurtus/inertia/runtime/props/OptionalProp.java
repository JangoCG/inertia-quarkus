package com.gurtus.inertia.runtime.props;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A prop that is only included during partial reloads, not on the initial page load.
 * This is useful for expensive computations that are not needed on the first visit.
 */
public class OptionalProp extends IgnoreOnFirstLoadProp {
    
    public OptionalProp(Function<Object, Object> evaluator) {
        super(evaluator);
    }
    
    public OptionalProp(Supplier<Object> supplier) {
        super(supplier);
    }
    
    public OptionalProp(Object value) {
        super(value);
    }
} 