package com.gurtus.inertia.runtime;

import io.quarkus.runtime.annotations.Recorder;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * Recorder for Inertia.js extension.
 */
@Recorder
public class InertiaRecorder {

    /**
     * Create the Inertia filter handler.
     */
    public Handler<RoutingContext> createInertiaFilter() {
        return new Handler<RoutingContext>() {
            @Override
            public void handle(RoutingContext context) {
                InertiaFilter filter = context.get("inertiaFilter");
                if (filter == null) {
                    // Get the filter from CDI
                    filter = io.quarkus.arc.Arc.container().instance(InertiaFilter.class).get();
                    context.put("inertiaFilter", filter);
                }
                
                if (filter.handle(context)) {
                    context.next();
                }
                // If filter returns false, the response has already been sent
            }
        };
    }
} 