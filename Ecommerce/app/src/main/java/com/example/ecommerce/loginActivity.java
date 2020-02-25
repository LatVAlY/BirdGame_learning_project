package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.ExtractedText;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Model.Users;
import com.example.ecommerce.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class loginActivity extends AppCompatActivity {
private Button login;
private EditText InputPassword, InputPhoneNbr;
private ProgressDialog loadingBar;
private String parentDbName = "Users";
private com.rey.material.widget.CheckBox checkBoxRememberMe;
private TextView AdminLink, NotAdminLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button)findViewById(R.id.login_btn);
        InputPassword = (EditText)findViewById(R.id.login_password_input);
        InputPhoneNbr = (EditText)findViewById(R.id.login_phone_number_input);
        loadingBar = new ProgressDialog(this);
        checkBoxRememberMe = (com.rey.material.widget.CheckBox)findViewById(R.id.remember_me_chkb);
        AdminLink = (TextView)findViewById(R.id.admin_panel_link);
        NotAdminLink =(TextView)findViewById(R.id.not_admin_panel_link);
        Paper.init(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });
        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
            }
        });
        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
            }
        });
    }

    private void LoginUser() {
        String phone = InputPhoneNbr.getText().toString();
        String password = InputPassword.getText().toString();
        if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Please enter your phone", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        }
        else{
                loadingBar.setTitle("Login Account");
                loadingBar.setMessage("Please wait, while we are checking the credential.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            AllowAccessToAccount(phone, password);
        }
    }

    private void AllowAccessToAccount(final String phone, final String password) {
        if (checkBoxRememberMe.isChecked()){
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);

        }


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ((dataSnapshot.child(parentDbName).child(phone).exists())){

                    Users usersdata = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);


                    if (usersdata.getPhone().equals(phone)){

                       if  (usersdata.getPassword().equals(password)){

                           if (parentDbName.equals("Admins")){
                               Toast.makeText(loginActivity.this, "Welcome Admin Logged in successfully... ", Toast.LENGTH_SHORT).show();
                               loadingBar.dismiss();

                               Intent intent = new Intent (loginActivity.this, AdminAddCategoryActivity.class);
                               startActivity(intent);
                           }
                           else if(parentDbName.equals("Users")){
                               Toast.makeText(loginActivity.this, "Logging is successfully... ", Toast.LENGTH_SHORT).show();
                               loadingBar.dismiss();

                               Intent intent = new Intent (loginActivity.this, HomeActivity.class);
                               startActivity(intent);
                               Prevalent.currentOnlineUser = usersdata;
                           }
                       }

                    else {
                        Toast.makeText(loginActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                    }

 }               }
                else{
                    Toast.makeText(loginActivity.this, "Account with this " + phone +" number or with password do not exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
