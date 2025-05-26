package com.gurtus.inertia.it.validation;

import java.util.HashMap;
import java.util.Map;

import com.gurtus.inertia.runtime.InertiaService;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InertiaValidationExceptionMapper implements ExceptionMapper<InertiaValidationException> {
    
    @Inject
    InertiaService inertiaService;
    
    @Override
    public Response toResponse(InertiaValidationException exception) {
        Map<String, Object> props = new HashMap<>(exception.getProps());
        props.put("errors", exception.getErrors());
        props.put("old", exception.getOld());
        
        return Response.status(422)
                .entity(inertiaService.buildResponse(exception.getComponent(), props))
                .build();
    }
} 