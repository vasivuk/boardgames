package com.vasivuk.boardgames.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @GeneratedValue
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isAdmin;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Order> orders;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Review> reviews;

    public User() {
    }

    public User(Long id, String firstName, String lastName, String email, String password, boolean isAdmin, List<Order> orders, List<Review> reviews) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.orders = orders;
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                ", orders=" + orders +
                ", reviews=" + reviews +
                '}';
    }
}
