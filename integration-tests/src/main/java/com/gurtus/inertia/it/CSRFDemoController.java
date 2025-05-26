package com.gurtus.inertia.it;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gurtus.inertia.it.dto.MessageDto;
import com.gurtus.inertia.it.validation.InertiaValidationHelper;
import com.gurtus.inertia.it.validation.ValidateInertia;
import com.gurtus.inertia.runtime.InertiaController;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/csrf-demo")
public class CSRFDemoController extends InertiaController {

    @Inject
    InertiaValidationHelper validationHelper;

    // In-memory storage for demo purposes
    private static final List<Map<String, Object>> messages = new ArrayList<>();
    private static final int MAX_MESSAGES = 10;


    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response form() {
        Map<String, Object> props = new HashMap<>();
        props.put("title", "CSRF Protection Demo with Inertia.js + Axios");
        props.put("description", "This demonstrates automatic CSRF protection using XSRF-TOKEN cookies and Axios");
        props.put("messages", new ArrayList<>(messages)); // Kopie erstellen
        props.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        
        return inertia("CSRFDemo", props);
    }

    @POST
    @Path("/submit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response submitForm(MessageDto messageDto) {
        
        // Laravel-style Validation mit dem neuen Helper
        InertiaValidationHelper.ValidationResult validation = validationHelper.validate(messageDto);
        
        if (validation.hasErrors()) {
            Map<String, Object> props = createBaseProps();
            return validationHelper.withValidationErrors("CSRFDemo", props, validation, messageDto);
        }

        // Nachricht zum In-Memory-Speicher hinzufügen
        Map<String, Object> newMessage = new LinkedHashMap<>(); // LinkedHashMap behält Einfügereihenfolge bei
        newMessage.put("name", messageDto.getName().trim());
        newMessage.put("email", messageDto.getEmail().trim());
        newMessage.put("message", messageDto.getMessage().trim());
        newMessage.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        
        synchronized (messages) {
            messages.add(0, newMessage); // Am Anfang hinzufügen
            // Nur die letzten MAX_MESSAGES Nachrichten behalten
            if (messages.size() > MAX_MESSAGES) {
                messages.subList(MAX_MESSAGES, messages.size()).clear();
            }
        }

        // Zurück zum Formular mit Erfolgsmeldung
        // Man könnte hier auch einen Redirect machen, aber Inertia löst das oft durch
        // erneutes Laden der Seite mit neuen Props.
        // Für `useForm` `onSuccess` ist es üblich, dass der Server die Seite neu rendert (oder Props zurückgibt).
        Map<String, Object> successProps = new HashMap<>();
        successProps.put("title", "CSRF Protection Demo with Inertia.js + Axios");
        successProps.put("description", "This demonstrates automatic CSRF protection using XSRF-TOKEN cookies and Axios");
        successProps.put("messages", new ArrayList<>(messages));
        successProps.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        successProps.put("success", "Message submitted successfully! CSRF protection worked automatically via Axios.");
        
        // Wichtig: Nach einem erfolgreichen POST sollte man idealerweise einen Redirect machen (Post/Redirect/Get Pattern).
        // Inertia.js handhabt dies jedoch oft, indem die Seite mit den neuen Props (einschließlich der Erfolgsmeldung)
        // neu geladen wird. Die `reset()` Funktion im `onSuccess` Callback im Frontend leert die Formularfelder.
        // Wenn du einen echten Redirect machen willst:
        // return Response.seeOther(URI.create("/csrf-demo")).header("X-Inertia", "true").build();
        // und dann die 'success' Nachricht über Flash-Messages handhaben.
        // Für die Einfachheit hier, rendern wir die Seite neu mit 'success' prop.
        return inertia("CSRFDemo", successProps);
    }

    @GET
    @Path("/clear")
    @Produces(MediaType.TEXT_HTML)
    public Response clearMessages() {
        synchronized (messages) {
            messages.clear();
        }
        
        Map<String, Object> props = new HashMap<>();
        props.put("title", "CSRF Protection Demo with Inertia.js + Axios");
        props.put("description", "This demonstrates automatic CSRF protection using XSRF-TOKEN cookies and Axios");
        props.put("messages", new ArrayList<>(messages)); // Leere Liste
        props.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        props.put("success", "All messages cleared!");
        
        // Auch hier wäre ein Redirect besser, aber für die Konsistenz mit dem obigen POST:
        return inertia("CSRFDemo", props);
    }

    // Alternative Methode mit automatischer Validation (Interceptor)
    @POST
    @Path("/submit-auto")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    @ValidateInertia // Automatische Validation! Component wird aus "CSRFDemoController" -> "CSRFDemo" abgeleitet
    public Response submitFormAuto(MessageDto messageDto) {
        
        // Validation passiert automatisch durch Interceptor!
        // Hier nur noch die Business Logic:
        
        Map<String, Object> newMessage = new LinkedHashMap<>();
        newMessage.put("name", messageDto.getName().trim());
        newMessage.put("email", messageDto.getEmail().trim());
        newMessage.put("message", messageDto.getMessage().trim());
        newMessage.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        
        synchronized (messages) {
            messages.add(0, newMessage);
            if (messages.size() > MAX_MESSAGES) {
                messages.subList(MAX_MESSAGES, messages.size()).clear();
            }
        }

        // Success Response
        Map<String, Object> props = new HashMap<>();
        props.put("title", "CSRF Protection Demo with Inertia.js + Axios");
        props.put("description", "This demonstrates automatic CSRF protection using XSRF-TOKEN cookies and Axios");
        props.put("messages", new ArrayList<>(messages));
        props.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        props.put("success", "Message submitted successfully! CSRF protection worked automatically via Axios.");
        
        return inertia("CSRFDemo", props);
    }

    private Map<String, Object> createBaseProps() {
        Map<String, Object> props = new HashMap<>();
        props.put("title", "CSRF Protection Demo with Inertia.js + Axios");
        props.put("description", "This demonstrates automatic CSRF protection using XSRF-TOKEN cookies and Axios");
        props.put("messages", new ArrayList<>(messages));
        props.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        return props;
    }
}