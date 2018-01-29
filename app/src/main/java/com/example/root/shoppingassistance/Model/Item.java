package com.example.root.shoppingassistance.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vibodha on 1/29/18.
 */

public class Item {

    private  String category;
    private Shop shop;
    private double price;
    private Date dateOfPurchace;
    private List<Attribute> attributes = new ArrayList<Attribute>();

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Date getDateOfPurchace() {
        return dateOfPurchace;
    }

    public void setDateOfPurchace(Date dateOfPurchace) {
        this.dateOfPurchace = dateOfPurchace;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void addAttribute(Attribute attribute){
        attributes.add(attribute);
    }
//dasdasdad
}
