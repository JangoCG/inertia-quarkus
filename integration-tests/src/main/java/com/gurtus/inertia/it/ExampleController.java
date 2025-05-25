package com.gurtus.inertia.it;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.gurtus.inertia.runtime.InertiaController;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class ExampleController extends InertiaController {

    @PostConstruct
    public void init() {
        // Share data globally
        share("appName", "Inertia.js + Quarkus");
        
        // Share data only for specific actions
        shareOnly(props("adminData", "Secret admin info"), "admin", "dashboard");
        
        // Share conditional data
        shareDataIf(props("debugMode", true), () -> true); // Always true for demo
        
        // Share dynamic data
        shareData(() -> props("serverTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
        
        // Share contextual data (with controller context)
        shareData(controller -> {
            Map<String, Object> data = new HashMap<>();
            data.put("controllerClass", controller.getClass().getSimpleName());
            return data;
        });
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response index() {
        return inertia("Home", props(
            "message", "Welcome to the Enhanced Inertia.js Quarkus Extension with Controller Integration!",
            "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
            "features", new String[]{
                "✅ Advanced Prop Types (Optional, Always, Defer, Merge)",
                "✅ Partial Reloads with Header Parsing", 
                "✅ Enhanced Middleware & Session Management",
                "✅ Controller Integration with Shared Data Filters",
                "✅ Action-based Conditional Sharing",
                "✅ Dynamic and Contextual Data Sharing"
            }
        ));
    }

    @GET
    @Path("/admin")
    @Produces(MediaType.TEXT_HTML)
    public Response admin() {
        return inertia("Admin", props(
            "message", "Admin Dashboard - This should include adminData from shared data",
            "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
        ));
    }

    @GET
    @Path("/dashboard")
    @Produces(MediaType.TEXT_HTML)
    public Response dashboard() {
        return inertia("Dashboard", props(
            "message", "Dashboard - This should also include adminData",
            "stats", props(
                "users", 42,
                "posts", 128,
                "comments", 256
            )
        ));
    }

    @GET
    @Path("/public")
    @Produces(MediaType.TEXT_HTML)
    public Response publicPage() {
        return inertia("Public", props(
            "message", "Public Page - This should NOT include adminData",
            "info", "This page demonstrates action-based filtering"
        ));
    }

    @Override
    protected String getCurrentAction() {
        // In a real implementation, this could be determined through:
        // - JAX-RS annotations reflection
        // - Request path analysis
        // - Method name extraction
        // For demo purposes, we'll use a simple approach
        
        // This is a simplified implementation for demonstration
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stack) {
            if (element.getClassName().equals(this.getClass().getName()) && 
                !element.getMethodName().equals("getCurrentAction")) {
                return element.getMethodName();
            }
        }
        return "index";
    }
} 