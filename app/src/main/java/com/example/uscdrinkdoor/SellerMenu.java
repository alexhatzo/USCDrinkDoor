package com.example.uscdrinkdoor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SellerMenu extends AppCompatActivity implements ItemAdapter.ItemClickListener{

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private static final String TAG = "SellerMenu";
    boolean storeAccount;

    ListView listview;

    final Context context = this;

    //instantiate list for the seller.menu for layout
    ArrayList<Item> menu = new ArrayList<Item>();
    String emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        emailAddress = getIntent().getStringExtra("email");
        String currentEmail = currentUser.getEmail();

        if(emailAddress == null){
            emailAddress = currentEmail;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        listview = findViewById(R.id.listView);

        Button checkout = findViewById(R.id.Checkout);


        DocumentReference docRef = db.collection("users").document(emailAddress);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        storeAccount = (boolean) document.get("store");

                        if (storeAccount){
                            checkout.setText("Add Product");
                            if(!currentEmail.equals(emailAddress)){
                                checkout.setVisibility(View.GONE);
                            }
                        }
                        else{
                            checkout.setText("Checkout");
                        }

                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }


            }
        });

        //need to receive intent for which seller id and user id


        //instantiate list for the seller.menu for layout
        //ArrayList<Item> menu = new ArrayList<Item>();

        //MUST ACCESS DB THROUGH EMAIL FROM INTENT! USER ALSO HAS EMAIL BUT NO MENU
        //THIS MUST BE SOLVED WHEN WE FIX THE MAP STORES
        db.collection("users").document(emailAddress).collection("Menu")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                menu.add(new Item((String) document.get("Name"), (String)document.get("description"), (long) document.get("Price"), (long) document.get("Caffeine"), (String) document.get("Email")));
                            }

                            ItemAdapter itemAdapter = new ItemAdapter(context, R.layout.menu_row, menu);

                            listview.setAdapter(itemAdapter);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }

                });


            checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;

                    if(storeAccount){
                        intent = new Intent(SellerMenu.this, AddProductToMenu.class);
                    }else{
                        intent = new Intent(SellerMenu.this, ShoppingCartActivity.class);
                    }
                    startActivity(intent);

                }
            });

    }

    @Override
    public void onAddToCartClick(String name, String userEmail) {
        Item toAdd = null;

        for (int i=0; i<menu.size(); i++){
            if (menu.get(i).getName().equals(name)){
                toAdd = menu.get(i);
            }
        }

        Map<String, Object> product = new HashMap<>();
        product.put("Name", toAdd.getName());
        product.put("Price", toAdd.getPrice() );
        product.put("Caffeine", toAdd.getCaffeine());
        product.put("description", toAdd.getDescription());
        product.put("Email", toAdd.getSellerEmail());


        //save new product to db
        db.collection("users").document(userEmail).collection("Cart").document(name)
                .set(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Product successfully added!");
                        Toast.makeText(SellerMenu.this, "Product added to cart! ", Toast.LENGTH_SHORT  ).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding product", e);

                    }
                });

    }
    @Override
    public void sendToEdit(String name) {
        Intent productEdit = new Intent(this, AddProductToMenu.class).putExtra("name", name);
        startActivity(productEdit);
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
