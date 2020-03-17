package com.saarit.temple_management.templemanagement.util.not_in_use;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.saarit.temple_management.templemanagement.util.Utility;

import androidx.core.app.ActivityCompat;


public class ThreadCurrentLocation extends Thread {
    String TAG = ThreadCurrentLocation.class.getSimpleName();
    Context context;
    Looper looper;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback mLocationCallback;
    String intent_type;

    public ThreadCurrentLocation(Context context,String intent_type) {

        this.context = context;
        this.intent_type = intent_type;

    }

    @Override
    public void run() {
        //super.run();
        //Looper.prepare();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        //setUpLocRequest();
        //setUpLocCallback();
        //startLocationUpdates();


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(
                        location -> {
                            location = null; // Dont use Last known location
                            if (location != null) {
                                Log.i(TAG, "Last Known...Lat:" + location.getLatitude() + " Lon:" + location.getLongitude() +
                                        "Accuracy:" + location.getAccuracy());
                                broadcastLocation(location);

                            } else {
                                setUpLocRequest();
                                setUpLocCallback();
                                startLocationUpdates();
                            }
                        }
                );

    }

    private void setUpLocRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);

    }

    private void setUpLocCallback() {

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Log.i(TAG, "onLocationResult()...");
                for (Location location : locationResult.getLocations()) {

                    Log.i(TAG, "Continuous...Lat:" + location.getLatitude() + " Lon:" + location.getLongitude() +
                            "Accuracy:" + location.getAccuracy());
                    mFusedLocationClient.removeLocationUpdates(mLocationCallback);
                    broadcastLocation(location);


                }

            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
                Log.i(TAG, "onLocationAvailability()...Loc available:" + locationAvailability.isLocationAvailable());

            }
        };
    }

    private void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mFusedLocationClient.requestLocationUpdates(locationRequest,
                mLocationCallback, null);
    }

    private void broadcastLocation(Location location){
        Bundle bundle = new Bundle();

        bundle.putDouble("lat",location.getLatitude());
        bundle.putDouble("lon",location.getLongitude());

        Intent intent = new Intent(intent_type);
        intent.putExtras(bundle);

        context.sendBroadcast(intent);
        Utility.log(TAG,"Broadcasted Location");

        context = null;

        //Thread.currentThread().interrupt();



    }


}
