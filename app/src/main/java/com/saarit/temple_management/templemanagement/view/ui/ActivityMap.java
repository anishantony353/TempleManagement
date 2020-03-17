package com.saarit.temple_management.templemanagement.view.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.databinding.ActivityMapBinding;
import com.saarit.temple_management.templemanagement.model.FormType_1;
import com.saarit.temple_management.templemanagement.util.Constant;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view_model.MapViewModel;

public class ActivityMap extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener, GoogleMap.OnInfoWindowClickListener {

    private String TAG = ActivityMap.class.getSimpleName();
    ActivityMapBinding binding;
    private MapViewModel viewModel;
    GoogleMap map;
    Marker selectedMarker;
    LatLng dragMarkerInitialLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.log(TAG,"onCreate()");
        super.onCreate(savedInstanceState);
        setupBindings(savedInstanceState);
    }

    private void setupBindings(Bundle savedInstanceState) {
        Utility.log(TAG,"setupBindings()");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map);
        viewModel = ViewModelProviders.of(this).get(MapViewModel.class);

        if (savedInstanceState == null) {
            Utility.log(TAG,"savedInstanceState == null");
            viewModel.init();
        }

        binding.setViewmodel(viewModel);
        setSupportActionBar(binding.toolbarActivityMap);
        setUpObservers();
        setUpmap();
    }

    private void setUpObservers() {
        Utility.log(TAG,"Observed: setUpObservers()");
        viewModel.getIsAddButtonClicked().observe(
                this,
                value -> {
                    Intent intent = new Intent(getBaseContext(),Form1Activity.class);
                    intent.putExtra("req_code",Constant.REQUEST_CODE_NEW_TEMPLE);
                   startActivityForResult(intent,Constant.REQUEST_CODE_NEW_TEMPLE);
                }
        );

        viewModel.getTempleToDisplay().observe(
                this,
                temple -> addTempleOnMap(temple)
        );

        viewModel.getTempleToDisplayFromServer().observe(
                this,
                temple ->{
                    Utility.log(TAG,"Observed: getTempleToDisplayFromServer()");
                    addServerTempleOnMap(temple);
                }
        );

        viewModel.getShouldAnimateMap().observe(
                this,
                latlng -> animateCamera(latlng.latitude,latlng.longitude)
        );
        viewModel.getIsDragSuccess().observe(
                this,
                value->{
                    if(true){
                        Utility.showToast("Updated Location",Toast.LENGTH_SHORT,getApplication());
                    }else{
                        Utility.showToast("Failed to Update Location",Toast.LENGTH_SHORT,getApplication());
                        selectedMarker.setPosition(dragMarkerInitialLoc);
                    }
                }
        );
        viewModel.observeLockScreenRequest().observe(
                this,
                shouldLock->{
                    if(shouldLock){
                        Utility.log(TAG,"Locking Screen...");
                        lockScreen();
                    }else{
                        Utility.log(TAG,"UnLocking Screen...");
                        unlockScreen();
                    }
                }
        );
    }

    private void lockScreen(){
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unlockScreen(){
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Utility.log(TAG,"onMapReady()");
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.setOnMarkerDragListener(this);
        map.setOnInfoWindowClickListener(this);

    }
    private void setUpmap() {
        if(map == null){
            ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map_ActivityMap)).getMapAsync(this);
        }
    }

    private void addTempleOnMap(FormType_1 temple){

        LatLng latLng;
        latLng = new LatLng(temple.latitude, temple.longitude);

        MarkerOptions markerOpts = new MarkerOptions().position(
                latLng);

        markerOpts.icon(BitmapDescriptorFactory.fromResource(R.drawable.temple_map_icon));
        Marker marker = map.addMarker(markerOpts);
        marker.setDraggable(true);
        marker.setTitle(temple.temple);
        Utility.log(TAG,"Temple Name on marker:"+temple.temple);

        viewModel.hashmapLocalTemples.put(marker, temple);

    }

    private void addServerTempleOnMap(FormType_1 temple){

        LatLng latLng;
        latLng = new LatLng(temple.latitude, temple.longitude);

        MarkerOptions markerOpts = new MarkerOptions().position(
                latLng);

        markerOpts.icon(BitmapDescriptorFactory.fromResource(R.drawable.temple_map_icon_2));
        Marker marker = map.addMarker(markerOpts);
        marker.setDraggable(false);
        marker.setTitle(temple.temple);
        Utility.log(TAG,"Server Temple Name on marker:"+temple.temple);

        viewModel.hashmapServerTemples.put(marker, temple);

    }

    private void animateCamera(double lat, double lon) {

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(lat,lon)).
                        zoom(17).build();
        map.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.satellite:
                /*prefManager.setMapselected(GoogleMap.MAP_TYPE_SATELLITE);
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);*/
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.hybrid:
                /*prefManager.setMapselected(GoogleMap.MAP_TYPE_HYBRID);
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);*/
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.terrain:
                /*prefManager.setMapselected(GoogleMap.MAP_TYPE_TERRAIN);
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);*/
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.normal:
                /*prefManager.setMapselected(GoogleMap.MAP_TYPE_NORMAL);
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);*/
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.action_getNearbyTemples:
                Utility.log(TAG,"Clicked Get Nearby Temples");
                if(Utility.hasPermission(getApplicationContext())){

                    Utility.showToast("Getting Location",Toast.LENGTH_SHORT,getApplicationContext());

                    viewModel.getNearByTemples();

                }else{

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            Constant.REQUEST_CODE_REQUEST_LOCATION_PERMISSION);
                }
                break;
            case R.id.action_clearNearbyTemples:
                viewModel.clearServerTemplesFromMap();
                break;
            case R.id.action_getLocalTemples:
                viewModel.getLocalTemples();
                break;
            case R.id.action_clearLocalTemples:
                viewModel.clearLocalTemplesFromMap();
                break;
            case R.id.action_createUser:
                startActivity(new Intent(this,Activity_CreateUser.class));
                break;

        }

        invalidateOptionsMenu();

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constant.REQUEST_CODE_REQUEST_LOCATION_PERMISSION: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    Utility.showToast("Getting Location",Toast.LENGTH_SHORT,getApplicationContext());
                    viewModel.getNearByTemples();


                } else {
                    // permission denied

                    Utility.showToast("Permission Denied",Toast.LENGTH_SHORT,getApplicationContext());
                }
                return;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.REQUEST_CODE_NEW_TEMPLE:
            case Constant.REQUEST_CODE_CLICK_SERVER_TREES:

                switch (resultCode) {

                    case RESULT_OK:
                        FormType_1 formType1 = (FormType_1) data.getSerializableExtra(Constant.KEY_ADDED_TEMPLE);
                        Utility.log(TAG,"onActivityResult()..id:"+formType1.id);
                        addTempleOnMap(formType1);
                        animateCamera(formType1.latitude,formType1.longitude);
                        break;

                    case Constant.RESULT_CODE_AFTER_FORM_SUMBIT:
                        int value = data.getIntExtra(Constant.KEY_RESHOW_LOCAL_TEMPLES,0);
                        switch(value){
                            case 1:
                                viewModel.getLocalTemples();
                                break;
                        }

                        break;

                    default:

                        break;

                }
                break;

            case Constant.REQUEST_CODE_CLICK_LOCAL_TREES:

                switch (resultCode) {

                    case RESULT_OK:
                        FormType_1 formType1 = (FormType_1) data.getSerializableExtra(Constant.KEY_ADDED_TEMPLE);
                        Utility.log(TAG,"onActivityResult()..id:"+formType1.templeId);
                        selectedMarker.hideInfoWindow();
                        selectedMarker.setTitle(formType1.temple);

                        break;

                    case Constant.RESULT_CODE_AFTER_FORM_SUMBIT:
                        int value = data.getIntExtra(Constant.KEY_RESHOW_LOCAL_TEMPLES,0);
                        switch(value){
                            case 1:
                                viewModel.getLocalTemples();
                                break;
                        }

                        break;

                    default:

                        break;

                }
                break;
        }

    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        selectedMarker = marker;
        FormType_1 temple = viewModel.hashmapLocalTemples.get(selectedMarker);
        LatLng latLng = new LatLng(temple.latitude,temple.longitude);

        dragMarkerInitialLoc = latLng;
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        showAlertDialog(marker);
    }

    private boolean showAlertDialog(final Marker m){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setTitle("Do you want to update the position?");

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(
                        "Yes",
                        (dialog,id)-> viewModel.updateDragLocation(selectedMarker)
                )
                .setNegativeButton("No", (dialog,id)->{

                            selectedMarker.setPosition(dragMarkerInitialLoc);// keep the marker where it was
                            dialog.cancel();
                        }
                );

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Utility.log(TAG,"onInfoWindowClick()");
        selectedMarker = marker;

        if(viewModel.hashmapServerTemples.get(marker) != null){


            Intent i = new Intent(this, Form1Activity.class);

            i.putExtra("temple_id",viewModel.hashmapServerTemples.get(marker).templeId);
            i.putExtra("req_code",Constant.REQUEST_CODE_CLICK_SERVER_TREES);

            startActivityForResult(i,Constant.REQUEST_CODE_CLICK_SERVER_TREES);

        }else if(viewModel.hashmapLocalTemples.get(marker) != null){

            Intent i = new Intent(this, Form1Activity.class);

            i.putExtra("id",viewModel.hashmapLocalTemples.get(marker).id);
            i.putExtra("temple_id",viewModel.hashmapLocalTemples.get(marker).templeId);
            i.putExtra("req_code",Constant.REQUEST_CODE_CLICK_LOCAL_TREES);

            startActivityForResult(i,Constant.REQUEST_CODE_CLICK_LOCAL_TREES);

        }


    }

    @Override
    protected void onPause() {
        Utility.log(TAG,"onPause()");
        super.onPause();
        /*viewModel.unRegisterReceiver();*/

    }

    @Override
    protected void onStop() {
        Utility.log(TAG,"onStop()");
        super.onStop();
    }

    @Override
    protected void onResume() {
        Utility.log(TAG,"onResume()");
        super.onResume();
        /*viewModel.setUpLocationBroadcastReceiver();*/
    }
}