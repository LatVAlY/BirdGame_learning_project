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

import com.example.meditena.Admin.AdminAddCategoryActivity;
import com.example.meditena.Buyer.LoginActivity;
import com.example.meditena.Buyer.MainActivity;
import com.example.meditena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class LoginSellerActivity extends AppCompatActivity {

    private EditText sellerEmail, sellerPassword;
    private Button sellerLoginBtn;
    private TextView registerTextView;
    private DatabaseReference loginSellerRefs, userRefs;
    private FirebaseAuth auth;
    private ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_seller);
        getSupportActionBar().hide();
        sellerEmail = (EditText)findViewById(R.id.seller_login_email);
        sellerPassword = (EditText)findViewById(R.id.seller_login_password);
        sellerLoginBtn = (Button)findViewById(R.id.seller_login_button);
        registerTextView = (TextView)findViewById(R.id.login_redirection_seller);
        auth = FirebaseAuth.getInstance();


        //............................RegisterText......................
        final String Text = "Already have an account? register";
        SpannableString ss = new SpannableString(Text);
        ForegroundColorSpan fcsPin = new ForegroundColorSpan(Color.rgb(29, 233, 182));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(LoginSellerActivity.this, RegisterSellerActivity.class);
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
                Intent intent = new Intent(LoginSellerActivity.this, RegisterSellerActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan1, 0, 24, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ss.setSpan(clickableSpan, 25, 33, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ss.setSpan(fcsPin, 0, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        registerTextView.setMovementMethod(LinkMovementMethod.getInstance());
        registerTextView.setText(ss);
//.......................................................................................
        sellerLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = sellerEmail.getText().toString();
                final String password = sellerPassword.getText().toString();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(LoginSellerActivity.this, "Please Enter your Seller Email Address", Toast.LENGTH_SHORT).show();
                    return;
                }
                 if (TextUtils.isEmpty(password)){
                    Toast.makeText(LoginSellerActivity.this, "Please Enter your Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull final Task<AuthResult> task) {
                                //Login for Admin.......................
                                loginSellerRefs = FirebaseDatabase.getInstance().getReference();
                                final String uid = auth.getCurrentUser().getUid();
                                loginSellerRefs.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.child("Sellers").child(uid).exists()){
                                            if (task.isSuccessful()){
                                                Toast.makeText(LoginSellerActivity.this, "Seller logged successfully", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(LoginSellerActivity.this, MainSellerActivity.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(LoginSellerActivity.this, "Error "
                                                        + task.getException(), Toast.LENGTH_SHORT).show();
                                                loadingBar.setVisibility(View.INVISIBLE);
                                            }

                                        }
                                        else {
                                            Toast.makeText(LoginSellerActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });

                            }
                        });
                }
        });
    }
}
