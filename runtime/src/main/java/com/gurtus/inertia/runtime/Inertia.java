package com.gurtus.inertia.runtime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a method as an Inertia.js endpoint.
 * Methods annotated with this will automatically handle Inertia requests.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Inertia {
    
    /**
     * The component name to render.
     * If not specified, it will be derived from the method name or class.
     */
    String component() default "";
} 