package com.example.uscdrinkdoor;

import com.example.uscdrinkdoor.Item;

import java.util.ArrayList;
import java.util.List;

public class Order {

    public String Status; // "Active" or "Past"
    private int OrderID;
    private String userEmail;
    private String sellerEmail;
    private List<Item> items = new ArrayList<Item>();;

    public Order(String userEmail, String sellerEmail, List<Item> items){
        this.userEmail = userEmail;
        this.sellerEmail = sellerEmail;
        this.items = items;
    }

    public void Display_Order(){}

    public void Update_Status(){}

    public int Get_Order_ID(){ return this.OrderID; }

    public String Get_User_ID(){ return this.userEmail; }

    public String Get_Seller_ID(){ return this.sellerEmail; }

    public List<Item> Get_Items(){
        return new ArrayList<>();
    }


}