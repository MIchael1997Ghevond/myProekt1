package com.example.renthome;

import android.Manifest;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsSearch extends FragmentActivity implements GoogleMap.InfoWindowAdapter, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {


    private GoogleMap mMap;
    public Marker location;
    public Marker location1;
    public Marker location3;
    LocationManager lm;
    public EditText editText;
    public int radius1 = 100;
    ArrayList<Marker> arr = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_search);
        editText = (EditText) findViewById(R.id.editText2);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
        Location location2 = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        // TODO: Before enabling the My Location layer, you must request
        // location permission from the user. This sample does not include
        // a request for location permission.
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);


        Intent intent1 = getIntent();
        Double x = intent1.getDoubleExtra("longitude", 0);
        Double y = intent1.getDoubleExtra("latitude", 0);
        String str = intent1.getStringExtra("Address1");
        String floor = intent1.getStringExtra("Floor1");
        String price = intent1.getStringExtra("Price1");
        String phone_number = intent1.getStringExtra("Phone_number1");

        LatLng latLng = new LatLng(y, x);
        LatLng latLng1 = new LatLng(40.183745, 44.527232);
        LatLng latLng2 = new LatLng(40.185464, 44.532347);
        LatLng latLng3 = new LatLng(location2.getLatitude(), location2.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng3, 17f));

        location3 = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Address-" + str).visible(false)
                .snippet("floor-" + floor + "\n" + "Price-" + price + "\n" + "Phone Number-" + phone_number));

        location = mMap.addMarker(new MarkerOptions().position(latLng1).visible(false).title("hello").icon(BitmapDescriptorFactory.defaultMarker()));
        location1 = mMap.addMarker(new MarkerOptions().position(latLng2).visible(false).title("michael").icon(BitmapDescriptorFactory.defaultMarker()));
        arr.add(location);
        arr.add(location1);
        arr.add(location3);
        //ehgvuhwoiuhpwuheiwhguih
        mMap.setInfoWindowAdapter(this);
        final Button buttonRadius2 = (Button) findViewById(R.id.radius_500);
        buttonRadius2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    radius1 = 0;
                    for (int i = 0; i < arr.size(); i++) {
                        arr.get(i).setVisible(false);

                    }
                    if (ContextCompat.checkSelfPermission(MapsSearch.this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    } else {
                        // Show rationale and request permission.
                    }

                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();

                    String str = editText.getText().toString();
                    radius1 = Integer.parseInt(str);
                    for (int i = 0; i < arr.size(); i++) {

                        if (getDistance(latitude, longitude, arr.get(i).getPosition().latitude, arr.get(i).getPosition().longitude) < radius1) {
                            arr.get(i).setVisible(true);
                            arr.get(i).showInfoWindow();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    double getDistance(double LAT1, double LONG1, double LAT2, double LONG2) {
        double distance = 2 * 6371000 * Math.asin(Math.sqrt(Math.pow((Math.sin((LAT2 * (3.14159 / 180) - LAT1 * (3.14159 / 180)) / 2)), 2) + Math.cos(LAT2 * (3.14159 / 180)) * Math.cos(LAT1 * (3.14159 / 180)) * Math.sin(Math.pow(((LONG2 * (3.14159 / 180) - LONG1 * (3.14159 / 180)) / 2), 2))));
        return distance;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();


    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
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
        LinearLayout infoView = new LinearLayout(MapsSearch.this);
        LinearLayout.LayoutParams infoViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        infoView.setOrientation(LinearLayout.HORIZONTAL);
        infoView.setLayoutParams(infoViewParams);

        ImageView infoImageView = new ImageView(MapsSearch.this);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.homesecuritymonitoringsmall, null);
        infoImageView.setImageDrawable(drawable);
        infoView.addView(infoImageView);


        LinearLayout subInfoView = new LinearLayout(MapsSearch.this);
        LinearLayout.LayoutParams subInfoViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        subInfoView.setOrientation(LinearLayout.VERTICAL);
        subInfoView.setLayoutParams(subInfoViewParams);

        TextView subInfoTit = new TextView(MapsSearch.this);
        subInfoTit.setText(marker.getTitle());
        TextView subInfoSnip = new TextView(MapsSearch.this);
        subInfoSnip.setText(marker.getSnippet());
        subInfoView.addView(subInfoTit);
        subInfoView.addView(subInfoSnip);
        infoView.addView(subInfoView);

        return infoView;
    }

    @Override
    public void onBackPressed() {

        try {
            Intent intent = new Intent(MapsSearch.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }

}
