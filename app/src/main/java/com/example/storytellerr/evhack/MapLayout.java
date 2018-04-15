package com.example.storytellerr.evhack;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import com.google.android.gms.location.LocationListener;

import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapLayout extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private FirebaseAuth mAuth;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private DatabaseReference mDatabase;
    ArrayList<Double> lat = new ArrayList<Double>();
    ArrayList<Double> lon = new ArrayList<Double>();
    ArrayList<String> shopname = new ArrayList<String>();
    ArrayList<Integer> stars = new ArrayList<Integer>();
    ArrayList<Long> phone = new ArrayList<Long>();
    ArrayList<String> address = new ArrayList<String>();



    ProgressBar progressBar,progressBar2;
    Button btn1,btn2;
    LinearLayout ll1,ll2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maplayout);

        mAuth=FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Shops");
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        ll1=findViewById(R.id.leniar1);
        ll2=findViewById(R.id.leniar2);
        btn1=findViewById(R.id.first);
        btn2=findViewById(R.id.second);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll2.setVisibility(View.GONE);
                ll1.setVisibility(View.VISIBLE);
                getdata();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll1.setVisibility(View.GONE);
                ll2.setVisibility(View.VISIBLE);

            }
        });


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        getdata();
    }
    public void getdata() {

        progressBar.setVisibility(View.VISIBLE);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            int i=0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Log.d("da", data.child("lon").getValue().toString());
                    Log.d("n",data.getKey());
                    lat.add((Double) data.child("lat").getValue());
                    lon.add((Double) data.child("lon").getValue());
                    shopname.add(data.child("name").getValue().toString());
                    Log.d("fe","f");
                    //phone.add(Long.parseLong(data.child("phone").getValue().toString()));
                    Log.d("fe1","f");
                    stars.add(Integer.parseInt(data.child("stars").getValue().toString()));
                    address.add(data.child("address").getValue().toString());
                }
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(MapLayout.this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        for (int i = 0; i < lat.size(); i++) {
            LatLng p = new LatLng(lat.get(i), lon.get(i));
            googleMap.addMarker(new MarkerOptions().position(p)
                    .title(shopname.get(i)));
            //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p, 10));
        }
        progressBar.setVisibility(View.GONE);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        mMap.setOnMarkerClickListener(this);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());


        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }

    public void appointment(View v) {
        Toast.makeText(this,"dwd",Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            sendToStart();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String id = marker.getId();
        id = id.substring(1);
        Log.d("c", id);
        Bundle args = new Bundle();
        args.putString("name", marker.getTitle());
        Location destination = new Location(marker.getTitle());
        destination.setLatitude(lat.get(Integer.parseInt(id)));
        destination.setLatitude(lon.get(Integer.parseInt(id)));
        //current location
        Location currentpos = new Location("current");
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            destination.setLatitude(myLocation.getLatitude());
            destination.setLatitude(myLocation.getLongitude());
        }
       Float distance= currentpos.distanceTo(destination);
        args.putString("distance", distance.toString());
        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
        args.putString("stars",stars.get(Integer.parseInt(id)).toString());
        args.putString("address",address.get(Integer.parseInt(id)));
       // args.putString("phone",phone.get(Integer.parseInt(id)).toString());

        bottomSheetFragment.setArguments(args);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

        //data retrieveing





        return false;
    }


    private void sendToStart() {

        Intent startIntent = new Intent(MapLayout.this, LoginActivity.class);
        startActivity(startIntent);
        finish();

    }

}

































/*
public class MapLayout extends AppCompatActivity  implements OnMapReadyCallback {

    private DatabaseReference mDatabase;
    ArrayList<Double> lat = new ArrayList<Double>();
    ArrayList<Double> lon = new ArrayList<Double>();
    ArrayList<String> shopname = new ArrayList<String>();
    ProgressBar progressBar;
    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.maplayout);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Shops");
        // new getLatlong().execute();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        getdata();


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        for (int i = 0; i < lat.size(); i++) {
            LatLng p = new LatLng(lat.get(i), lon.get(i));
            googleMap.addMarker(new MarkerOptions().position(p)
                    .title(shopname.get(i)));
            //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p, 10));
        }


        progressBar.setVisibility(View.GONE);
    }

    public void getdata() {
        progressBar.setVisibility(View.VISIBLE);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Log.d("da", data.child("lon").getValue().toString());
                    lat.add((Double) data.child("lat").getValue());
                    lon.add((Double) data.child("lon").getValue());
                    shopname.add(data.child("name").toString());
                }
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(MapLayout.this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}*/
