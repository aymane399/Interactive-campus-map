package com.example.richard.dev01;

import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String TAG = MapsActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near IMT Atlantique, France.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

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
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }



        // Add a marker in IMT Atlantique and move the camera
        LatLng imt = new LatLng(48.359375,  -4.570071);
        mMap.addMarker(new MarkerOptions()
                .position(imt)
                .title("IMT Atlantique")
                .snippet("L'école d'ingénieur la plus proche de New York")
                .infoWindowAnchor(0.5f, 0.5f));


        //RESTRICTION DE LA CARTE
        LatLngBounds ADELAIDE = new LatLngBounds(
                new LatLng(48.353, -4.5739) , new LatLng(48.363, -4.565));
        // Constrain the camera target to the IMT Atlantique bounds (BAS-GAUCHE,HAUT-DROITE)
        // TODO : ROTATION DES LATLNGBOUNDS.
        mMap.setLatLngBoundsForCameraTarget(ADELAIDE);

        //mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(ADELAIDE, 0));

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ADELAIDE.getCenter(), 10));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(imt)      // Sets the center of the map to imt
                .zoom(17)                   // Sets the zoom
                .bearing(-24)                // Sets the orientation of the camera to imt north
                .build();                   // Creates a CameraPosition from the builder



        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(imt,20));

        mMap.animateCamera(CameraUpdateFactory.zoomIn());

        mMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mMap.setMaxZoomPreference(19f); //Zoom maximal
        mMap.setMinZoomPreference(15f); //Zoom Minimal



    }
}
