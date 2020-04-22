package com.saarit.temple_management.templemanagement.view_model;

import android.app.Application;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.model.FormType_5;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_FormType_1;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_FormType_4;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_FormType_5;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_server;
import com.saarit.temple_management.templemanagement.util.Constant;
import com.saarit.temple_management.templemanagement.util.PrefManager;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view.adapters.DonatedProductsAdapter;

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

public class FormType5_ViewModel extends AndroidViewModel {
    private String TAG = FormType5_ViewModel.class.getSimpleName();
    public FormType5_ViewModel(@NonNull Application application) {
        super(application);
    }

    public int templeId;
    public FormType_5 formType_5 = new FormType_5();

    public ObservableField<String> local_server_new_ObservableField = new ObservableField<>("");
    public ObservableInt progressBar = new ObservableInt(View.GONE);

    ///// LiveDatas /////
    public MutableLiveData finishActivity = new MutableLiveData();
    public MutableLiveData<Boolean> requestScreenLockMutableLiveData = new MutableLiveData<>();

    CompositeDisposable disposable = new CompositeDisposable();

    public void init(int id){
        templeId = id;

        setUpUI();
    }
    ////////////////////////////////////// FETCHING  /////////////////////
    private void setUpUI() {

        getFormType_5_ByTempleId();

    }

    private void getFormType_5_ByTempleId() {
        disposable.add(

                Repo_FormType_5.getInstance(getApplication()).getFormByTempleId(templeId).toObservable().
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(
                                form -> {
                                    formType_5 = form;
                                    formType_5.notifyChange();
                                    local_server_new_ObservableField.set("Local");

                                },
                                throwable -> {
                                    Utility.log(TAG, throwable.getMessage());
                                    if(throwable instanceof EmptyResultSetException){
                                        getFormType_5_ByTempleId_Server();
                                    }
                                },
                                () -> {
                                    Utility.log(TAG,"Fetched form 5 from Local DB");
                                },
                                dsposable -> {

                                }
                        )
        );
    }

    private void getFormType_5_ByTempleId_Server() {
        disposable.add(
                Repo_server.getInstance().apiService.getFormType5byTempleId(templeId).toObservable().
                        flatMap(
                                dto ->{
                                    if(dto.getSuccess() == 0){
                                        throw new EmptyResultSetException("Form 5 not found on server");
                                    }else{
                                        formType_5 = dto.getFormType_5();
                                        formType_5.id = 0;
                                        return null;
                                    }
                                }
                        ).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(
                                value -> {

                                },
                                throwable -> {
                                    Utility.log(TAG, throwable.getMessage());
                                    if(throwable instanceof EmptyResultSetException){
                                        getFormType_1_ByTempleId();
                                    }
                                },
                                ()->{
                                    Utility.log(TAG,"Fetched form 5 from server");
                                    formType_5.notifyChange();
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
                Repo_FormType_1.getInstance(getApplication()).getFormByTempleId(templeId).toObservable().
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(

                                formType_1 ->{
                                    formType_5.temple_id = formType_1.templeId;
                                    formType_5.temple_name = formType_1.temple;
                                    formType_5.village_name = formType_1.village;
                                    formType_5.taluka_name = formType_1.taluka;
                                    formType_5.district_name = formType_1.district;

                                    formType_5.notifyChange();
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

                Repo_server.getInstance().apiService.getFormType1byTempleId(templeId).toObservable().
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

                                    formType_5.temple_id = formType_1.templeId;
                                    formType_5.temple_name = formType_1.temple;
                                    formType_5.village_name = formType_1.village;
                                    formType_5.taluka_name = formType_1.taluka;
                                    formType_5.district_name = formType_1.district;

                                    formType_5.notifyChange();
                                    local_server_new_ObservableField.set("New");
                                    requestScreenLockMutableLiveData.setValue(false);
                                    progressBar.set(View.GONE);

                                },
                                throwable -> {
                                    Utility.log(TAG, throwable.getMessage());
                                    Utility.showToast(throwable.getMessage(), Toast.LENGTH_LONG,getApplication());
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

    ////////////////////////////////////// SAVING / SUBMITING  ///////////////////
    public void onSaveClick(View view){
        Utility.log(TAG, "onSaveClick()");
        /*Utility.log(TAG, "Values:"+formType_4);*/
        save_submit_Form(Constant.REQ_SAVE);
    }

    public void onSubmitClick(View view){
        Utility.log(TAG, "onSubmitClick()");
        formType_5.user_id = PrefManager.getUserId(getApplication());
        save_submit_Form(Constant.REQ_SUBMIT);
    }

    private void save_submit_Form(int req_type) {
        disposable.add(
                Repo_FormType_5.getInstance(getApplication()).insertForm(formType_5).toObservable().
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(
                                id->{
                                    Utility.log(TAG,"from onNext()..SAVING");
                                },
                                throwable->{
                                    Utility.log(TAG,"Error:"+throwable.getMessage());
                                    Utility.showToast(throwable.getMessage(),Toast.LENGTH_SHORT,getApplication());
                                    requestScreenLockMutableLiveData.setValue(false);
                                    progressBar.set(View.GONE);

                                },
                                ()->{
                                    Utility.log(TAG,"from onComplete()..SAVED");
                                    switch(req_type){
                                        case Constant.REQ_SAVE:
                                            Utility.showToast("Added Successfully",Toast.LENGTH_SHORT,getApplication());
                                            requestScreenLockMutableLiveData.setValue(false);
                                            progressBar.set(View.GONE);
                                            finishActivity.setValue(null);
                                            break;

                                        case Constant.REQ_SUBMIT:
                                            submitForm();
                                            break;
                                    }
                                },
                                dspoble->{
                                    Utility.log(TAG,"About to Save");
                                }
                        )
        );

    }

    private void submitForm() {

        disposable.add(

                Repo_server.getInstance().apiService.addForm5(formType_5)
                        .flatMap(
                                baseResponse -> {

                                    Utility.log(TAG,"Success:"+baseResponse.getSuccess());
                                    if(baseResponse.getSuccess() == 1){
                                        return Observable.just(true);
                                    }else{
                                        throw new Exception(baseResponse.getMsg());
                                    }
                                }
                        ).subscribeOn(Schedulers.io())
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

    ////////////////////////////////////

    public void onSwitchCheckedChange(boolean value){
        Utility.log(TAG,"Switch value:"+value);
        formType_5.setHas_occupant_encroached_plot(value);
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id, int type){
        if(position == 0){
            return;
        }
        Utility.log(TAG,"Selected:"+parent.getItemAtPosition(position));
        switch (type){
            case Constant.SPINNER_TYPE_PURPOSE_USE_GIVEN_LAND:
                formType_5.setPurpose_use_given_land(parent.getItemAtPosition(position).toString());
                break;

            case Constant.SPINNER_TYPE_PURPOSE_USE_UNUSED_LAND:
                formType_5.setPurpose_use_unused_plot(parent.getItemAtPosition(position).toString());
                break;

            case Constant.SPINNER_TYPE_LAND_UTILISATION:
                formType_5.setLand_utilisation(parent.getItemAtPosition(position).toString());
                break;

        }

    }

    // Observed Methods//
    public LiveData shouldFinishActivity(){
        return finishActivity;
    }
    public LiveData<Boolean> observeLockScreenRequest() {
        return requestScreenLockMutableLiveData;
    }

    @Override
    protected void onCleared() {
        disposable.clear();
        super.onCleared();
    }

}
