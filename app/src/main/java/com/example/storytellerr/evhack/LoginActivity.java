package com.example.storytellerr.evhack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Button signup_user;
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


    }
}
