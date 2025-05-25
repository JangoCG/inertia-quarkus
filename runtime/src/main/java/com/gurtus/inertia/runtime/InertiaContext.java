package com.gurtus.inertia.runtime;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.UriInfo;

/**
 * Request-scoped context that holds HTTP request information
 * for Inertia responses.
 */
@RequestScoped
public class InertiaContext {
    
    @Context
    private HttpHeaders headers;
    
    @Context
    private UriInfo uriInfo;
    
    public HttpHeaders getHeaders() {
        return headers;
    }
    
    public UriInfo getUriInfo() {
        return uriInfo;
    }
    
    public boolean isInertiaRequest() {
        return headers != null && "true".equals(headers.getHeaderString(InertiaHeaders.X_INERTIA));
    }
    
    public String getRequestUri() {
        return uriInfo != null ? uriInfo.getRequestUri().toString() : "";
    }
    
    public String getInertiaVersion() {
        return headers != null ? headers.getHeaderString(InertiaHeaders.X_INERTIA_VERSION) : null;
    }
    
    public String getPartialComponent() {
        return headers != null ? headers.getHeaderString(InertiaHeaders.X_INERTIA_PARTIAL_COMPONENT) : null;
    }
    
    public List<String> getPartialData() {
        String partialData = headers != null ? headers.getHeaderString(InertiaHeaders.X_INERTIA_PARTIAL_DATA) : null;
        if (partialData == null || partialData.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(partialData.split(","));
    }
    
    public List<String> getPartialExcept() {
        String partialExcept = headers != null ? headers.getHeaderString(InertiaHeaders.X_INERTIA_PARTIAL_EXCEPT) : null;
        if (partialExcept == null || partialExcept.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(partialExcept.split(","));
    }
    
    public List<String> getResetKeys() {
        String resetKeys = headers != null ? headers.getHeaderString(InertiaHeaders.X_INERTIA_RESET) : null;
        if (resetKeys == null || resetKeys.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(resetKeys.split(","));
    }
    
    public boolean isPartialReload() {
        return getPartialComponent() != null;
    }
} 