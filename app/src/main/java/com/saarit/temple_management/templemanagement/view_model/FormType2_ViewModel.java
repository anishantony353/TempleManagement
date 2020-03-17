package com.saarit.temple_management.templemanagement.view_model;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.model.Festival;
import com.saarit.temple_management.templemanagement.model.FormType_2;
import com.saarit.temple_management.templemanagement.model.PoojariWork;
import com.saarit.temple_management.templemanagement.model.RespectedPerson;
import com.saarit.temple_management.templemanagement.model.WorshipingHouse;
import com.saarit.temple_management.templemanagement.model.WorshipingType;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_FormType_1;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_FormType_2;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_server;
import com.saarit.temple_management.templemanagement.util.Constant;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view.adapters.FestivalsAdapter;
import com.saarit.temple_management.templemanagement.view.adapters.PoojariWorkAdapter;
import com.saarit.temple_management.templemanagement.view.adapters.RespectedPersonAdapter;
import com.saarit.temple_management.templemanagement.view.adapters.WorshipingHouseAdapter;
import com.saarit.temple_management.templemanagement.view.adapters.WorshipingTypeAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.EmptyResultSetException;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FormType2_ViewModel extends AndroidViewModel {
    ////*****VARIABLES*****//////

    private String TAG = FormType2_ViewModel.class.getSimpleName();

    public FormType2_ViewModel(@NonNull Application application) {
        super(application);
    }

    //// LAYOUT MODEL ////
    public FormType_2 formType_2;
    public ObservableField<FormType_2> formType_2ObservableField = new ObservableField<>();
    public ObservableField<String> local_server_new_ObservableField = new ObservableField<>("");
    public ObservableField<String> custom1ImgObservableField = new ObservableField<>();
    public ObservableField<String> custom2ImgObservableField = new ObservableField<>();

    ////VISIBILITY////
    public ObservableInt visibility_layout_worshipingHouses = new ObservableInt(View.GONE);
    public ObservableInt visibility_layout_respectedPersons = new ObservableInt(View.GONE);
    public ObservableInt visibility_layout_worshipingProcedure = new ObservableInt(View.GONE);
    public ObservableInt visibility_layout_worshipingTypes = new ObservableInt(View.GONE);
    public ObservableInt visibility_layout_otherServicemenWork = new ObservableInt(View.GONE);

    public ObservableInt progressBar = new ObservableInt(View.GONE);



    ////For Sub DBs //////
    public ArrayList<Festival> festivals = new ArrayList<>();
    public ArrayList<WorshipingHouse> worshipingHouses = new ArrayList<>();
    public ArrayList<PoojariWork> poojariWorks = new ArrayList<>();
    public ArrayList<RespectedPerson> respectedPersons = new ArrayList<>();
    public ArrayList<WorshipingType> worshipingTypes = new ArrayList<>();

    ///// RV Adapters ////
    public FestivalsAdapter festivalsAdapter;
    public WorshipingHouseAdapter worshipingHouseAdapter;
    public PoojariWorkAdapter poojariWorkAdapter;
    public RespectedPersonAdapter respectedPersonAdapter;
    public WorshipingTypeAdapter worshipingTypeAdapter;

    ///// SETTING UP ADAPTERS ON RV /////
    public ObservableField<FestivalsAdapter> festivalsAdapterObservableField = new ObservableField<>();
    public ObservableField<WorshipingHouseAdapter> worshipingHouseAdapterObservableField = new ObservableField<>();
    public ObservableField<PoojariWorkAdapter> poojariWorkAdapterObservableField = new ObservableField<>();
    public ObservableField<RespectedPersonAdapter> respectedPersonAdapterObservableField = new ObservableField<>();
    public ObservableField<WorshipingTypeAdapter> worshipingTypeAdapterObservableField = new ObservableField<>();

    ///// REPOSITORIES ////
    public Repo_FormType_1 repo_formType_1;
    public Repo_FormType_2 repo_formType_2;
    public Repo_server repo_server;

    //// RxJAVA////
    CompositeDisposable disposable;

    public int templeId;

    ///// LiveDatas /////
    public MutableLiveData finishActivity = new MutableLiveData();
    public MutableLiveData<Boolean> getImageBooleanMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> requestScreenLockMutableLiveData = new MutableLiveData<>();

    public Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    public int imageReqType;
    public Uri fileUri;


    public int REQ;


    ////*******METHODS******//////
    public void init(int temple_id) {
        Utility.log(TAG,"init()");

        repo_formType_1 = Repo_FormType_1.getInstance(getApplication());
        repo_formType_2 = Repo_FormType_2.getInstance(getApplication());
        repo_server = Repo_server.getInstance();

        disposable = new CompositeDisposable();
        templeId = temple_id;
        Utility.log(TAG,"Temple ID:"+templeId);

        festivalsAdapter = new FestivalsAdapter(R.layout.row_festival_info,this);
        festivalsAdapter.setFestivals(festivals);
        festivalsAdapterObservableField.set(festivalsAdapter);

        worshipingHouseAdapter = new WorshipingHouseAdapter(R.layout.row_worshiping_house_info,this);
        worshipingHouseAdapter.setWorshipingHouses(worshipingHouses);
        worshipingHouseAdapterObservableField.set(worshipingHouseAdapter);

        poojariWorkAdapter = new PoojariWorkAdapter(R.layout.row_poojari_work_info,this);
        poojariWorkAdapter.setPoojariWorks(poojariWorks);
        poojariWorkAdapterObservableField.set(poojariWorkAdapter);

        respectedPersonAdapter = new RespectedPersonAdapter(R.layout.row_respected_person_info,this);
        respectedPersonAdapter.setRespectedPersons(respectedPersons);
        respectedPersonAdapterObservableField.set(respectedPersonAdapter);

        worshipingTypeAdapter = new WorshipingTypeAdapter(R.layout.row_worshiping_type_info,this);
        worshipingTypeAdapter.setWorshipingTypes(worshipingTypes);
        worshipingTypeAdapterObservableField.set(worshipingTypeAdapter);

        /*formType_3a = new FormType_2();
        formType_3aObservableField.set(formType_3a);*/
        deleteTempImages();
        setUpUI();
    }

    private void setUpUI() {

        getFormType_2_ByTempleId();

    }

    private void getFormType_2_ByTempleId() {
        disposable.add(

                repo_formType_2.getFormByTempleId(templeId).toObservable().
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(
                                form -> {
                                    formType_2 = form;


                                    formType_2ObservableField.set(formType_2);
                                    local_server_new_ObservableField.set("Local");

                                    /*getSubDatas();*/
                                    fetchSubRecords();

                                },
                                throwable -> {
                                    Utility.log(TAG, throwable.getMessage());
                                    if(throwable instanceof EmptyResultSetException){
                                            //getFormType_1_ByTempleId();
                                            getFormType_2_ByTempleId_Server();
                                    }
                                },
                                () -> {
                                    Utility.log(TAG,"Fetched form 2 from Local DB");
                                },
                                dsposable -> {

                                }
                        )
        );
    }

    private void getFormType_2_ByTempleId_Server() {
        disposable.add(
                repo_server.apiService.getFormType2byTempleId(templeId).toObservable().
                        flatMap(
                                dto ->{
                                    if(dto.getSuccess() == 0){
                                        throw new EmptyResultSetException("Form 2 not found on server");
                                    }else{
                                        formType_2 = dto.getFormType_2();
                                        formType_2.id = 0;


                                        festivals.addAll(dto.getFestivals());


                                        poojariWorks.addAll(dto.getPoojariWorks());


                                        respectedPersons.addAll(dto.getRespectedPersons());


                                        worshipingTypes.addAll(dto.getWorshipingTypes());


                                        worshipingHouses.addAll(dto.getWorshipingHouses());



                                        return Observable.just(
                                                Constant.REQUEST_CODE_TAKE_CUSTOM_1_IMAGE,
                                                Constant.REQUEST_CODE_TAKE_CUSTOM_2_IMAGE

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
                                    if(throwable instanceof EmptyResultSetException){
                                        getFormType_1_ByTempleId();

                                    }

                                },
                                ()->{
                                    Utility.log(TAG,"Fetched form 2 from server");
                                    formType_2ObservableField.set(formType_2);
                                    local_server_new_ObservableField.set("Server");

                                    festivalsAdapter.notifyDataSetChanged();
                                    poojariWorkAdapter.notifyDataSetChanged();
                                    respectedPersonAdapter.notifyDataSetChanged();
                                    worshipingTypeAdapter.notifyDataSetChanged();
                                    worshipingHouseAdapter.notifyDataSetChanged();

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
                                    formType_2 = new FormType_2();
                                    formType_2.temple_id = formType_1.templeId;
                                    formType_2.temple_name = formType_1.temple;
                                    formType_2.village_name = formType_1.village;
                                    formType_2.taluka_name = formType_1.taluka;
                                    formType_2.district_name = formType_1.district;

                                    formType_2ObservableField.set(formType_2);
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
                                    formType_2 = new FormType_2();
                                    formType_2.temple_id = formType_1.templeId;
                                    formType_2.temple_name = formType_1.temple;
                                    formType_2.village_name = formType_1.village;
                                    formType_2.taluka_name = formType_1.taluka;
                                    formType_2.district_name = formType_1.district;

                                    formType_2ObservableField.set(formType_2);
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

    public void fetchSubRecords(){
        Observable.mergeArray(
                repo_formType_2.getFestivals(templeId).toObservable().subscribeOn(Schedulers.io()),
                repo_formType_2.getWorshipingHouses(templeId).toObservable().subscribeOn(Schedulers.io()),
                repo_formType_2.getPoojariWorks(templeId).toObservable().subscribeOn(Schedulers.io()),
                repo_formType_2.getRespectedPersons(templeId).toObservable().subscribeOn(Schedulers.io()),
                repo_formType_2.getWorshipingTypes(templeId).toObservable().subscribeOn(Schedulers.io())

                ).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(
                        list->{
                            if(list.size() != 0){
                                Object obj = list.get(0);
                                if(obj instanceof Festival){
                                    festivals.addAll((ArrayList<Festival>)list);
                                    festivalsAdapter.notifyDataSetChanged();
                                }else if(obj instanceof WorshipingHouse){
                                    worshipingHouses.addAll((ArrayList<WorshipingHouse>)list);
                                    worshipingHouseAdapter.notifyDataSetChanged();
                                }else if(obj instanceof PoojariWork){
                                    poojariWorks.addAll((ArrayList<PoojariWork>)list);
                                    poojariWorkAdapter.notifyDataSetChanged();
                                }else if(obj instanceof RespectedPerson){
                                    Utility.log(TAG,"Respected Persons SIZE:"+list.size());
                                    respectedPersons.addAll((ArrayList<RespectedPerson>)list);
                                    respectedPersonAdapter.notifyDataSetChanged();
                                }else if(obj instanceof WorshipingType){
                                    Utility.log(TAG,"Worshiping Types SIZE:"+list.size());
                                    worshipingTypes.addAll((ArrayList<WorshipingType>)list);
                                    worshipingTypeAdapter.notifyDataSetChanged();
                                }
                            }
                            Utility.log(TAG, "List SIZE:"+list.size());
                        },
                        throwable -> {
                            Utility.log(TAG, throwable.getMessage());
                        },
                        ()->{
                            Utility.log(TAG, "OnComplete");
                            getImagesById(formType_2.id);
                        },
                        dsposbl->{
                            Utility.log(TAG, "Initialization");
                        }
                );
    }

    private ObservableSource<?> downloadImg(int templeId, int imgType) {
        String imgUrl = "";


        switch(imgType){
            case Constant.REQUEST_CODE_TAKE_CUSTOM_1_IMAGE:
                imgUrl = Constant.IMG_URL_FORM_2 +templeId+"_image1.jpg";
                break;
            case Constant.REQUEST_CODE_TAKE_CUSTOM_2_IMAGE:
                imgUrl = Constant.IMG_URL_FORM_2 +templeId+"_image2.jpg";
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
            case Constant.REQUEST_CODE_TAKE_CUSTOM_1_IMAGE:
                custom1ImgObservableField.set(Utility.getMediaFileUri(getApplication(),imgType).getPath());
                break;
            case Constant.REQUEST_CODE_TAKE_CUSTOM_2_IMAGE:
                custom2ImgObservableField.set(Utility.getMediaFileUri(getApplication(),imgType).getPath());
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

    private void getImagesById(int id) {
        try {
            custom1ImgObservableField.set(Utility.getLocalImageUri(getApplication(),Constant.IMAGE_TYPE_CUSTOM_1,id).getPath());
        }catch(Exception e){
            if(e instanceof FileNotFoundException){
                Utility.showToast(e.getMessage(),Toast.LENGTH_SHORT,getApplication());
            }
        }

        try{
            custom2ImgObservableField.set(Utility.getLocalImageUri(getApplication(),Constant.IMAGE_TYPE_CUSTOM_2,id).getPath());
        }catch(Exception e){
            if(e instanceof FileNotFoundException){
                Utility.showToast(e.getMessage(),Toast.LENGTH_SHORT,getApplication());
            }
        }

    }


    public void onSaveClick(View view){
        Utility.log(TAG,"onSaveClick");


        Utility.log(TAG,"Model Info:"+formType_2.toString());
        save_or_submit_form2(Constant.REQ_SAVE);

    }

    private void save_or_submit_form2(int req) {

        disposable.add(
                repo_formType_2.insertForm(formType_2).toObservable().
                        flatMap(
                                id -> {
                                    Utility.renameImage("temp_"+Constant.IMAGE_TYPE_CUSTOM_1+".jpg",id+"_"+Constant.IMAGE_TYPE_CUSTOM_1+".jpg",getApplication());
                                    return Observable.just(id);

                                }
                        ).
                        flatMap(
                                id -> {
                                    Utility.renameImage("temp_"+Constant.IMAGE_TYPE_CUSTOM_2+".jpg",id+"_"+Constant.IMAGE_TYPE_CUSTOM_2+".jpg",getApplication());
                                    return Observable.just(id);

                                }
                        ).
                        flatMap(
                                id->{
                                    if(!formType_2.is_worship_different_houses){
                                        worshipingHouses.clear();
                                    }
                                    if(!formType_2.is_respected_person_available){
                                        respectedPersons.clear();
                                    }
                                    if(!formType_2.types_of_worshiping){
                                        worshipingTypes.clear();
                                    }


                                    return Observable.just(id);
                                }
                        ).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                id -> {
                                    Utility.log(TAG,"Added Form 2   ID:"+id);
                                    formType_2.id = id.intValue();
                                    deleteSubRecords(req);
                                },
                                throwable -> {
                                    Utility.log(TAG,throwable.getMessage());
                                    Utility.showToast(throwable.getMessage(),Toast.LENGTH_SHORT,getApplication());
                                    requestScreenLockMutableLiveData.setValue(false);
                                    progressBar.set(View.GONE);
                                },
                                ()->{},
                                disposble->{
                                    requestScreenLockMutableLiveData.setValue(true);
                                    progressBar.set(View.VISIBLE);
                                    if(!formType_2.is_worshiping_regular_basis){
                                        formType_2.worshiping_procedure = null;
                                    }
                                    if(!formType_2.is_other_servicemen_available){
                                        formType_2.servicemen_work = null;
                                    }
                                }


                        )
        );
    }

    public void onSubmitClick(View view) {
        Utility.log(TAG, "onSubmitClick()");

            save_or_submit_form2(Constant.REQ_SUBMIT);

    }



    public void deleteSubRecords(int req){
        Observable.mergeArray(
                repo_formType_2.deleteFestivals(templeId).toObservable().subscribeOn(Schedulers.io()),
                repo_formType_2.deleteWorshipingHouses(templeId).toObservable().subscribeOn(Schedulers.io()),
                repo_formType_2.deletePoojariWorks(templeId).toObservable().subscribeOn(Schedulers.io()),
                repo_formType_2.deleteRespectedPersons(templeId).toObservable().subscribeOn(Schedulers.io()),
                repo_formType_2.deleteWorshipingTypes(templeId).toObservable().subscribeOn(Schedulers.io())

                ).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(
                    list->{
                        Utility.log(TAG,"Deleted Sub Data");
                    },
                    throwable -> {
                        Utility.log(TAG,throwable.getMessage());
                        Utility.showToast(throwable.getMessage(),Toast.LENGTH_SHORT,getApplication());
                        requestScreenLockMutableLiveData.setValue(false);
                        progressBar.set(View.GONE);
                    },
                    ()->{
                        Utility.log(TAG,"Finished Deleting");
                        insertSubRecords(req);
                    }
                );
    }

    private void insertSubRecords(int req) {

        Observable.mergeArray(
                repo_formType_2.insertFestivals(festivals).toObservable().subscribeOn(Schedulers.io()),
                repo_formType_2.insertWorshipingHouses(worshipingHouses).toObservable().subscribeOn(Schedulers.io()),
                repo_formType_2.insertPoojariWorks(poojariWorks).toObservable().subscribeOn(Schedulers.io()),
                repo_formType_2.insertRespectedPersons(respectedPersons).toObservable().subscribeOn(Schedulers.io()),
                repo_formType_2.insertWorshipingTypes(worshipingTypes).toObservable().subscribeOn(Schedulers.io())
                ).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(
                        list->{
                            Utility.log(TAG,"Added Sub Data");
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
                                    submitForm2();
                                    break;
                            }


                        }

                );

    }

    private void submitForm2() {
        MultipartBody.Part custom1ImgPart=null,custom2ImgPart=null;


        try {
            File custom1ImgFile = Utility.getLocalImageFile(getApplication(),Constant.IMAGE_TYPE_CUSTOM_1,formType_2.id);
            RequestBody frontFileReq = RequestBody.create(MediaType.parse("image/*"),custom1ImgFile);

            custom1ImgPart = MultipartBody.Part.createFormData("img1",
                    "custom1_img.jpg",
                    frontFileReq);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            File custom2ImgFile = Utility.getLocalImageFile(getApplication(),Constant.IMAGE_TYPE_CUSTOM_2,formType_2.id);
            RequestBody leftFileReq = RequestBody.create(MediaType.parse("image/*"),custom2ImgFile);

            custom2ImgPart = MultipartBody.Part.createFormData("img2",
                    "custom2_img.jpg",
                    leftFileReq);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        disposable.add(

                repo_server.apiService.addForm2(formType_2,festivals,poojariWorks,worshipingHouses,respectedPersons,worshipingTypes,custom1ImgPart,custom2ImgPart)
                        .flatMap(
                                baseResponse -> {

                                    Utility.log(TAG,"Success:"+baseResponse.getSuccess());
                                    if(baseResponse.getSuccess() == 1){
                                        Utility.deleteImagesById_Form2(getApplication(),formType_2.id);
                                        return repo_formType_2.deleteFormById(formType_2.id).toObservable();

                                    }else{
                                        throw new Exception(baseResponse.getMsg());
                                    }

                                }
                        )
                        .flatMap(
                                value->{

                                    return Observable.mergeArray(
                                                repo_formType_2.deleteFestivals(templeId).toObservable().subscribeOn(Schedulers.io()),
                                                repo_formType_2.deleteWorshipingHouses(templeId).toObservable().subscribeOn(Schedulers.io()),
                                                repo_formType_2.deletePoojariWorks(templeId).toObservable().subscribeOn(Schedulers.io()),
                                                repo_formType_2.deleteRespectedPersons(templeId).toObservable().subscribeOn(Schedulers.io()),
                                                repo_formType_2.deleteWorshipingTypes(templeId).toObservable().subscribeOn(Schedulers.io())
                                            );

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

    public void onAddClick(int type){

        switch(type){
            case Constant.CLICK_FESTIVAL:
                Utility.log(TAG,"onAddClick Festival");
                Festival festival = new Festival();
                festival.temple_id = templeId;
                festivals.add(festival);

                festivalsAdapter.notifyItemInserted(festivals.size() - 1);
                break;

            case Constant.CLICK_WORSHIPING_HOUSE:
                Utility.log(TAG,"onAddClick Woshiping house");
                WorshipingHouse worshipingHouse = new WorshipingHouse();
                worshipingHouse.temple_id = templeId;
                worshipingHouses.add(worshipingHouse);

                worshipingHouseAdapter.notifyItemInserted(worshipingHouses.size() - 1);
                break;
            case Constant.CLICK_POOJARI_WORK:
                Utility.log(TAG,"onAddClick Poojari Work");
                PoojariWork poojariWork = new PoojariWork();
                poojariWork.temple_id = templeId;
                poojariWorks.add(poojariWork);

                poojariWorkAdapter.notifyItemInserted(poojariWorks.size() - 1);
                break;
            case Constant.CLICK_RESPECTED_PERSON:
                Utility.log(TAG,"onAddClick Respected person");
                RespectedPerson respectedPerson = new RespectedPerson();
                respectedPerson.temple_id = templeId;
                respectedPersons.add(respectedPerson);

                respectedPersonAdapter.notifyItemInserted(respectedPersons.size() - 1);
                break;
            case Constant.CLICK_WORSHIPING_TYPE:
                Utility.log(TAG,"onAddClick Worshiping type");
                WorshipingType worshipingType = new WorshipingType();
                worshipingType.temple_id = templeId;
                worshipingTypes.add(worshipingType);

                worshipingTypeAdapter.notifyItemInserted(worshipingTypes.size() - 1);
                break;

        }

    }

    public void onRemoveClick(int type,int pos){

        switch(type){
            case Constant.CLICK_FESTIVAL:
                Utility.log(TAG,"onRemoveClick..Pos:"+pos);
                festivals.remove(pos);
                festivalsAdapter.notifyDataSetChanged();
                break;

            case Constant.CLICK_WORSHIPING_HOUSE:
                Utility.log(TAG,"onRemoveClick..Pos:"+pos);
                worshipingHouses.remove(pos);
                worshipingHouseAdapter.notifyDataSetChanged();
                break;

            case Constant.CLICK_POOJARI_WORK:
                Utility.log(TAG,"onRemoveClick..Pos:"+pos);
                poojariWorks.remove(pos);
                poojariWorkAdapter.notifyDataSetChanged();
                break;

            case Constant.CLICK_RESPECTED_PERSON:
                Utility.log(TAG,"onRemoveClick..Pos:"+pos);
                respectedPersons.remove(pos);
                respectedPersonAdapter.notifyDataSetChanged();
                break;
            case Constant.CLICK_WORSHIPING_TYPE:
                Utility.log(TAG,"onRemoveClick..Pos:"+pos);
                worshipingTypes.remove(pos);
                worshipingTypeAdapter.notifyDataSetChanged();
                break;
        }
    }

    public void onSwitchCheckedChange(View view,boolean value,int type){
        Utility.log(TAG,"onSwitchCheckedChange..Status:"+value+" Type:"+type);

        switch (type){
            case Constant.SWITCH_WORSHIPING_HOUSE:
                if(value){
                    visibility_layout_worshipingHouses.set(View.VISIBLE);

                }else{
                    visibility_layout_worshipingHouses.set(View.GONE);
                }
                break;
            case Constant.SWITCH_RESPECTED_PERSON_AVAILABLE:
                if(value){
                    visibility_layout_respectedPersons.set(View.VISIBLE);
                }else{
                    visibility_layout_respectedPersons.set(View.GONE);
                }
                break;
            case Constant.SWITCH_WORSHIPING_REGULAR_BASIS:
                if(value){
                    visibility_layout_worshipingProcedure.set(View.VISIBLE);
                }else{
                    visibility_layout_worshipingProcedure.set(View.GONE);
                }
                break;
            case Constant.SWITCH_WORSHIPING_TYPE:
                if(value){
                    visibility_layout_worshipingTypes.set(View.VISIBLE);
                }else{
                    visibility_layout_worshipingTypes.set(View.GONE);
                }
                break;
            case Constant.SWITCH_OTHER_SERVICES_PRESENT:
                if(value){
                    visibility_layout_otherServicemenWork.set(View.VISIBLE);
                }else{
                    visibility_layout_otherServicemenWork.set(View.GONE);
                }
                break;
        }

    }

    public void onCustom1BtnClick(View view) {
        Utility.log(TAG, "onCustom1BtnClick()");
        dispatchTakePictureIntent(Constant.REQUEST_CODE_TAKE_CUSTOM_1_IMAGE);
    }

    public void onCustom2BtnClick(View view) {
        Utility.log(TAG, "onCustom2BtnClick()");
        dispatchTakePictureIntent(Constant.REQUEST_CODE_TAKE_CUSTOM_2_IMAGE);
    }

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

                                        case Constant.REQUEST_CODE_TAKE_CUSTOM_1_IMAGE:
                                            custom1ImgObservableField.set(fileUri.getPath());

                                            break;

                                        case Constant.REQUEST_CODE_TAKE_CUSTOM_2_IMAGE:
                                            custom2ImgObservableField.set(fileUri.getPath());

                                            break;

                                    }

                                },
                                throwable -> {
                                    Utility.showToast(throwable.getMessage(),Toast.LENGTH_LONG,getApplication());
                                }

                        )
        );
    }

    private void deleteTempImages() {
        disposable.add(
                Observable.fromCallable(
                            ()->{
                                Utility.deleteTempImages_Form2(getApplication());

                                return true;
                            }
                        )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(

                        )
        );

    }

    // Observed Methods//
    public LiveData shouldFinishActivity(){
        return finishActivity;
    }
    public LiveData getImageFromCamera() {
        Utility.log(TAG, "getImageFromCamera()");
        return getImageBooleanMutableLiveData;
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
