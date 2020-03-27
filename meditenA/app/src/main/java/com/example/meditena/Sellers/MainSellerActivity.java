package com.example.meditena.Sellers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meditena.Admin.AddNewProductsAdminActivity;
import com.example.meditena.Admin.AdminAddCategoryActivity;
import com.example.meditena.Admin.CostumersAdminActivity;
import com.example.meditena.Admin.DashboardActivity;
import com.example.meditena.Admin.OrdersAdminValidationActivity;
import com.example.meditena.Buyer.LoginActivity;
import com.example.meditena.Buyer.MainActivity;
import com.example.meditena.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainSellerActivity extends AppCompatActivity {

    private TextView txtDashboardSeller, txtProductsSeller, txtOrdersSeller, welcomeText;
    private ImageView imgDashboard, imgProducts, imgOrders;
    FirebaseAuth auth;
    DatabaseReference dataRefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_seller);

        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        welcomeText = (TextView)findViewById(R.id.txt_welcome_seller);
        logoutseller = (Button)findViewById(R.id.logout_button_seller);
        txtDashboardSeller = (TextView)findViewById(R.id.txt_dashboard_seller);
        txtProductsSeller = (TextView)findViewById(R.id.txt_products_seller);
        txtOrdersSeller = (TextView)findViewById(R.id.txt_orders_admin);
        imgDashboard = (ImageView) findViewById(R.id.dashboard_icon);
        imgProducts = (ImageView) findViewById(R.id.products_icon);
        imgOrders = (ImageView)findViewById(R.id.orders_icon);
        final String uid = auth.getCurrentUser().getUid();
        dataRefs = FirebaseDatabase.getInstance().getReference().child("Sellers").child(uid);
        dataRefs.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                        String name = dataSnapshot.child("name").getValue().toString();
                        welcomeText.setText("Welcome " + name );

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        imgDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainSellerActivity.this, AddSellerProductInformationActivity.class);
                startActivity(intent);

            }
        });
        imgProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainSellerActivity.this, AddSellerProductTypeActivity.class);
                startActivity(intent);

            }
        });

        imgOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainSellerActivity.this, OrdersAdminValidationActivity.class);
                startActivity(intent);

            }
        });
        logoutseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainSellerActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }
}
