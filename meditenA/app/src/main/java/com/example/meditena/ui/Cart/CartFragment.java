package com.example.meditena.ui.Cart;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meditena.R;
import com.example.meditena.ViewHolder.CartViewHolder;
import com.example.meditena.model.Cart;
import com.example.meditena.ui.ProductsDetails.ProductsDetailsFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartFragment extends Fragment {

    private CartViewModel mViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button nextProcessBtn;
    private TextView txtTotalPrice, txtmsg1;
    private int overTotalPrice= 0;
    private FirebaseAuth auth;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.cart_fragment, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        auth = FirebaseAuth.getInstance();
        nextProcessBtn =(Button)view.findViewById(R.id.button_Next);
        txtTotalPrice = (TextView)view.findViewById(R.id.text_price);
        txtmsg1 = (TextView)view.findViewById(R.id.msg1);


        final String TAG = "TAG";
        final String uid = auth.getCurrentUser().getUid();
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef.child("User View")
                                .child(uid)
                                .child("Products"), Cart.class)
                        .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter =
                new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model) {

                        holder.txtProductQuantity.setText("Quantity = "+model.getQuantity());
                        holder.txtProductName.setText( model.getPname());
                        holder.txtProductPrice.setText("Price = "+ model.getPrice()+"€");

                        int oneTypeProductTPrice = ((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());
                        overTotalPrice = overTotalPrice + oneTypeProductTPrice;
                        txtTotalPrice.setText("Total Price = " +  String.valueOf(overTotalPrice) + "€");

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[] = new CharSequence[]{
                                        "Edit",
                                        "Remove"
                                };
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Cart Option: ");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        if (i == 0){
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
                                        if (i == 1 ){
                                            cartListRef.child("User View").child(uid)
                                                    .child("Products")
                                                    .child(model.getPid())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()){
                                                                Toast.makeText(getActivity(), "Item removed successfully", Toast.LENGTH_SHORT).show();

                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });
                                builder.show();

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                        CartViewHolder holder = new CartViewHolder(view);
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
        mViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        // TODO: Use the ViewModel
    }
}
