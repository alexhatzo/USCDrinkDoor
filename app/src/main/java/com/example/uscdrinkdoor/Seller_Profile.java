package com.example.uscdrinkdoor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class Seller_Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile);
    }

    public void clickAccount(View view) {
        Intent intent = new Intent(this, Seller_Profile.class);
        startActivity(intent);
    }

    public void clickMenu(View view) {
        Intent intent = new Intent(this, SellerMenu.class);
        startActivity(intent);
    }

    public void clickHome(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}