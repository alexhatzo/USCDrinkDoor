package com.example.uscdrinkdoor;

public class Item {
    String itemName;
    String description;
    long price;
    long caffeineMg;
    String sellerEmail;

    public Item(String itemName, String description, long price, long caffeineMg, String email) {
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.caffeineMg = caffeineMg;
        this.sellerEmail = email;
    }

    //getters
    public String getName() { return itemName; }

    public String getDescription() { return description; }

    public long getPrice() { return price; }

    public long getCaffeine() { return caffeineMg; }

    public String getSellerEmail() { return this.sellerEmail; }

    //setters
    public void changeName() {}

    public void changeDescription() {}

    public void changePrice() {}




}