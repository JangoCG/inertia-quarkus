package com.gurtus.inertia.it;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gurtus.inertia.runtime.InertiaController;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
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
        return inertia("CSRFDemo", props(
            "title", "CSRF Protection Demo with Inertia.js + Axios",
            "description", "This demonstrates automatic CSRF protection using XSRF-TOKEN cookies and Axios",
            "messages", new ArrayList<>(messages),
            "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
        ));
    }

    @POST
    @Path("/submit")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response submitForm(
            @FormParam("name") String name,
            @FormParam("email") String email,
            @FormParam("message") String message) {
        
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
            return inertia("CSRFDemo", props(
                "title", "CSRF Protection Demo with Inertia.js + Axios",
                "description", "This demonstrates automatic CSRF protection using XSRF-TOKEN cookies and Axios",
                "messages", new ArrayList<>(messages),
                "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
                "errors", errors,
                "old", props(
                    "name", name,
                    "email", email,
                    "message", message
                )
            ));
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
        return inertia("CSRFDemo", props(
            "title", "CSRF Protection Demo with Inertia.js + Axios",
            "description", "This demonstrates automatic CSRF protection using XSRF-TOKEN cookies and Axios",
            "messages", new ArrayList<>(messages),
            "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
            "success", "Message submitted successfully! CSRF protection worked automatically via Axios."
        ));
    }

    @GET
    @Path("/clear")
    @Produces(MediaType.TEXT_HTML)
    public Response clearMessages() {
        synchronized (messages) {
            messages.clear();
        }
        
        return inertia("CSRFDemo", props(
            "title", "CSRF Protection Demo with Inertia.js + Axios",
            "description", "This demonstrates automatic CSRF protection using XSRF-TOKEN cookies and Axios",
            "messages", new ArrayList<>(messages),
            "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
            "success", "All messages cleared!"
        ));
    }

    @Override
    protected String getCurrentAction() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stack) {
            if (element.getClassName().equals(this.getClass().getName()) && 
                !element.getMethodName().equals("getCurrentAction")) {
                return element.getMethodName();
            }
        }
        return "form";
    }
} 