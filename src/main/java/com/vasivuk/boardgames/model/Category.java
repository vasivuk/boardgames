package com.vasivuk.boardgames.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 1, max = 30)
    private String name;

    @JsonBackReference
    @ManyToMany(mappedBy = "categories")
    private List<Product> products;
}
