package com.vasivuk.boardgames.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {

    @Id
    @SequenceGenerator(
            name = "sequence_category",
            sequenceName = "sequence_category",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_category"
    )
    private Long categoryId;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 1, max = 30)
    @Column(unique = true)
    private String name;

    @Size(max = 1000)
    private String description;

}
