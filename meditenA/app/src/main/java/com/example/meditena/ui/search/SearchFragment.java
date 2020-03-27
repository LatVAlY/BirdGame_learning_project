package com.example.meditena.ui.search;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.meditena.R;
import com.example.meditena.ViewHolder.ProductSearchViewHolder;
import com.example.meditena.model.Products;
import com.example.meditena.ui.ProductsDetails.ProductsDetailsFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SearchFragment extends Fragment {
    private SearchViewModel mViewModel;
    private RecyclerView recyclerSearch;
    private String inputText;
    private Button searchbtn;
    private EditText searchText;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        View view = inflater.inflate(R.layout.search_fragment, null);
        final String TAG = "TAG";

        recyclerSearch = (RecyclerView) view.findViewById(R.id.search_list_fragment);
        recyclerSearch.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getContext());
        recyclerSearch.setLayoutManager(layoutManager);
        recyclerSearch.setAdapter(null);
        searchbtn = (Button) view.findViewById(R.id.search_product_button);
        searchText = (EditText) view.findViewById(R.id.search_product_name);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText = searchText.getText().toString();
                onStart();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        DatabaseReference searchRef = FirebaseDatabase.getInstance().getReference().child("Products");

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(searchRef.orderByChild("pname").startAt(inputText).endAt(inputText+"\uf8ff"), Products.class)
                .build();
        FirebaseRecyclerAdapter<Products, ProductSearchViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductSearchViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductSearchViewHolder holder, int position, @NonNull final Products model) {
                holder.textProductName.setText(model.getPname());
                holder.textProductDescription.setText(model.getDescription());
                holder.textProductPrice.setText(model.getPrice()+"€");
                Picasso.get().load(model.getImage()).into(holder.imageViewSearch);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Fragment fragment = new ProductsDetailsFragment();
                        Bundle b1 = new Bundle();
                        b1.putString("pid", model.getPid());
                        fragment.setArguments(b1);
                        FragmentManager fragmentManager =  getFragmentManager();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.nav_host_fragment, fragment)
                                .addToBackStack("PD")
                                .commit();
                    }
                });
            }

            @NonNull
            @Override
            public ProductSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_product, parent, false);
                ProductSearchViewHolder holder = new ProductSearchViewHolder(view);
                return holder;
            }

        };
        recyclerSearch.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
