package com.example.uscdrinkdoor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddProductToMenu extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private static final String TAG = "AddProduct";

    private EditText price;
    private EditText name;
    private EditText description;
    private EditText caffeine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_to_menu);

        name = findViewById(R.id.productName);
        price = findViewById(R.id.productPrice);
        caffeine = findViewById(R.id.productCaffeine);
        description = findViewById(R.id.productDescription);

        Button addProductbtn = findViewById(R.id.addProductbtn);

        addProductbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String ns = name.getText().toString();
               Integer ps = Integer.parseInt(name.getText().toString());
               Integer cs =Integer.parseInt(caffeine.getText().toString());
               String ds = description.getText().toString();

                if(ns.isEmpty() || ps==null || cs ==null || ds.isEmpty()){
                    Toast.makeText(AddProductToMenu.this, "Make sure you have filled all fields", Toast.LENGTH_SHORT  ).show();
                }else{
                    Map<String, Object> product = new HashMap<>();
                    product.put("Name", ns);
                    product.put("Price", ps );
                    product.put("Caffeine", cs);
                    product.put("description", ds);

                    Log.d(TAG, "onClick: " + ns + ps + cs+ ds);

                    db.collection("users").document(currentUser.getEmail()).collection("Menu").document(ns)
                            .set(product)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "Product successfully added!");
                                    updateUI();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding product", e);

                                }
                            });
                }

            }

        });
    }

    public void updateUI(){
        name.setText(" ");
        price.setText(" ");
        description.setText(" ");
        caffeine.setText(" ");
    }
}