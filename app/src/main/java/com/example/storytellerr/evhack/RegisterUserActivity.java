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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class RegisterUserActivity extends AppCompatActivity {

    private EditText name,mobile,address,email,password;     //hit option + enter if you on mac , for windows hit ctrl + enter
    private Button signup_button,sign_in_button;
    private ProgressDialog mProgress;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        auth = FirebaseAuth.getInstance();

        name=(EditText)findViewById(R.id.name);
        mobile=(EditText)findViewById(R.id.mobile);
        address=(EditText)findViewById(R.id.address);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);

        sign_in_button=(Button)findViewById(R.id.sign_in_button);
        signup_button=(Button)findViewById(R.id.sign_up_button);

        mProgress=new ProgressDialog(this);


        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent=new Intent(RegisterUserActivity.this,LoginActivity.class);
                startActivity(LoginIntent);
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name1=name.getText().toString().trim();
                final String mobile1=mobile.getText().toString().trim();
                final String address1=address.getText().toString().trim();

                final String email1 = email.getText().toString().trim();
                String password1 = password.getText().toString().trim();

                if (TextUtils.isEmpty(name1)) {
                    Toast.makeText(getApplicationContext(), "Enter name please!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(mobile1)) {
                    Toast.makeText(getApplicationContext(), "Enter name mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(address1)) {
                    Toast.makeText(getApplicationContext(), "Enter name address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email1)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password1)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_LONG).show();
                    return;
                }

                mProgress.setMessage("Signing up.....");
                mProgress.setCanceledOnTouchOutside(false);
                mProgress.show();

                //create user
                auth.createUserWithEmailAndPassword(email1, password1)
                        .addOnCompleteListener(RegisterUserActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterUserActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_LONG).show();

                                    mProgress.dismiss();
                                } else {
                                    Toast.makeText(RegisterUserActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                    mProgress.setMessage("Setting Up your Account");

                                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                                    String uid = current_user.getUid();

                                    mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

                                    String device_token = FirebaseInstanceId.getInstance().getToken();

                                    HashMap<String, String> userMap = new HashMap<>();
                                    userMap.put("name", name1);
                                    userMap.put("mobile", mobile1);
                                    userMap.put("address", address1);
                                    userMap.put("email", email1);

                                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){

                                                mProgress.dismiss();
                                                Toast.makeText(RegisterUserActivity.this,"User created successfully Login Now",Toast.LENGTH_LONG).show();
                                                Intent LoginIntent=new Intent(RegisterUserActivity.this, LoginActivity.class);
                                                LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(LoginIntent);
                                                finish();
                                            }

                                        }
                                    });

                                }
                            }
                        });
            }
        });
    }
}
