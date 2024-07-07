package org.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cart {
    @JsonProperty("userId")
    private int userId;

    @JsonProperty("date")
    private String date;

    @JsonProperty("products")
    private List<CartItem> products;

    @JsonProperty("id")
    private int id;

    public Cart() {
    }

    public Cart(int userId, String date, List<CartItem> products, int id) {
        this.userId = userId;
        this.date = date;
        this.products = products;
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<CartItem> getProducts() {
        return products;
    }

    public void setProducts(List<CartItem> products) {
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "userId=" + userId +
                ", date='" + date + '\'' +
                ", products=" + products +
                ", id=" + id +
                '}';
    }
}
