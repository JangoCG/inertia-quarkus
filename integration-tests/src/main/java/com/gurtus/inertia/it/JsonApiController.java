package com.gurtus.inertia.it;

import com.gurtus.inertia.runtime.InertiaController;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Example controller demonstrating RESTEasy Jackson usage with Inertia.js
 * Shows both JSON API endpoints and Inertia page rendering
 */
@Path("/api")
public class JsonApiController extends InertiaController {

    /**
     * JSON API endpoint - demonstrates automatic JSON serialization with RESTEasy Jackson
     */
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<String, Object>> getUsersAsJson() {
        return List.of(
            Map.of(
                "id", 1,
                "name", "Max Mustermann",
                "email", "max.mustermann@example.com",
                "role", "Administrator",
                "active", true,
                "createdAt", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            ),
            Map.of(
                "id", 2,
                "name", "Anna Schmidt", 
                "email", "anna.schmidt@example.com",
                "role", "Developer",
                "active", true,
                "createdAt", LocalDateTime.now().minusDays(5).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            )
        );
    }

    /**
     * JSON API endpoint that accepts JSON input
     */
    @POST
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> createUser(CreateUserRequest request) {
        // RESTEasy Jackson automatically deserializes the JSON request
        Map<String, Object> response = new HashMap<>();
        response.put("id", 3);
        response.put("name", request.getName());
        response.put("email", request.getEmail());
        response.put("role", request.getRole());
        response.put("active", true);
        response.put("createdAt", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        response.put("message", "User created successfully");
        
        return response;
    }

    /**
     * Inertia page that shows JSON data
     */
    @GET
    @Path("/demo")
    @Produces(MediaType.TEXT_HTML)
    public Response jsonDemo() {
        // You can also fetch data from your JSON API endpoints
        var users = getUsersAsJson();
        
        return inertia("JsonDemo", props(
            "title", "RESTEasy Jackson Demo",
            "users", users,
            "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
            "info", "This page demonstrates RESTEasy Jackson integration with Inertia.js"
        ));
    }

    /**
     * DTO for JSON request body
     */
    public static class CreateUserRequest {
        private String name;
        private String email;
        private String role;

        // Getters and setters for Jackson
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
}
