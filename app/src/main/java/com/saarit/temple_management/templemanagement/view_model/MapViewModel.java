package com.saarit.temple_management.templemanagement.view_model;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.model.FormType_1;
import com.saarit.temple_management.templemanagement.model.LoginFeilds;
import com.saarit.temple_management.templemanagement.model.Repo_FormType_1;
import com.saarit.temple_management.templemanagement.model.SuccessOrFailure;
import com.saarit.temple_management.templemanagement.repository.ApiService;
import com.saarit.temple_management.templemanagement.repository.Repository;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view.ui.ActivityMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.EmptyResultSetException;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MapViewModel extends AndroidViewModel {

    private static String TAG = MapViewModel.class.getSimpleName();
    public MapViewModel(@NonNull Application application) {
        super(application);
    }

    Repo_FormType_1 repo_formType_1;

    public MutableLiveData<Boolean> startActivityBooleanMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<FormType_1> formType1MutableLiveData = new MutableLiveData<>();
    public MutableLiveData<FormType_1> animateMapBooleanMutableLiveData = new MutableLiveData<>();


    private CompositeDisposable disposable = new CompositeDisposable();

    public HashMap<Marker,FormType_1> hashmapLocalTemples;
    public HashMap<Marker,FormType_1> hashmapServerTemples;

    public void init(){
        Utility.log(TAG,"init()");
        repo_formType_1 = Repo_FormType_1.getInstance(getApplication());
        hashmapLocalTemples = new HashMap<>();
        hashmapServerTemples = new HashMap<>();
    }

    public void onAddClick(View view){
        Utility.log(TAG,"onAddClick()");
        startActivityBooleanMutableLiveData.setValue(true);
    }

    public void getLocalTemples() {

        Utility.log(TAG,"getLocalTemples()");

        disposable.add(

                repo_formType_1.getLocalTemples().toObservable()
                        .flatMap(
                                list->{

                                    if(list.size() == 0){
                                        throw new EmptyResultSetException("");
                                    }else{
                                        return Observable.fromIterable(list);
                                    }

                                }
                        )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(

                                obj-> formType1MutableLiveData.setValue(obj)
                                ,
                                throwable->{

                                    //Utilities.dismissDialog();
                                    Log.i(TAG,"Exception accured",throwable);

                                    if(throwable instanceof EmptyResultSetException){
                                        Utility.log(TAG,"Count is 0");
                                        //toolbar.setTitle("Count: 0");
                                        Utility.showToast("No Local temples",Toast.LENGTH_SHORT,getApplication());
                                    }else{
                                        Utility.showToast("Error displaying temples",Toast.LENGTH_SHORT,getApplication());
                                    }

                                },
                                ()->{

                                    Utility.log(TAG,"Completed Displaying temples");
                                    Utility.showToast("Displayed temples sucessfully",Toast.LENGTH_SHORT,getApplication());

                                    Collection<FormType_1> setOfMarkers = hashmapLocalTemples.values();

                                    FormType_1 localTemple = setOfMarkers.iterator().next();

                                    animateMapBooleanMutableLiveData.setValue(localTemple);
                                },
                                dspsbl->{

                                    Utility.log(TAG,"initializing ");
                                    clearLocalTemplesFromMap();
                                    //Utilities.getProgressDialog(this,"Displaying Local Trees...");
                                }

                        )

        );

    }

    public void clearLocalTemplesFromMap(){
        if(hashmapLocalTemples != null && hashmapLocalTemples.size() != 0){
            Set<Marker> setOfMarkers = hashmapLocalTemples.keySet();
            Iterator<Marker> iterator = setOfMarkers.iterator();
            while (iterator.hasNext()){
                Marker marker = iterator.next();
                marker.remove();
            }
        }
        hashmapLocalTemples.clear();
    }

    public void clearServerTemplesFromMap(){
        if(hashmapServerTemples != null && hashmapServerTemples.size() != 0){
            Set<Marker> setOfMarkers = hashmapServerTemples.keySet();
            Iterator<Marker> iterator = setOfMarkers.iterator();
            while (iterator.hasNext()){
                Marker marker = iterator.next();
                marker.remove();
            }
        }
        hashmapServerTemples.clear();
    }



    public LiveData<Boolean> getIsAddButtonClicked(){
        Utility.log(TAG,"getIsAddButtonClicked()");

        return startActivityBooleanMutableLiveData;
    }
    public LiveData<FormType_1> getTempleToDisplay(){
        Utility.log(TAG,"getTempleToDisplay()");

        return formType1MutableLiveData;
    }
    public LiveData<FormType_1> getShouldAnimateMap(){
        Utility.log(TAG,"getShouldAnimateMap()");

        return animateMapBooleanMutableLiveData;
    }

    @Override
    protected void onCleared() {
        //super.onCleared();
        Utility.log(TAG,"onCleared()");
        disposable.clear();
    }
}
