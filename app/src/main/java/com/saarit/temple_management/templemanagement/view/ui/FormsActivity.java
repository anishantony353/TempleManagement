package com.saarit.temple_management.templemanagement.view.ui;


import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.databinding.ActivityFormsBinding;
import com.saarit.temple_management.templemanagement.model.not_in_use.FormName;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view_model.FormsViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class FormsActivity extends AppCompatActivity {

    private String TAG = FormsActivity.class.getSimpleName();

    private FormsViewModel viewModel;
    ActivityFormsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  DataBindingUtil.setContentView(this,R.layout.activity_forms);

        setupBindings(binding,savedInstanceState);

    }

    private void setupBindings(ActivityFormsBinding binding, Bundle savedInstanceState) {

        //ActivityDogBreedsBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_dog_breeds);
        viewModel = ViewModelProviders.of(this).get(FormsViewModel.class);

        if (savedInstanceState == null) {
            viewModel.init();
        }
        binding.setVm(viewModel);

        setUpObserver();

        if (savedInstanceState == null) {
            setupList();
        }
    }

    private void setupList() {
        viewModel.loading.set(View.VISIBLE);
        viewModel.fetchList();

    }

    private void setUpObserver() {

        Utility.log(TAG,"setUpObserver()");

        viewModel.getList().observe(this, new Observer<List<FormName>>() {
            @Override
            public void onChanged(@Nullable List<FormName> forms) {

                Utility.log(TAG,"New List received...");

                viewModel.loading.set(View.GONE);

                if(forms.size() > 0){

                    viewModel.showEmpty.set(View.GONE);
                    viewModel.setListInAdapter(forms);

                    //Update RecyclerView Adapter from ViewModel

                }else{

                    viewModel.showEmpty.set(View.VISIBLE);

                }
            }
        });

        viewModel.getSelectedForm().observe(this, new Observer<FormName>() {
            @Override
            public void onChanged(@Nullable FormName form) {

                Utility.log(TAG,"Selected Form received...");
                startActivity(new Intent(getBaseContext(),Form1Activity.class));

            }
        });
    }
}
