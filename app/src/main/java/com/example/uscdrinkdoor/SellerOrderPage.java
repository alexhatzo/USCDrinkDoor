package com.example.uscdrinkdoor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SellerOrderPage extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private static final String TAG = "OrderInfoPage";
    private TextView orderTitle;
    private TextView orderCompleted;
    private TextView orderName;
    private TextView orderAddress;
    private TextView orderPhone;
    private TextView orderEmail;
    private TextView orderTime;
    private TextView orderTotal;


    private ListView listview;

    String orderID;

    Context context = this;

    ArrayList<orderItem> order = new ArrayList<orderItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_order_page);

        String currentEmail = mAuth.getCurrentUser().getEmail();
        orderID = getIntent().getStringExtra("id");


        orderTitle = findViewById(R.id.orderTitle);
        orderCompleted = findViewById(R.id.orderCompleted);
        orderName = findViewById(R.id.orderCustomer);
        orderAddress = findViewById(R.id.orderAddress);
        orderPhone = findViewById(R.id.orderPhone);
        orderEmail = findViewById(R.id.orderEmail);
        orderTime = findViewById(R.id.orderTime);
        orderTotal = findViewById(R.id.orderTotal);

        orderTitle.append(" " + orderID);

        listview = findViewById(R.id.orderLv);

        DocumentReference docRef =  db.collection("users").document(currentEmail).collection("Orders").document(orderID);
                docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d(TAG, "DocumentSnapshot data: " + documentSnapshot.getData());

                        updateUI(documentSnapshot);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error reading document", e);

                    }
                });

                docRef.collection("Products")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                            order.add(new orderItem((String) document.get("Name"), (long)document.get("Price"), (String)document.get("description")));
                                        }
//

                                        OrderPageAdapter itemAdapter = new OrderPageAdapter(context, R.layout.cart_row, order);
//
                                        listview.setAdapter(itemAdapter);

                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                            })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

    }

    public void updateUI(DocumentSnapshot document){
        orderCompleted.append(document.get("Completed").toString());

        orderName.append(document.get("Customer Name").toString());
        orderAddress.append(document.get("Delivery Address").toString());
        orderPhone.append(document.get("Customer Phone").toString());
        orderEmail.append(document.get("Customer Email").toString());
        orderTotal.append(document.get("Total Amount").toString());
        orderTime.append(document.get("Time Ordered").toString());
    }
}