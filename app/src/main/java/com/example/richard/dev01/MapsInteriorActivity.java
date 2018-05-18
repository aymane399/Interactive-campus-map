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

import android.view.View;
import android.widget.TextView;
import android.widget.Button;







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

import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;


import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;






//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;


public class MapsInteriorActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public static int id_batiment;


    //Polygones des batiments//
    private Polygon polygonei3;
    private Polygon polygoneB03;

    private GroundOverlay imageOverlay;


    private Button b1,b2;



    //DICTIONNAIRE
    private List data_bat;
    private Map<Integer , List > dict_data;

    //Position central
    private LatLng posi1;
    private LatLng posi3;
    private LatLng posB03;




    private CameraPosition cameraPosition;




    private static final String TAG = MapsInteriorActivity.class.getSimpleName();



    //Data variables
    private LatLng pos_wanted;
    private int res_wanted;
    private Tuple anchor_tuple ;
    private Tuple dim_tuple;











    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapsinterior);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        b1=findViewById(R.id.button1); //Button RDC
        b2=findViewById(R.id.button2); //Button 1E

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                button_action(MapsActivity.id_batiment,1);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                button_action(MapsActivity.id_batiment,2);
            }
        });

        TypedValue typedValue = new TypedValue();



        //INITIALISATION

        data_bat = new LinkedList();
        dict_data = new HashMap<>();
        Tuple<Float, Float> anchor_tuple;
        Tuple<Float, Float> dim_tuple;


        //CENTRE I1
        getResources().getValue(R.dimen.posi1x, typedValue, true);
        float posi1x = typedValue.getFloat();

        getResources().getValue(R.dimen.posi1y, typedValue, true);
        float posi1y = typedValue.getFloat();

        posi1 = new LatLng(posi1y,posi1x);


        //CENTRE I3
        getResources().getValue(R.dimen.posi3x, typedValue, true);
        float posi3x = typedValue.getFloat();

        getResources().getValue(R.dimen.posi3y, typedValue, true);
        float posi3y = typedValue.getFloat();

        posi3 = new LatLng(posi3y,posi3x);

        //CENTRE B03
        getResources().getValue(R.dimen.posB03x, typedValue, true);
        float posB03x = typedValue.getFloat();

        getResources().getValue(R.dimen.posB03y, typedValue, true);
        float posB03y = typedValue.getFloat();

        posB03 = new LatLng(posB03y,posB03x);

        //Data
        anchor_tuple  = new Tuple(0.49,0.55); //Centre de l'image
        dim_tuple = new Tuple(41.0,31.0); //Dimension de l'image

        data_bat.add(0,posB03); //POS
        data_bat.add(1,R.drawable.b03_plan_rdc_v2); //RDC
        data_bat.add(2,R.drawable.b03_plan_1e); //1E
        data_bat.add(3,0); //2E or -1E
        data_bat.add(4,0); //other
        data_bat.add(5,anchor_tuple); //anchor_tuple
        data_bat.add(6,dim_tuple); //dim_tuple
        dict_data.put(15,data_bat);






        //TODO : CREATE DICTIONARIES clé : id_bat (en String) // valeur : pos_... , R.drawable1... , R.drawable2...

    }




    CameraPosition newcameraposition(LatLng position,int val_zoom) { //Fonction : centrer sur une coordonnée avec une valeur de zoom
        cameraPosition = new CameraPosition.Builder()
                .target(position)      // Sets the center of the map to imt
                .zoom(val_zoom)                   // Sets the zoom
                .bearing(-24)                // Sets the orientation of the camera to imt north
                .build();                   // Creates a CameraPosition from the builder

        return(cameraPosition);
    }


    //ACTUALISATION DES PLANS SELON LES BOUTONS

    private int local_res = 0; //Permet de ne pas redessiner par dessus le même groundoverlay en cliquant sur le même bouton
    private GroundOverlay local_imageOverlay; //Permet de ne pas redessiner le même groundoverlay

    private void button_action(int id_bat,int button_number) {

        System.out.println("Button " + button_number + " clicked ");


        //RETRIEVE DATA OF ID_BAT
        pos_wanted = (LatLng) dict_data.get(id_bat).get(0); //Position voulue
        res_wanted = (int) dict_data.get(id_bat).get(button_number); //Affichage du plan
        anchor_tuple = (Tuple) dict_data.get(id_bat).get(5); //get anchor_tuple
        dim_tuple = (Tuple) dict_data.get(id_bat).get(6); //get dim_tuple

        //CONVERSION DOUBLE (8 octets) -> FLOAT (4 octets)
        float anchor_x = (float) (double) anchor_tuple.get(0);
        float anchor_y = (float) (double) anchor_tuple.get(1);
        float dim_x = (float) (double) dim_tuple.get(0);
        float dim_y = (float) (double) dim_tuple.get(1);

        System.out.println("res_wanted = " + res_wanted + " // pos_wanted = " + pos_wanted + " // local_res =  " + local_res);
        //System.out.println("local... = " + local_imageOverlay);


        if ((res_wanted != 0) && (res_wanted != local_res)) {
            local_res = res_wanted;
            Bitmap bm = BitmapFactory.decodeResource(getResources(), res_wanted);

            GroundOverlayOptions drawn_plan = new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory.fromBitmap(bm))
                    .position(pos_wanted, dim_x, dim_y)
                    .zIndex(1)
                    .bearing(-24)
                    .anchor(anchor_x,anchor_y);


            if (local_imageOverlay != null) {
                local_imageOverlay.remove(); //retire l'ancien GroundOverlay pour ne pas dépasser la mémoire
            }
            GroundOverlay imageOverlay = mMap.addGroundOverlay(drawn_plan);
            local_imageOverlay = imageOverlay;

        }



    }





    @Override
    public void onMapReady(GoogleMap googleMap) {


        /////
        /////INITIALISATION DES COORDONNES/////
                                          /////

        mMap = googleMap;

        //UiSettings
        UiSettings UiSet = mMap.getUiSettings();
        UiSet.setCompassEnabled(false);
        UiSet.setMapToolbarEnabled(false);
        UiSet.setZoomControlsEnabled(true);

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

        //FOND ECRAN POUR LES BATIMENTS
        PolygonOptions rectOptionsBlanc = new PolygonOptions()
                .add(new LatLng(48.355303, -4.575282))
                .add(new LatLng(48.360716, -4.574562))
                .add(new LatLng(48.360666, -4.568075))
                .add(new LatLng(48.355671, -4.567455))
                .zIndex(-1) //Position z
                .fillColor(Color.WHITE);

        Polygon polygoneblanc = mMap.addPolygon(rectOptionsBlanc);



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

            button_action(MapsActivity.id_batiment,1); //Affichage plan étage RDC
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
            //polygoneB03 = mMap.addPolygon(rectOptionsB03);
            //polygoneB03.setTag("B03"); //permet de différencier les polygones

            //Moving Camera to B03
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posB03, 20));
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newcameraposition(posB03, 20)));

            //RESTRICTION DE LA CARTE
            LatLngBounds RESTRICIMT = new LatLngBounds(
                    new LatLng(48.358201, -4.570943), new LatLng(48.358614, -4.570147));
            mMap.setLatLngBoundsForCameraTarget(RESTRICIMT);

            button_action(MapsActivity.id_batiment,1); //Affichage plan étage RDC


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
