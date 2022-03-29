package com.example.uscdrinkdoor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class User_Profile extends AppCompatActivity{

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private Button userName;
    private Button userAddress;
    private Button userEmail;
    private Button userOrder;
    private Button userCaffeine;
    private Button userPhone;
    private Button userLogoff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        FirebaseUser currentUser = mAuth.getCurrentUser();

        userName = findViewById(R.id.userName);
        userAddress = findViewById(R.id.userAddress);
        userEmail  = findViewById(R.id.userEmail);
        userOrder  = findViewById(R.id.userOrder);
        userCaffeine = findViewById(R.id.userCaffeine);
        userLogoff  = findViewById(R.id.userLogoff) ;
        userPhone = findViewById(R.id.userPhone);

        DocumentReference docRef =  db.collection("users").document(currentUser.getEmail());

        //get general order data and display on screen
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        updateUI(document);

                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }


            }



        });
//       userOrder.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               Intent to orders
//               Intent orders = new Intent(User_Profile.this, User_order.class);
//                startActivity(orders);
//           }
//
//       });


//        userCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               Intent to cart
//               Intent cart = new Intent(User_Profile.this, cart.class);
//                startActivity(cart);
//            }
//        });
//
//        userLogoff.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mAuth.signOut();
//                Intent logOut = new Intent(User_Profile.this, LoginActivity.class);
//                startActivity(logOut);
//            }
//        });

    }
    public void updateUI(DocumentSnapshot document){
        userAddress.setText((String)document.get("address"));
        userName.setText((String)document.get("name"));
        userEmail.setText((String)document.get("emailAddress"));
        userPhone.setText((String)document.get("phone"));
        userCaffeine.setText("Caffeine Intake:" + (String) document.get("Caffeine"));

    }

    public void clickAccount(View view) {
        Intent intent = new Intent(this, User_Profile.class);
        startActivity(intent);
    }

    public void clickCart(View view) {
        Intent intent = new Intent(this, ShoppingCartActivity.class);
        startActivity(intent);
    }

    public void clickOrder(View view){
        Intent intent = new Intent(this, OrderCompleteActivity.class);
        startActivity(intent);
    }

    public void clickHome(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }


}
