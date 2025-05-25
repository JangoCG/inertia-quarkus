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
            .with("message", "Willkommen bei der Inertia.js Quarkus Extension mit React!")
            .with("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")))
            .build();
    }

    @GET
    @Path("/about")
    public Response about() {
        var features = List.of(
            "Vollständige Inertia.js Protokoll-Unterstützung",
            "Asset-Versionierung für Cache-Busting",
            "Partial Reloads für optimierte Performance",
            "Server-Side Rendering (SSR) Support",
            "Qute Template Integration",
            "CDI Integration",
            "Native Image Support",
            "React Integration mit Vite",
            "Moderne UI-Komponenten",
            "Responsive Design"
        );

        return inertiaService.inertia("About")
            .with("title", "Über die Inertia.js Quarkus Extension")
            .with("description", "Eine moderne Extension für die Entwicklung von SPAs mit Server-Side Patterns")
            .with("features", features)
            .build();
    }

    @GET
    @Path("/users")
    public Response users() {
        // Erweiterte Mock-Daten für die React-Komponente
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
                "role", "Entwicklerin",
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
                "role", "Projektmanagerin",
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