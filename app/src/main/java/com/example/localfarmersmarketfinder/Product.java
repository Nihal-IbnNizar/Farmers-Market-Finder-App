package com.example.localfarmersmarketfinder;

public class Product {
    private int id;
    private int farmerId;
    private String name;
    private int quantity;
    private String unit;
    private double price;

    public Product() {
    }

    public Product(int farmerId, String name, int quantity, String unit, double price) {
        this.farmerId = farmerId;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(int farmerId) {
        this.farmerId = farmerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " - " + quantity + " " + unit + " at Rs." + price + " per/" + unit;
    }
}
