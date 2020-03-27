package com.example.meditena;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.meditena.Admin.AdminAddCategoryActivity;
import com.example.meditena.Buyer.JoinActivity;
import com.example.meditena.Buyer.MainActivity;
import com.example.meditena.Sellers.MainSellerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends Application {
    private DatabaseReference userRefs;
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        userRefs = FirebaseDatabase.getInstance().getReference();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            final String uid = auth.getCurrentUser().getUid();
            userRefs.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("Users").child(uid).exists()){
                        Intent i = new Intent(Home.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    } else if(dataSnapshot.child("Admins").child(uid).exists()){
                        Toast.makeText(Home.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Home.this, AdminAddCategoryActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    else if(dataSnapshot.child("Sellers").child(uid).exists()){
                        Toast.makeText(Home.this, "Welcome Seller", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Home.this, MainSellerActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }
}