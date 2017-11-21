package com.example.tom.otgstore.model;

/**
 * Created by Mirna on 11/21/2017.
 */

public class ItemHistory {

    private String name;
    private String date;
    private int quantity;
    private double price;

    public ItemHistory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
