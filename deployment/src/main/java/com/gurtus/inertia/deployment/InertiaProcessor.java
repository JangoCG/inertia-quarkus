package com.gurtus.inertia.deployment;

import com.gurtus.inertia.runtime.InertiaConfig;
import com.gurtus.inertia.runtime.InertiaContext;
import com.gurtus.inertia.runtime.InertiaFilter;
import com.gurtus.inertia.runtime.InertiaService;

import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;

/**
 * Processor for the Inertia.js extension.
 */
public class InertiaProcessor {

    private static final String FEATURE = "inertia-js";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    AdditionalBeanBuildItem registerBeans() {
        return AdditionalBeanBuildItem.builder()
                .addBeanClasses(InertiaService.class, InertiaFilter.class, InertiaContext.class, InertiaConfig.class)
                .addBeanClass("com.gurtus.inertia.runtime.InertiaRenderer")
                .build();
    }

    @BuildStep
    ReflectiveClassBuildItem registerForReflection() {
        return ReflectiveClassBuildItem.builder(
                "com.gurtus.inertia.runtime.InertiaPage",
                "com.gurtus.inertia.runtime.InertiaResponse",
                "com.gurtus.inertia.runtime.InertiaRenderer"
        ).build();
    }

    // TODO: Add filter support later
    // @BuildStep
    // @Record(ExecutionTime.RUNTIME_INIT)
    // FilterBuildItem addInertiaFilter(InertiaRecorder recorder) {
    //     return new FilterBuildItem(recorder.createInertiaFilter(), 100);
    // }
} 