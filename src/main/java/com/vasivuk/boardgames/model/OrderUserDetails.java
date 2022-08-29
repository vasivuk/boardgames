package com.vasivuk.boardgames.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
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
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String city;
    private String address;
}
