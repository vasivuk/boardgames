package com.vasivuk.boardgames.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Klasa predstavlja korisnika sistema.
 * @author Vale
 */

@Entity
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(
                name = "email_unique",
                columnNames = "email"
        )
)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AppUser {

    /**
     * Identifikacioni broj korisnika, generisana vrednost
     */
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;

    /**
     * Ime korisnika kao String
     */
    private String firstName;
    /**
     * Prezime korisnika kao String
     */
    private String lastName;
    /**
     * Email adresa korisnika kao String
     */
    private String email;
    /**
     * Sifra korisnika kao String
     */
    private String password;

    /**
     * Drzava korisnika kao String
     */
    private String country;
    /**
     * Mesto prebivalista korisnika kao String
     */
    private String city;
    /**
     * Adresa korisnika kao String
     */
    private String address;

    /**
     * Uloga korisnika u sistemu kao String
     */
    private String userRole;

    /**
     * Sve porudzbine korisnika
     */
    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY)
    private List<Order> orders;

}
