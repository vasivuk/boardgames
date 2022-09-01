package com.vasivuk.boardgames.model.dto;

import com.vasivuk.boardgames.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @Size(max = 40)
    private String name;
    private String imageUrl;
    @Positive
    private BigDecimal price;
    @Size(max = 1000)
    private String description;
    @Positive
    private int gameTime;
    private String numberOfPlayers;
    @Min(0) @Max(5)
    private double complexity;
    @Min(0) @Max(5)
    private double rating;
    @Min(0)
    private int stockQuantity;

    private Set<Category> categories;
}
