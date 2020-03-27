package com.example.meditena.model;

public class Products {
    private  String pname, description, price, image, category, pid, date, time, discount, DPrice, pnameSearch;


    public Products(){

    }



    public Products(String pname, String description, String price, String image, String category,
                    String pid, String date, String time, String discount, String DPrice, String pnameSearch) {
        this.pname = pname;
        this.description = description;
        this.price = price;
        this.DPrice = DPrice;
        this.image = image;
        this.category = category;
        this.pid = pid;
        this.date = date;
        this.time = time;
        this.discount = discount;
        this.pnameSearch = pnameSearch;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public  String getPid() {
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

    public String getDiscount() { return discount; }

    public void setDiscount(String discount) { this.discount = discount; }

    public String getDPrice() { return DPrice; }

    public void setDPrice(String DPrice) { this.DPrice = DPrice; }

    public String getPnameSearch() {return pnameSearch;}

    public void setPnameSearch(String pnameSearch){this.pnameSearch= pnameSearch;}
}

