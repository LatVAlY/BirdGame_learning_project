package com.example.meditena.ViewHolder;

import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meditena.R;
import com.example.meditena.Interface.ItemClickListener;

public class ProductSearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView imageViewSearch;
    public TextView textProductName, textProductDescription, textProductPrice;
    public ItemClickListener listener;
    public ProductSearchViewHolder(@NonNull View itemView) {
        super(itemView);
        imageViewSearch = (ImageView)itemView.findViewById(R.id.product_search_image);
        textProductName = (TextView) itemView.findViewById(R.id.pName_search);
        textProductDescription = (TextView)itemView.findViewById(R.id.pDescription_search);
        textProductPrice = (TextView)itemView.findViewById(R.id.pPrice_search);
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
