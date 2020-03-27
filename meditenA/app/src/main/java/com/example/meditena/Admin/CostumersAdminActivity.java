package com.example.meditena.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.meditena.R;
import com.example.meditena.ViewHolder.CustomerViewHolder;
import com.example.meditena.model.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CostumersAdminActivity extends AppCompatActivity {
    private RecyclerView recyclerCustomer;
    private String inputText;
    private Button searchbtn;
    private EditText searchText;
    private FirebaseAuth auth;
    private DatabaseReference customerRefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costumers_admin);
        getSupportActionBar().hide();
        recyclerCustomer = (RecyclerView) findViewById(R.id.search_costumer_recycler);
        recyclerCustomer.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(CostumersAdminActivity.this);
        recyclerCustomer.setLayoutManager(layoutManager);
        recyclerCustomer.setAdapter(null);
        searchbtn = (Button) findViewById(R.id.search_costumer_button);
        searchText = (EditText) findViewById(R.id.search_customer_name);
        auth = FirebaseAuth.getInstance();


        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText = searchText.getText().toString();
                if (inputText == null){
                    onStart();
                }
                onStart();
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        String uid = auth.getCurrentUser().getUid();
        customerRefs = FirebaseDatabase.getInstance().getReference().child("Users");

        FirebaseRecyclerOptions<Users> options = new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(customerRefs.orderByChild("name").startAt(inputText).endAt(inputText+"\uf8ff"), Users.class)
                .build();
        FirebaseRecyclerAdapter<Users, CustomerViewHolder> adapter = new FirebaseRecyclerAdapter<Users, CustomerViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CustomerViewHolder holder, int position, @NonNull final Users model) {
                holder.customerName.setText(model.getName());
                holder.customerAddress.setText(model.getAddress());
                holder.customerEmail.setText(model.getEmail());
                Picasso.get().load(model.getImage()).into(holder.customerImage);
            }

            @NonNull
            @Override
            public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_costumers_layout, parent, false);
                CustomerViewHolder holder = new CustomerViewHolder(view);
                return holder;
            }

        };
        recyclerCustomer.setAdapter(adapter);
        adapter.startListening();

    }
    @Override
    public void onBackPressed() {
        if (!(inputText == null)){ inputText = null; onStart();}
        else{super.onBackPressed();}

    }
}
