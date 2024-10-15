package com.example.localfarmersmarketfinder;

public class Farmer {
    private int id;
    private String name;
    private String email;
    private String password;
    private String location;
    private String contactNumber;

    // Default constructor
    public Farmer() {
    }

    // Constructor with id
    public Farmer(int id, String name, String email, String password, String location, String contactNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.location = location;
        this.contactNumber = contactNumber;
    }

    // Constructor without id (for creating new farmers)
    public Farmer(String name, String email, String password, String location, String contactNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.location = location;
        this.contactNumber = contactNumber;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return "Farmer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
    }
}