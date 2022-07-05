package com.vasivuk.boardgames.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
public class AppUser {

    @GeneratedValue
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser")
    private List<Order> orders;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser")
    private List<Review> reviews;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserAuthorizationLevel authorizationLevel;
}
