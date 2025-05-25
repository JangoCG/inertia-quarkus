package com.gurtus.inertia.it;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.gurtus.inertia.runtime.InertiaService;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import io.quarkus.runtime.StartupEvent;

/**
 * Global configuration for Inertia shared data.
 * This ensures shared data is available across all controllers.
 */
@ApplicationScoped
public class GlobalInertiaConfig {

    @Inject
    InertiaService inertiaService;

    public void onStart(@Observes StartupEvent ev) {
        init();
    }

    @PostConstruct
    public void init() {
        // Share data globally across all controllers
        inertiaService.share("appName", "Inertia.js + Quarkus");
        
        // Share conditional data
        inertiaService.shareDataIf(props("debugMode", true), () -> true); // Always true for demo
        
        // Share dynamic data
        inertiaService.shareData(() -> props("serverTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
        
        // Share contextual data (with null-safe controller context)
        inertiaService.shareData(controller -> {
            Map<String, Object> data = new HashMap<>();
            if (controller != null) {
                data.put("controllerClass", controller.getClass().getSimpleName());
            } else {
                data.put("controllerClass", "GlobalConfig");
            }
            return data;
        });
    }

    /**
     * Helper method to create a map with a single key-value pair.
     */
    private Map<String, Object> props(String key, Object value) {
        Map<String, Object> props = new HashMap<>();
        props.put(key, value);
        return props;
    }
} 