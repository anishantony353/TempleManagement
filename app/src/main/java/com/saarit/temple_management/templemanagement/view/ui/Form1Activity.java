package com.saarit.temple_management.templemanagement.view.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.maps.model.LatLng;
import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.databinding.ActivityForm1Binding;
import com.saarit.temple_management.templemanagement.model.SuccessOrFailure;
import com.saarit.temple_management.templemanagement.util.Constant;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view_model.FormType1_ViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class Form1Activity extends AppCompatActivity {

    private String TAG = Form1Activity.class.getSimpleName();

    ActivityForm1Binding binding;
    FormType1_ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.log(TAG,"onCreate()");
        //setContentView(R.layout.activity_form1);
        setupBindings(savedInstanceState);
        setUpObservers();
    }

    private void setupBindings(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_form1);
        viewModel = ViewModelProviders.of(this).get(FormType1_ViewModel.class);

        if(savedInstanceState == null){
            viewModel.init(getIntent().getIntExtra("req_code",0),
                    getIntent().getIntExtra("id",0));
        }
        binding.setViewmodel(viewModel);

    }

    private void setUpObservers() {

        viewModel.getSubmitResponse().observe(
                this,
                response -> Utility.log(TAG,"Observed Submit Response")
        );

        viewModel.getOnLocationClick().observe(
                this,
                value -> {
                    Utility.log(TAG,"Observed Location Click");
                    if(Utility.hasPermission(getApplicationContext())){

                        Utility.showToast("Getting Location",Toast.LENGTH_SHORT,getApplicationContext());

                        viewModel.getCurrentLocation();

                    }else{

                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                Constant.REQUEST_CODE_REQUEST_LOCATION_PERMISSION);
                    }
                }

        );

        viewModel.getOnTempleAdded().observe(
                    this,
                    temple -> {
                        Utility.log(TAG,"Observed Temple Added");
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(Constant.KEY_ADDED_TEMPLE, (Serializable) temple);
                        setResult(Activity.RESULT_OK,resultIntent);
                        finish();

                    }

                );
        viewModel.getImageFromCamera().observe(
                this,
                value->{
                    Utility.log(TAG,"Observed getImage Request");
                    startActivityForResult(viewModel.takePictureIntent, viewModel.imageReqType);
                }
        );

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
                    viewModel.getCurrentLocation();


                } else {
                    // permission denied

                    Utility.showToast("Permission Denied",Toast.LENGTH_SHORT,getApplicationContext());
                }
                return;
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult()");

        if (resultCode == RESULT_OK) {

            viewModel.setImageOnImageView(requestCode);

        }
        else if( resultCode == Activity.RESULT_CANCELED){

            Utility.showToast("Could not capture Image" ,Toast.LENGTH_SHORT,getApplication());


        }

    }


}
