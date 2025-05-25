package com.gurtus.inertia.deployment;

import com.gurtus.inertia.runtime.InertiaService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/test")
public class TestInertiaResource {

    @Inject
    InertiaService inertiaService;

    @GET
    public Response test() {
        return inertiaService.inertia("TestComponent")
            .with("message", "Hello from test!")
            .build();
    }
} 