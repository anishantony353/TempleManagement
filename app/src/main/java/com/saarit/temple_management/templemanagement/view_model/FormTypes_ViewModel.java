package com.saarit.temple_management.templemanagement.view_model;

import android.app.Application;
import android.view.View;
import android.widget.AdapterView;

import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.util.Utility;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class FormTypes_ViewModel extends AndroidViewModel {

    private String TAG = FormTypes_ViewModel.class.getSimpleName();
    public FormTypes_ViewModel(@NonNull Application application) {
        super(application);
    }

    public AdapterView.OnItemClickListener listner;
    public ObservableField<AdapterView.OnItemClickListener> observableListner = new ObservableField<>();

    //LiveMutable to be observed by actiivty
    MutableLiveData<String> activityToStart = new MutableLiveData<>();

    //Methods
    public void init(int form_or_temple_id) {
        Utility.log(TAG,"form_or_temple_id:"+form_or_temple_id);

        listner = (adapterView, view,  i,  l) ->{
            String selectedItem = (String)adapterView.getAdapter().getItem(i);
                Utility.log(TAG,"onItemClick::"+selectedItem);
            activityToStart.setValue(selectedItem);

            };

        observableListner.set(listner);
    }

    public LiveData<String> observe_activity_to_start(){
        return activityToStart;
    }
}
