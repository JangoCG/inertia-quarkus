package com.gurtus.inertia.it;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gurtus.inertia.runtime.InertiaController;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/csrf-demo")
public class CSRFDemoController extends InertiaController {

    // In-memory storage for demo purposes
    private static final List<Map<String, Object>> messages = new ArrayList<>();

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response form() {
        Map<String, Object> props = new HashMap<>();
        props.put("title", "CSRF Protection Demo with Inertia.js + Axios");
        props.put("description", "This demonstrates automatic CSRF protection using XSRF-TOKEN cookies and Axios");
        props.put("messages", new ArrayList<>(messages));
        props.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        
        return inertia("CSRFDemo", props);
    }

    @POST
    @Path("/submit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response submitForm(String jsonData) {
        
        // Simple JSON parsing for demo purposes
        String name = extractJsonValue(jsonData, "name");
        String email = extractJsonValue(jsonData, "email");
        String message = extractJsonValue(jsonData, "message");
        
        // Validate input
        Map<String, String> errors = new HashMap<>();
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Name is required");
        }
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "Email is required");
        }
        if (message == null || message.trim().isEmpty()) {
            errors.put("message", "Message is required");
        }

        if (!errors.isEmpty()) {
            // Return with validation errors
            Map<String, Object> props = new HashMap<>();
            props.put("title", "CSRF Protection Demo with Inertia.js + Axios");
            props.put("description", "This demonstrates automatic CSRF protection using XSRF-TOKEN cookies and Axios");
            props.put("messages", new ArrayList<>(messages));
            props.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
            props.put("errors", errors);
            
            Map<String, Object> old = new HashMap<>();
            old.put("name", name);
            old.put("email", email);
            old.put("message", message);
            props.put("old", old);
            
            return inertia("CSRFDemo", props);
        }

        // Add message to our in-memory storage
        Map<String, Object> newMessage = new HashMap<>();
        newMessage.put("name", name.trim());
        newMessage.put("email", email.trim());
        newMessage.put("message", message.trim());
        newMessage.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        
        synchronized (messages) {
            messages.add(0, newMessage); // Add to beginning
            // Keep only last 10 messages
            if (messages.size() > 10) {
                messages.subList(10, messages.size()).clear();
            }
        }

        // Redirect back to form with success message
        Map<String, Object> props = new HashMap<>();
        props.put("title", "CSRF Protection Demo with Inertia.js + Axios");
        props.put("description", "This demonstrates automatic CSRF protection using XSRF-TOKEN cookies and Axios");
        props.put("messages", new ArrayList<>(messages));
        props.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        props.put("success", "Message submitted successfully! CSRF protection worked automatically via Axios.");
        
        return inertia("CSRFDemo", props);
    }

    @GET
    @Path("/clear")
    @Produces(MediaType.TEXT_HTML)
    public Response clearMessages() {
        synchronized (messages) {
            messages.clear();
        }
        
        Map<String, Object> props = new HashMap<>();
        props.put("title", "CSRF Protection Demo with Inertia.js + Axios");
        props.put("description", "This demonstrates automatic CSRF protection using XSRF-TOKEN cookies and Axios");
        props.put("messages", new ArrayList<>(messages));
        props.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        props.put("success", "All messages cleared!");
        
        return inertia("CSRFDemo", props);
    }

    private String extractJsonValue(String json, String key) {
        // Simple JSON value extraction for demo purposes
        String pattern = "\"" + key + "\"\\s*:\\s*\"([^\"]+)\"";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(json);
        return m.find() ? m.group(1) : null;
    }

} 