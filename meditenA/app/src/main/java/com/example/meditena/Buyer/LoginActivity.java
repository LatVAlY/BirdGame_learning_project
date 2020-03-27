package com.example.meditena.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.example.meditena.Sellers.LoginSellerActivity;
import com.example.meditena.Sellers.MainSellerActivity;
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

public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private com.rey.material.widget.CheckBox checkBoxRememberMe;
    private ProgressBar loadingBar;
    private FirebaseAuth auth;
    private TextView loginRedirection;
    private DatabaseReference userRefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        TextView loginTextView = (TextView) findViewById(R.id.login_textView);
        inputEmail = (EditText) findViewById(R.id.Email_input_login);
        inputPassword = (EditText)findViewById(R.id.password_input_login);
        TextView txtForgetPassword = (TextView) findViewById(R.id.forget_password_link);
        Button loginBtn = (Button) findViewById(R.id.login_button);
        loadingBar =(ProgressBar) findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();
        loginRedirection = (TextView) findViewById(R.id.login_seller_redirection);
        userRefs = FirebaseDatabase.getInstance().getReference();

        //......................................LoginLink.................................
        String Text = "You don't have an account? Join Now";
        SpannableString ss = new SpannableString(Text);
        ForegroundColorSpan fcsPin = new ForegroundColorSpan(Color.rgb(239, 28, 119));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
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
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan1, 0, 25, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ss.setSpan(clickableSpan, 25, 35, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ss.setSpan(fcsPin, 0, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        loginTextView.setMovementMethod(LinkMovementMethod.getInstance());
        loginTextView.setText(ss);
//.......................................................................................
        loginRedirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (LoginActivity.this, LoginSellerActivity.class);
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
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    } else if(dataSnapshot.child("Admins").child(uid).exists()){
                        Toast.makeText(LoginActivity.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, AdminAddCategoryActivity.class);
                        startActivity(intent);
                    }
                    else if(dataSnapshot.child("Sellers").child(uid).exists()){
                        Toast.makeText(LoginActivity.this, "Welcome Seller", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainSellerActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    inputEmail.setError("Please enter your email address");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    inputPassword.setError("Please enter your password");
                    return;
                }
                if (password.length() < 6) {
                    inputPassword.setError("Password must be >= 6 characters or more!");
                    return;
                }

                //Login for Users.............

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull final Task<AuthResult> task) {
                                //Login for Admin.......................
                                final String uid = auth.getCurrentUser().getUid();
                                userRefs.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        loadingBar.setVisibility(View.INVISIBLE);
                                        if (dataSnapshot.child("Users").child(uid).exists()){
                                            if (task.isSuccessful()){
                                                Toast.makeText(LoginActivity.this, "logging successfully", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(LoginActivity.this, "Error "
                                                        + task.getException(), Toast.LENGTH_SHORT).show();
                                                loadingBar.setVisibility(View.INVISIBLE);
                                            }

                                        }
                                        else if(dataSnapshot.child("Admins").child(uid).exists()){
                                            Toast.makeText(LoginActivity.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(LoginActivity.this, AdminAddCategoryActivity.class);
                                            startActivity(intent);
                                        }
                                        else {
                                            Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });

                            }
                        });
                loadingBar.setVisibility(View.VISIBLE);
            }
        });


        txtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                final EditText resetEmail = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Rest Password ?");
                passwordResetDialog.setMessage("Enter your Email to receive Reset Link");
                passwordResetDialog.setView(resetEmail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = resetEmail.getText().toString();
                        auth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginActivity.this, "You will receive a Reset Link to your Email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Error | Reset link is not Sent "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = passwordResetDialog.create();
                alert.show();
            }
        });
    }
}

