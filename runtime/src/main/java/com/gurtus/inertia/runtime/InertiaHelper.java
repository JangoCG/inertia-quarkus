package com.gurtus.inertia.runtime;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

/**
 * Helper class for Inertia.js template functions.
 * Based on Rails InertiaRails::Helper.
 */
@RequestScoped
public class InertiaHelper {

    @Inject
    InertiaContext context;

    private String ssrHead;
    private boolean inertiaRendering = false;

    /**
     * Get the SSR head content for the current request.
     * This is set by the SSR service during rendering.
     */
    public String getInertiaSSRHead() {
        return ssrHead != null ? ssrHead : "";
    }

    /**
     * Set the SSR head content.
     * This is called internally by the SSR service.
     */
    public void setInertiaSSRHead(String head) {
        this.ssrHead = head;
    }

    /**
     * Check if we are currently rendering an Inertia response.
     */
    public boolean isInertiaRendering() {
        return inertiaRendering;
    }

    /**
     * Set the Inertia rendering flag.
     * This is called internally during Inertia response rendering.
     */
    public void setInertiaRendering(boolean rendering) {
        this.inertiaRendering = rendering;
    }

    /**
     * Check if the current request is an Inertia request.
     */
    public boolean isInertiaRequest() {
        return context.isInertiaRequest();
    }

    /**
     * Check if the current request is a partial reload.
     */
    public boolean isPartialReload() {
        return context.isPartialReload();
    }

    /**
     * Get the current Inertia version.
     */
    public String getInertiaVersion() {
        return context.getInertiaVersion();
    }

    /**
     * Get the component name for partial reloads.
     */
    public String getPartialComponent() {
        return context.getPartialComponent();
    }

    /**
     * Helper method to generate component path from controller path and action.
     */
    public String resolveComponentPath(String controllerPath, String action, String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            pattern = "{path}/{action}";
        }
        
        return pattern
                .replace("{path}", controllerPath != null ? controllerPath : "")
                .replace("{action}", action != null ? action : "index");
    }

    /**
     * Check if SSR head content is available.
     */
    public boolean hasSSRHead() {
        return ssrHead != null && !ssrHead.trim().isEmpty();
    }

    /**
     * Get safe SSR head content (HTML-escaped if needed).
     */
    public String getSafeSSRHead() {
        String head = getInertiaSSRHead();
        // In a real implementation, you might want to sanitize the HTML
        // For now, we'll return it as-is since it comes from our SSR server
        return head;
    }
} 