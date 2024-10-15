package com.example.localfarmersmarketfinder;

import java.util.List;
import java.util.ArrayList;

public class Market {
    private int id;
    private String name;
    private String address;
    private String operatingHours;
    private List<Farmer> farmers;
    private List<Product> products;

    // Default constructor
    public Market() {
        farmers = new ArrayList<>();
        products = new ArrayList<>();
    }

    // Constructor with id
    public Market(int id, String name, String address, String operatingHours) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.operatingHours = operatingHours;
        farmers = new ArrayList<>();
        products = new ArrayList<>();
    }

    // Constructor without id (for creating new markets)
    public Market(String name, String address, String operatingHours) {
        this.name = name;
        this.address = address;
        this.operatingHours = operatingHours;
        farmers = new ArrayList<>();
        products = new ArrayList<>();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
    }

    public List<Farmer> getFarmers() {
        return farmers;
    }

    public void setFarmers(List<Farmer> farmers) {
        this.farmers = farmers;
    }

    public void addFarmer(Farmer farmer) {
        this.farmers.add(farmer);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    @Override
    public String toString() {
        return "Market{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", operatingHours='" + operatingHours + '\'' +
                ", farmers=" + farmers.size() +
                ", products=" + products.size() +
                '}';
    }
}
