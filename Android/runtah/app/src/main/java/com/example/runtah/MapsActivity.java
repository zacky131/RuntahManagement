package com.example.runtah;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    Double latitude;
    Double longitude;
    Double FillLevel;
    String SerialNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e("Map", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("Map", "Can't find style. Error: ", e);
        }

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.child("locations").getChildren()){
                    latitude = child.child("Latitude").getValue(Double.class);
                    longitude = child.child("Longitude").getValue(Double.class);
                    FillLevel = child.child("FillLevel").getValue(Double.class);
                    SerialNumber = child.child("SerialNumber").getValue(String.class);


                    LatLng latLng = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(SerialNumber)
                            .snippet("Fill Level = " + FillLevel )
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.open_trash)));
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        // Construct a CameraPosition focusing on Jakarta and animate the camera to that position.
//        LatLng jakarta = new LatLng(-6.182181,106.828523);
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(jakarta)      // Sets the center of the map to Jakarta
//                .zoom(8)                   // Sets the zoom
//                .bearing(0)                // Sets the orientation of the camera to east
//                .tilt(0)                   // Sets the tilt of the camera to 30 degrees
//                .build();                   // Creates a CameraPosition from the builder
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));






//        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
//        LatLng jakarta = new LatLng(-6.182181,106.828523);
//        if(location != null) {
//            CameraPosition cameraPosition = new CameraPosition.Builder()
//                    .target(jakarta)
//                    .zoom(13)
//                    .build();
//            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//        }
    }
}


