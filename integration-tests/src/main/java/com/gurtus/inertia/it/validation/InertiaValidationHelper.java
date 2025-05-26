package com.gurtus.inertia.it.validation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class InertiaValidationHelper {
    
    @Inject
    Validator validator;
    
    public static class ValidationResult {
        private final Map<String, String> errors;
        private final boolean hasErrors;
        
        public ValidationResult(Map<String, String> errors) {
            this.errors = errors;
            this.hasErrors = !errors.isEmpty();
        }
        
        public Map<String, String> getErrors() { 
            return errors; 
        }
        
        public boolean hasErrors() { 
            return hasErrors; 
        }
        
        public boolean isValid() { 
            return !hasErrors; 
        }
    }
    
    public <T> ValidationResult validate(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        Map<String, String> errors = new HashMap<>();
        
        for (ConstraintViolation<T> violation : violations) {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        
        return new ValidationResult(errors);
    }
    
    // Laravel-style withErrors() für Inertia Response
    public Response withValidationErrors(String component, Map<String, Object> props, 
                                       ValidationResult validation, Object dto) {
        if (validation.hasErrors()) {
            Map<String, Object> errorProps = new HashMap<>(props);
            errorProps.put("errors", validation.getErrors());
            
            // Old input zurückgeben (Laravel-style)
            Map<String, Object> old = extractOldInput(dto);
            errorProps.put("old", old);
            
            // Für jetzt geben wir eine einfache Response zurück
            // Das wird später durch den InertiaService ersetzt
            return Response.status(422) // Unprocessable Entity
                    .entity(errorProps)
                    .build();
        }
        return null; // Keine Errors
    }
    
    public Map<String, Object> extractOldInput(Object dto) {
        Map<String, Object> old = new HashMap<>();
        
        // Via Reflection alle Getter durchgehen
        Class<?> clazz = dto.getClass();
        for (Method method : clazz.getMethods()) {
            if (method.getName().startsWith("get") && 
                method.getParameterCount() == 0 && 
                !method.getName().equals("getClass")) {
                
                try {
                    String fieldName = method.getName().substring(3);
                    fieldName = Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
                    
                    Object value = method.invoke(dto);
                    if (value != null) {
                        old.put(fieldName, value);
                    }
                } catch (Exception e) {
                    // Ignore reflection errors
                }
            }
        }
        
        return old;
    }
} 