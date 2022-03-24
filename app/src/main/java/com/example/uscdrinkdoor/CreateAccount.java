package com.example.uscdrinkdoor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private static final String TAG = "CreateAccount";

    private EditText emailAddress;
    private EditText name;
    private EditText address;
    private EditText zip;
    private EditText phone;

    private Switch storeAccount;

    private Button saveBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        emailAddress = findViewById(R.id.emailAddress);

        emailAddress.setText(currentUser.getEmail());

        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        zip = findViewById(R.id.zip);
        phone = findViewById(R.id.phoneNumber);



        storeAccount = findViewById(R.id.storeSwitch);

        saveBtn = findViewById(R.id.saveInfo);



        Map<String, Object> user = new HashMap<>();
        user.put("emailAddress", currentUser.getEmail());
        user.put("name", name.getText().toString());
        user.put("address", address.getText().toString());
        user.put("zip", zip.getText().toString());
        user.put("phone", phone.getText().toString());
        user.put("store", storeAccount.isChecked());
        //add location lat and long using google geo API

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("users").document(emailAddress.getText().toString())
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                updateUI();
                            }

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });
            }
        });


    }

    public void updateUI(){
        Intent maps = new Intent(CreateAccount.this, MapsActivity.class).putExtra("store",storeAccount.isChecked());
        startActivity(maps);

    }
}
