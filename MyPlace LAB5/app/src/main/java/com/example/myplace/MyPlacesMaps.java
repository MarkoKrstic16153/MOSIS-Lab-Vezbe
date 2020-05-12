package com.example.myplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class MyPlacesMaps extends AppCompatActivity {

    MyLocationNewOverlay myLocationOverlay =null;
    ItemizedIconOverlay myPlacesOverlay = null;
    static int NEW_PLACE = 1;
    static final int LOCATION_PERMISSION = 2;
    MapView map = null;

    IMapController mapController = null;


    public static final int SHOW_MAP = 0;
    public static final int CENTER_PLACE_ON_MAP = 1;
    public static final int SELECT_COORDINATES = 2;


    private int state = 0;
    private boolean selCoorsEnabled = false;
    private GeoPoint placeLoc = null;
    private boolean selected = false;
    private Menu meni = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_my_places_maps);
        Intent mapIntent = getIntent();
        Bundle mapBundle = mapIntent.getExtras();
        state = mapBundle.getInt("state");
        if(state == CENTER_PLACE_ON_MAP){
            String pLat = mapBundle.getString("lat");
            String pLon = mapBundle.getString("lon");
            placeLoc = new GeoPoint(Double.parseDouble(pLat),Double.parseDouble(pLon));

        }
        map = (MapView) findViewById(R.id.map);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        mapController = map.getController();
        mapController.setZoom(15.0);
        if(state == CENTER_PLACE_ON_MAP)
            mapController.animateTo(placeLoc);
        else {
            GeoPoint startPoint = new GeoPoint(43.3209, 21.8958);
            mapController.setCenter(startPoint);
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_NETWORK_STATE},LOCATION_PERMISSION);
        }
        else{

            setupMap();
        }

        findViewById(R.id.fab).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getApplicationContext(), "New Place!", Toast.LENGTH_LONG).show();
                Intent i = new Intent( getApplicationContext(),EditMyPlace.class);
                startActivityForResult(i,NEW_PLACE);
            }
        });
    }

    @SuppressLint("Missing Permissions")
    @Override
    public void onRequestPermissionsResult(int reqCode,String permissions[],int[] grantedResults ){
        setupMap();
    }

    private void setMyLocationOverlay(){
        myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this),map);
        myLocationOverlay.enableMyLocation();
        map.getOverlays().add(myLocationOverlay);
        mapController.setZoom(15.0);
        myLocationOverlay.enableFollowLocation();
    }

    private void setCenterPlaceOnMap(){
        mapController = map.getController();
        mapController.setZoom(15.0);
        mapController.setCenter(placeLoc);
    }

    private void setupMap(){
        switch (state){
            case SHOW_MAP :
                setMyLocationOverlay();
                break;
            case SELECT_COORDINATES:
                mapController = map.getController();
                if(mapController!=null){
                    mapController.setZoom(15.0);
                    GeoPoint startPoint = new GeoPoint(43.3209,21.8958);
                    mapController.setCenter(startPoint);
                    setOnClickMapOverlay();
                }
                break;
            case CENTER_PLACE_ON_MAP:
            default:
                setCenterPlaceOnMap();
                break;
        }
        showMyPlaces();
    }

    private void setOnClickMapOverlay(){
        MapEventsReceiver mReceiver = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                if(state == SELECT_COORDINATES && selCoorsEnabled == true){
                    String lon = Double.toString(p.getLongitude());
                    String lat = Double.toString(p.getLatitude());
                    Intent locationIntent  = new Intent();
                    locationIntent.putExtra("lon",lon);
                    locationIntent.putExtra("lat",lat);
                    setResult(Activity.RESULT_OK,locationIntent);
                    finish();
                }
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(mReceiver);
        map.getOverlays().add(mapEventsOverlay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(state == SELECT_COORDINATES && !selCoorsEnabled)
        {
            meni = menu;
           addMenuItems(meni);
            return super.onCreateOptionsMenu(meni);
        }
        else {
            getMenuInflater().inflate(R.menu.maps_menu, menu);
            return true;
        }
    }

    private void addMenuItems(Menu menu){
        menu.clear();
        if(selCoorsEnabled == false){
            menu.add(0,1,1,"Select Coordinates");
            menu.add(0,2,2,"Cancel");
        }
        else
        menu.add(0,1,1,"Cancel");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if(state == SELECT_COORDINATES && !selCoorsEnabled)
        {
            if(id == 1){
                selCoorsEnabled = true;
                Toast.makeText(this,"Select Coordinates!", Toast.LENGTH_LONG).show();
                addMenuItems(meni);
            }
            else if (id == 2){
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        }
        //noinspection SimplifiableIfStatement
        if(id == R.id.about){
            Toast.makeText(this,"About!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this,About.class);
            startActivity(i);
        }
        else if (id == R.id.my_places){
            Toast.makeText(this,"My Places!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, List.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
        map.onPause();
    }

    private void showMyPlaces(){
        if(myPlacesOverlay!=null)
            map.getOverlays().remove(myPlacesOverlay);
        final ArrayList<OverlayItem> overlayArrayList = new ArrayList<>();
        for(int i = 0; i<MyPlacesData.getInstance().myPlaces.size();i++){
            MyPlace mp = MyPlacesData.getInstance().getPlace(i);
            OverlayItem overlayItem = new OverlayItem(mp.name,mp.description,new GeoPoint(Double.parseDouble(mp.getLatitude()),Double.parseDouble(mp.getLongitude())));
            overlayItem.setMarker(this.getResources().getDrawable(R.drawable.elfak));
            overlayArrayList.add(overlayItem);
        }
        Toast.makeText(this,"My Places!", Toast.LENGTH_LONG).show();
        myPlacesOverlay = new ItemizedIconOverlay<>(overlayArrayList, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                Intent intent = new Intent(MyPlacesMaps.this,ViewPlace.class);
                intent.putExtra("position",index);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                Intent intent = new Intent(MyPlacesMaps.this,EditMyPlace.class);
                intent.putExtra("position",index);
                startActivityForResult(intent,5);
                return false;
            }
        },getApplicationContext());
        this.map.getOverlays().add(myPlacesOverlay);
    }



}
