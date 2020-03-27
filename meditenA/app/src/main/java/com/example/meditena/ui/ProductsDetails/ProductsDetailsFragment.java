package com.example.meditena.ui.ProductsDetails;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.meditena.Buyer.MainActivity;
import com.example.meditena.R;
import com.example.meditena.model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductsDetailsFragment extends Fragment {

    private ProductsDetailsViewModel mViewModel;

    private AppBarConfiguration mAppBarConfiguration;
    private ImageView productImage;
    private ElegantNumberButton counterButton;
    private TextView productDescription, productPrice, productName, productDiscount;
    private String productID = "", price = "";
    private Button addToCartBtn;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.products_details_fragment, container, false);
        productImage = (ImageView)view.findViewById(R.id.product_image_details);
        counterButton = (ElegantNumberButton)view.findViewById(R.id.elegant_number_products);
        productDescription = (TextView)view.findViewById(R.id.product_description_details);
        productName = (TextView)view.findViewById(R.id.product_name_details);
        productPrice = (TextView)view.findViewById(R.id.product_price_details);
        productDiscount = (TextView)view.findViewById(R.id.product_discount_details);
        addToCartBtn =(Button)view.findViewById(R.id.add_to_cart_button);
        auth = FirebaseAuth.getInstance();

        productDescription.setMovementMethod(new ScrollingMovementMethod());
        Bundle bundle = this.getArguments();


        productID= bundle.getString("pid");


        getProductDetails(productID);

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
        return view;
    }
    private void addToCart() {
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());


        final String uid = auth.getCurrentUser().getUid();
        final DatabaseReference databaseRefs = FirebaseDatabase.getInstance().getReference().child("Cart List");
        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", productID);
        cartMap.put("price", productPrice.getText().toString());
        cartMap.put("pname", productName.getText().toString());
        cartMap.put("description", productDescription.getText().toString());
        cartMap.put("time", saveCurrentTime);
        cartMap.put("date", saveCurrentDate);
        cartMap.put("quantity", counterButton.getNumber());
        cartMap.put("discount", productDiscount.getText().toString());

        databaseRefs.child("User View").child(uid).child("Products")
                .child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            databaseRefs.child("Admin View").child(uid).child("Products")
                                    .child(productID)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(getActivity(), "Product added to the cart list", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                        }
                        else{
                            Toast.makeText(getActivity(), "Error ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getProductDetails(String productID) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //verifying if the data exits in the database
                if (dataSnapshot.exists()){
                    Products products = dataSnapshot.getValue(Products.class);
                    productName.setText(products.getPname());
                    productDescription.setText(products.getDescription());
                    productPrice.setText(products.getDPrice());
                    productDiscount.setText(products.getDiscount() + "% off ");
                    productDescription.setMovementMethod(new ScrollingMovementMethod());
                    Picasso.get().load(products.getImage()).into(productImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProductsDetailsViewModel.class);
        // TODO: Use the ViewModel
    }

}
