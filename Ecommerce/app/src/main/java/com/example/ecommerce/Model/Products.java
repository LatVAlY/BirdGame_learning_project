package com.example.ecommerce.Model;

public class Products {
    private String pname, description, price, image, catgegory, pid, date, time;
    public Products(){

    }

    public Products(String pname, String description, String price, String image, String catgegory,
                    String pid, String date, String time) {
        this.pname = pname;
        this.description = description;
        this.price = price;
        this.image = image;
        this.catgegory = catgegory;
        this.pid = pid;
        this.date = date;
        this.time = time;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCatgegory() {
        return catgegory;
    }

    public void setCatgegory(String catgegory) {
        this.catgegory = catgegory;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pide) {
        this.pid = pide;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
