package com.example.meditena;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.meditena.model.Products;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class CustomSwipAdapeter extends PagerAdapter {
    private int[] imageResources = {R.drawable.watch, R.drawable.iphone, R.drawable.asset_1, R.drawable.young_dress};
    private Context ctx;
    private LayoutInflater layoutInflater;
    private DatabaseReference Refs;

    public CustomSwipAdapeter(Context ctx){
        this.ctx = ctx;
    }
    @Override
    public int getCount() {
        return imageResources.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view ==(LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
       layoutInflater =(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View item_view = layoutInflater.inflate(R.layout.swap_layout, container, false);
        ImageView  imageView = (ImageView)item_view.findViewById(R.id.image_view);
        TextView textView = (TextView)item_view.findViewById(R.id.image_count);
        imageView.setImageResource(imageResources[position]);
        textView.setText("Image: "+ position);
        container.addView(item_view);
        return item_view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
