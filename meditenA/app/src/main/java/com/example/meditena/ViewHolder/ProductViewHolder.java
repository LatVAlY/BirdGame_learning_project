package com.example.meditena.ViewHolder;

import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meditena.Interface.ItemClickListener;
import com.example.meditena.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView imageView, favoriteImage;
    public TextView textProductName, textProductDescription, textProductPrice, textProductDiscount,textProductDiscountPrice;
    public ItemClickListener listener;


    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.product_image_display);
        textProductName = (TextView) itemView.findViewById(R.id.pname_display);
        textProductDescription = (TextView)itemView.findViewById(R.id.product_description_display);
        textProductDiscount = (TextView)itemView.findViewById(R.id.discount_display);
        textProductPrice = (TextView) itemView.findViewById(R.id.product_price_display);
        textProductDiscountPrice = (TextView) itemView.findViewById(R.id.product_price_discount_display);
        favoriteImage = (ImageView) itemView.findViewById(R.id.favorite_item_image);
        textProductPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        textProductName.setPaintFlags(textProductName.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {
        listener.Onclick(v, getAdapterPosition(), false);

    }
}
