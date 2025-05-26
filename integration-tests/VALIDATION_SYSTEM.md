# Inertia DTO Validation System

Dieses System bietet Laravel-style Validation für Inertia.js mit Quarkus. Es unterstützt sowohl manuelle als auch automatische Validation über Interceptors.

## Features

- ✅ Laravel-style Validation mit `errors` und `old` Props
- ✅ Automatische Component-Name Erkennung
- ✅ Bean Validation Integration
- ✅ Manuelle und automatische Validation
- ✅ Exception Mapper für saubere Error Handling
- ✅ Reflection-basierte "old input" Extraktion

## Komponenten

### 1. InertiaValidationHelper

Zentrale Klasse für Validation Logic:

```java
@ApplicationScoped
public class InertiaValidationHelper {
    public <T> ValidationResult validate(T dto);
    public Response withValidationErrors(String component, Map<String, Object> props,
                                       ValidationResult validation, Object dto);
    public Map<String, Object> extractOldInput(Object dto);
}
```

### 2. @ValidateInertia Annotation

Für automatische Validation:

```java
@ValidateInertia // Component wird automatisch ermittelt
@ValidateInertia(component = "CustomComponent") // Expliziter Component Name
```

### 3. InertiaValidationInterceptor

Automatische Validation für Methoden mit `@ValidateInertia`:

- Erkennt DTO Parameter automatisch (endend mit "Dto" oder `@Valid`)
- Ermittelt Component Name aus Controller Name (CSRFDemoController → CSRFDemo)
- Wirft `InertiaValidationException` bei Fehlern

### 4. InertiaValidationExceptionMapper

Automatische Behandlung von Validation Exceptions:

- Konvertiert Exception zu 422 Response
- Fügt `errors` und `old` Props hinzu
- Verwendet InertiaService für korrekte Response-Struktur

## Verwendung

### Manuelle Validation (Laravel-style)

```java
@POST
@Path("/submit")
public Response submitForm(MessageDto messageDto) {

    ValidationResult validation = validationHelper.validate(messageDto);

    if (validation.hasErrors()) {
        Map<String, Object> props = createBaseProps();
        return validationHelper.withValidationErrors("CSRFDemo", props, validation, messageDto);
    }

    // Business Logic...
    return handleSuccess();
}
```

### Automatische Validation (Interceptor)

```java
@POST
@Path("/submit-auto")
@ValidateInertia // Kein component String nötig! Wird automatisch ermittelt
public Response submitFormAuto(MessageDto messageDto) {

    // Validation passiert automatisch durch Interceptor!
    // Hier nur noch die Business Logic:

    // Success handling...
    return inertia("CSRFDemo", props);
}
```

### DTO mit Validation Annotations

```java
public class MessageDto {

    @NotBlank(message = "Name ist erforderlich")
    @Size(min = 2, max = 50, message = "Name muss zwischen 2 und 50 Zeichen lang sein")
    private String name;

    @NotBlank(message = "E-Mail ist erforderlich")
    @Email(message = "Bitte geben Sie eine gültige E-Mail-Adresse ein")
    private String email;

    @NotBlank(message = "Nachricht ist erforderlich")
    @Size(min = 10, max = 500, message = "Nachricht muss zwischen 10 und 500 Zeichen lang sein")
    private String message;

    // Getters und Setters...
}
```

## Frontend Integration

Das System ist kompatibel mit Inertia.js `useForm`:

```typescript
const { data, setData, post, processing, errors, reset } = useForm({
  name: "",
  email: "",
  message: "",
});

const submit = (e) => {
  e.preventDefault();

  post("/csrf-demo/submit", {
    onSuccess: () => {
      reset(); // Formular leeren
    },
    onError: (errors) => {
      // Errors werden automatisch in `errors` verfügbar
      console.log("Validation errors:", errors);
    },
  });
};
```

## Response Struktur

Bei Validation Fehlern:

```json
{
  "component": "CSRFDemo",
  "props": {
    "title": "...",
    "messages": [...],
    "errors": {
      "name": "Name ist erforderlich",
      "email": "Bitte geben Sie eine gültige E-Mail-Adresse ein"
    },
    "old": {
      "name": "John",
      "email": "invalid-email",
      "message": "Test"
    }
  },
  "url": "/csrf-demo/submit",
  "version": "..."
}
```

## Konfiguration

### Dependencies

```xml
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-hibernate-validator</artifactId>
</dependency>
```

### Component Name Ermittlung

1. **Explizit**: `@ValidateInertia(component = "MyComponent")`
2. **Automatisch**: `CSRFDemoController` → `CSRFDemo`
3. **Fallback**: Method Name

## Beispiel: CSRF Demo Controller

Siehe `CSRFDemoController.java` für vollständige Implementierung mit beiden Ansätzen:

- `/csrf-demo/submit` - Manuelle Validation
- `/csrf-demo/submit-auto` - Automatische Validation

## Vorteile

1. **Laravel-ähnlich**: Bekannte API für Laravel-Entwickler
2. **Flexibel**: Sowohl manuell als auch automatisch
3. **Type-Safe**: Vollständige Java Type Safety
4. **Inertia-optimiert**: Perfekte Integration mit Inertia.js
5. **Clean Code**: Weniger Boilerplate in Controllern
