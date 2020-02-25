package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class OtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
       // Toast.makeText(this, "Landed in Other activity...", Toast.LENGTH_SHORT).show();

        Bundle bundle = getIntent().getExtras();
        String str =    bundle.getString("KEY");
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

    }
}
