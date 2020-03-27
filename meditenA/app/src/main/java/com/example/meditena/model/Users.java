package com.example.meditena.model;

public class Users {
    private String name, image, email, address;

    private Users(){

    }
    public Users(String name, String image, String email, String address) {
        this.name = name;
        this.image = image;
        this.email = email;
        this.address = address;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
