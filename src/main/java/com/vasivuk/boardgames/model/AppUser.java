package com.vasivuk.boardgames.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppUser {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;

    public AppUser(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser")
    private List<Order> orders = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser")
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private UserRole userRole;
}
