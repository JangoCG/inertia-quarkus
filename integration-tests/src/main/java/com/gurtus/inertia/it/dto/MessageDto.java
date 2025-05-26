package com.gurtus.inertia.it.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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

    // Standard-Konstruktor (wird von Jackson benötigt)
    public MessageDto() {
    }

    // Konstruktor für Testzwecke oder manuelle Erstellung (optional)
    public MessageDto(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }

    // Getter und Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}