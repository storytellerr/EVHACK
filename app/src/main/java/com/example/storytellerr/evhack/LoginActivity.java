package com.example.storytellerr.evhack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {

    private EditText lemail,lpassword;
    private Button signup_user,btn_login,btn_reset_password,btn_signupshop;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;

    private DatabaseReference mUserDatabase;

     Button signup_vendor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgress=new ProgressDialog(this);
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        mAuth = FirebaseAuth.getInstance();
        lemail=(EditText)findViewById(R.id.email);
        lpassword=(EditText)findViewById(R.id.password);

        btn_login=(Button)findViewById(R.id.btn_login);
        signup_user=(Button)findViewById(R.id.btn_signupuser);
        btn_reset_password=(Button)findViewById(R.id.btn_reset_password);

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

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = lemail.getText().toString().trim();
                String password = lpassword.getText().toString().trim();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){

                    mProgress.setTitle("Logging In");
                    mProgress.setMessage("Please wait while we check your credentials.");
                    mProgress.setCanceledOnTouchOutside(false);
                    mProgress.show();

                    loginUser(email, password);

                }
            }
        });
    }


    private void loginUser(String email, String password) {


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    mProgress.dismiss();
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }
                else
                    {

                    mProgress.hide();

                    String task_result = task.getException().getMessage().toString();

                    Toast.makeText(LoginActivity.this, "Error : " + task_result, Toast.LENGTH_LONG).show();

                }

            }
        });

    }
}
