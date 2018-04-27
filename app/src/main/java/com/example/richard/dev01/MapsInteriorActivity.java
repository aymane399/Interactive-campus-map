package com.example.richard.dev01;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.util.TypedValue;







import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import android.graphics.drawable.Drawable;
import android.graphics.BitmapFactory;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;


import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;




//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;


public class MapsInteriorActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public static int id_batiment;


    //Polygones des batiments//
    private Polygon polygonei3;
    private Polygon polygoneB03;

    private GroundOverlay imageOverlay;




    private CameraPosition cameraPosition;




    private static final String TAG = MapsInteriorActivity.class.getSimpleName();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapsinterior);
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



    CameraPosition newcameraposition(LatLng position,int val_zoom) { //Fonction : centrer sur une coordonnée avec une valeur de zoom
        cameraPosition = new CameraPosition.Builder()
                .target(position)      // Sets the center of the map to imt
                .zoom(val_zoom)                   // Sets the zoom
                .bearing(-24)                // Sets the orientation of the camera to imt north
                .build();                   // Creates a CameraPosition from the builder

        return(cameraPosition);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {



        /////
        /////INITIALISATION DES COORDONNES/////
                                          /////

        mMap = googleMap;

        TypedValue typedValue = new TypedValue();


        //CENTRE I1
        getResources().getValue(R.dimen.posi1x, typedValue, true);
        float posi1x = typedValue.getFloat();

        getResources().getValue(R.dimen.posi1y, typedValue, true);
        float posi1y = typedValue.getFloat();

        final LatLng posi1 = new LatLng(posi1y,posi1x);


        //CENTRE I3
        getResources().getValue(R.dimen.posi3x, typedValue, true);
        float posi3x = typedValue.getFloat();

        getResources().getValue(R.dimen.posi3y, typedValue, true);
        float posi3y = typedValue.getFloat();

        final LatLng posi3 = new LatLng(posi3y,posi3x);

        //CENTRE B03
        getResources().getValue(R.dimen.posB03x, typedValue, true);
        float posB03x = typedValue.getFloat();

        getResources().getValue(R.dimen.posB03y, typedValue, true);
        float posB03y = typedValue.getFloat();

        final LatLng posB03 = new LatLng(posB03y,posB03x);






        //RESTRICTION ZOOM
        mMap.setMaxZoomPreference(22f); //Zoom maximal
        mMap.setMinZoomPreference(20f); //Zoom Minimal
        //////////////////////////////////////////////









        ///STYLE DE LA MAP/// (Par défaut pour l'instant)
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json_interior));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        /////////////////////////////////////////////////////////



        if (MapsActivity.id_batiment == 3) {

            //polygonI3////////////////////////////////////////////////////////

            PolygonOptions rectOptionsI3 = new PolygonOptions()
                    .add(new LatLng(48.358136, -4.571402))
                    .add(new LatLng(48.358184, -4.571225))
                    .add(new LatLng(48.358000, -4.571101))
                    .add(new LatLng(48.357957, -4.571068))
                    .add(new LatLng(48.357991, -4.570939))
                    .add(new LatLng(48.357893, -4.570872))
                    .add(new LatLng(48.357808, -4.571171))
                    .add(new LatLng(48.357947, -4.571288))
                    .add(new LatLng(48.358136, -4.571402))  // Closes the polyline.
                    .clickable(true)
                    .zIndex(0) //Position z
                    .fillColor(Color.GRAY);


            // Ajout du polygone I3 sur la carte
            polygonei3 = mMap.addPolygon(rectOptionsI3);
            polygonei3.setTag("I3"); //permet de différencier les polygones

            //Moving Camera to I3
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posi3,20));
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newcameraposition(posi3,20)));

            //RESTRICTION DE LA CARTE
            LatLngBounds RESTRICIMT = new LatLngBounds(
                    new LatLng(48.357791, -4.571434) , new LatLng(48.358215, -4.570825));
            // Constrain the camera target to the IMT Atlantique bounds (BAS-GAUCHE,HAUT-DROITE)
            // TODO : ROTATION DES LATLNGBOUNDS.
            mMap.setLatLngBoundsForCameraTarget(RESTRICIMT);
        }

        else if (MapsActivity.id_batiment == 15) {

            //polygonB03///////////////////////////////////////////////////////

            PolygonOptions rectOptionsB03 = new PolygonOptions()
                    .add(new LatLng(48.358468, -4.570853))
                    .add(new LatLng(48.358533, -4.570646))
                    .add(new LatLng(48.358510, -4.570631))
                    .add(new LatLng(48.358555, -4.570483))
                    .add(new LatLng(48.358543, -4.570478))
                    .add(new LatLng(48.358571, -4.570391))
                    .add(new LatLng(48.358412, -4.570283))
                    .add(new LatLng(48.358336, -4.570540))
                    .add(new LatLng(48.358363, -4.570560))
                    .add(new LatLng(48.358311, -4.570742))  // Closes the polyline.
                    .clickable(true)
                    .zIndex(0) //Position z
                    .fillColor(Color.GRAY);

            // Ajout du polygone sur la carte
            polygoneB03 = mMap.addPolygon(rectOptionsB03);
            polygoneB03.setTag("B03"); //permet de différencier les polygones

            //Moving Camera to B03
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posB03, 20));
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newcameraposition(posB03, 20)));

            //RESTRICTION DE LA CARTE
            LatLngBounds RESTRICIMT = new LatLngBounds(
                    new LatLng(48.358201, -4.570943), new LatLng(48.358614, -4.570147));
            mMap.setLatLngBoundsForCameraTarget(RESTRICIMT);


            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.b03_plan3_reduit);

            GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory.fromBitmap(bm))
                    .position(posB03, 40f, 31f)
                    .zIndex(1)
                    .bearing(-24)
                    .anchor((float)0.5,(float)0.55);


            // Add an overlay to the map, retaining a handle to the GroundOverlay object.
            GroundOverlay imageOverlay = mMap.addGroundOverlay(newarkMap);


        }



        //Action du click sur n'importe quel polygone
        mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
            @Override
            public void onPolygonClick(Polygon polygon) {

                //Action sur les salles de classe

            }
        });







    }

}
