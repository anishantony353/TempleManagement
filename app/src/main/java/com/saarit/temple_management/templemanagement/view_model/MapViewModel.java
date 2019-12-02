package com.saarit.temple_management.templemanagement.view_model;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.saarit.temple_management.templemanagement.model.FormType_1;
import com.saarit.temple_management.templemanagement.model.ThreadCurrentLocation;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_FormType_1;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_server;
import com.saarit.temple_management.templemanagement.util.Constant;
import com.saarit.temple_management.templemanagement.util.Utility;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.EmptyResultSetException;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MapViewModel extends AndroidViewModel {

    private static String TAG = MapViewModel.class.getSimpleName();
    private Location locNorth,locEast,locSouth,locWest;

    public MapViewModel(@NonNull Application application) {
        super(application);
    }

    Repo_FormType_1 repo_formType_1;
    Repo_server repo_server;

    public MutableLiveData<Boolean> startActivityBooleanMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<FormType_1> formType1MutableLiveData = new MutableLiveData<>();
    public MutableLiveData<FormType_1> formType1ServerMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<LatLng> animateMapBooleanMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> dragSuccessBooleanMutableLiveData = new MutableLiveData<>();

    public ObservableInt progressVisibility = new ObservableInt(View.GONE);


    private CompositeDisposable disposable = new CompositeDisposable();

    public HashMap<Marker,FormType_1> hashmapLocalTemples;
    public HashMap<Marker,FormType_1> hashmapServerTemples;

    BroadcastReceiver locationBroadCastReceiver;
    ThreadCurrentLocation threadCurrentLocation = new ThreadCurrentLocation(getApplication(),Constant.BROADCAST_INTENT_MAP_ACTIVITY);

    public void init(){
        Utility.log(TAG,"init()");
        repo_formType_1 = Repo_FormType_1.getInstance(getApplication());
        repo_server = Repo_server.getInstance();
        hashmapLocalTemples = new HashMap<>();
        hashmapServerTemples = new HashMap<>();


    }

    public void onAddClick(View view){
        Utility.log(TAG,"onAddClick()");
        startActivityBooleanMutableLiveData.setValue(true);
    }

    public void getLocalTemples() {

        Utility.log(TAG,"getLocalTemples()");

        disposable.add(

                repo_formType_1.getLocalTemples().toObservable()
                        .flatMap(
                                list->{

                                    if(list.size() == 0){
                                        throw new EmptyResultSetException("");
                                    }else{
                                        return Observable.fromIterable(list);

                                    }

                                }
                        )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(

                                obj-> formType1MutableLiveData.setValue(obj)
                                ,
                                throwable->{

                                    //Utilities.dismissDialog();
                                    Log.i(TAG,"Exception accured",throwable);

                                    if(throwable instanceof EmptyResultSetException){
                                        Utility.log(TAG,"Count is 0");
                                        //toolbar.setTitle("Count: 0");
                                        Utility.showToast("No Local temples",Toast.LENGTH_SHORT,getApplication());
                                    }else{
                                        Utility.showToast("Error displaying temples",Toast.LENGTH_SHORT,getApplication());
                                    }

                                },
                                ()->{

                                    Utility.log(TAG,"Completed Displaying temples");
                                    Utility.showToast("Displayed temples sucessfully",Toast.LENGTH_SHORT,getApplication());

                                    Collection<FormType_1> setOfMarkers = hashmapLocalTemples.values();

                                    FormType_1 localTemple = setOfMarkers.iterator().next();

                                    animateMapBooleanMutableLiveData.setValue(new LatLng(localTemple.latitude,localTemple.longitude));
                                },
                                dspsbl->{

                                    Utility.log(TAG,"initializing ");
                                    clearLocalTemplesFromMap();
                                    //Utilities.getProgressDialog(this,"Displaying Local Trees...");
                                }

                        )

        );

    }

    public void clearLocalTemplesFromMap(){
        if(hashmapLocalTemples != null && hashmapLocalTemples.size() != 0){
            Set<Marker> setOfMarkers = hashmapLocalTemples.keySet();
            Iterator<Marker> iterator = setOfMarkers.iterator();
            while (iterator.hasNext()){
                Marker marker = iterator.next();
                marker.remove();
            }
        }
        hashmapLocalTemples.clear();
    }

    public void clearServerTemplesFromMap(){
        if(hashmapServerTemples != null && hashmapServerTemples.size() != 0){
            Set<Marker> setOfMarkers = hashmapServerTemples.keySet();
            Iterator<Marker> iterator = setOfMarkers.iterator();
            while (iterator.hasNext()){
                Marker marker = iterator.next();
                marker.remove();
            }
        }
        hashmapServerTemples.clear();
    }

    public void getNearByTemples(){

        Utility.showToast("Getting Current Location",Toast.LENGTH_LONG,getApplication());
        progressVisibility.set(View.VISIBLE);


        threadCurrentLocation.start();

    }


    public LiveData<Boolean> getIsAddButtonClicked(){
        Utility.log(TAG,"getIsAddButtonClicked()");

        return startActivityBooleanMutableLiveData;
    }
    public LiveData<FormType_1> getTempleToDisplay(){
        Utility.log(TAG,"getTempleToDisplay()");

        return formType1MutableLiveData;
    }
    public LiveData<FormType_1> getTempleToDisplayFromServer(){
        Utility.log(TAG,"getTempleToDisplayFromServer()");

        return formType1ServerMutableLiveData;
    }
    public LiveData<LatLng> getShouldAnimateMap(){
        Utility.log(TAG,"getShouldAnimateMap()");

        return animateMapBooleanMutableLiveData;
    }

    public LiveData<Boolean> getIsDragSuccess(){
        Utility.log(TAG,"getIsDragSuccess()");

        return dragSuccessBooleanMutableLiveData;
    }


    public void updateDragLocation(Marker m) {
        FormType_1 temple = hashmapLocalTemples.get(m);
        LatLng latLng = m.getPosition();
        temple.latitude = latLng.latitude;
        temple.longitude = latLng.longitude;

        disposable.add(
                repo_formType_1.insertForm(temple)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                value -> {
                                    if(value == -1){
                                        dragSuccessBooleanMutableLiveData.setValue(false);
                                    }else{
                                        dragSuccessBooleanMutableLiveData.setValue(true);
                                    }
                                },
                                throwable -> dragSuccessBooleanMutableLiveData.setValue(false)

                        )
        );
    }

    public void setUpLocationBroadcastReceiver() {



        locationBroadCastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Utility.log(TAG,"onReceive() Broadcast");
                Bundle bundle = intent.getExtras();
                Double latitude = bundle.getDouble("lat");
                Double longitude = bundle.getDouble("lon");

                Utility.log("Lat n lon ", "Latitude: " + latitude
                        + " Longitude " + longitude);

                animateMapBooleanMutableLiveData.setValue(new LatLng(latitude,longitude));

                Location location = new Location("");
                location.setLatitude(latitude);
                location.setLongitude(longitude);


                getBounds(location);


            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCAST_INTENT_MAP_ACTIVITY);
        getApplication().registerReceiver(locationBroadCastReceiver, filter);
    }

    public void unRegisterReceiver(){
        getApplication().unregisterReceiver(locationBroadCastReceiver);
        if(threadCurrentLocation.isAlive()){
            threadCurrentLocation.interrupt();
        }
        progressVisibility.set(View.GONE);
    }

    public void getBounds(Location loc){

        disposable.add(
                Observable.just(0,90,180,270)
                        .flatMap(
                                degree-> Observable.just(degree)
                                        .map(value->GetPoint(loc,degree))
                                        .subscribeOn(Schedulers.computation())
                        )
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                value -> {},
                                throwable -> {
                                    progressVisibility.set(View.GONE);
                                    Utility.showToast(throwable.getMessage(),Toast.LENGTH_LONG,getApplication());
                                },
                                () -> {
                                    //Utilities.dismissDialog();

                                    getNearbyTemplesFromServer();

                                },
                                dsposble-> Utility.showToast("Getting Bounds",Toast.LENGTH_LONG,getApplication())

                        )
        );

    }

    private void getNearbyTemplesFromServer() {
        Utility.log(TAG,"getLocalTemples()");

        disposable.add(

                repo_server.apiService.getNearbyTemples(
                            locNorth.getLatitude(),
                            locSouth.getLatitude(),
                            locWest.getLongitude(),
                            locEast.getLongitude()
                        ).toObservable()
                        .flatMap(
                                dto->{

                                    if(dto.getSuccess() == 0){
                                        throw new Exception(dto.getMsg());
                                    }else{
                                        return Observable.fromIterable(dto.getFormType_1_List());

                                    }

                                }
                        )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(

                                obj-> formType1ServerMutableLiveData.setValue(obj)

                                ,
                                throwable->{

                                    Log.i(TAG,"Exception accured",throwable);

                                    Utility.showToast(throwable.getMessage(),Toast.LENGTH_SHORT,getApplication());
                                    progressVisibility.set(View.GONE);


                                },
                                ()->{

                                    Utility.log(TAG,"Completed Displaying Nearby temples");
                                    Utility.showToast("Displayed Nearby temples sucessfully",Toast.LENGTH_SHORT,getApplication());
                                    progressVisibility.set(View.GONE);


                                },
                                dspsbl->{

                                    Utility.log(TAG,"initializing ");
                                    Utility.showToast("Fetching Temples from Server",Toast.LENGTH_LONG,getApplication());
                                    clearServerTemplesFromMap();
                                    //Utilities.getProgressDialog(this,"Displaying Local Trees...");
                                }

                        )

        );


    }

    public boolean GetPoint(Location startLoc, float bearing) throws Exception
    {
        Utility.log(TAG,"Thread for degree.."+bearing+":"+Thread.currentThread().getName());

        float depth = 1000;

        Location newLocation = new Location("newLocation");

        double radius = 6371.0 * 1000; // earth's mean radius in mtr
        double lat1 = Math.toRadians(startLoc.getLatitude());//"19.17419383407308" //startLoc.getLatitude()
        double lng1 = Math.toRadians(startLoc.getLongitude());//"72.86094389855862" //startLoc.getLongitude()
        double brng = Math.toRadians(bearing);
        double lat2 = Math.asin( Math.sin(lat1)* Math.cos(depth/radius) + Math.cos(lat1)* Math.sin(depth/radius)* Math.cos(brng) );
        double lng2 = lng1 + Math.atan2(Math.sin(brng)* Math.sin(depth/radius)* Math.cos(lat1), Math.cos(depth/radius)- Math.sin(lat1)* Math.sin(lat2));
        lng2 = (lng2+ Math.PI)%(2* Math.PI) - Math.PI;

        // normalize to -180...+180
        if (lat2 == 0 || lng2 == 0)
        {
            newLocation.setLatitude(0.0);
            newLocation.setLongitude(0.0);
        }
        else
        {
            newLocation.setLatitude(Math.toDegrees(lat2));
            newLocation.setLongitude(Math.toDegrees(lng2));
        }

        switch((int)bearing){
            case 0:
                locNorth = newLocation;
                break;
            case 90:
                locEast = newLocation;
                break;
            case 180:
                locSouth = newLocation;
                break;
            case 270:
                locWest = newLocation;
                break;
        }

        return true;
    }

    @Override
    protected void onCleared() {
        //super.onCleared();
        Utility.log(TAG,"onCleared()");
        disposable.clear();
        //unRegisterReceiver();
    }
}
