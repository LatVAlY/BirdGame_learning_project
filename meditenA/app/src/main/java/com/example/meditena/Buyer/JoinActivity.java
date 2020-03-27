package com.example.meditena.Buyer;

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

import com.example.meditena.Admin.AdminAddCategoryActivity;
import com.example.meditena.R;
import com.example.meditena.Sellers.MainSellerActivity;
import com.example.meditena.Sellers.RegisterSellerActivity;
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

import org.w3c.dom.Text;

import java.util.HashMap;

public class JoinActivity extends AppCompatActivity {
    private EditText txtNameInput, txtEmailInput, txtPasswordInput, txtRePasswordInput;
    private TextView loginTextView, sellerAccountRedirection;
    private Button creatAcountBtn;
    private FirebaseAuth auth;
    private ProgressBar loadingBar;
    private DatabaseReference userRefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        getSupportActionBar().hide();
        loginTextView = (TextView)findViewById(R.id.login_textView);
        txtNameInput = (EditText) findViewById(R.id.name_input);
        txtEmailInput = (EditText) findViewById(R.id.email_input);
        txtPasswordInput = (EditText) findViewById(R.id.password_input);
        txtRePasswordInput = (EditText) findViewById(R.id.re_password_input);
        creatAcountBtn = (Button) findViewById(R.id.create_account_button);
        loadingBar =(ProgressBar) findViewById(R.id.progressBar);
        sellerAccountRedirection = (TextView) findViewById(R.id.seller_redirection);
        auth = FirebaseAuth.getInstance();
        userRefs = FirebaseDatabase.getInstance().getReference();
//......................................LoginLink.................................
        String Text = "You already have an account? Login";
        SpannableString ss = new SpannableString(Text);
        ForegroundColorSpan fcsPin = new ForegroundColorSpan(Color.rgb(239, 28, 119));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.WHITE);
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan1, 0, 28, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ss.setSpan(clickableSpan, 29, 34, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ss.setSpan(fcsPin, 0, 28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        loginTextView.setMovementMethod(LinkMovementMethod.getInstance());
        loginTextView.setText(ss);
//.......................................................................................

        sellerAccountRedirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (JoinActivity.this, RegisterSellerActivity.class);
                startActivity(intent);
            }
        });
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            final String uid = auth.getCurrentUser().getUid();
            userRefs.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("Users").child(uid).exists()){
                        Intent i = new Intent(JoinActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    } else if(dataSnapshot.child("Admins").child(uid).exists()){
                        Toast.makeText(JoinActivity.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(JoinActivity.this, AdminAddCategoryActivity.class);
                        startActivity(intent);
                    }
                    else if(dataSnapshot.child("Sellers").child(uid).exists()){
                        Toast.makeText(JoinActivity.this, "Welcome Seller", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(JoinActivity.this, MainSellerActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        creatAcountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });
    }
    private void CreateAccount() {
        final String name = txtNameInput.getText().toString();
        final String email = txtEmailInput.getText().toString();
        final String password = txtPasswordInput.getText().toString();
        final String re_password = txtRePasswordInput.getText().toString();

        if (TextUtils.isEmpty(name)) {
            txtNameInput.setError("Name is required");
            return;
        } else if (TextUtils.isEmpty(email)) {
            txtEmailInput.setError("Email is required");
            return;
        } else if (TextUtils.isEmpty(password)) {
            txtPasswordInput.setError("Password is required");
            return;
        } else if (TextUtils.isEmpty(re_password)) {
            txtNameInput.setError("re-write password is required");
            return;
        } else if (password.length() < 6) {
            txtPasswordInput.setError("Password must be >= 6 characters or more!");
            return;
        }else if (!(password.equals(re_password))) {
            txtRePasswordInput.setError("Please Enter Correct Password");
            return;
        }else {
            loadingBar.setVisibility(View.VISIBLE);

            ValidatePhoneNumber(email, password, name);


        }
    }
    private void ValidatePhoneNumber(final String email, final String password, final String name) {


        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    final DatabaseReference RootRef;
                    RootRef = FirebaseDatabase.getInstance().getReference();
                    final String uid = auth.getCurrentUser().getUid();
                    RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!(dataSnapshot.child("Users").child(uid).exists())){

                                HashMap<String, Object> userdataMap = new HashMap<>();
                                userdataMap.put("uid", uid);
                                userdataMap.put("email", email);
                                userdataMap.put("name", name);

                                RootRef.child("Users").child(uid).updateChildren(userdataMap);
                            }
                            else{
                                Toast.makeText(JoinActivity.this, "this email already exists", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(JoinActivity.this, "Verification Link sent to your Email", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(JoinActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    Toast.makeText(JoinActivity.this, "User successfully created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(JoinActivity.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        loadingBar.setVisibility(View.GONE);
    }
}
