package com.vasivuk.boardgames.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private String country;
    private String city;
    private String address;

    private String userRole;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY)
    private List<Order> orders;

}
