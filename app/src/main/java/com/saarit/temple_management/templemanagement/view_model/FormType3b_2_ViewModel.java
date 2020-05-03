package com.saarit.temple_management.templemanagement.view_model;

import android.annotation.SuppressLint;
import android.app.Application;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.patloew.rxlocation.RxLocation;
import com.saarit.temple_management.templemanagement.model.FormType_3b_2;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_FormType_1;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_FormType_3b_2;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_server;
import com.saarit.temple_management.templemanagement.util.Constant;
import com.saarit.temple_management.templemanagement.util.PrefManager;
import com.saarit.temple_management.templemanagement.util.Utility;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.EmptyResultSetException;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FormType3b_2_ViewModel extends AndroidViewModel {
    ////*****VARIABLES*****//////

    private String TAG = FormType3b_2_ViewModel.class.getSimpleName();

    public FormType3b_2_ViewModel(@NonNull Application application) {
        super(application);
    }

    public FormType3b_2_ViewModel(@NonNull Application application, String arg) {
        super(application);
        Utility.log(TAG,"Argument:"+arg);
    }

    //// LAYOUT MODEL ////
    public FormType_3b_2 formType_3b_2;
    public ObservableField<FormType_3b_2> formType_3b_2_ObservableField = new ObservableField<>();
    public ObservableField<String> local_server_new_ObservableField = new ObservableField<>("");

    ////VISIBILITY////
    public ObservableInt visibility_layout_land_cultivation_info = new ObservableInt(View.GONE);
    public ObservableInt visibility_layout_land_survey_info = new ObservableInt(View.GONE);
    public ObservableInt visibility_layout_land_used_cultivation_info = new ObservableInt(View.GONE);
    
    public ObservableInt progressBar = new ObservableInt(View.GONE);

    ///// REPOSITORIES ////
    public Repo_FormType_1 repo_formType_1;
    public Repo_FormType_3b_2 repo_formType_3b_2;
    public Repo_server repo_server;

    ////RxJAVA////
    CompositeDisposable disposable;

    public int templeId;

    ///// LiveDatas /////
    public MutableLiveData finishActivity = new MutableLiveData();
    public MutableLiveData showDatePicker = new MutableLiveData();
    public MutableLiveData<Boolean> requestScreenLockMutableLiveData = new MutableLiveData<>();

    RxLocation rxLocation = new RxLocation(getApplication());
    LocationRequest locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
            .setInterval(5000);

    Disposable locDisposable;

    public ObservableInt markerVisibility = new ObservableInt(View.VISIBLE);
    public ObservableInt progressVisibility = new ObservableInt(View.GONE);


    ////*******METHODS******//////
    public void init(int temple_id) {
        Utility.log(TAG,"init()");

        repo_formType_1 = Repo_FormType_1.getInstance(getApplication());
        repo_formType_3b_2 = Repo_FormType_3b_2.getInstance(getApplication());
        repo_server = Repo_server.getInstance();

        disposable = new CompositeDisposable();
        templeId = temple_id;
        Utility.log(TAG,"Temple ID:"+templeId);

        setUpUI();

    }

    private void setUpUI() {

        getFormType_3b_2_ByTempleId();

    }

    private void getFormType_3b_2_ByTempleId() {
        disposable.add(

                repo_formType_3b_2.getFormByTempleId(templeId).toObservable().
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(
                                form -> {
                                    formType_3b_2 = form;


                                    formType_3b_2_ObservableField.set(formType_3b_2);
                                    local_server_new_ObservableField.set("Local");


                                },
                                throwable -> {
                                    Utility.log(TAG, throwable.getMessage());
                                    if(throwable instanceof EmptyResultSetException){

                                            getFormType_3b_2_ByTempleId_Server();
                                    }
                                },
                                () -> {
                                    Utility.log(TAG,"Fetched form 3a from Local DB");
                                },
                                dsposable -> {

                                }
                        )
        );
    }

    private void getFormType_3b_2_ByTempleId_Server() {
        disposable.add(
                repo_server.apiService.getFormType3b2byTempleId(templeId).toObservable().
                        flatMap(
                                dto ->{
                                    if(dto.getSuccess() == 0){
                                        throw new EmptyResultSetException("Form 3a not found on server");
                                    }else{
                                        formType_3b_2 = dto.getFormType_3b_2();
                                        formType_3b_2.id = 0;

                                        return Observable.just(formType_3b_2);
                                    }
                                }
                        ).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(
                                formType_3b_2 -> {


                                },
                                throwable -> {
                                    Utility.log(TAG, throwable.getMessage());
                                    if(throwable instanceof EmptyResultSetException){
                                        getFormType_1_ByTempleId();

                                    }

                                },
                                ()->{
                                    Utility.log(TAG,"Fetched form 3a from server");
                                    formType_3b_2_ObservableField.set(formType_3b_2);
                                    local_server_new_ObservableField.set("Server");

                                    requestScreenLockMutableLiveData.setValue(false);
                                    progressBar.set(View.GONE);

                                },
                                dsposble->{
                                    requestScreenLockMutableLiveData.setValue(true);
                                    progressBar.set(View.VISIBLE);

                                }
                        )

        );
    }

    private void getFormType_1_ByTempleId() {
        disposable.add(
                repo_formType_1.getFormByTempleId(templeId).toObservable().
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(

                                formType_1 ->{
                                    formType_3b_2 = new FormType_3b_2();
                                    formType_3b_2.temple_id = formType_1.templeId;
                                    formType_3b_2.temple_name = formType_1.temple;
                                    formType_3b_2.god_name = formType_1.god_name;
                                    formType_3b_2.village_name = formType_1.village;
                                    formType_3b_2.taluka_name = formType_1.taluka;
                                    formType_3b_2.district_name = formType_1.district;
                                    formType_3b_2.latitude = ""+formType_1.latitude;
                                    formType_3b_2.longitude = ""+formType_1.longitude;

                                    formType_3b_2_ObservableField.set(formType_3b_2);
                                    local_server_new_ObservableField.set("New");

                                    requestScreenLockMutableLiveData.setValue(false);
                                    progressBar.set(View.GONE);

                                },
                                throwable -> {
                                    Utility.log(TAG, throwable.getMessage());
                                    if(throwable instanceof EmptyResultSetException){
                                        getFormType_1_ByTempleId_Server();
                                    }
                                },
                                () -> {

                                },
                                dsposable ->{

                                }
                        )
        );
    }

    private void getFormType_1_ByTempleId_Server() {
        disposable.add(

                repo_server.apiService.getFormType1byTempleId(templeId).toObservable().
                        flatMap(
                                dto ->{
                                    if(dto.getSuccess() == 0){
                                        throw new Exception(dto.getMsg());
                                    }else{
                                        return Observable.just(dto.getFormType_1());

                                    }
                                }
                        ).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(
                                formType_1 -> {
                                    formType_3b_2 = new FormType_3b_2();
                                    formType_3b_2.temple_id = formType_1.templeId;
                                    formType_3b_2.temple_name = formType_1.temple;
                                    formType_3b_2.god_name = formType_1.god_name;
                                    formType_3b_2.village_name = formType_1.village;
                                    formType_3b_2.taluka_name = formType_1.taluka;
                                    formType_3b_2.district_name = formType_1.district;
                                    formType_3b_2.latitude = ""+formType_1.latitude;
                                    formType_3b_2.longitude = ""+formType_1.longitude;

                                    formType_3b_2_ObservableField.set(formType_3b_2);
                                    local_server_new_ObservableField.set("New");

                                    requestScreenLockMutableLiveData.setValue(false);
                                    progressBar.set(View.GONE);

                                },
                                throwable -> {
                                    Utility.log(TAG, throwable.getMessage());
                                    Utility.showToast(throwable.getMessage(),Toast.LENGTH_LONG,getApplication());

                                    requestScreenLockMutableLiveData.setValue(false);
                                    progressBar.set(View.GONE);
                                    finishActivity.setValue(null);
                                },
                                ()->{

                                },
                                dsposble->{

                                }
                        )
        );
    }

    public void onSaveClick(View view){
        Utility.log(TAG,"onSaveClick");


        Utility.log(TAG,"Model Info:"+ formType_3b_2.toString());
        save_or_submit_form3b_1(Constant.REQ_SAVE);

    }

    public void onSubmitClick(View view) {
        Utility.log(TAG, "onSubmitClick()");

            formType_3b_2.user_id = PrefManager.getUserId(getApplication());
            save_or_submit_form3b_1(Constant.REQ_SUBMIT);

    }

    public void onLocationClick(View view) {
        Utility.log(TAG, "onLocationClick()");
        getCurrentLocation();

    }

    public void onDateClick(View view){
        Utility.log(TAG,"onDateClick");
        showDatePicker.setValue(null);
    }

    private void save_or_submit_form3b_1(int req) {

        disposable.add(
                repo_formType_3b_2.insertForm(formType_3b_2).toObservable().
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                id -> {
                                    Utility.log(TAG,"Added Form 3a   ID:"+id);
                                    formType_3b_2.id = id.intValue();
                                },
                                throwable -> {
                                    Utility.log(TAG,throwable.getMessage());
                                    Utility.showToast(throwable.getMessage(),Toast.LENGTH_SHORT,getApplication());
                                    requestScreenLockMutableLiveData.setValue(false);
                                    progressBar.set(View.GONE);
                                },
                                ()->{
                                    Utility.log(TAG,"Finished Adding");
                                    switch(req){
                                        case Constant.REQ_SAVE:
                                            Utility.showToast("Added Successfully",Toast.LENGTH_SHORT,getApplication());
                                            requestScreenLockMutableLiveData.setValue(false);
                                            progressBar.set(View.GONE);
                                            finishActivity.setValue(null);
                                            break;

                                        case Constant.REQ_SUBMIT:
                                            submitForm3b_2();
                                            break;
                                    }

                                },
                                disposble->{
                                    requestScreenLockMutableLiveData.setValue(true);
                                    progressBar.set(View.VISIBLE);

                                    if(!formType_3b_2.is_any_land_cultivation){
                                        formType_3b_2.area_agriculture_land = null;
                                        formType_3b_2.area_non_agriculture_land = null;
                                        formType_3b_2.total_area_cultivation = null;
                                    }
                                    if(!formType_3b_2.is_land_surveyed){
                                        formType_3b_2.survey_date = null;
                                        formType_3b_2.is_border_fixed = false;
                                    }
                                    if(!formType_3b_2.is_land_used_cultivation){
                                        formType_3b_2.land_cultivation_procedure = null;
                                    }

                                }


                        )
        );
    }

    private void submitForm3b_2() {

        disposable.add(

                repo_server.apiService.addForm3b_2(formType_3b_2)
                        .flatMap(
                                baseResponse -> {

                                    Utility.log(TAG,"Success:"+baseResponse.getSuccess());
                                    if(baseResponse.getSuccess() == 1){
                                        /*Utility.deleteImagesById_Form2(getApplication(), formType_3b_2.id);*/
                                        return repo_formType_3b_2.deleteFormById(formType_3b_2.id).toObservable();

                                    }else{
                                        throw new Exception(baseResponse.getMsg());
                                    }


                                }
                        )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                value -> {

                                    Utility.log(TAG,"onNext..Success:"+value);

                                },
                                throwable -> {
                                    Utility.log(TAG,throwable.getMessage());
                                    progressBar.set(View.GONE);
                                    requestScreenLockMutableLiveData.setValue(false);
                                    Utility.showToast("Saved Locally"+"\n"+throwable.getMessage(),Toast.LENGTH_SHORT,getApplication());

                                },
                                () -> {
                                    Utility.log(TAG,"completed");
                                    Utility.showToast("Submitted Successfully",Toast.LENGTH_SHORT,getApplication());
                                    progressBar.set(View.GONE);
                                    requestScreenLockMutableLiveData.setValue(false);
                                    finishActivity.setValue(null);
                                },
                                dspsbl -> {

                                    Utility.log(TAG,"Initialilize");

                                }

                        )

        );

    }

    public void onSwitchCheckedChange(View view,boolean value,int type){
        Utility.log(TAG,"onSwitchCheckedChange..Status:"+value+" Type:"+type);

        switch (type){
            case Constant.SWITCH_LAND_CULTIVATION:
                if(value){
                    visibility_layout_land_cultivation_info.set(View.VISIBLE);

                }else{
                    visibility_layout_land_cultivation_info.set(View.GONE);
                }
                break;
            case Constant.SWITCH_LAND_SURVEYED:
                if(value){
                    visibility_layout_land_survey_info.set(View.VISIBLE);
                }else{
                    visibility_layout_land_survey_info.set(View.GONE);
                }
                break;
            case Constant.SWITCH_LAND_USED_CULTIVATION:
                if(value){
                    visibility_layout_land_used_cultivation_info.set(View.VISIBLE);
                }else{
                    visibility_layout_land_used_cultivation_info.set(View.GONE);
                }
                break;

        }

    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id, int type){
        if(position == 0){
            return;
        }
        Utility.log(TAG,"Selected:"+parent.getItemAtPosition(position));
        switch (type){
            case Constant.SPINNER_TYPE_LAND_DOC_A:
                formType_3b_2.setLand_document_a(parent.getItemAtPosition(position).toString());
                break;

            case Constant.SPINNER_TYPE_LAND_DOC_B:
                formType_3b_2.setLand_document_b(parent.getItemAtPosition(position).toString());
                break;
        }

    }


    public void setDate(String date){
        Utility.log(TAG,"setDate...date:"+date);
        formType_3b_2.setSurvey_date(date);
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {

        /*threadCurrentLocation.start();*/

        locDisposable = rxLocation.location().updates(locationRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        location -> {
                            Utility.log(TAG,"Thread onNext:"+Thread.currentThread().getName());
                            Utility.log(TAG,"Loc Received:"+location.getLatitude()+" / "+location.getLongitude());
                            locDisposable.dispose();
                            markerVisibility.set(View.VISIBLE);
                            progressVisibility.set(View.GONE);

                            formType_3b_2.setLatitude(""+location.getLatitude());
                            formType_3b_2.setLongitude(""+location.getLongitude());

                        },
                        throwable -> {},
                        () -> {},
                        dsposable -> {
                            markerVisibility.set(View.GONE);
                            progressVisibility.set(View.VISIBLE);
                        }
                );

    }


    // Observed Methods//
    public LiveData shouldFinishActivity(){
        return finishActivity;
    }

    public LiveData shouldShowDatePicker(){
        return showDatePicker;
    }

    public LiveData<Boolean> observeLockScreenRequest() {
        return requestScreenLockMutableLiveData;
    }


    @Override
    protected void onCleared() {
        //super.onCleared();
        Utility.log(TAG,"onCleared()");
        disposable.clear();
        if(locDisposable != null){
            locDisposable.dispose();
        }
        super.onCleared();
        //getApplication().unregisterReceiver(locationBroadCastReceiver);

    }

}
