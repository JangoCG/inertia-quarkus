package com.gurtus.inertia.it.validation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@ValidateInertia
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class InertiaValidationInterceptor {
    
    @Inject
    InertiaValidationHelper validationHelper;
    
    @AroundInvoke
    public Object validateRequest(InvocationContext context) throws Exception {
        ValidateInertia annotation = context.getMethod().getAnnotation(ValidateInertia.class);
        
        if (annotation != null) {
            // Finde DTO Parameter
            Object[] parameters = context.getParameters();
            for (Object param : parameters) {
                if (param != null && isValidationTarget(param)) {
                    InertiaValidationHelper.ValidationResult validation = validationHelper.validate(param);
                    
                    if (validation.hasErrors()) {
                        // Component Name automatisch ermitteln
                        String componentName = getComponentName(annotation, context);
                        
                        // Base props für die Seite erstellen
                        Map<String, Object> baseProps = createBaseProps();
                        
                        throw new InertiaValidationException(
                            componentName,
                            baseProps,
                            validation.getErrors(),
                            validationHelper.extractOldInput(param)
                        );
                    }
                }
            }
        }
        
        return context.proceed();
    }
    
    private String getComponentName(ValidateInertia annotation, InvocationContext context) {
        // Option 1: Explizit angegeben
        if (!annotation.component().isEmpty()) {
            return annotation.component();
        }
        
        // Option 2: Aus Controller/Method Name ableiten
        String className = context.getTarget().getClass().getSimpleName();
        
        // CSRFDemoController.submitForm -> CSRFDemo
        if (className.endsWith("Controller")) {
            return className.substring(0, className.length() - 10); // "Controller" entfernen
        }
        
        // Fallback: Method name als Component
        String methodName = context.getMethod().getName();
        return capitalize(methodName);
    }
    
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    
    private boolean isValidationTarget(Object param) {
        // Prüfe ob es ein DTO ist (z.B. durch Package oder Annotation)
        return param.getClass().getSimpleName().endsWith("Dto") ||
               param.getClass().isAnnotationPresent(jakarta.validation.Valid.class);
    }
    
    private Map<String, Object> createBaseProps() {
        // Standard Props die jede Seite braucht
        Map<String, Object> props = new HashMap<>();
        props.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        return props;
    }
} 