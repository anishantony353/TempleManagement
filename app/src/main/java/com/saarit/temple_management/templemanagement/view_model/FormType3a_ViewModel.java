package com.saarit.temple_management.templemanagement.view_model;

import android.app.Application;
import android.view.View;
import android.widget.Toast;

import com.saarit.temple_management.templemanagement.model.FormType_3a;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_FormType_1;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_FormType_3a;
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
import io.reactivex.schedulers.Schedulers;

public class FormType3a_ViewModel extends AndroidViewModel {
    ////*****VARIABLES*****//////

    private String TAG = FormType3a_ViewModel.class.getSimpleName();

    public FormType3a_ViewModel(@NonNull Application application) {
        super(application);
    }

    //// LAYOUT MODEL ////
    public FormType_3a formType_3a;
    public ObservableField<FormType_3a> formType_3aObservableField = new ObservableField<>();
    public ObservableField<String> local_server_new_ObservableField = new ObservableField<>("");
    /*public ObservableField<String> custom1ImgObservableField = new ObservableField<>();
    public ObservableField<String> custom2ImgObservableField = new ObservableField<>();*/

    ////VISIBILITY////
    public ObservableInt visibility_layout_dharmshala_info = new ObservableInt(View.GONE);
    public ObservableInt visibility_layout_areaOpenPlot = new ObservableInt(View.GONE);
    public ObservableInt visibility_layout_office_info = new ObservableInt(View.GONE);
    public ObservableInt visibility_layout_school_info = new ObservableInt(View.GONE);
    public ObservableInt visibility_layout_organisation_info = new ObservableInt(View.GONE);
    public ObservableInt visibility_layout_property_rent_info = new ObservableInt(View.GONE);
    public ObservableInt visibility_layout_electricity_meter_info = new ObservableInt(View.GONE);
    public ObservableInt visibility_layout_enrochment_info = new ObservableInt(View.GONE);
    public ObservableInt visibility_layout_sub_committee_info = new ObservableInt(View.GONE);
    public ObservableInt visibility_layout_renovation_info = new ObservableInt(View.GONE);

    public ObservableInt progressBar = new ObservableInt(View.GONE);

    ///// REPOSITORIES ////
    public Repo_FormType_1 repo_formType_1;
    public Repo_FormType_3a repo_formType_3a;
    public Repo_server repo_server;

    //// RxJAVA////
    CompositeDisposable disposable;

    public int templeId;

    ///// LiveDatas /////
    public MutableLiveData finishActivity = new MutableLiveData();
    public MutableLiveData showDatePicker = new MutableLiveData();
    public MutableLiveData<Boolean> requestScreenLockMutableLiveData = new MutableLiveData<>();


    ////*******METHODS******//////
    public void init(int temple_id) {
        Utility.log(TAG,"init()");

        repo_formType_1 = Repo_FormType_1.getInstance(getApplication());
        repo_formType_3a = Repo_FormType_3a.getInstance(getApplication());
        repo_server = Repo_server.getInstance();

        disposable = new CompositeDisposable();
        templeId = temple_id;
        Utility.log(TAG,"Temple ID:"+templeId);

        setUpUI();

    }

    private void setUpUI() {

        getFormType_3a_ByTempleId();

    }

    private void getFormType_3a_ByTempleId() {
        disposable.add(

                repo_formType_3a.getFormByTempleId(templeId).toObservable().
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(
                                form -> {
                                    formType_3a = form;


                                    formType_3aObservableField.set(formType_3a);
                                    local_server_new_ObservableField.set("Local");

                                    /*getSubDatas();*/


                                },
                                throwable -> {
                                    Utility.log(TAG, throwable.getMessage());
                                    if(throwable instanceof EmptyResultSetException){
                                            //getFormType_1_ByTempleId();
                                            getFormType_3a_ByTempleId_Server();
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

    private void getFormType_3a_ByTempleId_Server() {
        disposable.add(
                repo_server.apiService.getFormType3abyTempleId(templeId).toObservable().
                        flatMap(
                                dto ->{
                                    if(dto.getSuccess() == 0){
                                        throw new EmptyResultSetException("Form 3a not found on server");
                                    }else{
                                        formType_3a = dto.getFormType_3a();
                                        formType_3a.id = 0;

                                        return Observable.just(formType_3a);
                                    }
                                }
                        ).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(
                                formType_3a -> {


                                },
                                throwable -> {
                                    Utility.log(TAG, throwable.getMessage());
                                    if(throwable instanceof EmptyResultSetException){
                                        getFormType_1_ByTempleId();

                                    }

                                },
                                ()->{
                                    Utility.log(TAG,"Fetched form 3a from server");
                                    formType_3aObservableField.set(formType_3a);
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
                                    formType_3a = new FormType_3a();
                                    formType_3a.temple_id = formType_1.templeId;
                                    formType_3a.temple_name = formType_1.temple;
                                    formType_3a.village_name = formType_1.village;
                                    formType_3a.taluka_name = formType_1.taluka;
                                    formType_3a.district_name = formType_1.district;
                                    formType_3a.latitude = ""+formType_1.latitude;
                                    formType_3a.longitude = ""+formType_1.longitude;

                                    formType_3aObservableField.set(formType_3a);
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
                                    formType_3a = new FormType_3a();
                                    formType_3a.temple_id = formType_1.templeId;
                                    formType_3a.temple_name = formType_1.temple;
                                    formType_3a.village_name = formType_1.village;
                                    formType_3a.taluka_name = formType_1.taluka;
                                    formType_3a.district_name = formType_1.district;
                                    formType_3a.latitude = ""+formType_1.latitude;
                                    formType_3a.longitude = ""+formType_1.longitude;

                                    formType_3aObservableField.set(formType_3a);
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


        Utility.log(TAG,"Model Info:"+ formType_3a.toString());
        save_or_submit_form3a(Constant.REQ_SAVE);

    }

    public void onSubmitClick(View view) {
        Utility.log(TAG, "onSubmitClick()");
            formType_3a.user_id = PrefManager.getUserId(getApplication());
            save_or_submit_form3a(Constant.REQ_SUBMIT);

    }

    public void onDateClick(View view){
        Utility.log(TAG,"onDateClick");
        showDatePicker.setValue(null);
    }

    private void save_or_submit_form3a(int req) {

        disposable.add(
                repo_formType_3a.insertForm(formType_3a).toObservable().
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                id -> {
                                    Utility.log(TAG,"Added Form 3a   ID:"+id);
                                    formType_3a.id = id.intValue();
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
                                            submitForm3a();
                                            break;
                                    }

                                },
                                disposble->{
                                    requestScreenLockMutableLiveData.setValue(true);
                                    progressBar.set(View.VISIBLE);
                                    if(!formType_3a.is_dharmshala_present_near_temple){
                                        formType_3a.dharmshala_details = null;
                                        formType_3a.dharmshala_area = null;
                                    }
                                    if(!formType_3a.is_open_plot_available_under_dharmshala){
                                        formType_3a.area_open_plot = null;
                                    }
                                    if(!formType_3a.is_office_available_under_temple){
                                        formType_3a.office_area = null;
                                        formType_3a.office_no_rooms = null;
                                        formType_3a.office_no_seats = null;
                                        formType_3a.office_caretaker_details = null;
                                    }
                                    if(!formType_3a.is_school_available_under_temple){
                                        formType_3a.school_area = null;
                                        formType_3a.school_no_rooms = null;
                                        formType_3a.school_no_seats = null;
                                        formType_3a.school_caretaker_details = null;
                                    }
                                    if(!formType_3a.is_organisation_available_under_temple){
                                        formType_3a.organisation_area = null;
                                        formType_3a.organisation_no_rooms = null;
                                        formType_3a.organisation_no_seats = null;
                                        formType_3a.organisation_caretaker_details = null;
                                    }

                                    if(!formType_3a.is_property_rent_given){
                                        formType_3a.rent_deposited_to = null;
                                        formType_3a.rent_per_month = null;

                                    }
                                    if(!formType_3a.is_electricity_meter_available){
                                        formType_3a.electricity_owner_details = null;
                                        formType_3a.electricity_payable_by = null;

                                    }
                                    if(!formType_3a.is_enrochment_premises){
                                        formType_3a.is_enrochment_authorized = false;
                                        formType_3a.enrochment_shop_house_other = null;
                                        formType_3a.enrochment_details_person_area = null;
                                        formType_3a.organisation_caretaker_details = null;
                                    }
                                    if(!formType_3a.is_sub_committee_present){
                                        formType_3a.sub_committee_name = null;
                                        formType_3a.sub_committee_order_no = null;
                                        formType_3a.sub_committee_order_no_date = null;
                                        formType_3a.sub_committee_president = null;
                                        formType_3a.sub_committee_members_details = null;
                                        formType_3a.is_audit_details_present = false;
                                        formType_3a.audit_details = null;
                                    }
                                    if(!formType_3a.is_renovation_going_on){
                                        formType_3a.renovation_permission = null;
                                        formType_3a.is_renovation_necessary = false;

                                    }
                                }


                        )
        );
    }

    private void submitForm3a() {

        disposable.add(

                repo_server.apiService.addForm3a(formType_3a)
                        .flatMap(
                                baseResponse -> {

                                    Utility.log(TAG,"Success:"+baseResponse.getSuccess());
                                    if(baseResponse.getSuccess() == 1){
                                        Utility.deleteImagesById_Form2(getApplication(), formType_3a.id);
                                        return repo_formType_3a.deleteFormById(formType_3a.id).toObservable();

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
                                    requestScreenLockMutableLiveData.setValue(false);
                                    progressBar.set(View.GONE);
                                    Utility.showToast("Saved Locally"+"\n"+throwable.getMessage(),Toast.LENGTH_SHORT,getApplication());

                                },
                                () -> {
                                    Utility.log(TAG,"completed");
                                    Utility.showToast("Submitted Successfully",Toast.LENGTH_SHORT,getApplication());
                                    requestScreenLockMutableLiveData.setValue(false);
                                    progressBar.set(View.GONE);
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
            case Constant.SWITCH_DHARMSHALA_PRESENT:
                if(value){
                    visibility_layout_dharmshala_info.set(View.VISIBLE);

                }else{
                    visibility_layout_dharmshala_info.set(View.GONE);
                }
                break;
            case Constant.SWITCH_OPEN_PLOT_DHARMSHALA:
                if(value){
                    visibility_layout_areaOpenPlot.set(View.VISIBLE);
                }else{
                    visibility_layout_areaOpenPlot.set(View.GONE);
                }
                break;
            case Constant.SWITCH_OFFICE_AVAILABLE:
                if(value){
                    visibility_layout_office_info.set(View.VISIBLE);
                }else{
                    visibility_layout_office_info.set(View.GONE);
                }
                break;
            case Constant.SWITCH_SCHOOL_AVAILABLE:
                if(value){
                    visibility_layout_school_info.set(View.VISIBLE);
                }else{
                    visibility_layout_school_info.set(View.GONE);
                }
                break;
            case Constant.SWITCH_ORGANISATION_AVAILABLE:
                if(value){
                    visibility_layout_organisation_info.set(View.VISIBLE);
                }else{
                    visibility_layout_organisation_info.set(View.GONE);
                }
                break;

                //
            case Constant.SWITCH_PROPERTY_RENT:
                if(value){
                    visibility_layout_property_rent_info.set(View.VISIBLE);

                }else{
                    visibility_layout_property_rent_info.set(View.GONE);
                }
                break;
            case Constant.SWITCH_ELECTRICITY_METER:
                if(value){
                    visibility_layout_electricity_meter_info.set(View.VISIBLE);
                }else{
                    visibility_layout_electricity_meter_info.set(View.GONE);
                }
                break;
            case Constant.SWITCH_ENROCHMENT:
                if(value){
                    visibility_layout_enrochment_info.set(View.VISIBLE);
                }else{
                    visibility_layout_enrochment_info.set(View.GONE);
                }
                break;
            case Constant.SWITCH_SUB_COMMITTEE:
                if(value){
                    visibility_layout_sub_committee_info.set(View.VISIBLE);
                }else{
                    visibility_layout_sub_committee_info.set(View.GONE);
                }
                break;
            case Constant.SWITCH_RENOVATION:
                if(value){
                    visibility_layout_renovation_info.set(View.VISIBLE);
                }else{
                    visibility_layout_renovation_info.set(View.GONE);
                }
                break;
        }

    }


    public void setDate(String date){
        Utility.log(TAG,"setDate...date:"+date);
        formType_3a.setSub_committee_order_no_date(date);
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
        super.onCleared();
        //getApplication().unregisterReceiver(locationBroadCastReceiver);

    }

}
