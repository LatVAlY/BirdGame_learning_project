package com.example.meditena.model;

public class Cart {
    private String pid, pname, date, time, totalAmount, quantity, discount, price;

    private Cart(){

    }

    public Cart(String pid, String pname, String date, String time, String totalAmount, String quantity, String discount, String price) {
        this.pid = pid;
        this.pname = pname;
        this.date = date;
        this.time = time;
        this.totalAmount = totalAmount;
        this.quantity = quantity;
        this.discount = discount;
        this.price = price;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
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

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
