package com.gurtus.inertia.runtime.props;

/**
 * Base interface for all Inertia prop types.
 * Props can be evaluated in different contexts and have different behaviors
 * during rendering (e.g., lazy loading, merging, etc.).
 */
public interface BaseProp {
    
    /**
     * Evaluate the prop value in the given context.
     * 
     * @param context The evaluation context (typically a controller or service)
     * @return The evaluated prop value
     */
    Object evaluate(Object context);
    
    /**
     * Whether this prop should be included during partial reloads.
     * Default implementation returns true.
     */
    default boolean includeOnPartialReload() {
        return true;
    }
    
    /**
     * Whether this prop should be included on the first page load.
     * Default implementation returns true.
     */
    default boolean includeOnFirstLoad() {
        return true;
    }
    
    /**
     * Whether this prop should always be included, regardless of partial reload settings.
     * Default implementation returns false.
     */
    default boolean isAlways() {
        return false;
    }
    
    /**
     * Whether this prop supports merging with previous values.
     * Default implementation returns false.
     */
    default boolean isMergeable() {
        return false;
    }
    
    /**
     * Whether this prop should use deep merging.
     * Only relevant if isMergeable() returns true.
     */
    default boolean isDeepMergeable() {
        return false;
    }
    
    /**
     * Whether this prop is deferred (loaded separately).
     * Default implementation returns false.
     */
    default boolean isDeferred() {
        return false;
    }
    
    /**
     * Get the defer group for deferred props.
     * Only relevant if isDeferred() returns true.
     */
    default String getDeferGroup() {
        return "default";
    }
} 