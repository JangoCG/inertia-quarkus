package com.gurtus.inertia.runtime;

import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

import java.util.Optional;

/**
 * Configuration for Inertia.js integration.
 */
@ConfigMapping(prefix = "quarkus.inertia")
@ConfigRoot(phase = ConfigPhase.RUN_TIME)
public interface InertiaConfig {

    /**
     * The root template file for Inertia pages.
     * This template should contain the HTML structure and include
     * the Inertia page data.
     */
    @WithDefault("inertia.html")
    String rootTemplate();

    /**
     * The asset version for cache busting.
     * When this changes, Inertia will force a full page reload.
     */
    Optional<String> version();

    /**
     * The URL prefix for the application.
     * Used for generating proper URLs in responses.
     */
    @WithDefault("/")
    String url();

    /**
     * Enable Server-Side Rendering (SSR).
     */
    @WithDefault("false")
    boolean ssrEnabled();

    /**
     * SSR server URL.
     */
    @WithDefault("http://127.0.0.1:13714")
    String ssrUrl();

    /**
     * SSR timeout in milliseconds.
     */
    @WithDefault("30000")
    int ssrTimeout();
} 