package com.vasivuk.boardgames.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Klasa predstavlja korisničke podatke koji su vezani za porudžbinu, ne moraju nužno biti podaci koji se nalaze u bazi.
 * @author Vale
 */

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@AttributeOverrides({
        @AttributeOverride(
                name = "firstName",
                column = @Column(name = "buyer_first_name")
        ),
        @AttributeOverride(
                name = "lastName",
                column = @Column(name = "buyer_last_name")
        ),
        @AttributeOverride(
                name = "email",
                column = @Column(name = "buyer_email")
        ),
        @AttributeOverride(
                name = "country",
                column = @Column(name = "buyer_country")
        ),
        @AttributeOverride(
                name = "city",
                column = @Column(name = "buyer_city")
        ),
        @AttributeOverride(
                name = "address",
                column = @Column(name = "buyer_address")
        )
})
public class OrderUserDetails {
    /**
     * Ime kupca kao String
     */
    @NotBlank(message = "First name is mandatory")
    @Size(max = 30)
    private String firstName;
    /**
     * Prezime kupca kao String
     */
    @NotBlank(message = "Last name is mandatory")
    @Size(max = 30)
    private String lastName;
    /**
     * Email kupca kao String
     */
    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;
    /**
     * Drzava kupca kao String
     */
    @NotBlank(message = "Country is mandatory")
    private String country;
    /**
     * Grad kupca kao String
     */
    @NotBlank(message = "City is mandatory")
    private String city;
    /**
     * Adresa kupca kao String
     */
    @NotBlank(message = "Address is mandatory")
    private String address;
}
