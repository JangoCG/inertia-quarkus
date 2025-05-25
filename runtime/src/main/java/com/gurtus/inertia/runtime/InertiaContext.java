package com.gurtus.inertia.runtime;

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
} 