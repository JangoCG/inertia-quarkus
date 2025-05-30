package com.gurtus.inertia.it;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import com.gurtus.inertia.runtime.InertiaService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * Example resource showing how to use Inertia.js with Quarkus and React.
 * Using the new Rails-like API.
 */
@Path("/")
public class InertiaResource {

    @Inject
    InertiaService inertiaService;

    @GET
    @Path("/")
    public Response home() {
        return inertiaService.inertia("Home")
            .with("message", "Welcome to the Inertia.js Quarkus Extension with React!")
            .with("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")))
            .build();
    }

    @GET
    @Path("/inertia")
    public String testEndpoint() {
        return "Hello inertia";
    }

    @GET
    @Path("/about")
    public Response about() {
        var features = List.of(
            "Full Inertia.js protocol support",
            "Asset versioning for cache busting",
            "Partial reloads for optimized performance",
            "Server-side rendering (SSR) support",
            "Qute template integration",
            "CDI integration",
            "Native image support",
            "React integration with Vite",
            "Modern UI components",
            "Responsive design"
        );

        return inertiaService.inertia("About")
            .with("title", "About the Inertia.js Quarkus Extension")
            .with("description", "A modern extension for developing SPAs with server-side patterns")
            .with("features", features)
            .build();
    }

    @GET
    @Path("/users")
    public Response users() {
        // Extended mock data for the React component
        var users = List.of(
            Map.of(
                "id", 1,
                "name", "Max Mustermann",
                "email", "max.mustermann@example.com",
                "role", "Administrator",
                "active", true,
                "createdAt", "15.01.2024 09:30"
            ),
            Map.of(
                "id", 2,
                "name", "Anna Schmidt",
                "email", "anna.schmidt@example.com",
                "role", "Developer",
                "active", true,
                "createdAt", "22.02.2024 14:15"
            ),
            Map.of(
                "id", 3,
                "name", "Tom Weber",
                "email", "tom.weber@example.com",
                "role", "Designer",
                "active", false,
                "createdAt", "03.03.2024 11:45"
            ),
            Map.of(
                "id", 4,
                "name", "Lisa Müller",
                "email", "lisa.mueller@example.com",
                "role", "Project Manager",
                "active", true,
                "createdAt", "18.04.2024 16:20"
            ),
            Map.of(
                "id", 5,
                "name", "Stefan Bauer",
                "email", "stefan.bauer@example.com",
                "role", "DevOps Engineer",
                "active", true,
                "createdAt", "05.05.2024 08:10"
            )
        );

        return inertiaService.inertia("Users")
            .with("users", users)
            .with("totalUsers", users.size())
            .build();
    }
}
