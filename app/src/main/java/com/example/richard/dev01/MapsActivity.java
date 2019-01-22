package com.example.richard.dev01;


import android.animation.Animator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View;
import android.animation.AnimatorListenerAdapter;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.content.Context;
import android.app.ProgressDialog;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;




import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;


import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import android.location.Location;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;

import android.Manifest;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;


import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.shredzone.commons.suncalc.SunTimes;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;







public class MapsActivity extends FragmentActivity
        implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnGroundOverlayClickListener,
        LocationListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        AdapterView.OnItemSelectedListener{

    private GoogleMap mMap;



    public static int id_batiment;


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

    //Nom icone bus
    private GroundOverlay mBus1;
    private GroundOverlay mBus2;



    private static final int default_zoom = 17;
    private final LatLng mDefaultLocation = new LatLng(48.359375, -4.570071);


    //Polygones des batiments//
    private Polygon polygonei1;
    private Polygon polygonei2;
    private Polygon polygonei3;
    private Polygon polygonei4;
    private Polygon polygonei5;
    private Polygon polygonei6;
    private Polygon polygonei7;
    private Polygon polygonei8;
    private Polygon polygonei9;
    private Polygon polygonei10;
    private Polygon polygonei11;
    private Polygon polygonei12;

    private Polygon polygoneB03;

    private LatLng posi1;
    private LatLng posi3;
    private LatLng posB03;
    private LatLng posBus1;
    private LatLng posBus2;


    //For the date and style
    private Calendar calendar = Calendar.getInstance();


    private CameraPosition cameraPosition;


    ///LOCATION ////////////////////////////////////////////////////////////////////////
    private boolean mPermissionDenied = false;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";


    private static final String TAG = MapsActivity.class.getSimpleName();

    private View mContentView;
    private View mLoadingView;
    private int mShortAnimationDuration;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;
    private LatLng current_pos;
    private boolean mLocationPermissionGranted;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionsGranted = false;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private TextView bat_name_text;


    @BindView(R.id.sunrise_tint)
    FrameLayout sunrise_filter;

    @BindView(R.id.sunset_tint)
    FrameLayout sunset_filter;





    //INFO WINDOW BOTTOM SHEET


    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;

    @BindView(R.id.plan_inte)
    Button plan_inte;

    @BindView(R.id.heure_ouvert)
    TextView heure_ouvert;

    @BindView(R.id.info_supp)
    TextView info_supp;

    @BindView(R.id.info1)
    TextView info1;

    @BindView(R.id.info2)
    TextView info2;

    BottomSheetBehavior sheetBehavior;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_main);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);






         ///TRANSITION LOADING SCREEN -> MAP
         mContentView = findViewById(R.id.map);
         mLoadingView = findViewById(R.id.frame_map);
         ImageView imageView = findViewById(R.id.imageView_loading);
         GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
         Glide.with(this).load(R.raw.loading_intensively).into(imageViewTarget);

         //BOTTOM SHEET
         bat_name_text = findViewById(R.id.bat_name);

         // Retrieve and cache the system's default "medium" animation time.
         mShortAnimationDuration = getResources().getInteger(android.R.integer.config_mediumAnimTime);


        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.Batiments, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        //INFO WINDOW BOTTOM SHEET

        ButterKnife.bind(this);


        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);




        ///LOCATION

        getLocationPermission();


    }















    @Override
    public void onMapReady(GoogleMap googleMap) {


        /////
        /////INITIALISATION DES COORDONNES/////
        /////

        mMap = googleMap;

        UiSettings UiSet = mMap.getUiSettings();
        UiSet.setCompassEnabled(true);
        UiSet.setMapToolbarEnabled(false);
        UiSet.setZoomControlsEnabled(true);

        // Prompt the user for permission.
        //getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        //updateLocationUI();

        // Get the current location of the device and set the position of the map.
        //getDeviceLocation();


        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        TypedValue typedValue = new TypedValue();


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

        //CENTRE Bus vers mairie
        getResources().getValue(R.dimen.posBus1x, typedValue, true);
        float posBus1x = typedValue.getFloat();

        getResources().getValue(R.dimen.posBus1y, typedValue, true);
        float posBus1y = typedValue.getFloat();

        posBus1 = new LatLng(posBus1y,posBus1x);

        //CENTRE Bus vers Brest
        getResources().getValue(R.dimen.posBus2x, typedValue, true);
        float posBus2x = typedValue.getFloat();

        getResources().getValue(R.dimen.posBus2y, typedValue, true);
        float posBus2y = typedValue.getFloat();

        posBus2 = new LatLng(posBus2y,posBus2x);


        ///RESTRICTIONS///

        //RESTRICTION CONTOUR
        LatLngBounds RESTRICIMT = new LatLngBounds(
                new LatLng(48.353, -4.5739) , new LatLng(48.363, -4.565));
        // Constrain the camera target to the IMT Atlantique bounds (BAS-GAUCHE,HAUT-DROITE)
        // TODO : ROTATION DES LATLNGBOUNDS.
        mMap.setLatLngBoundsForCameraTarget(RESTRICIMT);

        //RESTRICTION ZOOM
        mMap.setMaxZoomPreference(19f); //Zoom maximal
        mMap.setMinZoomPreference(15f); //Zoom Minimal
        //////////////////////////////////////////////




















        ///STYLE DE LA MAP/// (En fonction de l'heure)



        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        System.out.println("HOUR IS : " + currentHour + ":" + currentMinute);

        //https://github.com/caarmen/SunriseSunset
        Calendar[] sunriseSunset = ca.rmen.sunrisesunset.SunriseSunset.getSunriseSunset(Calendar.getInstance(), 48.359375, -4.570071);
        int sunriseHour = sunriseSunset[0].get(Calendar.HOUR_OF_DAY);
        int sunriseMinute = sunriseSunset[0].get(Calendar.MINUTE);
        int sunsetHour = sunriseSunset[1].get(Calendar.HOUR_OF_DAY);
        int sunsetMinute = sunriseSunset[1].get(Calendar.MINUTE);


        System.out.println("Sunrise at: " + sunriseHour + ":" + sunriseMinute);
        System.out.println("Sunset at: " + sunsetHour + ":"+ sunsetMinute);


        //Standardisation de l'heure Sunset sunrise :
        //Intervalle Sunrise :
        int min_sunriseMinute = 0;
        int min_sunriseHour = 0;
        int max_sunriseMinute = 0;
        int max_sunriseHour = 0;
        if (sunriseMinute < 30){
            min_sunriseMinute = sunriseMinute + 30;
            min_sunriseHour = sunriseHour - 1;
            max_sunriseMinute = sunriseMinute + 30;
            max_sunriseHour = sunriseHour;
        }
        else if (sunriseMinute >= 30){
            min_sunriseMinute = sunriseMinute - 30;
            min_sunriseHour = sunriseHour;
            max_sunriseMinute = sunriseMinute - 30;
            max_sunriseHour = sunriseHour + 1;
        }



        int min_sunsetMinute = 0;
        int min_sunsetHour = 0;
        int max_sunsetMinute = 0;
        int max_sunsetHour = 0;
        if (sunsetMinute < 30){
            min_sunsetMinute = sunsetMinute + 30;
            min_sunsetHour = sunsetHour - 1;
            max_sunsetMinute = sunsetMinute + 30;
            max_sunsetHour = sunsetHour;
        }
        else if (sunsetMinute >= 30){
            min_sunsetMinute = sunsetMinute - 30;
            min_sunsetHour = sunsetHour;
            max_sunsetMinute = sunsetMinute - 30;
            max_sunsetHour = sunsetHour + 1;
        }


        //Permet de comparer l'heure actuelle avec les heures min/max de sunrise/sunset
        //Valeur des min/max en minute
        int sum_min_sunrise = min_sunriseHour*60 + min_sunriseMinute;
        int sum_max_sunrise = max_sunriseHour*60 + max_sunriseMinute;
        int sum_min_sunset = min_sunsetHour*60 + min_sunsetMinute;
        int sum_max_sunset = max_sunsetHour*60 + max_sunsetMinute;

        //Valeur heure actuelle en minute
        int sum_current = currentHour*60 + currentMinute;




        System.out.println("min_sunrise at: " + min_sunriseHour + ":" + min_sunriseMinute);
        System.out.println("max_sunrise at: " + max_sunriseHour + ":" + max_sunriseMinute);
        System.out.println("min_sunset at: " + min_sunsetHour + ":" + min_sunsetMinute);
        System.out.println("max_sunset at: " + max_sunsetHour + ":" + max_sunsetMinute);








        try {

            //Met le style "NIGHT"
            if ( (sum_current >= sum_max_sunset) || (sum_current < sum_min_sunrise)) {
                boolean success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                this, R.raw.style_json));

                if (!success) {
                    Log.e(TAG, "Style parsing failed.");
                }

                System.out.println("NIGHT");
            }

            //Met le style "DAY"
            else {

                boolean success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                this, R.raw.style_json_day));

                if (!success) {
                    Log.e(TAG, "Style parsing failed.");
                }


                //Met le style "DAY" sans le filtre sunset/sunrise
                if ( ((sum_current >= sum_min_sunrise)
                        && (sum_current < sum_max_sunrise))
                        || ((sum_current >= sum_min_sunset)
                        && (sum_current < sum_max_sunset))
                         ) {
                    sunrise_filter.setVisibility(View.VISIBLE);
                    sunset_filter.setVisibility(View.VISIBLE);
                    System.out.println("SUNRISE OR SUNSET");
                }

                //Met le filtre "DAY" avec le filtre sunset/sunrise
                else {
                    System.out.println("DAY");
                }
            }


        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        /////////////////////////////////////////////////////////







        // Add a marker in IMT Atlantique and move the camera
        LatLng imt = new LatLng(48.359375,  -4.570071);


        mMap.addMarker(new MarkerOptions()
                .position(mDefaultLocation)
                .title("IMT Atlantique")
                .snippet("L'école d'ingénieur la plus proche de New York")
                .infoWindowAnchor(0.5f, 0.5f));
        ///////////////////////////////////////////////////////////////////


        //Place la caméra sur IMT Atlantique
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newcameraposition(mDefaultLocation,default_zoom)));







        //////NOM DES BATIMENTS//////


        //I1

        mI1nom = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.mipmap.nomi1))
                .bearing(-24)
                .zIndex(1) //Position z
                .position(posi1, 15f, 15f));

        mI1nom.setTag("Bâtiment I1");


        //I3

        mI3nom = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.mipmap.nomi3))
                .bearing(-24)
                .zIndex(1) //Position z
                .position(posi3, 15f, 15f));

        mI3nom.setTag("Bâtiment I3");


        //TODO faire pareil en changeant les noms de batiments et en mettant les bonnes coordonnées



        ///ICONE BUS///

        mBus1 = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.mipmap.bus_logo1))
                .bearing(-24)
                .clickable(true)
                .zIndex(2) //Position z
                .position(posBus1, 16f, 16f));

        mBus1.setTag("Bus vers mairie");

        mBus2 = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.mipmap.bus_logo1))
                .bearing(-24)
                .clickable(true)
                .zIndex(2) //Position z
                .position(posBus2, 16f, 16f));

        mBus2.setTag("Bus vers Brest");

        //AUTRE ICONE

//        mSurp = mMap.addGroundOverlay(new GroundOverlayOptions()
//                .image(BitmapDescriptorFactory.fromResource(R.mipmap.bus_logo1))
//                .bearing(-24)
//                .clickable(true)
//                .zIndex(2) //Position z
//                .position(posSurp, 18f, 18f));
//
//        mSurp.setTag("Bus vers Brest");



        //////
        //////FORME DES BATIMENTS//////
                                 /////////////////////////////////////////////////////////////////////////////////


        //polygonI1////////////////////////////////////////////////////////
        PolygonOptions rectOptionsI1 = new PolygonOptions()
                .add(new LatLng(48.357696, -4.571037))
                .add(new LatLng(48.357734, -4.570897))
                .add(new LatLng(48.357714, -4.570882))
                .add(new LatLng(48.357725, -4.570846))
                .add(new LatLng(48.357590, -4.570764))
                .add(new LatLng(48.357621, -4.570651))
                .add(new LatLng(48.357519, -4.570578))
                .add(new LatLng(48.357440, -4.570854)) // Closes the polyline.
                .clickable(true)
                .zIndex(0) //Position z
                .fillColor(Color.GRAY)
                .strokeWidth(2f);


        // Ajout du polygone I1 sur la carte
        polygonei1 = mMap.addPolygon(rectOptionsI1);
        polygonei1.setTag("I1"); //permet de différencier les polygones


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
                .fillColor(Color.GRAY)
                .strokeWidth(2f);


        // Ajout du polygone I3 sur la carte
        polygonei3 = mMap.addPolygon(rectOptionsI3);
        polygonei3.setTag("I3"); //permet de différencier les polygones



        //polygonB03///////////////////////////////////////////////////////
        PolygonOptions rectOptionsB03 = new PolygonOptions()
                .add(new LatLng(48.358468, -4.570853))
                .add(new LatLng(48.358533, -4.570646))
                .add(new LatLng(48.358510, -4.570631))
                .add(new LatLng(48.358555, -4.570483))
                .add(new LatLng(48.358571, -4.570391))
                .add(new LatLng(48.358412, -4.570277))
                .add(new LatLng(48.358336, -4.570540))
                .add(new LatLng(48.358363, -4.570560))
                .add(new LatLng(48.358311, -4.570742))  // Closes the polyline.
                .clickable(true)
                .zIndex(0) //Position z
                .fillColor(Color.GRAY)
                .strokeWidth(2f);

        // Ajout du polygone sur la carte
        polygoneB03 = mMap.addPolygon(rectOptionsB03);
        polygoneB03.setTag("B03"); //permet de différencier les polygones



        //polygonI2////////////////////////////////////////////////////////
        PolygonOptions rectOptionsI2 = new PolygonOptions()
                .add(new LatLng(48.358080, -4.570637))
                .add(new LatLng(48.358119, -4.570488))
                .add(new LatLng(48.357888, -4.570335))
                .add(new LatLng(48.357807, -4.570597))
                .add(new LatLng(48.357912, -4.570662))
                .add(new LatLng(48.357941, -4.570550))
                .add(new LatLng(48.358080, -4.570637))  // Closes the polyline.
                .clickable(true)
                .zIndex(0) //Position z
                .fillColor(Color.GRAY)
                .strokeWidth(2f);


        // Ajout du polygone I2 sur la carte
        polygonei2 = mMap.addPolygon(rectOptionsI2);
        polygonei2.setTag("I2"); //permet de différencier les polygones



        //polygonI4////////////////////////////////////////////////////////
        PolygonOptions rectOptionsI4 = new PolygonOptions()
                .add(new LatLng(48.357216, -4.570153))
                .add(new LatLng(48.357266, -4.569967))
                .add(new LatLng(48.357080, -4.569839))
                .add(new LatLng(48.357019, -4.570045))
                .add(new LatLng(48.356931, -4.570353))
                .add(new LatLng(48.357039, -4.570422))
                .add(new LatLng(48.357114, -4.570140))
                .add(new LatLng(48.357124, -4.570089))
                .add(new LatLng(48.357216, -4.570153))
                .clickable(true)
                .zIndex(0) //Position z
                .fillColor(Color.GRAY)
                .strokeWidth(2f);


        // Ajout du polygone I4 sur la carte
        polygonei4 = mMap.addPolygon(rectOptionsI4);
        polygonei4.setTag("I4"); //permet de différencier les polygones



        //polygonI5////////////////////////////////////////////////////////
        PolygonOptions rectOptionsI5 = new PolygonOptions()
                .add(new LatLng(48.357196, -4.570428))
                .add(new LatLng(48.357241, -4.570264))
                .add(new LatLng(48.357484, -4.570418))
                .add(new LatLng(48.357405, -4.570700))
                .add(new LatLng(48.357302, -4.570635))
                .add(new LatLng(48.357333, -4.570516))
                .add(new LatLng(48.357196, -4.570428))

                .clickable(true)
                .zIndex(0) //Position z
                .fillColor(Color.GRAY)
                .strokeWidth(2f);


        // Ajout du polygone I5 sur la carte/////a refaire
        polygonei5 = mMap.addPolygon(rectOptionsI5);
        polygonei5.setTag("I5"); //permet de différencier les polygones



        //polygonI6////////////////////////////////////////////////////////
        PolygonOptions rectOptionsI6 = new PolygonOptions()
                .add(new LatLng(48.358033, -4.571724))
                .add(new LatLng(48.357978, -4.571894))
                .add(new LatLng(48.357631, -4.571638))
                .add(new LatLng(48.357684, -4.571448))
                .add(new LatLng(48.357756, -4.571448))
                .add(new LatLng(48.357732, -4.571513))
                .add(new LatLng(48.358033, -4.571724))
                .clickable(true)
                .zIndex(0) //Position z
                .fillColor(Color.GRAY)
                .strokeWidth(2f);



        // Ajout du polygone I6 sur la carte
        polygonei6 = mMap.addPolygon(rectOptionsI6);
        polygonei6.setTag("I6"); //permet de différencier les polygones



        //polygonI7////////////////////////////////////////////////////////
        PolygonOptions rectOptionsI7 = new PolygonOptions()
                .add(new LatLng(48.357597, -4.570410))
                .add(new LatLng(48.357642, -4.570266))
                .add(new LatLng(48.357580, -4.570224))
                .add(new LatLng(48.357642, -4.570015))
                .add(new LatLng(48.357569, -4.569937))
                .add(new LatLng(48.357539, -4.569947))
                .add(new LatLng(48.357436, -4.570301))
                .clickable(true)
                .zIndex(0) //Position z
                .fillColor(Color.GRAY)
                .strokeWidth(2f);


        // Ajout du polygone I7 sur la carte
        polygonei7 = mMap.addPolygon(rectOptionsI7);
        polygonei7.setTag("I7"); //permet de différencier les polygones



        //polygonI8////////////////////////////////////////////////////////
        PolygonOptions rectOptionsI8 = new PolygonOptions()
                .add(new LatLng(48.356665, -4.570722))
                .add(new LatLng(48.356703, -4.570576))
                .add(new LatLng(48.356603, -4.570510))
                .add(new LatLng(48.356617, -4.570461))
                .add(new LatLng(48.356599, -4.570447))
                .add(new LatLng(48.356676, -4.570181))
                .add(new LatLng(48.356583, -4.570119))
                .add(new LatLng(48.356454, -4.570588))
                .clickable(true)
                .zIndex(0) //Position z
                .fillColor(Color.GRAY)
                .strokeWidth(2f);


        // Ajout du polygone I8 sur la carte
        polygonei8 = mMap.addPolygon(rectOptionsI8);
        polygonei8.setTag("I8"); //permet de différencier les polygones



        //polygonI9////////////////////////////////////////////////////////
        PolygonOptions rectOptionsI9 = new PolygonOptions()
                .add(new LatLng(48.356851, -4.570088))
                .add(new LatLng(48.356976, -4.569591))
                .add(new LatLng(48.356820, -4.569492))
                .add(new LatLng(48.356759, -4.569488))
                .add(new LatLng(48.356708, -4.569688))
                .add(new LatLng(48.356824, -4.569757))
                .add(new LatLng(48.356787, -4.569908))
                .add(new LatLng(48.356772, -4.569899))
                .add(new LatLng(48.356739, -4.570026))
                .clickable(true)
                .zIndex(0) //Position z
                .fillColor(Color.GRAY)
                .strokeWidth(2f);


        // Ajout du polygone I9 sur la carte
        polygonei9 = mMap.addPolygon(rectOptionsI9);
        polygonei9.setTag("I9"); //permet de différencier les polygones



        //polygonI10////////////////////////////////////////////////////////
        PolygonOptions rectOptionsI10 = new PolygonOptions()
                .add(new LatLng(48.357673, -4.572634))
                .add(new LatLng(48.357604, -4.572891))
                .add(new LatLng(48.357808, -4.573029))
                .add(new LatLng(48.357856, -4.572846))
                .add(new LatLng(48.357763, -4.572787))
                .add(new LatLng(48.357783, -4.572704))
                .clickable(true)
                .zIndex(0) //Position z
                .fillColor(Color.GRAY)
                .strokeWidth(2f);

        polygonei10= mMap.addPolygon(rectOptionsI10);
        polygonei10.setTag("I10"); //permet de différencier les polygones








        //Action du click sur n'importe quel polygone
        mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
            @Override
            public void onPolygonClick(Polygon polygon) {
                show_bottom_sheet_poly(polygon);

            }
        });

        mMap.setOnGroundOverlayClickListener(new GoogleMap.OnGroundOverlayClickListener() {
            @Override
            public void onGroundOverlayClick(GroundOverlay groundOverlay) {

                show_bottom_sheet_ground(groundOverlay);

            }
        });








        // When map renders entirely, and display MapFragment
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback()
        {
            @Override
            public void onMapLoaded()
            {

                System.out.println("MAP READY");

                FrameLayout layout = (FrameLayout)findViewById(R.id.frame_map);
                layout.setVisibility(View.GONE);

                 mContentView.setAlpha(0f);
                 mContentView.setVisibility(View.VISIBLE);

                 // Animate the content view to 100% opacity, and clear any animation
                 // listener set on the view.
                 mContentView.animate()
                         .alpha(1f)
                         .setDuration(mShortAnimationDuration)
                         .setListener(null);

                 // Animate the loading view to 0% opacity. After the animation ends,
                 // set its visibility to GONE as an optimization step (it won't
                 // participate in layout passes, etc.)
                 mLoadingView.animate()
                         .alpha(0f)
                         .setDuration(mShortAnimationDuration)
                         .setListener(new AnimatorListenerAdapter() {
                             @Override
                             public void onAnimationEnd(Animator animation) {
                                 mLoadingView.setVisibility(View.GONE);
                             }
                         });

            }
        });

        mMap.setOnMapClickListener(this);


        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

        }




    }

    CameraPosition newcameraposition(LatLng position, int val_zoom) { //Fonction : centrer sur une coordonnée avec une valeur de zoom
        cameraPosition = new CameraPosition.Builder()
                .target(position)      // Sets the center of the map to imt
                .zoom(val_zoom)                   // Sets the zoom
                .bearing(-24)                // Sets the orientation of the camera to imt north
                .build();                   // Creates a CameraPosition from the builder

        return (cameraPosition);
    }




    private void show_bottom_sheet_poly(Polygon polygon){
        plan_inte.setTextColor(Color.parseColor("#FFFFFF"));
        plan_inte.setBackgroundColor(Color.parseColor("#000000"));

        if (polygon.getTag() == "I1"){ //click sur le bat I1
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newcameraposition(posi1,18)));
            id_batiment = 3;
            //REDONDANCE DES DEUX CONDITIONS POUR METTRE BIEN A JOUR LA FENETRE D'INFO
            if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                //INFO
                plan_inte.setVisibility(View.GONE);
                bat_name_text.setText("Bâtiment I1");
                heure_ouvert.setVisibility(View.GONE);
                info_supp.setVisibility(View.GONE);
                info1.setVisibility(View.GONE);
                info2.setVisibility(View.GONE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


            }
            else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                //INFO
                plan_inte.setVisibility(View.GONE);
                bat_name_text.setText("Bâtiment I1");
                heure_ouvert.setVisibility(View.GONE);
                info_supp.setVisibility(View.GONE);
                info1.setVisibility(View.GONE);
                info2.setVisibility(View.GONE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        }

        if (polygon.getTag() == "I3"){ //click sur le bat I3
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newcameraposition(posi3,18)));
            id_batiment = 3;
            //REDONDANCE DES DEUX CONDITIONS POUR METTRE BIEN A JOUR LA FENETRE D'INFO
            if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                //INFO
                plan_inte.setVisibility(View.GONE);
                bat_name_text.setText("Bâtiment I3");
                heure_ouvert.setVisibility(View.GONE);
                info_supp.setVisibility(View.VISIBLE);
                info1.setVisibility(View.VISIBLE);
                info1.setText("Secrétariat de la Maisel");
                info2.setVisibility(View.GONE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


            }
            else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                //INFO
                plan_inte.setVisibility(View.GONE);
                bat_name_text.setText("Bâtiment I3");
                heure_ouvert.setVisibility(View.GONE);
                info_supp.setVisibility(View.VISIBLE);
                info1.setVisibility(View.VISIBLE);
                info1.setText("Secrétariat de la Maisel");
                info2.setVisibility(View.GONE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        }

        if (polygon.getTag() == "B03"){ //click sur le bat B03
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newcameraposition(posB03, 18)));
            id_batiment = 15;
            if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                //INFO
                plan_inte.setVisibility(View.VISIBLE);
                plan_inte.setText("Plan intérieur");
                bat_name_text.setText("Bâtiment B03");
                heure_ouvert.setVisibility(View.VISIBLE);
                heure_ouvert.setText("Heures ouverture/fermeture : 7h00 - 23h00");
                info_supp.setVisibility(View.GONE);
                info1.setVisibility(View.GONE);
                info2.setVisibility(View.GONE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


            }
            else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                //INFO
                plan_inte.setVisibility(View.VISIBLE);
                plan_inte.setText("Plan intérieur");
                bat_name_text.setText("Bâtiment B03");
                heure_ouvert.setVisibility(View.VISIBLE);
                heure_ouvert.setText("Heures ouverture/fermeture : 7h00 - 23h00");
                info_supp.setVisibility(View.GONE);
                info1.setVisibility(View.GONE);
                info2.setVisibility(View.GONE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }

        }


    }


    private void show_bottom_sheet_ground(GroundOverlay groundOverlay) {

        int currentDate = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println("DAY OF THE WEEK = " + currentDate);

        plan_inte.setVisibility(View.VISIBLE);
        plan_inte.setTextColor(Color.parseColor("#FFFFFF"));
        plan_inte.setBackgroundColor(Color.parseColor("#42AAEF"));
        plan_inte.setText("Horaires de bus");

        if (groundOverlay.getTag() == "Bus vers mairie") {
            DrawItinerary(posBus1);
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newcameraposition(posBus1,18)));
            System.out.println("MAIRIE BUS");
            id_batiment = 999;

            if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                //INFO
                bat_name_text.setText("Bus vers Mairie de Plouzané");
                heure_ouvert.setVisibility(View.VISIBLE);
                //Horaires du dimanche
                if (currentDate == 1){
                    heure_ouvert.setText("Premier Bus à 9h10 / Dernier bus à 00h42");
                    info_supp.setVisibility(View.VISIBLE);
                    info1.setVisibility(View.VISIBLE);
                    info1.setText("Attention : Horaires du dimanche et des jours fériés");
                    info2.setVisibility(View.GONE);
                }
                else {
                    heure_ouvert.setText("Premier Bus à 6h16 / Dernier bus à 00h42");
                    info_supp.setVisibility(View.VISIBLE);
                    info1.setVisibility(View.VISIBLE);
                    info1.setText("(Attention aux horaires des jours fériés)");
                    info2.setVisibility(View.GONE);
                }
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


            }
            else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                //INFO
                bat_name_text.setText("Bus vers Mairie de Plouzané");
                heure_ouvert.setVisibility(View.VISIBLE);
                if (currentDate == 1){
                    heure_ouvert.setText("Premier Bus à 9h10 / Dernier bus à 00h42");
                    info_supp.setVisibility(View.VISIBLE);
                    info1.setVisibility(View.VISIBLE);
                    info1.setText("Attention : Horaires du dimanche et des jours fériés");
                    info2.setVisibility(View.GONE);
                }
                else {
                    heure_ouvert.setText("Premier Bus à 6h16 / Dernier bus à 00h42");
                    info_supp.setVisibility(View.VISIBLE);
                    info1.setVisibility(View.VISIBLE);
                    info1.setText("(Attention aux horaires des jours fériés)");
                    info2.setVisibility(View.GONE);
                }
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }

        }

        else if (groundOverlay.getTag() == "Bus vers Brest") {
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newcameraposition(posBus2,18)));
            System.out.println("BREST BUS");
            id_batiment = 998;
            if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                //INFO
                plan_inte.setVisibility(View.VISIBLE);
                plan_inte.setText("Horaires de bus");
                bat_name_text.setText("Bus vers Fort Montbarey (Brest)");
                heure_ouvert.setVisibility(View.VISIBLE);
                if (currentDate == 1){
                    heure_ouvert.setText("Premier Bus à 9h24 / Dernier bus à 23h57");
                    info_supp.setVisibility(View.VISIBLE);
                    info1.setVisibility(View.VISIBLE);
                    info1.setText("Attention : Horaires du dimanche et des jours fériés");
                    info2.setVisibility(View.GONE);
                }
                else {
                    heure_ouvert.setText("Premier Bus à 5h52 / Dernier bus à 23h56");
                    info_supp.setVisibility(View.VISIBLE);
                    info1.setVisibility(View.VISIBLE);
                    info1.setText("(Attention aux horaires des jours fériés)");
                    info2.setVisibility(View.GONE);
                }
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


            }
            else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                //INFO
                plan_inte.setVisibility(View.VISIBLE);
                plan_inte.setText("Horaires de bus");
                bat_name_text.setText("Bus vers Fort Montbarey (Brest)");
                heure_ouvert.setVisibility(View.VISIBLE);
                if (currentDate == 1){
                    heure_ouvert.setText("Premier Bus à 9h24 / Dernier bus à 23h57");
                    info_supp.setVisibility(View.VISIBLE);
                    info1.setVisibility(View.VISIBLE);
                    info1.setText("Attention : Horaires du dimanche et des jours fériés");
                    info2.setVisibility(View.GONE);
                }
                else {
                    heure_ouvert.setText("Premier Bus à 5h52 / Dernier bus à 23h56");
                    info_supp.setVisibility(View.VISIBLE);
                    info1.setVisibility(View.VISIBLE);
                    info1.setText("(Attention aux horaires des jours fériés)");
                    info2.setVisibility(View.GONE);
                }
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }

        }


    }










    @OnClick(R.id.plan_inte)
    public void change_activity() {
        if (id_batiment == 15){ //click sur le bat B03
            startActivity(new Intent(MapsActivity.this, MapsInteriorActivity.class));
        }

        else if (id_batiment == 999){
            startActivity(new Intent(MapsActivity.this, AffichageImage.class));
        }

        else if (id_batiment == 998){
            startActivity(new Intent(MapsActivity.this, AffichageImage.class));
        }

    }

    //HIDE THE BOTTOM SHEET

    @Override
    public void onBackPressed() {
        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        else {
            this.finishAffinity();

        }

    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }



    @Override
    public void onGroundOverlayClick(GroundOverlay groundOverlay) {

    }



    /*** LISTE DES BATIMENTS ***//////////////////////////////////////////////////////:


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
        LatLng li1 = new LatLng(48.357530, -4.570756);
        LatLng li2 = new LatLng(48.357920, -4.570521);
        LatLng li3 = new LatLng(48.357916, -4.571108);
        LatLng li4 = new LatLng(48.357026, -4.570084);
        LatLng li6 = new LatLng(48.357747, -4.571592);
        LatLng li7 = new LatLng(48.357524, -4.570147);
        LatLng li9 = new LatLng(48.356824, -4.569795);
        LatLng li8 = new LatLng(48.356558, -4.570399);
        LatLng li10 = new LatLng(48.357708, -4.572834);
        LatLng li11 = new LatLng(48.357652, -4.572359);
        LatLng li12 = new LatLng(48.357915, -4.571713);
        LatLng lRAK = new LatLng(48.360097, -4.571210);
        LatLng lGymnase = new LatLng(48.358367, -4.572934);
        LatLng lB03 = new LatLng(48.358480, -4.570587);
        LatLng lFoyer = new LatLng(48.357973, -4.569120);
        String text = (String) adapterView.getItemAtPosition(i);
        LatLng dest = lB03;
        if (text.equals("Sélectionnez un bâtiment")) {
        } else {
            if (text.equals("i1")) {
                dest = li1;
            } else if (text.equals("i2")) {
                dest = li2;
            } else if (text.equals("i3")) {
                dest = li3;
                show_bottom_sheet_poly(polygonei3);
            } else if (text.equals("i4")) {
                dest = li4;
            } else if (text.equals("i6")) {
                dest = li6;
            } else if (text.equals("i7")) {
                dest = li7;
            } else if (text.equals("i8")) {
                dest = li8;
            } else if (text.equals("i9")) {
                dest = li9;
            } else if (text.equals("i10")) {
                dest = li10;
            } else if (text.equals("i11")) {
                dest = li11;
            } else if (text.equals("i12")) {
                dest = li12;
            } else if (text.equals("RAK")) {
                dest = lRAK;
            } else if (text.equals("Foyer")) {
                dest = lFoyer;
            } else if (text.equals("Gymnase")) {
                dest = lGymnase;
            } else if (text.equals("B03")) {
                dest = lB03;
                show_bottom_sheet_poly(polygoneB03);
            }


            CameraPosition cameraaPosition = new CameraPosition.Builder()
                    .target(dest)      // Sets the center of the map to imt
                    .zoom(18)                   // Sets the zoom
                    .bearing(-24)                // Sets the orientation of the camera to imt north
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraaPosition));

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    ////////////////////////////////////////////////////////


    /*** ITINERARY ***/
    private void DrawItinerary(LatLng dest) {

        if ((mLocationPermissionsGranted) & (current_pos != null)) {

            GoogleDirection.withServerKey("AIzaSyBWB_3Uekwk-ktivQ5Q-VHXjqZahEys7a0") //Clé API Google Direction
                    .from(current_pos)
                    .to(dest)
                    .transitMode(TransportMode.WALKING)
                    .execute(new DirectionCallback() {
                        @Override
                        public void onDirectionSuccess(Direction direction, String rawBody) {
                            if (direction.isOK()) {

                                List<Step> stepList = direction.getRouteList().get(0).getLegList().get(0).getStepList();
                                ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(getApplicationContext(), stepList, 3, Color.BLUE, 3, Color.BLUE);
                                for (PolylineOptions polylineOption : polylineOptionList) {
                                    mMap.addPolyline(polylineOption);
                                }

                                System.out.println("Direction OK");


                            } else {
                                System.out.println("Direction pas OK");


                            }
                        }

                        @Override
                        public void onDirectionFailure(Throwable t) {
                            System.out.println("Direction pas du tout OK");
                        }
                    });
        }

        else {
            System.out.println("NO CURRENT POSITION");
        }
    }










    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }



    /**
     * Gets the current location of the device, and positions the map's camera.
     */

    private Location currentLocation;

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            currentLocation = (Location) task.getResult();




                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }



    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Handles the result of the request for location permissions.
     */


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    initMap();
                }
            }
        }
    }






    @Override
    public boolean onMyLocationButtonClick() {
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
    }


    public LocationManager locationManager;



    @Override
    public void onLocationChanged(Location location){
        //ITINERAIRE :



        //remove location callback:
        locationManager.removeUpdates(this);

        double current_pos_y = location.getLatitude();
        double current_pos_x = location.getLongitude();
        current_pos = new LatLng(current_pos_y,current_pos_x);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }













}
