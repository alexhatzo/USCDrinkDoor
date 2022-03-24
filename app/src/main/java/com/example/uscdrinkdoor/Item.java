package com.example.uscdrinkdoor;

import java.util.*;

public class Item {
    String itemName;
    String description;
    int itemID;
    int price;
    int caffeineMg;
    int sellerID;

    public Item(String itemName, String description, int price, int caffeineMg) {
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.caffeineMg = caffeineMg;
    }

    //getters
    public String getName() { return itemName; }

    public String getDescription() { return description; }

    public int getItemID() { return this.itemID; }

    public int getPrice() { return price; }

    public int getCaffeine() { return caffeineMg; }

    public int getSellerID() { return this.sellerID; }

    //setters
    public void changeName() {}

    public void changeDescription() {}

    public void changePrice() {}




}