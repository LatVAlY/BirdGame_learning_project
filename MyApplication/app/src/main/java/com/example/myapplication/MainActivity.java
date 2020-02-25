package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.cert.Extension;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    TextView textView;
    Button Toast,DialPhone,ViewIntent,LetsGetPro,ShowLocation,OpenWebPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textview);

         DialPhone = (Button) findViewById(R.id.DialPhone);
         Toast = (Button) findViewById(R.id.Toast);
        ShowLocation = (Button) findViewById(R.id.ShowLocation);
        OpenWebPage = (Button) findViewById(R.id.OpenWebPage);
        ViewIntent = (Button) findViewById(R.id.ViewIntent);
        LetsGetPro = (Button) findViewById(R.id.LetsGetPro);



         DialPhone.setOnClickListener(this);
        Toast.setOnClickListener(this);
        ShowLocation.setOnClickListener(this);
        OpenWebPage.setOnClickListener(this);
        ViewIntent.setOnClickListener(this);
        LetsGetPro.setOnClickListener(this);



/*
        // Event Listener OnclickListener
        Push_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("tag", "Somebody pushed the Button1!");
                textView.setText("Hey u just pushed the first Button");
            }
        });
        Push_me2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag", "Somebody pushed the Button2!");
                textView.setText("Hey u just pushed the second Button");
            }
        });
        */
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.DialPhone :

                break;
            case R.id.Toast :
                Intent intent = new Intent(MainActivity.this,OtherActivity.class);
                startActivity(intent);

                break;
            case R.id.ShowLocation :

                break;
            case R.id.OpenWebPage :

                break;
            case R.id.ViewIntent :

                break;
            case R.id.LetsGetPro :

                break;

        }


    }
}
