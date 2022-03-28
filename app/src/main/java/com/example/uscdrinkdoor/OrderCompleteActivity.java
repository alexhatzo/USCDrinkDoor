package com.example.uscdrinkdoor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OrderCompleteActivity extends AppCompatActivity {
    ListView listview;

    final Context context = this;

    private static final String TAG = "ShoppingCartActivity";

    ArrayList<Item> cart = new ArrayList<Item>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String emailAddress = currentUser.getEmail();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete);

        listview = findViewById(R.id.listView);


        db.collection("users").document(emailAddress).collection("Cart")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                cart.add(new Item((String) document.get("Name"), (String)document.get("description"), (long) document.get("Price"), (long) document.get("Caffeine"), (String) document.get("Email")));
                            }

                            ItemAdapter itemAdapter = new ItemAdapter(context, R.layout.cart_row, cart);

                            listview.setAdapter(itemAdapter);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }

                });

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
