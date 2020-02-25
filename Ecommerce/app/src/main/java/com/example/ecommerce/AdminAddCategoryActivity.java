package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminAddCategoryActivity extends AppCompatActivity {
    private ImageView tSchirts, sportTShirts, femaleDresses, sweathers;
    private ImageView glasses, hatsCaps, walletsBegsPurses, shoes;
    private ImageView headPhones, laptopsPc, mobilePhones, watchs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_category);
        tSchirts = (ImageView)findViewById(R.id.t_shirt);
        sportTShirts = (ImageView)findViewById(R.id.sport_tshirt);
        femaleDresses = (ImageView)findViewById(R.id.femal_dresses);
        sweathers = (ImageView)findViewById(R.id.sweathers);
        glasses = (ImageView)findViewById(R.id.glasses);
        hatsCaps = (ImageView)findViewById(R.id.hats);
        walletsBegsPurses = (ImageView)findViewById(R.id.purs_begs_wallets);
        shoes = (ImageView)findViewById(R.id.shoess);
        headPhones = (ImageView)findViewById(R.id.head_phones);
        laptopsPc = (ImageView)findViewById(R.id.laptops_pc);
        mobilePhones = (ImageView)findViewById(R.id.mobile_phones);
        watchs = (ImageView)findViewById(R.id.watchs);

        tSchirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddCategoryActivity.this, AdminProductActivity.class);
                intent.putExtra("category", "tSchirts");
                startActivity(intent);

            }
        });
        sportTShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddCategoryActivity.this, AdminProductActivity.class);
                intent.putExtra("category", "Sport tshirts");
                startActivity(intent);

            }
        });
        femaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddCategoryActivity.this, AdminProductActivity.class);
                intent.putExtra("category", "Female Dresses");
                startActivity(intent);
            }
        });
        sweathers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddCategoryActivity.this, AdminProductActivity.class);
                intent.putExtra("category", "Sweathers");
                startActivity(intent);
            }
        });
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddCategoryActivity.this, AdminProductActivity.class);
                intent.putExtra("category", "Glasses");
                startActivity(intent);
            }
        });
        hatsCaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddCategoryActivity.this, AdminProductActivity.class);
                intent.putExtra("category", "Hats Caps");
                startActivity(intent);
            }
        });
        walletsBegsPurses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddCategoryActivity.this, AdminProductActivity.class);
                intent.putExtra("category", "Wallets Bags Purses");
                startActivity(intent);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddCategoryActivity.this, AdminProductActivity.class);
                intent.putExtra("category", "Shoes");
                startActivity(intent);
            }
        });
        headPhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddCategoryActivity.this, AdminProductActivity.class);
                intent.putExtra("category", "HeadPhone");
                startActivity(intent);
            }
        });
        laptopsPc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddCategoryActivity.this, AdminProductActivity.class);
                intent.putExtra("category", "Laptop");
                startActivity(intent);
            }
        });
        watchs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddCategoryActivity.this, AdminProductActivity.class);
                intent.putExtra("category", "Watches");
                startActivity(intent);
            }
        });
        mobilePhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddCategoryActivity.this, AdminProductActivity.class);
                intent.putExtra("category", "Mobile Phones");
                startActivity(intent);
            }
        });
    }
}
