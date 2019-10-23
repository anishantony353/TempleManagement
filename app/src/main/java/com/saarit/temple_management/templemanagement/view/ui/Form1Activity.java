package com.saarit.temple_management.templemanagement.view.ui;

import android.os.Bundle;


import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.databinding.ActivityForm1Binding;
import com.saarit.temple_management.templemanagement.model.SuccessOrFailure;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view_model.FormType1_ViewModel;

import androidx.appcompat.app.AppCompatActivity;
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

    private void setUpObservers() {

        viewModel.getSubmitResponse().observe(this, new Observer<SuccessOrFailure>() {
            @Override
            public void onChanged(SuccessOrFailure successOrFailure) {

                Utility.log(TAG,"from getSubmitResponse()");
            }
        });

        viewModel.getOnLocationClick().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {

                Utility.log(TAG,"from getOnLocationClick");

            }
        });
    }

    private void setupBindings(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_form1);
        viewModel = ViewModelProviders.of(this).get(FormType1_ViewModel.class);

        if(savedInstanceState == null){
            viewModel.init(1);
        }
        binding.setViewmodel(viewModel);
    }
}
