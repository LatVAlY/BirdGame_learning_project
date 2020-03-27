package com.example.meditena.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.meditena.Buyer.LoginActivity;
import com.example.meditena.R;
import com.example.meditena.ViewHolder.ProductViewHolder;
import com.example.meditena.model.Products;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AddNewProductsAdminActivity extends AppCompatActivity {
    private ImageView tSchirts, sportTShirts, femaleDresses, sweathers;
    private ImageView glasses, hatsCaps, walletsBegsPurses, shoes;
    private ImageView headPhones, laptopsPc, mobilePhones, watchs, category;
    private Button checkNewOrder, logoutbtn;
    private DatabaseReference ProductsRef;
    private  RecyclerView recyclerView;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_new_products_admin);
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
        checkNewOrder = (Button)findViewById(R.id.check_new_order);
        logoutbtn = (Button)findViewById(R.id.logoutbuton);
        category = (ImageView)findViewById(R.id.Category);

        //...............................firebase..................

        auth = FirebaseAuth.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.recyler_admin);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        final String uid = auth.getCurrentUser().getUid();
        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(ProductsRef, Products.class)
                .build();
        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final ProductViewHolder holder, final int position, @NonNull final Products model) {
                        holder.textProductName.setText(model.getPname());
                        holder.textProductDescription.setText(model.getDescription());
                        holder.textProductPrice.setText(model.getPrice()+"€");
                        holder.textProductDiscount.setText(model.getDiscount()+"% off");
                        Picasso.get().load(model.getImage()).into(holder.imageView);
                        holder.textProductDiscountPrice.setText(model.getDPrice()+"€");
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[] = new CharSequence[]{
                                        "Edit",
                                        "Remove"
                                };
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddNewProductsAdminActivity.this);
                                builder.setTitle("Cart Option: ");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        if (i == 0){
                                            Intent intent = new Intent (AddNewProductsAdminActivity.this, AdminAddProductActivity.class);
                                            intent.putExtra("pid", model.getPid());
                                            startActivity(intent);
                                        }
                                        if (i == 1 ){
                                            ProductsRef.child(model.getPid())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()){
                                                                Toast.makeText(AddNewProductsAdminActivity.this, "Item removed successfully", Toast.LENGTH_SHORT).show();

                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });
                                builder.show();

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        //..............................end........................

        tSchirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewProductsAdminActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category", "Men Dresses");
                startActivity(intent);

            }
        });
        sportTShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewProductsAdminActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category", "Sport tshirts");
                startActivity(intent);

            }
        });
        femaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewProductsAdminActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category", "Female Dresses");
                startActivity(intent);
            }
        });
        sweathers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewProductsAdminActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category", "Sweathers");
                startActivity(intent);
            }
        });
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewProductsAdminActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category", "Glasses");
                startActivity(intent);
            }
        });
        hatsCaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewProductsAdminActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category", "Hats Caps");
                startActivity(intent);
            }
        });
        walletsBegsPurses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewProductsAdminActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category", "Wallets Bags Purses");
                startActivity(intent);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewProductsAdminActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category", "Shoes");
                startActivity(intent);
            }
        });
        headPhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewProductsAdminActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category", "Headphones");
                startActivity(intent);
            }
        });
        laptopsPc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewProductsAdminActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category", "Laptop");
                startActivity(intent);
            }
        });
        watchs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewProductsAdminActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category", "Watches");
                startActivity(intent);
            }
        });
        mobilePhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewProductsAdminActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category", "Phones");
                startActivity(intent);
            }
        });
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewProductsAdminActivity.this, AdminCreatCategoryActivity.class);
                startActivity(intent);

            }
        });
        checkNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewProductsAdminActivity.this, OrdersAdminValidationActivity.class);
                startActivity(intent);
            }
        });
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AddNewProductsAdminActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
