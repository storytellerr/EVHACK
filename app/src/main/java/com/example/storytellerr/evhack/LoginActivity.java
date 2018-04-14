package com.example.storytellerr.evhack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Button signup_user;
    Button signup_vendor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup_user=(Button)findViewById(R.id.btn_signupuser);
        signup_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup_userIntent=new Intent(LoginActivity.this,RegisterUserActivity.class);
                startActivity(signup_userIntent);

            }
        });

        signup_vendor=(Button)findViewById(R.id.btn_signupshop);
        signup_vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup_vendor=new Intent(LoginActivity.this,Vendor_SignUp.class);
                startActivity(signup_vendor);

            }
        });


    }
}
