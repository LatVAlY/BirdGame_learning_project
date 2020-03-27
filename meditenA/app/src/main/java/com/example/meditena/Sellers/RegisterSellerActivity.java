package com.example.meditena.Sellers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meditena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterSellerActivity extends AppCompatActivity {

    private EditText sellerName, emailSeller, passwordSeller, rePasswordSeller;
    private Button registerButton;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference sellerRefs;
    private ProgressBar loadingBar;
    private TextView loginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_seller);
        sellerName = (EditText) findViewById(R.id.name_register_seller);
        emailSeller = (EditText) findViewById(R.id.email_register_seller);
        passwordSeller = (EditText) findViewById(R.id.password_register_seller);
        rePasswordSeller = (EditText) findViewById(R.id.re_password_register_seller);
        registerButton = (Button) findViewById(R.id.create_account_seller_button);
        loadingBar = (ProgressBar) findViewById(R.id.progressBar_seller);
        loginTextView = (TextView) findViewById(R.id.login_Text_seller);
        //......................................LoginLink.................................
        String Text = "Already have an account? Login";
        SpannableString ss = new SpannableString(Text);
        ForegroundColorSpan fcsPin = new ForegroundColorSpan(Color.rgb(29, 233, 182));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(RegisterSellerActivity.this, LoginSellerActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLACK);
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(RegisterSellerActivity.this, LoginSellerActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan1, 0, 24, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ss.setSpan(clickableSpan, 25, 30, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ss.setSpan(fcsPin, 0, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        loginTextView.setMovementMethod(LinkMovementMethod.getInstance());
        loginTextView.setText(ss);
//.......................................................................................
        sellerRefs = FirebaseDatabase.getInstance().getReference().child("Sellers");
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatSellerAccount();
            }
        });
    }
    private void CreatSellerAccount() {
        final String name = sellerName.getText().toString();
        final String email = emailSeller.getText().toString();
        final String password = passwordSeller.getText().toString();
        final String re_password = rePasswordSeller.getText().toString();

        if (TextUtils.isEmpty(name)) {
            sellerName.setError("Name is required");
            return;
        } else if (TextUtils.isEmpty(email)) {
            emailSeller.setError("Email is required");
            return;
        } else if (TextUtils.isEmpty(password)) {
            passwordSeller.setError("Password is required");
            return;
        } else if (TextUtils.isEmpty(re_password)) {
            rePasswordSeller.setError("re-write password is required");
            return;
        } else if (password.length() < 6) {
            passwordSeller.setError("Password must be >= 6 characters or more!");
            return;
        } else if (!(password.equals(re_password))) {
            rePasswordSeller.setError("Please Enter Correct Password");
            return;
        } else {
            loadingBar.setVisibility(View.VISIBLE);
            ValidatePhoneNumber(email, password, name);

        }
    }

    private void ValidatePhoneNumber(final String email, String password, final String name) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    final String uid = auth.getCurrentUser().getUid();
                    sellerRefs.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!(dataSnapshot.child(uid).exists())){

                                HashMap<String, Object> userdataMap = new HashMap<>();
                                userdataMap.put("uid", uid);
                                userdataMap.put("email", email);
                                userdataMap.put("name", name);
                                sellerRefs.child(uid).updateChildren(userdataMap);
                            }
                            else{
                                Toast.makeText(RegisterSellerActivity.this, "this email already exists", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    FirebaseUser user = auth.getCurrentUser();
                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(RegisterSellerActivity.this, "Verification Link sent to your Email", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterSellerActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

                    Toast.makeText(RegisterSellerActivity.this, "User successfully created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterSellerActivity.this, LoginSellerActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(RegisterSellerActivity.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        loadingBar.setVisibility(View.GONE);
    }
}