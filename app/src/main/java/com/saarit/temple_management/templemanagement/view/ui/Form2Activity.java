package com.saarit.temple_management.templemanagement.view.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;


import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.databinding.ActivityForm2Binding;
import com.saarit.temple_management.templemanagement.util.Constant;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view_model.FormType2_ViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class Form2Activity extends AppCompatActivity {

    private String TAG = Form2Activity.class.getSimpleName();
    ActivityForm2Binding binding;
    FormType2_ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.log(TAG,"onCreate()");
        setupBindings(savedInstanceState);
        setUpObservers();
    }

    private void setupBindings(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_form2);
        viewModel = ViewModelProviders.of(this).get(FormType2_ViewModel.class);

        if(savedInstanceState == null){
            viewModel.init(getIntent().getIntExtra(Constant.KEY_EXTRA_TEMPLE_ID,0));
        }

        binding.setViewmodel(viewModel);
    }

    private void setUpObservers() {

        viewModel.shouldFinishActivity().observe(
                this,
                value-> finish()


        );

        viewModel.getImageFromCamera().observe(
                this,
                value->{
                    Utility.log(TAG,"Observed getImage Request");
                    startActivityForResult(viewModel.takePictureIntent, viewModel.imageReqType);
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
