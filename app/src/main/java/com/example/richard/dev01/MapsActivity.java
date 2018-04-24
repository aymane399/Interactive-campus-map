package com.example.richard.dev01;

import android.content.res.Resources;
import android.graphics.Color;
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
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;

//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    //Nom des batiments//
    private GroundOverlay mI1nom;
    private GroundOverlay mI2nom;
    private GroundOverlay mI3nom;
    private GroundOverlay mI4nom;
    private GroundOverlay mI5nom;
    private GroundOverlay mI6nom;
    private GroundOverlay mI7nom;
    private GroundOverlay mI8nom;
    private GroundOverlay mI9nom;
    private GroundOverlay mI10nom;
    private GroundOverlay mI11nom;
    private GroundOverlay mI12nom;

    
    //Polygones des batiments//
    private Polygon polygonei3;




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




        //////NOM DES BATIMENTS//////


        //I1

        mI1nom = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.mipmap.nomi1))
                .bearing(-24)
                .zIndex(1)
                .position(new LatLng(48.357574, -4.570841), 15f, 15f)
                .clickable(true));

        mI1nom.setTag("Bâtiment I1");


        //I3 48.357958, -4.571160

        mI3nom = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.mipmap.nomi3))
                .bearing(-24)
                .zIndex(1)
                .position(new LatLng(48.357958, -4.571160), 15f, 15f)
                .clickable(true));

        mI3nom.setTag("Bâtiment I3");


        //TODO faire pareil en changeant les noms de batiments et en mettant les bonnes coordonnées




        //////FORME DES BATIMENTS//////

        //polygonI3

        PolygonOptions rectOptions = new PolygonOptions()
                .add(new LatLng(48.358136, -4.571402))
                .add(new LatLng(48.358184, -4.571225))
                .add(new LatLng(48.358000, -4.571101))
                .add(new LatLng(48.357957, -4.571068))
                .add(new LatLng(48.357991, -4.570939))
                .add(new LatLng(48.357893, -4.570872))
                .add(new LatLng(48.357808, -4.571171))
                .add(new LatLng(48.357947, -4.571288))
                .add(new LatLng(48.358136, -4.571402))  // Closes the polyline.
                .zIndex(0)
                .fillColor(Color.BLUE);

        // Get back the mutable Polygon
        polygonei3 = mMap.addPolygon(rectOptions);


    }
}
