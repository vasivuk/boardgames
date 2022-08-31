package com.vasivuk.boardgames.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Klasa predstavlja poruku o greški
 * @author Vale
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    /**
     * Status greske koji se salje u odgovoru
     */
    private HttpStatus status;
    /**
     * Poruka o greski kao String
     */
    private String message;
    /**
     * Detalji poruke o greski kao String
     */
    private String details;
    /**
     * Vreme greske kao LocalDateTime
     */
    private LocalDateTime timestamp;

    /**
     * Postavlja vrednosti na parametre konstruktora i vreme greske na trenutno lokalno vreme
     * @param status status odgovora
     * @param message poruka o greski
     * @param details detalji poruke
     */
    public ErrorMessage(HttpStatus status, String message, String details) {
        this.status = status;
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }
}
