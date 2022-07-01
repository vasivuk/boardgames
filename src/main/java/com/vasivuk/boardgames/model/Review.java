package com.vasivuk.boardgames.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Predstavlja recenziju korisnika nad nekim proizvodom.
 */
@Entity
@Table(name = "reviews")
public class Review {

    /**
     * Identifikacioni broj recenzije, automatski se generiše
     */
    @GeneratedValue
    @Id
    private Long id;
    /**
     * Broj zvezdica izražen u vrednosti od 1 do 5
     */
    private Integer rating;
    /**
     * Tekst recenzije, koji korisnik može da izabere da ispiše
     */
    private String reviewText;
    /**
     * Datum recenzije
     */
    private Date reviewDate;

    /**
     * Korisnik koji piše recenziju
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_review"))
    private User user;

    /**
     * Proizvod nad kojim se piše recenzija
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_product_review"))
    private Product product;

    public Review() {
    }

    /**
     * Postavlja sve atribute na zadate vrednosti
     *
     * @param rating     novi rejting
     * @param reviewText novi tekst recenzije
     * @param reviewDate novi datum recenzije
     * @param user       korisnik
     * @param product    proizvod
     */
    public Review(Integer rating, String reviewText, Date reviewDate, User user, Product product) {
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
        this.user = user;
        this.product = product;
    }

    /**
     * Vraća identifikacioni broj.
     *
     * @return identifikacioni broj
     */
    public Long getId() {
        return id;
    }

    /**
     * Postavlja identifikacioni broj.
     *
     * @param id identifikacioni broj
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Vraća postavljeni rejting
     *
     * @return korisnikov rejting za određeni proizvod
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * Postavlja rejting na zadatu vrednost od 1 do 5
     *
     * @param rating rejting proizvoda
     * @throws NullPointerException Ako je uneti rejting null
     * @throws IllegalArgumentException Ako je uneti rejting iznad 5 ili ispod 1.
     */
    public void setRating(Integer rating) {
        if (rating == null) {
            throw new NullPointerException("Product rating cannot be empty!");
        }
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Product rating cannot be higher than 5 or lower than 1");
        }
        this.rating = rating;
    }

    /**
     * Vraća tekst recenzije
     * @return tekst recenzije.
     */
    public String getReviewText() {
        return reviewText;
    }

    /**
     * Postavlja tekst recenzije maksimalne dužine od 255 karaktera.
     * @param reviewText tekst recenzije
     * @throws IllegalArgumentException Ako je uneti tekst duži od 255 karaktera.
     */
    public void setReviewText(String reviewText) {
        if(reviewText != null && reviewText.length() > 255) {
            throw new IllegalArgumentException("Maximum review length size is 255 characters!");
        }
    }

    /**
     * Vraća datum napisane recenzije.
     * @return datum napisane recenzije
     */
    public Date getReviewDate() {
        return reviewDate;
    }

    /**
     * Postavlja datum napisane recenzije
     * @param reviewDate datum napisane recenzije
     */
    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    /**
     * Vraća korisnika koji je napisao recenziju
     * @return Korisnik koji je napisao recenziju
     */
    public User getUser() {
        return user;
    }

    /**
     * Postavlja korisnika koji je napisao recenziju
     * @param user Korisnik koji je napisao recenziju
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Vraća proizvod za koji je napisana recenzija
     * @return proizvod za koji je napisana recenzija
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Postavlja proizvod za koji je napisana recenzija
     * @param product proizvod za koji je napisana recenzija
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Vraća sve podatke o recenziji u jednom stringu
     * @return String sa svim podacima o recenziji
     */
    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rating=" + rating +
                ", reviewText='" + reviewText + '\'' +
                ", reviewDate=" + reviewDate +
                ", user=" + user +
                ", product=" + product +
                '}';
    }
}
