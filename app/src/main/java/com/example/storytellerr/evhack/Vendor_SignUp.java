package com.example.storytellerr.evhack;

import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Vendor_SignUp extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mDatabase;
    private EditText vname, vaddress;
    private EditText vphone, vstar;
    private EditText vlat, vlon;
    private String name, address, phone, star, lat, lon;
    private FloatingActionButton mSubmitButton;
    private FirebaseAuth auth;
    private Button vendor_signup;
    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor__sign_up);

        auth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        vname = (EditText) findViewById(R.id.vendor_name);
        vaddress = (EditText) findViewById(R.id.vendor_address);
        vphone = (EditText) findViewById(R.id.vendor_phone);
        vstar = (EditText) findViewById(R.id.vendor_stars);
        vlat = (EditText) findViewById(R.id.vendor_lat);
        vlon = (EditText) findViewById(R.id.vendor_lon);
        vendor_signup = (Button)findViewById(R.id.submit);
        vendor_signup.setOnClickListener(this);
    }

    private void submit_vendor()
    {
        String name = vname.getText().toString().trim();
        String address = vaddress.getText().toString().trim();
        String phone = vphone.getText().toString().trim();
        String star = vstar.getText().toString().trim();
        String lat = vlat.getText().toString().trim();
        String lon = vlon.getText().toString().trim();


        if(TextUtils.isEmpty(name)){
            vname.setError("Required");
            return;
        }
        if(TextUtils.isEmpty(address)){
            vaddress.setError("Required");
            return;
        }
        if(TextUtils.isEmpty(phone)){
            vphone.setError("Required");
            return;
        }
        Toast.makeText(this, "Registering",Toast.LENGTH_SHORT).show();

//        Vendor_SignUp vendor = new Vendor_SignUp();
//        vendor.newUser(name, address, phone, star, lat, lon);
    }


    public void newUser()
    {
        Vendor vd= new Vendor();
        vd.pushdata(name, address, phone, star, lat, lon);
        mDatabase.child("Shops").setValue(vd);

    }

    @Override
    public void onClick(View v) {
        newUser();
    }
}
    class Vendor{
    public String name,address,  phone, star, lat,lon;

    Vendor(){

    }

        public void pushdata(String name1, String address1, String phone1, String star1, String lon1, String lat1)
        {
            this.name = name1;
            this.address = address1;
            this.phone = phone1;
            this.star = star1;
            this.lat = lat1;
            this.lon = lon1;
        }
    }