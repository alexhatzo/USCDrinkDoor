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

public class SellerMenu extends AppCompatActivity implements ItemAdapter.ItemClickListener{

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private static final String TAG = "SellerMenu";
    boolean storeAccount;

    ListView listview;

    final Context context = this;

    //instantiate list for the seller.menu for layout
    ArrayList<Item> menu = new ArrayList<Item>();

    ShoppingCart cart = new ShoppingCart();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String emailAddress = currentUser.getEmail();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        listview = findViewById(R.id.listView);


        DocumentReference docRef = db.collection("users").document(emailAddress);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        storeAccount = (boolean) document.get("store");

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


        db.collection("users").document(emailAddress).collection("Menu")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                menu.add(new Item((String) document.get("Name"), (String)document.get("description"), (long)document.get("Price"), (long)document.get("Caffeine")));
                            }

                            ItemAdapter itemAdapter = new ItemAdapter(context, R.layout.menu_row, menu);

                            listview.setAdapter(itemAdapter);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }




                });


//        menu.add(new Item("Coffee", "Hot Caffeinated Coffee", 5, 50));
//        menu.add(new Item("Iced Coffee", "Iced Caffeinated Coffee", 5, 50));
//        menu.add(new Item("Tea", "Hot Green Tea", 3, 20));


        //create a shopping cart with user id, seller id, shopping cart id
            //add items to shopping cart
            //check out --> pass intent to shopping cart (save shopping cart to database so seller can use)

    }

    @Override
    public void onAddToCartClick(int id) {
        for (int i=0; i<menu.size(); i++){
            if (menu.get(i).getItemID() == id){
                cart.Add_Item(menu.get(i));
            }
        }

    }

    public void clickAccount(View view) {
        Intent intent = new Intent(this, Seller_Profile.class);
        startActivity(intent);
    }

    public void addProduct(View view) {
        Intent intent = new Intent(this, AddProductToMenu.class);
        startActivity(intent);
    }

    public void clickHome(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void clickCheckout(View view) {
        //if cart not empty
        Intent intent = new Intent(this, ShoppingCartActivity.class);
        startActivity(intent);
        //if cart is empty, need to display message
    }

}
