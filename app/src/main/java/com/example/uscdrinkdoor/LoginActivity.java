package com.example.uscdrinkdoor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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

public class LoginActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseAuth mAuth;

    private static final String TAG = "EmailPassword";

    private String dbUser = "Admin";
    private String dbPassword = "1234";

    private EditText email;
    private EditText password;

    private Button loginbtn;
    private Button createbtn;

    private TextView attempts;

    boolean isValid = false;

    int attemptCount = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        loginbtn = findViewById(R.id.btnlogin);
        createbtn = findViewById(R.id.createaccount);

        attempts = findViewById(R.id.attempts);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 String inputUsername = email.getText().toString();
                 String inputPassword = password.getText().toString();


                 if(inputUsername.isEmpty() || inputPassword.isEmpty() ){
                     Toast.makeText(LoginActivity.this, "Make sure you have filled both fields", Toast.LENGTH_SHORT  ).show();
                 }else{

                     signIn(inputUsername, inputPassword);


                 }

            }
        });

        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputUsername = email.getText().toString();
                String inputPassword = password.getText().toString();


                if(inputUsername.isEmpty() || inputPassword.isEmpty() ){
                    Toast.makeText(LoginActivity.this, "Make sure you have filled both fields", Toast.LENGTH_SHORT  ).show();
                }else{

                    createAccount(inputUsername, inputPassword);

                }

            }
        });

    }

    public void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //                             ||
                            //must pass in user for intent \/
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //null for refresh
                            updateUI();
                        }
                    }
                });
    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            isValid = true;
                            //must pass in user for intent \/
                            updateUI();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            //null for refresh
                            updateUI();
                        }
                    }
                });
    }

    public String authUser(String username, String password){
         String[] dbPassword2 = {""};

        DocumentReference docRef =  db.collection("users").document(username);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());

                        if(password.equals(document.get("password"))){
                            isValid = true;
                        }


                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
                updateUI();

            }



        });
            return dbPassword2[0];
    }

    public void updateUI(){
        if(!isValid && attemptCount>0){

            attemptCount --;
            attempts.setText("Incorrect attempt, remaining attempts:" + attemptCount);

        }else if(attemptCount ==0) {
            loginbtn.setEnabled(false);
            attempts.setText("Account has been locked. Reset your password." );

        }else{

            attempts.setText("Login successful! Redirecting");
            Intent homePage = new Intent(LoginActivity.this, Seller_Profile.class);
            startActivity(homePage);
            findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        }
    }


}