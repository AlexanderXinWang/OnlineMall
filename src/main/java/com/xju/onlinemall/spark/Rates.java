package com.xju.onlinemall.spark;

import java.io.Serializable;

public class Rates implements Serializable {
    private Integer user;
    private Integer product;
    private Double rating;

    public Rates(Integer user, Integer product, Double rating) {
        this.user = user;
        this.product = product;
        this.rating = rating;
    }

    public Rates() {
    }

    @Override
    public String toString() {
        return user +
                "\t" + product +
                "\t" + rating ;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getProduct() {
        return product;
    }

    public void setProduct(Integer product) {
        this.product = product;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
