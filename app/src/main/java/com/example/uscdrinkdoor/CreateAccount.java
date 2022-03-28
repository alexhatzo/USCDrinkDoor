package com.example.uscdrinkdoor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
    private EditText birthday;

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
        birthday = findViewById(R.id.birthday);


        storeAccount = findViewById(R.id.storeSwitch);

        saveBtn = findViewById(R.id.saveInfo);


        //add location lat and long using google geo API

        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String ns = name.getText().toString();
                String zs =  zip.getText().toString();
                String ps = phone.getText().toString();
                String bs = birthday.getText().toString();
                String as = address.getText().toString();

                LatLng l = null;
                try {
                    l = getCoordinates(as);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Map<String, Object> user = new HashMap<>();
                user.put("emailAddress", currentUser.getEmail());
                user.put("name", ns );
                user.put("address", as);
                user.put("zip", zs);
                user.put("phone", ps);
                user.put("birthday", bs);
                user.put("store", storeAccount.isChecked());
                user.put("coordinates", l);
                Log.d(TAG, "onClick: " + ns + as + zs+ ps);

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
    public LatLng getCoordinates(String s) throws IOException {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList;
        addressList = geocoder.getFromLocationName(s,1);
        LatLng coordinates = new LatLng(addressList.get(0).getLatitude(),addressList.get(0).getLongitude());
        return coordinates;
    }

    public void updateUI(){
        Intent maps = new Intent(CreateAccount.this, MapsActivity.class).putExtra("store",storeAccount.isChecked());
        startActivity(maps);

    }
}
