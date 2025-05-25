package com.gurtus.inertia.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gurtus.inertia.runtime.props.BaseProp;
import com.gurtus.inertia.runtime.props.DeferProp;

import io.quarkus.qute.Engine;
import io.quarkus.runtime.LaunchMode;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

/**
 * Enhanced Inertia renderer that supports advanced prop types and partial reloads.
 */
@ApplicationScoped
public class InertiaRenderer {

    @Inject
    InertiaConfig config;

    @Inject
    ObjectMapper objectMapper;

    @Inject
    InertiaContext context;

    @Inject
    Engine quteEngine;

    /**
     * Render an Inertia response with advanced prop handling.
     */
    public Response render(String component, Map<String, Object> props, Map<String, Object> sharedProps, Object controllerContext) {
        // Merge props with shared props
        Map<String, Object> mergedProps = mergeProps(sharedProps, props);
        
        // Process props based on their types and current request context
        Map<String, Object> processedProps = processProps(mergedProps, component, controllerContext);
        
        // Create the page object
        InertiaPage page = createPage(component, processedProps, mergedProps);
        
        // Check if this is an Inertia request
        if (context.isInertiaRequest()) {
            return createJsonResponse(page);
        } else {
            return createHtmlResponse(page);
        }
    }

    private Map<String, Object> mergeProps(Map<String, Object> sharedProps, Map<String, Object> props) {
        Map<String, Object> merged = new HashMap<>();
        
        if (sharedProps != null) {
            if (config.deepMergeSharedData()) {
                merged.putAll(deepMerge(sharedProps, new HashMap<>()));
            } else {
                merged.putAll(sharedProps);
            }
        }
        
        if (props != null) {
            if (config.deepMergeSharedData()) {
                merged = deepMerge(merged, props);
            } else {
                merged.putAll(props);
            }
        }
        
        return merged;
    }

    private Map<String, Object> processProps(Map<String, Object> props, String component, Object controllerContext) {
        Map<String, Object> processed = new HashMap<>();
        boolean isPartialReload = context.isPartialReload();
        String partialComponent = context.getPartialComponent();
        List<String> partialData = context.getPartialData();
        List<String> partialExcept = context.getPartialExcept();
        List<String> resetKeys = context.getResetKeys();

        for (Map.Entry<String, Object> entry : props.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (shouldIncludeProp(key, value, component, isPartialReload, partialComponent, partialData, partialExcept, resetKeys)) {
                if (value instanceof BaseProp) {
                    BaseProp prop = (BaseProp) value;
                    processed.put(key, prop.evaluate(controllerContext));
                } else {
                    processed.put(key, value);
                }
            }
        }

        return processed;
    }

    private boolean shouldIncludeProp(String key, Object value, String component, boolean isPartialReload, 
                                    String partialComponent, List<String> partialData, List<String> partialExcept, List<String> resetKeys) {
        
        // Check if this is a BaseProp with special behavior
        if (value instanceof BaseProp) {
            BaseProp prop = (BaseProp) value;
            
            // Always props are always included
            if (prop.isAlways()) {
                return true;
            }
            
            // Check first load vs partial reload
            if (!isPartialReload && !prop.includeOnFirstLoad()) {
                return false;
            }
            
            if (isPartialReload && !prop.includeOnPartialReload()) {
                return false;
            }
            
            // For partial reloads, check component match
            if (isPartialReload && !component.equals(partialComponent)) {
                return false;
            }
            
            // Skip deferred props during partial reloads
            if (isPartialReload && prop.isDeferred()) {
                return false;
            }
        }

        // Handle partial reload filtering
        if (isPartialReload) {
            // Check if prop is in reset keys (should be excluded)
            if (resetKeys.contains(key)) {
                return false;
            }
            
            // Check partial data inclusion
            if (!partialData.isEmpty()) {
                List<String> keyPrefixes = generateKeyPrefixes(key);
                if (keyPrefixes.stream().noneMatch(partialData::contains)) {
                    return false;
                }
            }
            
            // Check partial except exclusion
            if (!partialExcept.isEmpty()) {
                List<String> keyPrefixes = generateKeyPrefixes(key);
                if (keyPrefixes.stream().anyMatch(partialExcept::contains)) {
                    return false;
                }
            }
        }

        return true;
    }

    private List<String> generateKeyPrefixes(String key) {
        List<String> prefixes = new ArrayList<>();
        String[] parts = key.split("\\.");
        
        for (int i = 0; i < parts.length; i++) {
            StringBuilder prefix = new StringBuilder();
            for (int j = 0; j <= i; j++) {
                if (j > 0) prefix.append(".");
                prefix.append(parts[j]);
            }
            prefixes.add(prefix.toString());
        }
        
        return prefixes;
    }

    private InertiaPage createPage(String component, Map<String, Object> processedProps, Map<String, Object> allProps) {
        InertiaPage page = new InertiaPage();
        page.setComponent(component);
        page.setProps(processedProps);
        page.setUrl(context.getRequestUri());
        page.setVersion(config.version().orElse("1"));
        page.setEncryptHistory(config.encryptHistory() ? true : null);
        page.setClearHistory(config.clearHistory() ? true : null);

        // Add deferred props info
        if (!context.isPartialReload()) {
            Map<String, List<String>> deferredProps = collectDeferredProps(allProps);
            if (!deferredProps.isEmpty()) {
                page.setDeferredProps(deferredProps);
            }
        }

        // Add merge props info
        List<String> mergeProps = collectMergeProps(allProps, false);
        List<String> deepMergeProps = collectMergeProps(allProps, true);
        
        if (!mergeProps.isEmpty()) {
            page.setMergeProps(mergeProps);
        }
        
        if (!deepMergeProps.isEmpty()) {
            page.setDeepMergeProps(deepMergeProps);
        }

        return page;
    }

    private Map<String, List<String>> collectDeferredProps(Map<String, Object> props) {
        Map<String, List<String>> deferredProps = new HashMap<>();
        
        for (Map.Entry<String, Object> entry : props.entrySet()) {
            if (entry.getValue() instanceof DeferProp) {
                DeferProp deferProp = (DeferProp) entry.getValue();
                String group = deferProp.getDeferGroup();
                deferredProps.computeIfAbsent(group, k -> new ArrayList<>()).add(entry.getKey());
            }
        }
        
        return deferredProps;
    }

    private List<String> collectMergeProps(Map<String, Object> props, boolean deepMerge) {
        List<String> resetKeys = context.getResetKeys();
        
        return props.entrySet().stream()
                .filter(entry -> entry.getValue() instanceof BaseProp)
                .map(entry -> Map.entry(entry.getKey(), (BaseProp) entry.getValue()))
                .filter(entry -> entry.getValue().isMergeable() && 
                               entry.getValue().isDeepMergeable() == deepMerge &&
                               !resetKeys.contains(entry.getKey()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> deepMerge(Map<String, Object> original, Map<String, Object> update) {
        Map<String, Object> result = new HashMap<>(original);
        
        for (Map.Entry<String, Object> entry : update.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            
            if (result.containsKey(key) && result.get(key) instanceof Map && value instanceof Map) {
                result.put(key, deepMerge((Map<String, Object>) result.get(key), (Map<String, Object>) value));
            } else {
                result.put(key, value);
            }
        }
        
        return result;
    }

    private Response createJsonResponse(InertiaPage page) {
        try {
            String json = objectMapper.writeValueAsString(page);
            return Response.ok(json)
                    .header("Content-Type", "application/json")
                    .header("Vary", "X-Inertia")
                    .header(InertiaHeaders.X_INERTIA, "true")
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Error serializing Inertia page: " + e.getMessage())
                    .build();
        }
    }

    private Response createHtmlResponse(InertiaPage page) {
        try {
            String pageJson = objectMapper.writeValueAsString(page);
            
            // Load the configured template
            String templateName = config.rootTemplate();
            var template = quteEngine.getTemplate(templateName);
            
            if (template == null) {
                return Response.serverError()
                    .entity("Inertia template '" + templateName + "' not found. Please create the template file.")
                    .build();
            }
            
            // Check if we're in development mode
            boolean isDevelopment = LaunchMode.current() == LaunchMode.DEVELOPMENT;
            
            String html = template.data("page", page)
                                .data("pageJson", pageJson)
                                .data("isDevelopment", isDevelopment)
                                .render();
            
            return Response.ok(html)
                    .header("Content-Type", "text/html")
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Error rendering Inertia template: " + e.getMessage())
                    .build();
        }
    }
} 