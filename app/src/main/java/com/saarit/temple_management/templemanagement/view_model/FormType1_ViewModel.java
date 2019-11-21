package com.saarit.temple_management.templemanagement.view_model;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.saarit.temple_management.templemanagement.model.FormType_1;
import com.saarit.temple_management.templemanagement.model.Repo_FormType_1;
import com.saarit.temple_management.templemanagement.model.SuccessOrFailure;
import com.saarit.temple_management.templemanagement.model.ThreadCurrentLocation;
import com.saarit.temple_management.templemanagement.util.Constant;
import com.saarit.temple_management.templemanagement.util.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FormType1_ViewModel extends AndroidViewModel {

    private String TAG = FormType1_ViewModel.class.getSimpleName();

    public FormType1_ViewModel(@NonNull Application application) {
        super(application);
    }

    Repo_FormType_1 repo_formType_1;

    public CompositeDisposable disposable = new CompositeDisposable();


    //Model
    public FormType_1 formType_1;

    //Mapping form(UI) with Model(FormType_1)
    public ObservableField<FormType_1> formType_1ObservableField;

    public ObservableField<String> frontImgObservableField;
    public ObservableField<String> leftImgObservableField;
    public ObservableField<String> rightImgObservableField;
    public ObservableField<String> entryImgObservableField;


    //TO Set Visibility
    public ObservableInt reqType;

    public ObservableInt markerVisibility = new ObservableInt(View.VISIBLE);
    public ObservableInt progressVisibility = new ObservableInt(View.GONE);


    public ObservableField<String> error_msg_templeName = new ObservableField<>(Constant.VALID);
    public ObservableField<String> error_msg_latitude = new ObservableField<>(Constant.VALID);
    public ObservableField<String> error_msg_longitude = new ObservableField<>(Constant.VALID);


    //To Observe Submit Response
    public MutableLiveData<SuccessOrFailure> submitResponseSuccessOrFailureMutableLiveData = new MutableLiveData<>();

    //To Observe 'Get Location' click
    public MutableLiveData locationClickMutableLiveData = new MutableLiveData();
    //To Observe newly added Temple in SqLite
    public MutableLiveData<FormType_1> addedFormMutableLivedata = new MutableLiveData<>();
    //To Observe 'Get Image' click
    public MutableLiveData<Boolean> getImageBooleanMutableLiveData = new MutableLiveData<>();


    BroadcastReceiver locationBroadCastReceiver;

    public Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    public int imageReqType;
    public Uri fileUri;

    public void init(int REQ_TYPE, int id) {
        reqType = new ObservableInt(REQ_TYPE);
        repo_formType_1 = Repo_FormType_1.getInstance(getApplication());
        setUpLocationBroadcastReceiver();
        formType_1ObservableField = new ObservableField<>();
        frontImgObservableField = new ObservableField<>();
        leftImgObservableField = new ObservableField<>();
        rightImgObservableField = new ObservableField<>();
        entryImgObservableField = new ObservableField<>();

        switch(REQ_TYPE){
            case Constant.REQUEST_CODE_CLICK_LOCAL_TREES:
                markerVisibility.set(View.GONE);
                progressVisibility.set(View.GONE);

                getTempleById(id);

                break;

            default:
                formType_1 = new FormType_1();
                formType_1ObservableField.set(formType_1);
                break;

        }
    }

    private void getTempleById(int id) {
        disposable.add(

                repo_formType_1.getFormByTempleId(id).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(
                                temple -> {
                                    formType_1 = temple;
                                    formType_1ObservableField.set(formType_1);
                                    getImagesById(id);
                                },
                                throwable -> Utility.log(TAG, throwable.getMessage())
                        )
        );
    }

    private void getImagesById(int id) {
        disposable.add(
                Observable.fromCallable(
                        ()->{
                                Utility.deleteTempImages(getApplication());

                            return true;
                        }
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        value->{
                            try {
                                frontImgObservableField.set(Utility.getLocalImageUri(getApplication(),"front",id).getPath());
                                leftImgObservableField.set(Utility.getLocalImageUri(getApplication(),"left",id).getPath());
                                rightImgObservableField.set(Utility.getLocalImageUri(getApplication(),"right",id).getPath());
                                entryImgObservableField.set(Utility.getLocalImageUri(getApplication(),"entry",id).getPath());
                            }catch(Exception e){
                                if(e instanceof FileNotFoundException){
                                    Utility.showToast(e.getMessage(),Toast.LENGTH_SHORT,getApplication());
                                }
                            }


                        }
                )
        );

    }

//    public void setFormType_1ObservableField(FormType_1 formType_1){
//        formType_1ObservableField.set(formType_1);
//    }

    public LiveData<SuccessOrFailure> getSubmitResponse() {
        Utility.log(TAG, "getSubmitResponse()");
        return submitResponseSuccessOrFailureMutableLiveData;
    }

    public LiveData getOnLocationClick() {
        Utility.log(TAG, "getOnLocationClick()");
        return locationClickMutableLiveData;
    }

    public LiveData getOnTempleAdded() {
        Utility.log(TAG, "getOnTempleAdded()");
        return addedFormMutableLivedata;
    }

    public LiveData getImageFromCamera() {
        Utility.log(TAG, "getImageFromCamera()");
        return getImageBooleanMutableLiveData;
    }


    public void onSaveClick(View view) {
        Utility.log(TAG, "onSaveClick()..\n Village:" + formType_1.village + "\n GodName:" + formType_1.god_name+ "\n Lat:" + formType_1.latitude);

        if(!isValid()){
            return;
        }
        disposable.add(

                repo_formType_1.insertForm(formType_1).
                        flatMap(
                                id -> {
                                    Utility.log(TAG, "Temple Id:" + id);
                                    Utility.renameImage("temp_front.jpg",id+"_front.jpg",getApplication());
                                    return Single.just(id);

                                }
                        ).
                        flatMap(
                                id -> {
                                    Utility.renameImage("temp_left.jpg",id+"_left.jpg",getApplication());
                                    return Single.just(id);

                                }
                        ).
                        flatMap(
                                id -> {
                                    Utility.renameImage("temp_right.jpg",id+"_right.jpg",getApplication());
                                    return Single.just(id);

                                }
                        ).
                        flatMap(
                                id -> {
                                    Utility.renameImage("temp_entry.jpg",id+"_entry.jpg",getApplication());
                                    return repo_formType_1.getFormByTempleId(id);

                                }
                        ).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(
                                temple -> addedFormMutableLivedata.setValue(temple),
                                throwable -> Utility.log(TAG, throwable.getMessage())
                        )
        );

    }

    private boolean isValid() {
        boolean value = true;
        if(formType_1.temple == null || formType_1.temple.equals("")){
             error_msg_templeName.set("Insert Temple name");
             value = false;
        }

        if(formType_1.latitude == 0.0){
            Utility.showToast("Get Location",Toast.LENGTH_SHORT,getApplication());
            value = false;
        }

        return value;
    }

    public void onSubmitClick(View view) {
        Utility.log(TAG, "onSubmitClick()");
    }

    public void onUpdateClick(View view) {
        Utility.log(TAG, "onUpdateClick()");
    }

    public void onLocationClick(View view) {
        Utility.log(TAG, "onLocationClick()");
        locationClickMutableLiveData.setValue(null);

    }

    public void onFrontBtnClick(View view) {
        Utility.log(TAG, "onFrontBtnClick()");
        dispatchTakePictureIntent(Constant.REQUEST_CODE_TAKE_FRONT_IMAGE);
    }

    public void onLeftBtnClick(View view) {
        Utility.log(TAG, "onLeftBtnClick()");
        dispatchTakePictureIntent(Constant.REQUEST_CODE_TAKE_LEFT_IMAGE);
    }

    public void onRightBtnClick(View view) {
        Utility.log(TAG, "onRightBtnClick()");
        dispatchTakePictureIntent(Constant.REQUEST_CODE_TAKE_RIGHT_IMAGE);
    }

    public void onEntryBtnClick(View view) {
        Utility.log(TAG, "onEntryBtnClick()");
        dispatchTakePictureIntent(Constant.REQUEST_CODE_TAKE_ENTRY_IMAGE);
    }

    public void dispatchTakePictureIntent(int requestType) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inSampleSize = 8;

        fileUri = Utility.getMediaFileUri(getApplication(),requestType);
        Utility.log(TAG,"URI:"+fileUri);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        //takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        if (takePictureIntent.resolveActivity(getApplication().getPackageManager()) != null) {
            imageReqType = requestType;
            getImageBooleanMutableLiveData.setValue(true);
        }
    }

    public void setImageOnImageView(int req){
        try {

            //Compress the image in background thread using RxJava
            disposable.add(

                    Observable.fromCallable(
                            ()->{
                                Bitmap out = Utility.rotateImageWithPath(fileUri.getPath());

                                File file = Utility.getMediaFile(getApplication(),req);
                                FileOutputStream fOut = new FileOutputStream(file);
                                out.compress(Bitmap.CompressFormat.JPEG, 15, fOut);
                                fOut.flush();
                                fOut.close();

                                return true;
                            }
                    )
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    value->{
                                        switch(req){

                                            case Constant.REQUEST_CODE_TAKE_FRONT_IMAGE:
                                                frontImgObservableField.set(fileUri.getPath());

                                                break;

                                            case Constant.REQUEST_CODE_TAKE_LEFT_IMAGE:
                                                leftImgObservableField.set(fileUri.getPath());

                                                break;

                                            case Constant.REQUEST_CODE_TAKE_RIGHT_IMAGE:
                                                rightImgObservableField.set(fileUri.getPath());

                                                break;

                                            case Constant.REQUEST_CODE_TAKE_ENTRY_IMAGE:
                                                entryImgObservableField.set(fileUri.getPath());

                                                break;

                                        }

                                    }

                            )
            );

        } catch (OutOfMemoryError outOfMemoryError) {
            Utility.showToast("Out of Memory.Restart App",Toast.LENGTH_LONG,getApplication());
        } catch (Exception e) {
            Utility.showToast("Failed to Capture",Toast.LENGTH_LONG,getApplication());
        }

    }

    public void getCurrentLocation() {

        markerVisibility.set(View.GONE);
        progressVisibility.set(View.VISIBLE);
        ThreadCurrentLocation threadCurrentLocation = new ThreadCurrentLocation(getApplication());
        threadCurrentLocation.start();

    }

    private void setUpLocationBroadcastReceiver() {

        locationBroadCastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                markerVisibility.set(View.VISIBLE);
                progressVisibility.set(View.GONE);

                Bundle bundle = intent.getExtras();
                Double latitude = bundle.getDouble("lat");
                Double longitude = bundle.getDouble("lon");

                Utility.log("Lat n lon ", "Latitude: " + latitude
                        + " Longitude " + longitude);
//                formType_1.latitude = latitude;
//                formType_1.longitude = longitude;

                formType_1.setLatitude(latitude);
                formType_1.setLongitude(longitude);

                }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCAST_INTENT);
        getApplication().registerReceiver(locationBroadCastReceiver, filter);
    }


    @Override
    protected void onCleared() {
        //super.onCleared();
        Utility.log(TAG,"onCleared()");
        disposable.clear();
        getApplication().unregisterReceiver(locationBroadCastReceiver);
    }


}
