package com.example.meditena.Sellers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.meditena.CustomSwipAdapeter;
import com.example.meditena.R;
import com.example.meditena.ViewHolder.CategoryViewHolder;
import com.example.meditena.model.Products;
import com.example.meditena.ui.ProductsDetails.ProductsDetailsViewModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class AddSellerProductInformationActivity extends AppCompatActivity {

    ViewPager viewPager;
    CustomSwipAdapeter adapeter;
    private Button addBtn;
    private ImageView imageView;
    private static final int GalleryPick = 1;
    private Uri imageUri;
    private String search = "";
    private  RecyclerView recyclerViewCategory;
    private DatabaseReference CategoryRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_seller_product_information);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        adapeter = new CustomSwipAdapeter(this);
        viewPager.setAdapter(adapeter);
        imageView = (ImageView)findViewById(R.id.image_view);
        addBtn = (Button)findViewById(R.id.add_picture_btn);

        recyclerViewCategory = (RecyclerView) findViewById(R.id.image_slider_recycler);
        RecyclerView.LayoutManager layoutManager1;
        layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategory.setLayoutManager(layoutManager1);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewCategory);

        CategoryRef = FirebaseDatabase.getInstance().getReference().child("categories");
        FirebaseRecyclerOptions<Products> options1 = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(CategoryRef, Products.class)
                .build();
        FirebaseRecyclerAdapter<Products, CategoryViewHolder> adapter1 = new
                FirebaseRecyclerAdapter<Products, CategoryViewHolder>(options1) {
                    @Override
                    protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull final Products model) {
                        Picasso.get().load(model.getImage()).into(holder.imageCircleCategory);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                search = model.getCategory();
                                onStart();
                                Toast.makeText(AddSellerProductInformationActivity.this, search +"", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imagelayout, parent, false);
                        CategoryViewHolder holder = new CategoryViewHolder(view);
                        return holder;
                    }
                };
        recyclerViewCategory.setAdapter(adapter1);
        adapter1.startListening();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.getCurrentItem();
                Toast.makeText(AddSellerProductInformationActivity.this, "this is the current " +
                        "Image " + viewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
