package com.saarit.temple_management.templemanagement.view_model;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.patloew.rxlocation.RxLocation;
import com.saarit.temple_management.templemanagement.model.FormType_1;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_FormType_1;
import com.saarit.temple_management.templemanagement.util.PrefManager;
import com.saarit.temple_management.templemanagement.util.not_in_use.ThreadCurrentLocation;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_Temples_master;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_server;
import com.saarit.temple_management.templemanagement.model.Temple_master;
import com.saarit.temple_management.templemanagement.util.Constant;
import com.saarit.temple_management.templemanagement.util.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FormType1_ViewModel extends AndroidViewModel {

    private String TAG = FormType1_ViewModel.class.getSimpleName();

    public FormType1_ViewModel(@NonNull Application application) {
        super(application);
    }


    Repo_FormType_1 repo_formType_1;
    Repo_server repo_server;
    Repo_Temples_master repo_temples_master;

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
    public ObservableInt progressSubmitting = new ObservableInt(View.GONE);
    public int temple_id;

    public ObservableInt markerVisibility = new ObservableInt(View.VISIBLE);
    public ObservableInt progressVisibility = new ObservableInt(View.GONE);


    public ObservableField<String> error_msg_templeName = new ObservableField<>(Constant.VALID);
    public ObservableField<String> error_msg_latitude = new ObservableField<>(Constant.VALID);
    public ObservableField<String> error_msg_longitude = new ObservableField<>(Constant.VALID);
    public ObservableField<ArrayAdapter<Temple_master>> adapterTemples = new ObservableField<>();
    public ObservableField<AdapterView.OnItemClickListener> listner = new ObservableField<>();


    //To Observe Submit Response
    public MutableLiveData<Integer> submitResponseMutableLiveData = new MutableLiveData<>();

    //To Observe 'Get Location' click
    public MutableLiveData locationClickMutableLiveData = new MutableLiveData();
    //To Observe newly added Temple in SqLite
    public MutableLiveData<FormType_1> addedFormMutableLivedata = new MutableLiveData<>();
    //To Observe 'Get Image' click
    public MutableLiveData<Boolean> getImageBooleanMutableLiveData = new MutableLiveData<>();

    //To Observe 'Get NextForms' click
    public MutableLiveData<Boolean> nextFormsMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Boolean> requestScreenLockMutableLiveData = new MutableLiveData<>();


    RxLocation rxLocation = new RxLocation(getApplication());
    LocationRequest locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
            .setInterval(5000);

    Disposable locDisposable;

    public Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    public int imageReqType;
    public Uri fileUri;

    public ArrayAdapter<Temple_master> autotextview_adapter;

    public AdapterView.OnItemClickListener onItemClickListener;

    public void init(int REQ_TYPE, int id, int templeId) {



        reqType = new ObservableInt(REQ_TYPE);

        repo_temples_master = Repo_Temples_master.getInstance(getApplication());
        repo_formType_1 = Repo_FormType_1.getInstance(getApplication());
        repo_server = Repo_server.getInstance();

        //setUpLocationBroadcastReceiver();
        formType_1ObservableField = new ObservableField<>();
        frontImgObservableField = new ObservableField<>();
        leftImgObservableField = new ObservableField<>();
        rightImgObservableField = new ObservableField<>();
        entryImgObservableField = new ObservableField<>();


        deleteTempImages();
        setUpUI(REQ_TYPE, id, templeId);

        onItemClickListener = (adapterView, view, i, l)->{

            Temple_master temple = (Temple_master) adapterView.getAdapter().getItem(i);
            Utility.log(TAG,"Temple:"+temple.temple);
            Utility.log(TAG,"Village:"+temple.village);
            Utility.log(TAG,"Taluka:"+temple.taluka);
            Utility.log(TAG,"District:"+temple.district);

            formType_1.setTemple(temple.temple);
            formType_1.setVillage(temple.village);
            formType_1.setTaluka(temple.taluka);
            formType_1.setDistrict(temple.district);

            formType_1.templeId = temple.templeId;

        };

    }

    private void setUpUI(int req_type, int id, int templeId) {

        disposable.add(

                repo_temples_master.getTemplesForSelection(templeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        listOfTemple->{

                            Utility.log(TAG,"List Size:"+listOfTemple.size());

                            autotextview_adapter = new ArrayAdapter<>(getApplication(),android.R.layout.simple_list_item_1,listOfTemple);
                            Utility.log(TAG,"Adapter Count:"+autotextview_adapter.getCount());


                            adapterTemples.set(autotextview_adapter);
                            listner.set(onItemClickListener);

                            switch(req_type){
                                case Constant.REQUEST_CODE_CLICK_LOCAL_TREES:
                                    markerVisibility.set(View.GONE);
                                    progressVisibility.set(View.GONE);

                                    getFormType_1_ById(id);

                                    break;
                                case Constant.REQUEST_CODE_CLICK_SERVER_TREES:
                                    markerVisibility.set(View.GONE);
                                    progressVisibility.set(View.GONE);
                                    Utility.log(TAG,"about to fetch server form");
                                    getFormType_1_ByTempleId_Server(templeId);

                                    break;
                                default:
                                    formType_1 = new FormType_1();
                                    formType_1ObservableField.set(formType_1);
                                    break;

                            }

                        }
                )
        );


    }

    private void getFormType_1_ByTempleId_Server(int templeId) {
        disposable.add(

                repo_server.apiService.getFormType1byTempleId(templeId).toObservable().
                        flatMap(
                                dto ->{
                                    if(dto.getSuccess() == 0){
                                        throw new Exception(dto.getMsg());
                                    }else{
                                        formType_1 = dto.getFormType_1();
                                        formType_1.id = 0;
                                        temple_id = formType_1.templeId;
                                         return Observable.just(
                                                     Constant.REQUEST_CODE_TAKE_FRONT_IMAGE,
                                                     Constant.REQUEST_CODE_TAKE_LEFT_IMAGE,
                                                     Constant.REQUEST_CODE_TAKE_RIGHT_IMAGE,
                                                     Constant.REQUEST_CODE_TAKE_ENTRY_IMAGE
                                         )
                                        .flatMap(imgType->downloadImg(templeId,imgType))
                                        .subscribeOn(Schedulers.io());
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
                                    requestScreenLockMutableLiveData.setValue(false);
                                    progressSubmitting.set(View.GONE);
                                },
                                ()->{
                                    Utility.log(TAG,"Updating UI with  server values");
                                    formType_1ObservableField.set(formType_1);
                                    Utility.log(TAG,"Form Id:"+formType_1.id);
                                    Utility.log(TAG,"Temple Id:"+formType_1.templeId);
                                    requestScreenLockMutableLiveData.setValue(false);
                                    progressSubmitting.set(View.GONE);
                                },
                                dsposble->{
                                    requestScreenLockMutableLiveData.setValue(true);
                                    progressSubmitting.set(View.VISIBLE);

                                }
                        )
        );
    }

    private ObservableSource<?> downloadImg(int templeId, int imgType) {
        String imgUrl = "";


        switch(imgType){
            case Constant.REQUEST_CODE_TAKE_FRONT_IMAGE:
                imgUrl = Constant.IMG_URL_FORM_1 +templeId+"_image1.jpg";
                break;
            case Constant.REQUEST_CODE_TAKE_LEFT_IMAGE:
                imgUrl = Constant.IMG_URL_FORM_1 +templeId+"_image2.jpg";
                break;
            case Constant.REQUEST_CODE_TAKE_RIGHT_IMAGE:
                imgUrl = Constant.IMG_URL_FORM_1 +templeId+"_image3.jpg";
                break;
            case Constant.REQUEST_CODE_TAKE_ENTRY_IMAGE:
                imgUrl = Constant.IMG_URL_FORM_1 +templeId+"_image4.jpg";
                break;

        }

        Utility.log(TAG,"Glide about to download image");

        /*frontImgObservableField.set(imgUrl);
        leftImgObservableField.set(imgUrl);
        rightImgObservableField.set(imgUrl);
        entryImgObservableField.set(imgUrl);*/



        try {
            InputStream is = null;
            is = new java.net.URL(imgUrl).openStream();

            if(is != null){
                Utility.log(TAG,"STREAM not NULL");
                //Bitmap bitmap = BitmapFactory.decodeStream(stream);


                OutputStream os = new FileOutputStream(
                            Utility.getMediaFile(getApplication(),
                                    imgType)
                    );

                byte[] b = new byte[2048];
                int length;

                while ((length = is.read(b)) != -1) {
                    os.write(b, 0, length);
                }

                is.close();
                os.close();
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        switch(imgType){
            case Constant.REQUEST_CODE_TAKE_FRONT_IMAGE:
                frontImgObservableField.set(Utility.getMediaFileUri(getApplication(),imgType).getPath());
                break;
            case Constant.REQUEST_CODE_TAKE_LEFT_IMAGE:
                leftImgObservableField.set(Utility.getMediaFileUri(getApplication(),imgType).getPath());
                break;
            case Constant.REQUEST_CODE_TAKE_RIGHT_IMAGE:
                rightImgObservableField.set(Utility.getMediaFileUri(getApplication(),imgType).getPath());
                break;
            case Constant.REQUEST_CODE_TAKE_ENTRY_IMAGE:
                entryImgObservableField.set(Utility.getMediaFileUri(getApplication(),imgType).getPath());
                break;
        }



        /*RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .priority(Priority.HIGH)
                .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())));

        Glide.with(getApplication()).
                asBitmap()
                .load(imgUrl)
                .apply(options)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {


                        try {
                            Utility.log(TAG,"ABout to put bitmap in file");
                            OutputStream fOut = new FileOutputStream(
                                    Utility.getMediaFile(getApplication(),
                                            imgType)
                            );
                            resource.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                            fOut.close();

                            switch(imgType){
                                case Constant.REQUEST_CODE_TAKE_FRONT_IMAGE:
                                    frontImgObservableField.set(Utility.getMediaFileUri(getApplication(),imgType).getPath());
                                    break;
                                case Constant.REQUEST_CODE_TAKE_LEFT_IMAGE:
                                    leftImgObservableField.set(Utility.getMediaFileUri(getApplication(),imgType).getPath());
                                    break;
                                case Constant.REQUEST_CODE_TAKE_RIGHT_IMAGE:
                                    rightImgObservableField.set(Utility.getMediaFileUri(getApplication(),imgType).getPath());
                                    break;
                                case Constant.REQUEST_CODE_TAKE_ENTRY_IMAGE:
                                    entryImgObservableField.set(Utility.getMediaFileUri(getApplication(),imgType).getPath());
                                    break;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });*/
        return Observable.just(true);
    }

    private void getFormType_1_ById(int id) {
        disposable.add(

                repo_formType_1.getFormById(id).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(
                                form -> {
                                    formType_1 = form;
                                    formType_1ObservableField.set(formType_1);
                                    Utility.log(TAG,"Form Id:"+formType_1.id);
                                    Utility.log(TAG,"Temple Id:"+formType_1.templeId);
                                    temple_id = formType_1.templeId;
                                    getImagesById(id);
                                },
                                throwable -> Utility.log(TAG, throwable.getMessage())
                        )
        );
    }

    private void deleteTempImages() {
        disposable.add(
                Observable.fromCallable(
                        ()->{
                            Utility.deleteTempImages_Form1(getApplication());

                            return true;
                        }
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(

                        )
        );

    }

    private void getImagesById(int id) {
        try {
            frontImgObservableField.set(Utility.getLocalImageUri(getApplication(),Constant.IMAGE_TYPE_FRONT,id).getPath());
        }catch(Exception e){
            if(e instanceof FileNotFoundException){
                Utility.showToast(e.getMessage(),Toast.LENGTH_SHORT,getApplication());
            }
        }

        try{
            leftImgObservableField.set(Utility.getLocalImageUri(getApplication(),Constant.IMAGE_TYPE_LEFT,id).getPath());
        }catch(Exception e){
            if(e instanceof FileNotFoundException){
                Utility.showToast(e.getMessage(),Toast.LENGTH_SHORT,getApplication());
            }
        }
        try{
            rightImgObservableField.set(Utility.getLocalImageUri(getApplication(),Constant.IMAGE_TYPE_RIGHT,id).getPath());
        }catch(Exception e){
            if(e instanceof FileNotFoundException){
                Utility.showToast(e.getMessage(),Toast.LENGTH_SHORT,getApplication());
            }
        }
        try{
            entryImgObservableField.set(Utility.getLocalImageUri(getApplication(),Constant.IMAGE_TYPE_ENTRY,id).getPath());
        }catch(Exception e){
            if(e instanceof FileNotFoundException){
                Utility.showToast(e.getMessage(),Toast.LENGTH_SHORT,getApplication());
            }
        }

    }

//    public void setFormType_1ObservableField(FormType_1 formType_1){
//        formType_1ObservableField.set(formType_1);
//    }

    public LiveData<Integer> getSubmitResponse() {
        Utility.log(TAG, "getSubmitResponse()");
        return submitResponseMutableLiveData;
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

    public LiveData getNextFormsActivity(){
        Utility.log(TAG, "getNextFormsActivity()");
        return nextFormsMutableLiveData;
    }

    public LiveData<Boolean> observeLockScreenRequest() {
        return requestScreenLockMutableLiveData;
    }


    public void onSaveClick(View view) {
        Utility.log(TAG, "onSaveClick()..\n Village:" + formType_1.village + "\n GodName:" + formType_1.god_name+ "\n Lat:" + formType_1.latitude);

        if(!isValid()){
            return;
        }


        disposable.add(

                repo_formType_1.getCountByTempleId(formType_1.templeId).
                        flatMap(
                                count ->{
                                    if(reqType.get() == Constant.REQUEST_CODE_CLICK_LOCAL_TREES){
                                        return repo_formType_1.insertForm(formType_1);
                                    }else{
                                        if(count > 0){
                                            throw new Exception("Temple already exist locally");
                                        }else{

                                            return repo_formType_1.insertForm(formType_1);
                                        }
                                    }

                                }
                        ).
                        flatMap(
                                id -> {
                                    Utility.log(TAG, "Form ID:" + id);
                                    Utility.renameImage("temp_"+Constant.IMAGE_TYPE_FRONT+".jpg",id+"_"+Constant.IMAGE_TYPE_FRONT+".jpg",getApplication());
                                    return Single.just(id);

                                }
                        ).
                        flatMap(
                                id -> {
                                    Utility.renameImage("temp_"+Constant.IMAGE_TYPE_LEFT+".jpg",id+"_"+Constant.IMAGE_TYPE_LEFT+".jpg",getApplication());
                                    return Single.just(id);

                                }
                        ).
                        flatMap(
                                id -> {
                                    Utility.renameImage("temp_"+Constant.IMAGE_TYPE_RIGHT+".jpg",id+"_"+Constant.IMAGE_TYPE_RIGHT+".jpg",getApplication());
                                    return Single.just(id);

                                }
                        ).
                        flatMap(
                                id -> {
                                    Utility.renameImage("temp_"+Constant.IMAGE_TYPE_ENTRY+".jpg",id+"_"+Constant.IMAGE_TYPE_ENTRY+".jpg",getApplication());
                                    return repo_formType_1.getFormById(id);

                                }
                        ).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(
                                temple -> addedFormMutableLivedata.setValue(temple),
                                throwable -> {
                                    Utility.log(TAG, throwable.getMessage());
                                    Utility.showToast(throwable.getMessage(),Toast.LENGTH_SHORT,getApplication());
                                }
                        )
        );

    }

    private boolean isValid() {
        Utility.log(TAG,"Temple Id:"+formType_1.templeId);
        boolean value = true;
        if(formType_1.templeId == 0){
            Utility.log(TAG,"Temple Id inside If:"+formType_1.templeId);
             error_msg_templeName.set("Select Temple");
            error_msg_templeName.notifyChange();
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
        if(isValid()){
            Utility.showToast("Submitting...",Toast.LENGTH_LONG,getApplication());
            requestScreenLockMutableLiveData.setValue(true);
            progressSubmitting.set(View.VISIBLE);

            formType_1.user_id = PrefManager.getUserId(getApplication());
            saveBeforeSubmit();

        }
    }

    private void saveBeforeSubmit() {
        disposable.add(

                repo_formType_1.insertForm(formType_1).
                        flatMap(
                                id -> {
                                    Utility.log(TAG, "Form ID:" + id);
                                    Utility.renameImage("temp_"+Constant.IMAGE_TYPE_FRONT+".jpg",id+"_"+Constant.IMAGE_TYPE_FRONT+".jpg",getApplication());
                                    return Single.just(id);

                                }
                        ).
                        flatMap(
                                id -> {
                                    Utility.renameImage("temp_"+Constant.IMAGE_TYPE_LEFT+".jpg",id+"_"+Constant.IMAGE_TYPE_LEFT+".jpg",getApplication());
                                    return Single.just(id);

                                }
                        ).
                        flatMap(
                                id -> {
                                    Utility.renameImage("temp_"+Constant.IMAGE_TYPE_RIGHT+".jpg",id+"_"+Constant.IMAGE_TYPE_RIGHT+".jpg",getApplication());
                                    return Single.just(id);

                                }
                        ).
                        flatMap(
                                id -> {
                                    Utility.renameImage("temp_"+Constant.IMAGE_TYPE_ENTRY+".jpg",id+"_"+Constant.IMAGE_TYPE_ENTRY+".jpg",getApplication());
                                    return Single.just(id);

                                }
                        ).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(
                                id -> {
                                    formType_1.id = id.intValue();
                                    submitTemple();
                                },
                                throwable -> {
                                    Utility.log(TAG, throwable.getMessage());
                                    Utility.showToast(throwable.getMessage(),Toast.LENGTH_SHORT,getApplication());
                                    requestScreenLockMutableLiveData.setValue(false);
                                    progressSubmitting.set(View.GONE);


                                }
                        )
        );

    }

    private void submitTemple() {
        MultipartBody.Part frontImgPart=null,leftImgPart=null,rightImgPart=null,entryImgPart=null;


        try {
            File frontImgFile = Utility.getLocalImageFile(getApplication(),Constant.IMAGE_TYPE_FRONT,formType_1.id);
            RequestBody frontFileReq = RequestBody.create(MediaType.parse("image/*"),frontImgFile);

            frontImgPart = MultipartBody.Part.createFormData("img1",
                    "front_img.jpg",
                    frontFileReq);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            File leftImgFile = Utility.getLocalImageFile(getApplication(),Constant.IMAGE_TYPE_LEFT,formType_1.id);
            RequestBody leftFileReq = RequestBody.create(MediaType.parse("image/*"),leftImgFile);

            leftImgPart = MultipartBody.Part.createFormData("img2",
                    "left_img.jpg",
                    leftFileReq);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            File rightImgFile = Utility.getLocalImageFile(getApplication(),Constant.IMAGE_TYPE_RIGHT,formType_1.id);
            RequestBody rightFileReq = RequestBody.create(MediaType.parse("image/*"),rightImgFile);

            rightImgPart = MultipartBody.Part.createFormData("img3",
                    "right_img.jpg",
                    rightFileReq);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            File entryImgFile = Utility.getLocalImageFile(getApplication(),Constant.IMAGE_TYPE_ENTRY,formType_1.id);
            RequestBody entryFileReq = RequestBody.create(MediaType.parse("image/*"),entryImgFile);

            entryImgPart = MultipartBody.Part.createFormData("img4",
                    "entry_img.jpg",
                    entryFileReq);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        disposable.add(

                repo_server.apiService.addTemple(formType_1,frontImgPart,leftImgPart,rightImgPart,entryImgPart)
                        .flatMap(
                             baseResponse -> {

                                 Utility.log(TAG,"Success:"+baseResponse.getSuccess());
                                 if(baseResponse.getSuccess() == 1){
                                     Utility.deleteImagesById_Form1(getApplication(),formType_1.id);
                                     return repo_formType_1.deleteFormById(formType_1.id).toObservable();

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
                                    requestScreenLockMutableLiveData.setValue(false);
                                    progressSubmitting.set(View.GONE);
                                    submitResponseMutableLiveData.setValue(value);


                                },
                                throwable -> {
                                    Utility.log(TAG,throwable.getMessage());
                                    Utility.showToast("Saved Locally"+"\n"+throwable.getMessage(),Toast.LENGTH_LONG,getApplication());
                                    requestScreenLockMutableLiveData.setValue(false);
                                    progressSubmitting.set(View.GONE);

                                },
                                () -> {
                                    Utility.log(TAG,"completed");
                                },
                                dspsbl -> {

                                    Utility.log(TAG,"Initialilize");

                                }

                        )

        );

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

    public void onNextFormsClick(View view){
        Utility.log(TAG, "onNextFormsClick()");
        nextFormsMutableLiveData.setValue(true);

    }

    public void onTextChanged(CharSequence s, int start, int before, int count){
        Utility.log(TAG, "onTextChanged()");
        formType_1.templeId = 0;

    }

//    public void onItemClick(AdapterView<?> adapterView,View view,int pos,long id){
//        FormType_1 formType1 = ((ArrayAdapter<FormType_1>)adapterView.getAdapter()).getItem(pos);
//
//        Utility.log(TAG,"From Method...Temple:"+formType1.temple);
//
//    }

    public void dispatchTakePictureIntent(int requestType) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inSampleSize = 8;

        fileUri = Utility.getMediaFileUri(getApplication(),requestType);
        Utility.log(TAG,"URI:"+fileUri);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);


        if (takePictureIntent.resolveActivity(getApplication().getPackageManager()) != null) {
            imageReqType = requestType;
            getImageBooleanMutableLiveData.setValue(true);
        }
    }

    public void setImageOnImageView(int req){


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

                                    },
                                    throwable -> {
                                        Utility.showToast(throwable.getMessage(),Toast.LENGTH_LONG,getApplication());
                                    }

                            )
            );


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

                            formType_1.setLatitude(location.getLatitude());
                            formType_1.setLongitude(location.getLongitude());

                        },
                        throwable -> {},
                        () -> {},
                        dsposable -> {
                            markerVisibility.set(View.GONE);
                            progressVisibility.set(View.VISIBLE);
                        }
                );

    }

/*    public void setUpLocationBroadcastReceiver() {
        Utility.log(TAG,"setUpLocationBroadcastReceiver()");

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
                // Observing individual feilds
                formType_1.setLatitude(latitude);
                formType_1.setLongitude(longitude);

                //Observing entire Object
                *//*formType_1.latitude = latitude;
                formType_1.longitude = longitude;

                formType_1ObservableField.set(formType_1);
                formType_1ObservableField.notifyChange();*//*



                }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCAST_INTENT_FORM_TYPE_1_ACTIVITY);
        getApplication().registerReceiver(locationBroadCastReceiver, filter);
    }*/

    /*public void unRegisterReceiver(){
        Utility.log(TAG,"unRegisterReceiver()");
        getApplication().unregisterReceiver(locationBroadCastReceiver);

//            Utility.log(TAG,"Thread is Alive");
//            threadCurrentLocation.interrupt();


    }*/


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
