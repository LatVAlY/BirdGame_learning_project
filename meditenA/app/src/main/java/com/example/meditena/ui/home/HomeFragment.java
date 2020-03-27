package com.example.meditena.ui.home;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meditena.R;
import com.example.meditena.ViewHolder.CategoryViewHolder;
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

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private DatabaseReference ProductsRef, CategoryRef;
    private  RecyclerView recyclerView, recyclerViewCategory;
    private String productID = "", search = "";
    private FirebaseAuth auth;
    RecyclerView.LayoutManager layoutManager;
    private Parcelable savedRecyclerLayoutState;
    private GridLayoutManager mGridLayoutManager;
    private static final String BUNDLE_RECYCLER_LAYOUT = "recycler_layout";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View view = inflater.inflate(R.layout.fragment_home, null);

        auth = FirebaseAuth.getInstance();

        //...................recyclerView for Products...............
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_menu1);
        //recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        //.................RecyclerView for Categories............
        recyclerViewCategory = (RecyclerView) view.findViewById(R.id.recycler_category);
      //  recyclerViewCategory.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager1;
        layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategory.setLayoutManager(layoutManager1);

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        CategoryRef = FirebaseDatabase.getInstance().getReference().child("categories");
        //..........................RecyclerView for the Categories..........................
        FirebaseRecyclerOptions<Products> options1 = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(CategoryRef, Products.class)
                .build();
        FirebaseRecyclerAdapter<Products, CategoryViewHolder> adapter1 = new
                FirebaseRecyclerAdapter<Products, CategoryViewHolder>(options1) {
            @Override
            protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull final Products model) {
                        Picasso.get().load(model.getImage()).into(holder.imageCircleCategory);
                        holder.categoryName.setText(model.getCategory());
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                search = model.getCategory();
                                onStart();
                                Toast.makeText(getActivity(), search +"", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout, parent, false);
                CategoryViewHolder holder = new CategoryViewHolder(view);
                return holder;
            }
        };
        recyclerViewCategory.setAdapter(adapter1);
        adapter1.startListening();
//........................end......................................
                return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        final String TAG = "TAG";
        //........................RecyclerView for the Products...................................
        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(ProductsRef.orderByChild("category").startAt(search).endAt(search+"\uf8ff"), Products.class)
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
                        final String uid = auth.getCurrentUser().getUid();
                        final DatabaseReference databaseRefs = FirebaseDatabase.getInstance().getReference().child("Favorite");
                        databaseRefs.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!(dataSnapshot.child(uid).child("Products").child(model.getPid()).exists())){
                                    holder.favoriteImage.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                                }
                                else{
                                    holder.favoriteImage.setImageResource(R.drawable.ic_favorite_pink_24dp);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        holder.favoriteImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final String uid = auth.getCurrentUser().getUid();
                                final DatabaseReference databaseRefs = FirebaseDatabase.getInstance().getReference().child("Favorite");
                                databaseRefs.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (!(dataSnapshot.child(uid).child("Products").child(model.getPid()).exists()))  {
                                            String saveCurrentDate, saveCurrentTime;
                                            Calendar calForDate = Calendar.getInstance();
                                            SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                                            saveCurrentDate = currentDate.format(calForDate.getTime());

                                            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                                            saveCurrentTime = currentTime.format(calForDate.getTime());
                                            productID = saveCurrentDate + saveCurrentTime;

                                            final HashMap<String, Object> cartMap = new HashMap<>();
                                            cartMap.put("pid", model.getPid());
                                            cartMap.put("price", model.getPrice());
                                            cartMap.put("pname", model.getPname());
                                            cartMap.put("description", model.getDescription());
                                            cartMap.put("time", saveCurrentTime);
                                            cartMap.put("category", model.getCategory());
                                            cartMap.put("date", saveCurrentDate);
                                            cartMap.put("dprice", model.getDPrice());
                                            cartMap.put("discount", model.getDiscount());
                                            cartMap.put("image", model.getImage());


                                            databaseRefs.child(uid).child("Products")
                                                    .child(model.getPid())
                                                    .updateChildren(cartMap)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(getActivity(), "Product added to the Favorite list", Toast.LENGTH_SHORT).show();
                                                                holder.favoriteImage.setImageResource(R.drawable.ic_favorite_pink_24dp);
                                                            } else {
                                                                Toast.makeText(getActivity(), "Error ", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                        else  {
                                            databaseRefs.child(uid).child("Products").child(model.getPid()).removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(getActivity(), "your favorite item deleted", Toast.LENGTH_SHORT).show();
                                                                holder.favoriteImage.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                                                            }
                                                        }
                                                    });
                                        }

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
                                        .addToBackStack("Tag")
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
        //..........................................end......................

    }
}
