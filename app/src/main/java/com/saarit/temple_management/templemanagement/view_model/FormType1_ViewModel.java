package com.saarit.temple_management.templemanagement.view_model;

import android.app.Application;
import android.view.View;

import com.saarit.temple_management.templemanagement.model.FormType_1;
import com.saarit.temple_management.templemanagement.model.SuccessOrFailure;
import com.saarit.temple_management.templemanagement.util.Utility;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class FormType1_ViewModel extends AndroidViewModel {

    private String TAG = FormType1_ViewModel.class.getSimpleName();

    public FormType1_ViewModel(@NonNull Application application) {
        super(application);
    }

    public CompositeDisposable disposable = new CompositeDisposable();


    //Model
    public FormType_1 formType_1;

    //To Replace entire form with new Model
    public ObservableField<FormType_1> formType_1ObservableField;

    //TO Set Visibility
    public ObservableInt reqType;

    //To Observe Submit Response
    public MutableLiveData<SuccessOrFailure> submitResponse = new MutableLiveData<>();

    //To Observe 'Get Location' click
    public MutableLiveData locationClick = new MutableLiveData();


    public void init(int REQ_TYPE) {
        formType_1 = new FormType_1("V","T",235.25,986.65);

        formType_1ObservableField = new ObservableField<>();
        formType_1ObservableField.set(formType_1);

        reqType = new ObservableInt(REQ_TYPE);

    }

    public void getFormDataById(int FORM_ID){
        //Restrofit and RxJava to get 'FormType_1'

        //Dummy 'FormType_1'
        formType_1 = new FormType_1("Villll","Taaaa",21.25,41.65);


        setFormType_1ObservableField(formType_1);

    }

    public void setFormType_1ObservableField(FormType_1 formType_1){
        formType_1ObservableField.set(formType_1);
    }

    public LiveData<SuccessOrFailure> getSubmitResponse(){
        Utility.log(TAG,"getSubmitResponse()");

        return submitResponse;
    }

    public LiveData  getOnLocationClick(){
        Utility.log(TAG,"getOnLocationClick()");

        return locationClick;
    }

    public void onSaveClick(View view){

    }

    public void onSubmitClick(View view){

        Utility.log(TAG,"onSubmitClick()");

//        formType_1.setLatitude(111.333);
//        formType_1.setLongitude(222.444);
//        Utility.log(TAG,"Value:\n"+formType_1.village+"\n"+
//                formType_1.taluka+"\n"+formType_1.latitude+"\n"+formType_1.longitude);

    }

    public void onUpdateClick(View view){

        Utility.log(TAG,"onUpdateClick()");


    }

    public void onLocationClick(View view){
        Utility.log(TAG,"onLocationClick()");
        locationClick.setValue(null);

    }

//    public LiveData<SuccessOrFailure> getFormFromServer(){
//
//    }


    @Override
    protected void onCleared() {
        //super.onCleared();
        Utility.log(TAG,"onCleared()");
        disposable.clear();
    }
}
