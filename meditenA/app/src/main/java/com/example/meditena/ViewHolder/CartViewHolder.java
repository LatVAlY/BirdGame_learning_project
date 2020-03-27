package com.example.meditena.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meditena.Interface.ItemClickListener;
import com.example.meditena.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductName, txtProductPrice, txtProductQuantity;
    public ItemClickListener itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtProductPrice = itemView.findViewById(R.id.cart_product_Price);
        txtProductQuantity = itemView.findViewById(R.id.cart_product_Quantity);
    }

    @Override
    public void onClick(View v) {

        itemClickListner.Onclick(v, getAdapterPosition(), false);
    }

    public void setItemClickListner(ItemClickListener itemClickListner) {
        this.itemClickListner = itemClickListner;

    }
}
