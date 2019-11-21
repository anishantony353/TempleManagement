package com.saarit.temple_management.templemanagement.view.ui;


import android.content.Intent;
import android.os.Bundle;
import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.databinding.ActivityLoginBinding;
import com.saarit.temple_management.templemanagement.model.SuccessOrFailure;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view_model.LoginViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class LoginActivity extends AppCompatActivity {

    private String TAG = LoginActivity.class.getSimpleName();

    ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        setupBindings(savedInstanceState);

    }

    private void setupBindings(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        //viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        //viewModel = new ViewModelProvider(LoginViewModel.class);

        if (savedInstanceState == null) {
            viewModel.init();
        }

        binding.setViewmodel(viewModel);
        setupIsSuccessfullLoginObserver();
    }

    private void setupIsSuccessfullLoginObserver() {


        viewModel.getIsSucessfulLogin().observe(
                this,
                successOrFailure-> {

                        Utility.log(TAG,"onChanged()");

                        if(successOrFailure != null){
                            Utility.log(TAG,"SuccessOrFailure: NOT NULL");

                                switch (successOrFailure.getSuccess()){
                                    case 1:
                                        Utility.log(TAG,"Success:1");
                                        startActivity(new Intent(getBaseContext(),ActivityMap.class));
                                        //startActivity(new Intent(getBaseContext(),Form1Activity.class));
                                        break;

                                    case 0:
                                        Utility.log(TAG,"Success:0");

                                        //Toast here

                                        break;
                                }

                        }else{
                            Utility.log(TAG,"SuccessOrFailure:NULL");

                        }

            }
        );

    }
}
