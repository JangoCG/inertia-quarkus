package com.gurtus.inertia.it;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.gurtus.inertia.runtime.InertiaController;
import com.gurtus.inertia.runtime.InertiaHelper;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class ExampleController extends InertiaController {

    @Inject
    InertiaHelper inertiaHelper;

    @PostConstruct
    public void init() {
        // Share data only for specific actions (controller-specific)
        shareOnly(props("adminData", "Secret admin info"), "admin", "dashboard");
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
                "✅ Dynamic and Contextual Data Sharing",
                "✅ SSR Support with Head Injection",
                "✅ Helper Functions (inertia_ssr_head, inertia_rendering)",
                "✅ Enhanced Configuration with Environment Variables"
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

    @GET
    @Path("/ssr-demo")
    @Produces(MediaType.TEXT_HTML)
    public Response ssrDemo() {
        return inertia("SSRDemo", props(
            "message", "SSR Demo Page",
            "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
            "helperInfo", props(
                "isInertiaRequest", inertiaHelper.isInertiaRequest(),
                "isPartialReload", inertiaHelper.isPartialReload(),
                "isInertiaRendering", inertiaHelper.isInertiaRendering(),
                "inertiaVersion", inertiaHelper.getInertiaVersion(),
                "partialComponent", inertiaHelper.getPartialComponent(),
                "hasSSRHead", inertiaHelper.hasSSRHead()
            ),
            "ssrInfo", props(
                "ssrEnabled", "Check configuration",
                "note", "SSR would render this page server-side if enabled and SSR server is running"
            )
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