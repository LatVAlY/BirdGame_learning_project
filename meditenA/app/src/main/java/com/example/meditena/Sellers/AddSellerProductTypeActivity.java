package com.example.meditena.Sellers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.meditena.R;
import com.google.firebase.auth.FirebaseAuth;

public class AddSellerProductTypeActivity extends AppCompatActivity {
    private ImageView tSchirts, sportTShirts, femaleDresses, sweathers;
    private ImageView glasses, hatsCaps, walletsBegsPurses, shoes;
    private ImageView headPhones, laptopsPc, mobilePhones, watchs;
    private Button checkNewOrder, logoutbtn;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_seller_product_type);
        tSchirts = (ImageView)findViewById(R.id.t_shirt_seller);
        sportTShirts = (ImageView)findViewById(R.id.sport_tshirt_seller);
        femaleDresses = (ImageView)findViewById(R.id.femal_dresses_seller);
        sweathers = (ImageView)findViewById(R.id.sweathers_seller);
        glasses = (ImageView)findViewById(R.id.glasses_seller);
        hatsCaps = (ImageView)findViewById(R.id.hats_seller);
        walletsBegsPurses = (ImageView)findViewById(R.id.purs_begs_wallets_seller);
        shoes = (ImageView)findViewById(R.id.shoess_seller);
        headPhones = (ImageView)findViewById(R.id.head_phones_seller);
        laptopsPc = (ImageView)findViewById(R.id.laptops_pc_seller);
        mobilePhones = (ImageView)findViewById(R.id.mobile_phones_seller);
        watchs = (ImageView)findViewById(R.id.watchs_seller);
        checkNewOrder = (Button)findViewById(R.id.check_new_order_seller);
        logoutbtn = (Button)findViewById(R.id.logoutbuton_seller);

        tSchirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSellerProductTypeActivity.this, AddSellerProductInformationActivity.class);
                intent.putExtra("category", "tSchirts");
                startActivity(intent);

            }
        });
        sportTShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSellerProductTypeActivity.this, AddSellerProductInformationActivity.class);
                intent.putExtra("category", "Sport tshirts");
                startActivity(intent);

            }
        });
        femaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSellerProductTypeActivity.this, AddSellerProductInformationActivity.class);
                intent.putExtra("category", "Female Dresses");
                startActivity(intent);
            }
        });
        sweathers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSellerProductTypeActivity.this, AddSellerProductInformationActivity.class);
                intent.putExtra("category", "Sweathers");
                startActivity(intent);
            }
        });
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSellerProductTypeActivity.this, AddSellerProductInformationActivity.class);
                intent.putExtra("category", "Glasses");
                startActivity(intent);
            }
        });
        hatsCaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSellerProductTypeActivity.this, AddSellerProductInformationActivity.class);
                intent.putExtra("category", "Hats Caps");
                startActivity(intent);
            }
        });
        walletsBegsPurses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSellerProductTypeActivity.this, AddSellerProductInformationActivity.class);
                intent.putExtra("category", "Wallets Bags Purses");
                startActivity(intent);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSellerProductTypeActivity.this, AddSellerProductInformationActivity.class);
                intent.putExtra("category", "Shoes");
                startActivity(intent);
            }
        });
        headPhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSellerProductTypeActivity.this, AddSellerProductInformationActivity.class);
                intent.putExtra("category", "HeadPhone");
                startActivity(intent);
            }
        });
        laptopsPc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSellerProductTypeActivity.this, AddSellerProductInformationActivity.class);
                intent.putExtra("category", "Laptop");
                startActivity(intent);
            }
        });
        watchs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSellerProductTypeActivity.this, AddSellerProductInformationActivity.class);
                intent.putExtra("category", "Watches");
                startActivity(intent);
            }
        });
        mobilePhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSellerProductTypeActivity.this, AddSellerProductInformationActivity.class);
                intent.putExtra("category", "Mobile Phones");
                startActivity(intent);
            }
        });
        checkNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSellerProductTypeActivity.this, AddSellerProductInformationActivity.class);
                startActivity(intent);
            }
        });
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AddSellerProductTypeActivity.this, LoginSellerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
