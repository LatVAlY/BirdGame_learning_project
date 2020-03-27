package com.example.meditena.Admin;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meditena.Buyer.MainActivity;
import com.example.meditena.R;
import com.google.firebase.auth.FirebaseAuth;

public class AdminAddCategoryActivity extends FragmentActivity {

    private Button logout;
    private TextView txtDashboard, txtProducts, txtCostumers, txtOrders;
    private ImageView imgDashboard, imgProducts, imgCostumers, imgOrders;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_category);

        auth = FirebaseAuth.getInstance();
        logout = (Button)findViewById(R.id.logout_button_admin);
        txtDashboard = (TextView)findViewById(R.id.txt_dashboard_admin);
        txtProducts = (TextView)findViewById(R.id.txt_products_admin);
        txtCostumers = (TextView)findViewById(R.id.txt_costumers_admin);
        txtOrders = (TextView)findViewById(R.id.txt_orders_admin);
        imgDashboard = (ImageView) findViewById(R.id.dashboard_icon);
        imgProducts = (ImageView) findViewById(R.id.products_icon);
        imgCostumers = (ImageView)findViewById(R.id.costumers_icon);
        imgOrders = (ImageView)findViewById(R.id.orders_icon);


        txtDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddCategoryActivity.this, DashboardActivity.class);
                startActivity(intent);

            }
        });
        txtProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddCategoryActivity.this, AddNewProductsAdminActivity.class);
                startActivity(intent);

            }
        });
        txtCostumers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddCategoryActivity.this, CostumersAdminActivity.class);
                startActivity(intent);

            }
        });
        txtOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddCategoryActivity.this, OrdersAdminValidationActivity.class);
                startActivity(intent);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent (AdminAddCategoryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }
}

