package com.example.meditena.ViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meditena.Interface.ItemClickListener;
import com.example.meditena.R;

public class CategoryViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ItemClickListener listener;
    public ImageView imageCircleCategory;
    public TextView categoryName;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        imageCircleCategory = (ImageView) itemView.findViewById(R.id.category_image_circle);
        categoryName = (TextView) itemView.findViewById(R.id.category_name);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }


    @Override
    public void onClick(View v) {
        listener.Onclick(v, getAdapterPosition(), false);
    }
}
