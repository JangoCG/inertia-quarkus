package com.gurtus.inertia.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

/**
 * Enhanced service for handling Inertia.js requests and responses with controller integration.
 */
@ApplicationScoped
public class InertiaService {

    @Inject
    InertiaRenderer renderer;

    @Inject
    InertiaSession inertiaSession;

    private final Map<String, Object> sharedProps = new ConcurrentHashMap<>();
    private final Map<String, Object> sharedViewData = new ConcurrentHashMap<>();
    private final List<InertiaSharedData> sharedDataList = new ArrayList<>();

    /**
     * Share a prop globally across all Inertia responses.
     */
    public void share(String key, Object value) {
        sharedProps.put(key, value);
    }

    /**
     * Share view data globally across all Inertia responses.
     */
    public void shareViewData(String key, Object value) {
        sharedViewData.put(key, value);
    }

    /**
     * Create an Inertia response builder for fluent API.
     * No need to pass context anymore!
     * Usage: return inertiaService.inertia("ComponentName").with("key", "value").build();
     */
    public InertiaResponseBuilder inertia(String component) {
        return new InertiaResponseBuilder(this, component);
    }

    /**
     * Build a JAX-RS Response for the given component and props.
     * Context is now automatically injected.
     */
    public Response buildResponse(String component, Map<String, Object> props) {
        return buildResponse(component, props, null);
    }
    
    /**
     * Build a JAX-RS Response for the given component and props with controller context.
     */
    public Response buildResponse(String component, Map<String, Object> props, Object controllerContext) {
        return buildResponse(component, props, controllerContext, null);
    }

    /**
     * Build a JAX-RS Response with action context for shared data filtering.
     */
    public Response buildResponse(String component, Map<String, Object> props, Object controllerContext, String currentAction) {
        // Merge all shared data sources
        Map<String, Object> allSharedProps = mergeSharedData(controllerContext, currentAction);
        return renderer.render(component, props, allSharedProps, controllerContext);
    }

    /**
     * Add shared data with filters.
     */
    public void shareData(InertiaSharedData sharedData) {
        synchronized (sharedDataList) {
            sharedDataList.add(sharedData);
        }
    }

    /**
     * Add shared data with action filter.
     */
    public void shareData(Map<String, Object> data, String... onlyActions) {
        if (onlyActions.length > 0) {
            InertiaActionFilter filter = new InertiaActionFilter(InertiaActionFilter.ConditionalKey.ONLY, onlyActions);
            shareData(new InertiaSharedData(data, filter));
        } else {
            shareData(new InertiaSharedData(data));
        }
    }

    /**
     * Add conditional shared data.
     */
    public void shareDataIf(Map<String, Object> data, Supplier<Boolean> condition) {
        shareData(new InertiaSharedData(data, condition));
    }

    /**
     * Add dynamic shared data.
     */
    public void shareData(Supplier<Map<String, Object>> dataSupplier) {
        shareData(new InertiaSharedData(dataSupplier));
    }

    /**
     * Add contextual shared data.
     */
    public void shareData(Function<Object, Map<String, Object>> dataFunction) {
        shareData(new InertiaSharedData(dataFunction));
    }

    /**
     * Clear all shared data.
     */
    public void clearSharedData() {
        sharedProps.clear();
        synchronized (sharedDataList) {
            sharedDataList.clear();
        }
    }

    /**
     * Get current shared data for debugging.
     */
    public Map<String, Object> getCurrentSharedData(Object controllerContext, String currentAction) {
        return mergeSharedData(controllerContext, currentAction);
    }

    private Map<String, Object> mergeSharedData(Object controllerContext, String currentAction) {
        Map<String, Object> merged = new HashMap<>(sharedProps);
        
        // Add session errors if they exist
        // TODO: Add session context when available
        
        // Process shared data list
        synchronized (sharedDataList) {
            for (InertiaSharedData sharedData : sharedDataList) {
                if (sharedData.shouldInclude(currentAction)) {
                    Map<String, Object> data = sharedData.evaluate(controllerContext, currentAction);
                    merged.putAll(data);
                }
            }
        }
        
        return merged;
    }

} 