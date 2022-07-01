package com.vasivuk.boardgames.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {

    @GeneratedValue
    @Id
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private int gameTime;
    private String numberOfPlayers;
    private double complexity;
    private double rating;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<Review> reviews;

    @ManyToMany
    private Set<Category> categories;

    public Product() {
    }

    public Product(Long id, String name, BigDecimal price, String description, int gameTime, String numberOfPlayers, double complexity, double rating, List<Review> reviews, Set<Category> categories) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.gameTime = gameTime;
        this.numberOfPlayers = numberOfPlayers;
        this.complexity = complexity;
        this.rating = rating;
        this.reviews = reviews;
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGameTime() {
        return gameTime;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    public String getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(String numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public double getComplexity() {
        return complexity;
    }

    public void setComplexity(double complexity) {
        this.complexity = complexity;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", gameTime=" + gameTime +
                ", numberOfPlayers='" + numberOfPlayers + '\'' +
                ", complexity=" + complexity +
                ", rating=" + rating +
                ", reviews=" + reviews +
                ", categories=" + categories +
                '}';
    }
}
