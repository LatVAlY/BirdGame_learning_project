package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static android.view.View.*;

public class Emplees extends AppCompatActivity {
    private TextView date, calender, chat, bug, contact, NOIlness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emplees);


        date = (TextView)findViewById(R.id.date);
        calender = (TextView)findViewById(R.id.calendar);
        chat = (TextView)findViewById(R.id.chat);
        bug = (TextView)findViewById(R.id.bug);
        contact = (TextView)findViewById(R.id.contact1);
        NOIlness = (TextView)findViewById(R.id.NOIllness);

        calender.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Emplees.this, Empleesikea.class);
                startActivity(intent);
            }
        });
        chat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Emplees.this, Empleesikea.class);
                startActivity(intent);
            }
        });
        bug.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Emplees.this, Empleesikea.class);
                startActivity(intent);
            }
        });
        contact.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Emplees.this, spinner.class);
                startActivity(intent);
            }
        });
        NOIlness.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Emplees.this, spinner.class);
                startActivity(intent);
            }
        });
        date.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Emplees.this, Empleesikea.class);
                startActivity(intent);
            }
        });
    }
}
