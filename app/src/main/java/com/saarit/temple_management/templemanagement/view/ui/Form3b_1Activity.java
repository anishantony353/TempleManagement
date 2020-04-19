package com.saarit.temple_management.templemanagement.view.ui;

import android.os.Bundle;
import android.view.WindowManager;

import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.databinding.ActivityForm3b1Binding;
import com.saarit.temple_management.templemanagement.util.Constant;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view.listeners.DateSetListner;
import com.saarit.temple_management.templemanagement.view_model.FormType3b_1_ViewModel;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class Form3b_1Activity extends AppCompatActivity implements DateSetListner {

    private String TAG = Form3b_1Activity.class.getSimpleName();
    ActivityForm3b1Binding binding;
    FormType3b_1_ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.log(TAG,"onCreate()");
        setupBindings(savedInstanceState);
        setUpObservers();
    }

    private void setupBindings(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_form3b_1);
        /*viewModel = ViewModelProviders.of(this).get(FormType3b_1_ViewModel.class);*/
        viewModel = ViewModelProviders.of(this).get(FormType3b_1_ViewModel.class);

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

        viewModel.shouldShowDatePicker().observe(
                this,
                value -> showDatePickerDialog()
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

    private void showDatePickerDialog() {

        FragDatePicker newFragment = new FragDatePicker();
        newFragment.setOnDateSetListner(this);

        newFragment.show(this.getSupportFragmentManager(),"datePicker");

    }
    @Override
    public void onDateSet(int year, int month, int day) {
        viewModel.setDate(Utility.formatDate(year,month,day));
        /*TV_transplant_date.setText(Utilities.formatDate(year,month,day));*/

    }
}
