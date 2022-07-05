package com.vasivuk.boardgames.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_roles")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRole {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
}
