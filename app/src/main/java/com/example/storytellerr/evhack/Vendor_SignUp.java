package com.example.storytellerr.evhack;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Vendor_SignUp extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private EditText vname, vaddress;
    private EditText vphone, vstar;
    private EditText vlat, vlon;
    private String name, address, phone, star, lat, lon;
    private FloatingActionButton mSubmitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor__sign_up);

        vname = (EditText)findViewById(R.id.vendor_name);
        vaddress = (EditText)findViewById(R.id.vendor_address);
        vphone = (EditText)findViewById(R.id.vendor_phone);
        vstar = (EditText)findViewById(R.id.vendor_stars);
        vlat = (EditText)findViewById(R.id.vendor_lat);
        vlon = (EditText)findViewById(R.id.vendor_lon);

//        String name = vname.getText().toString().trim();
//        String address = vaddress.getText().toString().trim();
//        String phone = vphone.getText().toString().trim();
//        String star = vstar.getText().toString().trim();
//        String lat = vlat.getText().toString().trim();
//        String lon = vlon.getText().toString().trim();

        mSubmitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                submit_vendor();
            }
        });
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
    }
    public Vendor_SignUp()
    {

    }

    public Vendor_SignUp(String name, String address, String phone, String star, String lat, String lon)
    {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.star = star;
        this.lat = lat;
        this.lon = lon;
    }

    public void newUser(String UserId,String name, String address, String phone, String star, String lat, String lon)
    {
        Vendor_SignUp user = new Vendor_SignUp(name, address, phone, star, lat, lon);
        mDatabase.child("Shops").setValue(user);
    }
}
