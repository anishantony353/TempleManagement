package com.saarit.temple_management.templemanagement.view.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.databinding.ActivityLoginBinding;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view_model.LoginViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


public class LoginActivity extends AppCompatActivity {

    private String TAG = LoginActivity.class.getSimpleName();

    ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.log(TAG,"After onCreate()....LifeCycle State:"+getLifecycle().getCurrentState().name());
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
        setupShouldLockScreenObserver();
    }

    private void setupIsSuccessfullLoginObserver() {


        viewModel.getIsSucessfulLogin().observe(
                this,
                successOrFailure-> {

                        Utility.log(TAG,"onChanged()");
                        Utility.log(TAG,"From onObserveLiveData....LifeCycle State:"+getLifecycle().getCurrentState().name());

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
                                        Utility.showToast(successOrFailure.getMsg(),Toast.LENGTH_LONG,getApplication());

                                        //Toast here

                                        break;
                                }

                        }else{
                            Utility.log(TAG,"SuccessOrFailure:NULL");

                        }
            }
        );

    }

    private void setupShouldLockScreenObserver(){
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
    protected void onResume() {
        super.onResume();
        Utility.log(TAG,"From onResume()....LifeCycle State:"+getLifecycle().getCurrentState().name());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utility.log(TAG,"From onPause()....LifeCycle State:"+getLifecycle().getCurrentState().name());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Utility.log(TAG,"From onStop()....LifeCycle State:"+getLifecycle().getCurrentState().name());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utility.log(TAG,"From onDestroy()....LifeCycle State:"+getLifecycle().getCurrentState().name());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Utility.log(TAG,"From onStart()....LifeCycle State:"+getLifecycle().getCurrentState().name());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Utility.log(TAG,"From onRestart()....LifeCycle State:"+getLifecycle().getCurrentState().name());
    }
}
