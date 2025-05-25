package com.gurtus.inertia.runtime;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Service for handling Server-Side Rendering (SSR) with Inertia.js.
 */
@ApplicationScoped
public class InertiaSSRService {

    @Inject
    InertiaConfig config;

    @Inject
    ObjectMapper objectMapper;

    private final HttpClient httpClient;

    public InertiaSSRService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    /**
     * SSR response containing rendered HTML and head content.
     */
    public static class SSRResponse {
        private final String body;
        private final String head;
        private final boolean success;
        private final String error;

        public SSRResponse(String body, String head, boolean success, String error) {
            this.body = body;
            this.head = head;
            this.success = success;
            this.error = error;
        }

        public String getBody() { return body; }
        public String getHead() { return head; }
        public boolean isSuccess() { return success; }
        public String getError() { return error; }

        public static SSRResponse success(String body, String head) {
            return new SSRResponse(body, head, true, null);
        }

        public static SSRResponse failure(String error) {
            return new SSRResponse(null, null, false, error);
        }
    }

    /**
     * Render a page using SSR.
     */
    public SSRResponse renderPage(InertiaPage page) {
        if (!config.ssrEnabled()) {
            return SSRResponse.failure("SSR is not enabled");
        }

        try {
            String pageJson = objectMapper.writeValueAsString(page);
            return performSSRRequest(pageJson);
        } catch (Exception e) {
            return SSRResponse.failure("Failed to serialize page: " + e.getMessage());
        }
    }

    private SSRResponse performSSRRequest(String pageJson) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(config.ssrUrl() + "/render"))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofMillis(config.ssrTimeout()))
                    .POST(HttpRequest.BodyPublishers.ofString(pageJson))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return parseSSRResponse(response.body());
            } else {
                return SSRResponse.failure("SSR server returned status: " + response.statusCode());
            }
        } catch (Exception e) {
            return SSRResponse.failure("SSR request failed: " + e.getMessage());
        }
    }

    private SSRResponse parseSSRResponse(String responseBody) {
        try {
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            
            String body = jsonNode.has("body") ? jsonNode.get("body").asText() : "";
            String head = "";
            
            if (jsonNode.has("head")) {
                JsonNode headNode = jsonNode.get("head");
                if (headNode.isArray()) {
                    StringBuilder headBuilder = new StringBuilder();
                    for (JsonNode headItem : headNode) {
                        headBuilder.append(headItem.asText());
                    }
                    head = headBuilder.toString();
                } else {
                    head = headNode.asText();
                }
            }
            
            return SSRResponse.success(body, head);
        } catch (Exception e) {
            return SSRResponse.failure("Failed to parse SSR response: " + e.getMessage());
        }
    }

    /**
     * Check if SSR is available.
     */
    public boolean isSSRAvailable() {
        if (!config.ssrEnabled()) {
            return false;
        }

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(config.ssrUrl() + "/health"))
                    .timeout(Duration.ofSeconds(2))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }
} 