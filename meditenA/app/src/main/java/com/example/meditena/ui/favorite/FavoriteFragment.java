package com.example.meditena.ui.favorite;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.meditena.R;
import com.example.meditena.ViewHolder.ProductViewHolder;
import com.example.meditena.model.Products;
import com.example.meditena.ui.ProductsDetails.ProductsDetailsFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

public class FavoriteFragment extends Fragment {
    private DatabaseReference ProductsRef;
    private  RecyclerView recyclerView;
    private FavoriteViewModel mViewModel;
    private FirebaseAuth auth;
    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_favorite);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager;
        auth = FirebaseAuth.getInstance();
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        final String uid = auth.getCurrentUser().getUid();
        final String TAG = "TAG";
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Favorite");

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(ProductsRef.child(uid)
                .child("Products"),
                Products.class)
                .build();
        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ProductViewHolder holder, int position, @NonNull final Products model) {
                holder.textProductName.setText(model.getPname());
                holder.textProductDescription.setText(model.getDescription());
                holder.textProductPrice.setText(model.getPrice()+"€");
                holder.textProductDiscount.setText(model.getDiscount()+"% off");
                Picasso.get().load(model.getImage()).into(holder.imageView);
                holder.textProductDiscountPrice.setText(model.getDPrice()+"€");
                final DatabaseReference databaseRefs = FirebaseDatabase.getInstance().getReference().child("Favorite");

                holder.favoriteImage.setImageResource(R.drawable.ic_favorite_pink_24dp);

                holder.favoriteImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String uid = auth.getCurrentUser().getUid();
                        holder.favoriteImage.setImageResource(R.drawable.ic_favorite_border_black_24dp);

                        databaseRefs.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    databaseRefs.child(uid).child("Products").child(model.getPid()).removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getActivity(), "your favorite item deleted", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Fragment fragment = new ProductsDetailsFragment();
                        Bundle b1 = new Bundle();
                        b1.putString("pid", model.getPid());
                        fragment.setArguments(b1);
                        FragmentManager fragmentManager =  getFragmentManager();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.nav_host_fragment, fragment, TAG)
                                .addToBackStack(null)
                                .commit();
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
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        // TODO: Use the ViewModel
    }

}
