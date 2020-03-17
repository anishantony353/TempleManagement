package com.saarit.temple_management.templemanagement.view_model.not_in_use;

import android.app.Application;

import com.saarit.temple_management.templemanagement.view_model.FormType3b_1_ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MyViewModelFactory implements ViewModelProvider.Factory {

    Application application;
    String arg;

    public MyViewModelFactory(Application application, String arg){
        this.application = application;
        this.arg = arg;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new FormType3b_1_ViewModel(application,arg);
    }
}
