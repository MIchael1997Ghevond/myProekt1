package com.example.renthome;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsInput extends FragmentActivity implements  GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMyLocationButtonClickListener,
        DialogInterface.OnClickListener{

    private GoogleMap mMap;
    private LatLng mClickPos;
    private Marker location;
    public String str;
    public String floor;
    public String price;
    public String phone_number;
    LocationManager lm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_input);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        Intent intent = getIntent();
        str = intent.getStringExtra("Address");
        floor = intent.getStringExtra("Floor");
        price = intent.getStringExtra("Price");
        phone_number = intent.getStringExtra("Phone_number");

        location = mMap.addMarker(new MarkerOptions().position(mClickPos).title("Address-" + str)
                .snippet("floor-" + floor + "\n" + "Price-" + price + "\n" + "Phone Number-" + phone_number));
        mMap.setInfoWindowAdapter(this);
        location.showInfoWindow();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
        Location location1 = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        // TODO: Before enabling the My Location layer, you must request
        // location permission from the user. This sample does not include
        // a request for location permission.
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);


        LatLng latLng3 = new LatLng(location1.getLatitude(), location1.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng3, 17f));

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mClickPos = latLng;
                new AlertDialog.Builder(MapsInput.this)
                        .setPositiveButton("Create", MapsInput.this)
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        final Button buttonnext = (Button) findViewById(R.id.button65);
        buttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent1 = new Intent(MapsInput.this, MapsSearch.class);

                    intent1.putExtra("latitude", mClickPos.latitude);
                    intent1.putExtra("longitude", mClickPos.longitude);
                    intent1.putExtra("Address1", str);
                    intent1.putExtra("Floor1", floor);
                    intent1.putExtra("Price1", price);
                    intent1.putExtra("Phone_number1", phone_number);
                    startActivity(intent1);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        final Button buttonDelete = (Button) findViewById(R.id.button64);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    location.remove();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBackPressed() {

        try {
            Intent intent = new Intent(MapsInput.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }


    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {


        Toast.makeText(this, "Info window clicked",
                Toast.LENGTH_SHORT).show();


    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return prepareInfoWindow(marker);
    }

    private View prepareInfoWindow(Marker marker) {
        LinearLayout infoView = new LinearLayout(MapsInput.this);
        LinearLayout.LayoutParams infoViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        infoView.setOrientation(LinearLayout.HORIZONTAL);
        infoView.setLayoutParams(infoViewParams);

        ImageView infoImageView = new ImageView(MapsInput.this);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.homesecuritymonitoringsmall, null);
        infoImageView.setImageDrawable(drawable);
        infoView.addView(infoImageView);


        LinearLayout subInfoView = new LinearLayout(MapsInput.this);
        LinearLayout.LayoutParams subInfoViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        subInfoView.setOrientation(LinearLayout.VERTICAL);
        subInfoView.setLayoutParams(subInfoViewParams);

        TextView subInfoTit = new TextView(MapsInput.this);
        subInfoTit.setText(marker.getTitle());
        TextView subInfoSnip = new TextView(MapsInput.this);
        subInfoSnip.setText(marker.getSnippet());
        subInfoView.addView(subInfoTit);
        subInfoView.addView(subInfoSnip);
        infoView.addView(subInfoView);

        return infoView;
    }
}
