    package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private EditText Password;
    private Button  Login;
    private TextView    Info;
    private EditText IDNumber;
    private int Counter = 5 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        Password =(EditText) findViewById(R.id.Password);
        Login = (Button) findViewById(R.id.Btnbutton);
        Info = (TextView) findViewById(R.id.textView);
        IDNumber = findViewById(R.id.IDNumber);

        Info.setText("No of attempts remaining: 5");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validate(editText.getText().toString(),Password.getText().toString(),IDNumber.getText().toString());
            }
        });

    }
    private void Validate(String userName, String userPassword, String userIDNumber){
        if ((userName.equals("Admin")) && (userPassword.equals("1234")) && (userIDNumber.equals("1444381"))){
            Intent intent = new Intent(MainActivity.this, Emplees.class);
            startActivity(intent);
        }else{
            Counter--;
            Info.setText("No of attempts : " + String.valueOf(Counter));
            if (Counter == 0){
                Login.setEnabled(false);
                Info.setText("You have been blocked");
            }
        }

    }
}
