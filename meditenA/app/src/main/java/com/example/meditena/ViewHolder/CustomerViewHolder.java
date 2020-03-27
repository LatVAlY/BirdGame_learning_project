package com.example.meditena.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meditena.Interface.ItemClickListener;
import com.example.meditena.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView customerImage;
    public TextView customerName, customerEmail, customerAddress;
    public ItemClickListener itemClickListner;

    public CustomerViewHolder(@NonNull View itemView) {
        super(itemView);

        customerImage = (ImageView) itemView.findViewById(R.id.customer_image);
        customerName = (TextView) itemView.findViewById(R.id.costumer_name);
        customerAddress = (TextView) itemView.findViewById(R.id.customer_address);
        customerEmail = (TextView) itemView.findViewById(R.id.costumer_email_address);

    }

    @Override
    public void onClick(View v) {
        itemClickListner.Onclick(v, getAdapterPosition(), false);
    }
    public void setItemClickListner(ItemClickListener itemClickListner) {
        this.itemClickListner = itemClickListner;

    }
}
