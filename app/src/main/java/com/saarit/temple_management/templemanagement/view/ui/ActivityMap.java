package com.saarit.temple_management.templemanagement.view.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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
import com.saarit.temple_management.templemanagement.view_model.LoginViewModel;
import com.saarit.temple_management.templemanagement.view_model.MapViewModel;

public class ActivityMap extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener, GoogleMap.OnInfoWindowClickListener {

    private String TAG = ActivityMap.class.getSimpleName();
    ActivityMapBinding binding;
    private MapViewModel viewModel;
    GoogleMap map;
    Marker selectedMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupBindings(savedInstanceState);
    }

    private void setupBindings(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map);
        viewModel = ViewModelProviders.of(this).get(MapViewModel.class);

        if (savedInstanceState == null) {
            viewModel.init();
        }

        binding.setViewmodel(viewModel);
        setSupportActionBar(binding.toolbarActivityMap);
        setUpObservers();
        setUpmap();

    }

    private void setUpObservers() {
        viewModel.getIsAddButtonClicked().observe(
                this,
                value -> startActivityForResult(new Intent(getBaseContext(),Form1Activity.class),Constant.REQUEST_CODE_NEW_TEMPLE)
        );

        viewModel.getTempleToDisplay().observe(
                this,
                temple -> addTempleOnMap(temple)
        );

        viewModel.getShouldAnimateMap().observe(
                this,
                temple -> animateCamera(temple.latitude,temple.longitude)
        );
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Utility.log(TAG,"onMapReady()");
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.getUiSettings().setMapToolbarEnabled(false);
        //Utilities.setGoogleMapUiSettings(map);
        map.setOnMarkerDragListener(this);
        map.setOnInfoWindowClickListener(this);
        /*map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View view = getLayoutInflater().inflate(R.layout.info_window_layout,null);

                TextView id =  view.findViewById(R.id.treeId);
                TextView girth =  view.findViewById(R.id.girth);
                TextView name =  view.findViewById(R.id.treeName);
                TextView height =  view.findViewById(R.id.height);

                if(hashmapNearbytree.get(marker) != null){
                    name.setText(hashmapNearbytree.get(marker).getTreeName());
                    id.setText("Id: "+hashmapNearbytree.get(marker).getTreeDetailsId());
                    girth.setText("Girth: "+hashmapNearbytree.get(marker).getGirth());
                    height.setText("Height :"+hashmapNearbytree.get(marker).getHeight());
                }else if(hashmapLocaltrees.get(marker) != null){
                    name.setText(hashmapLocaltrees.get(marker).getLocalName());
                    id.setText("Id: "+hashmapLocaltrees.get(marker).getId());
                    girth.setText("Girth: "+hashmapLocaltrees.get(marker).getGrithCm());
                    height.setText("Height :"+hashmapLocaltrees.get(marker).getHeightMtr());

                }


                return view;
            }
        });*/


    }
    private void setUpmap() {
        if(map == null){
            ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map_ActivityMap)).getMapAsync(this);
        }
    }

    private void addTempleOnMap(FormType_1 temple){

        LatLng latLng;
        latLng = new LatLng(temple.getLatitude(), temple.getLongitude());

        MarkerOptions markerOpts = new MarkerOptions().position(
                latLng);

        markerOpts.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker));
        Marker marker = map.addMarker(markerOpts);
        marker.setDraggable(true);
        marker.setTitle(temple.temple);

        viewModel.hashmapLocalTemples.put(marker, temple);

    }

    private void animateCamera(double lat, double lon) {

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(lat,lon)).
                        zoom(17).build(); //location.getLatitude(),location.getLongitude()...19.17419383407308,72.86094389855862
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
                /*if(Utilities.hasPermission(this)){

                    ThreadCurrentLocation threadCurrentLocation = new ThreadCurrentLocation(this);
                    threadCurrentLocation.start();

                    Utilities.showToast("Getting current location",Toast.LENGTH_SHORT,this);

                }else{
                    Utilities.requestForPermission(this);
                }*/
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

        }

        invalidateOptionsMenu();

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.REQUEST_CODE_NEW_TEMPLE:

                switch (resultCode) {

                    case RESULT_OK:
                        FormType_1 formType1 = (FormType_1) data.getSerializableExtra(Constant.KEY_ADDED_TEMPLE);
                        Utility.log(TAG,"onActivityResult()..id:"+formType1.templeId);
                        addTempleOnMap(formType1);
                        animateCamera(formType1.latitude,formType1.longitude);
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

                    default:

                        break;

                }
                break;

        }

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Utility.log(TAG,"onInfoWindowClick()");
        selectedMarker = marker;

        if(viewModel.hashmapServerTemples.get(marker) != null){


            Intent i = new Intent(this, Form1Activity.class);

            i.putExtra("id",viewModel.hashmapServerTemples.get(marker).templeId);
            i.putExtra("req_code",Constant.REQUEST_CODE_CLICK_SERVER_TREES);

            startActivityForResult(i,Constant.REQUEST_CODE_CLICK_SERVER_TREES);

        }else if(viewModel.hashmapLocalTemples.get(marker) != null){

            Intent i = new Intent(this, Form1Activity.class);

            i.putExtra("id",viewModel.hashmapLocalTemples.get(marker).templeId);
            i.putExtra("req_code",Constant.REQUEST_CODE_CLICK_LOCAL_TREES);

            startActivityForResult(i,Constant.REQUEST_CODE_CLICK_LOCAL_TREES);

        }


    }
}