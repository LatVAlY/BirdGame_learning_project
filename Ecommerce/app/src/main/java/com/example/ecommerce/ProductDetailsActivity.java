package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ecommerce.Model.Products;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AppCompatActivity {
    private FloatingActionButton addToCart;
    private ImageView productImage;
    private ElegantNumberButton counterButton;
    private TextView productDescription, productPrice, productName;
    private String productID = "";
    private TextView closeTextbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productID = getIntent().getStringExtra("pid");

        addToCart = (FloatingActionButton)findViewById(R.id.add_product_to_cart);
        productImage = (ImageView)findViewById(R.id.product_image_details);
        counterButton = (ElegantNumberButton)findViewById(R.id.elegant_number_products);
        productDescription = (TextView)findViewById(R.id.product_description_details);
        productName = (TextView)findViewById(R.id.product_name_details);
        productPrice = (TextView)findViewById(R.id.product_price_details);
        closeTextbtn = (TextView)findViewById(R.id.close_account_settings);

        closeTextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getProductDetails(productID);
    }

    private void getProductDetails(final String productID) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //verifying if the data exits in the database
                if (dataSnapshot.exists()){
                    Products products = dataSnapshot.getValue(Products.class);
                    productName.setText(products.getPname());
                    productDescription.setText(products.getDescription());
                    productPrice.setText(products.getPrice());
                    Picasso.get().load(products.getImage()).into(productImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
