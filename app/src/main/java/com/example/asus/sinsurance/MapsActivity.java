package com.example.asus.sinsurance;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnInfoWindowLongClickListener  {

    private GoogleMap mMap;
    GPSTracker gpsTracker;
    Location currentLocation;
    double currentLat;
    double currentLon;
    LatLng position;


    //---------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button okBtn = (Button) findViewById(R.id.btn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String url = "http://thingtalk.ir/update?key=YMU7N9CO82ZRGFO7&field1="+SerialClass.Serial+
                        "&field2="+String.valueOf(currentLat)+"&field3="+String.valueOf(currentLon);


                try {
                    new HttpPostRequest().execute(url).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(getApplicationContext() , GeneralInfoActivity.class);
                startActivity(i);
                finish();

            }
        });

    }


    public void onMapReady(GoogleMap map) {
        mMap = map;
        // Retrieve location and camera position from saved instance state.
        Log.e("create", "ddd");
        gpsTracker = GPSTracker.getInstance(this);
        Log.e("create", String.valueOf(gpsTracker.canGetLocation()));
        if(!gpsTracker.canGetLocation())
            gpsTracker.showSettingsAlert();

        currentLocation = gpsTracker.getLocation();
        if(currentLocation != null) {
            Log.e("create", "d");
            currentLat = currentLocation.getLatitude();
            currentLon = currentLocation.getLongitude();
            position = new LatLng(currentLat, currentLon);
            Log.e("gps ", currentLat + "");
            Log.e("gps ", currentLon + "");

        }


        mMap.addMarker(new MarkerOptions()
                .position(position)
                .snippet(position.toString())
                .title("here")
                .draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
        ).showInfoWindow();



    }
    /*
        private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
            String strAdd = "";
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
                if (addresses != null) {
                    Address returnedAddress = addresses.get(0);
                    StringBuilder strReturnedAddress = new StringBuilder("");

                    for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                    }
                    strAdd = strReturnedAddress.toString();
                    Log.w("My Current loction address", "" + strReturnedAddress.toString());
                } else {
                    Log.w("My Current loction address", "No Address returned!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.w("My Current loction address", "Cannot get Address!");
            }
            return strAdd;
        }
    */
    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {

    }


    //................................................................


    @Override
    public String toString() {
        return super.toString();
    }
}
