/*
* Pin each campus of Algonquin College on a Google map
*
* @author BO LIAO (liao0015)
* @version 2.0
*@Date Oct 13, 2015
*
* */


package com.algonquincollege.liao0015.googlemapfinder;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, Constants {

    private GoogleMap mMap;
    private Geocoder mGeocode;
    private String newUserInput;
    private EditText userInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        //instantiate Geocode
        mGeocode = new Geocoder(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        //Add a marker for a user entered location version 2.0
        userInput = (EditText)findViewById(R.id.userLocation);

        userInput.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL && (event.getAction() == KeyEvent.ACTION_DOWN || event.getAction() == KeyEvent.KEYCODE_ENTER)) {

                    //Toast.makeText(getApplicationContext(), "Hello World", Toast.LENGTH_LONG).show();
                    newUserInput = userInput.getText().toString();
                    MapsActivity.this.pin(newUserInput);
                    userInput.setText("");
                    return true;
                }
                else{
                    return false;
                }
            }
        });
    }

    /*Locate and pin locationName to the map*/
    private void pin(String locationName) {
        try {

            Address address = mGeocode.getFromLocationName(locationName,1).get(0);
            LatLng ll = new LatLng( address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(ll).title(locationName));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(ll));
            Toast.makeText(this, "Pinned: " + locationName, Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(this, "Not found: " + locationName, Toast.LENGTH_LONG).show();
        }
    }
}
