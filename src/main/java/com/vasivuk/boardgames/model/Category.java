package com.vasivuk.boardgames.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {

    @GeneratedValue
    @Id
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 1, max = 30)
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products;

}
