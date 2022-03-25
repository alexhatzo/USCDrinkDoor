package com.example.uscdrinkdoor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
    private Button userBirthday;
    private Button userCart;
    private Button userLogoff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseUser currentUser = mAuth.getCurrentUser();

        userName = findViewById(R.id.sellerName);
        userAddress = findViewById(R.id.sellerAddress);
        userEmail  = findViewById(R.id.userEmail);
        userOrder  = findViewById(R.id.userOrder);
        userCaffeine = findViewById(R.id.userCaffeine);
        userBirthday  = findViewById(R.id.userBirthday);
        userCart  = findViewById(R.id.userCart);
        userLogoff  = findViewById(R.id.sellerLogoff) ;



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        DocumentReference docRef =  db.collection("users").document(currentUser.getEmail());
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
    }
    public void updateUI(DocumentSnapshot document){
        userAddress.setText((String)document.get("address"));
        userName.setText((String)document.get("name"));
        userEmail.setText((String)document.get("emailAddress"));
        userEmail.setText((String)document.get("Birthday"));
        userEmail.setText((String)document.get("caffeine"));
        //add phone number
    }

}
