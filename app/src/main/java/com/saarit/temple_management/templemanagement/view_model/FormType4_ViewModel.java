package com.saarit.temple_management.templemanagement.view_model;

import android.app.Application;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.model.DonatedProduct;
import com.saarit.temple_management.templemanagement.model.FormType_4;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_FormType_1;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_FormType_4;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_server;
import com.saarit.temple_management.templemanagement.util.Constant;
import com.saarit.temple_management.templemanagement.util.PrefManager;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view.adapters.DonatedProductsAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FormType4_ViewModel extends AndroidViewModel {

    String TAG = FormType4_ViewModel.class.getSimpleName();
    public int templeId;
    public FormType4_ViewModel(@NonNull Application application) {
        super(application);
    }

    public FormType_4 formType_4 = new FormType_4();
    public ArrayList<DonatedProduct> products = new ArrayList<>();
    public DonatedProductsAdapter donatedProductsAdapter;
    public ObservableField<String> local_server_new_ObservableField = new ObservableField<>("");
    public ObservableInt progressBar = new ObservableInt(View.GONE);
    public ObservableField<DonatedProductsAdapter> productsAdapterObservableField = new ObservableField<>();

    ///// LiveDatas /////
    public MutableLiveData finishActivity = new MutableLiveData();
    public MutableLiveData<Boolean> requestScreenLockMutableLiveData = new MutableLiveData<>();

    CompositeDisposable disposable = new CompositeDisposable();

    public void init(int id){
        templeId = id;
        donatedProductsAdapter = new DonatedProductsAdapter(R.layout.row_donation_product_info,this);
        donatedProductsAdapter.setDonatedProducts(products);
        productsAdapterObservableField.set(donatedProductsAdapter);

        setUpUI();
    }

    private void setUpUI() {

        getFormType_4_ByTempleId();

    }

    private void getFormType_4_ByTempleId() {
        disposable.add(

                Repo_FormType_4.getInstance(getApplication()).getFormByTempleId(templeId).toObservable().
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(
                                form -> {
                                    formType_4 = form;
                                    formType_4.notifyChange();
                                    local_server_new_ObservableField.set("Local");

                                    fetchSubRecords();

                                },
                                throwable -> {
                                    Utility.log(TAG, throwable.getMessage());
                                    if(throwable instanceof EmptyResultSetException){
                                        //getFormType_1_ByTempleId();
                                        getFormType_4_ByTempleId_Server();
                                    }
                                },
                                () -> {
                                    Utility.log(TAG,"Fetched form 4 from Local DB");
                                },
                                dsposable -> {

                                }
                        )
        );
    }

    private void getFormType_4_ByTempleId_Server() {
        disposable.add(
                Repo_server.getInstance().apiService.getFormType4byTempleId(templeId).toObservable().
                        flatMap(
                                dto ->{
                                    if(dto.getSuccess() == 0){
                                        throw new EmptyResultSetException("Form 4 not found on server");
                                    }else{
                                        formType_4 = dto.getFormType_4();
                                        formType_4.id = 0;


                                        products.addAll(dto.getProducts());

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
                                    Utility.log(TAG,"Fetched form 4 from server");
                                    formType_4.notifyChange();
                                    local_server_new_ObservableField.set("Server");

                                    donatedProductsAdapter.notifyDataSetChanged();


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
                                    formType_4.temple_id = formType_1.templeId;
                                    formType_4.temple_name = formType_1.temple;
                                    formType_4.village_name = formType_1.village;
                                    formType_4.taluka_name = formType_1.taluka;
                                    formType_4.district_name = formType_1.district;
                                    formType_4.god_name = formType_1.god_name;

                                    formType_4.notifyChange();
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

                                    formType_4.temple_id = formType_1.templeId;
                                    formType_4.temple_name = formType_1.temple;
                                    formType_4.village_name = formType_1.village;
                                    formType_4.taluka_name = formType_1.taluka;
                                    formType_4.district_name = formType_1.district;
                                    formType_4.god_name = formType_1.god_name;

                                    formType_4.notifyChange();
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

    private void fetchSubRecords(){

                Repo_FormType_4.getInstance(getApplication()).getProducts(templeId).toObservable().subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(
                        list->{
                            if(list.size() != 0){
                                products.addAll(list);
                                donatedProductsAdapter.notifyDataSetChanged();
                            }
                            Utility.log(TAG, "List SIZE:"+list.size());
                        },
                        throwable -> {
                            Utility.log(TAG, throwable.getMessage());
                        },
                        ()->{
                            Utility.log(TAG, "OnComplete");
                        },
                        dsposbl->{
                            Utility.log(TAG, "Initialization");
                        }
                );
    }

    public void onSwitchCheckedChange(boolean value){
        Utility.log(TAG,"Switch value:"+value);
        formType_4.setIs_donation_box_available(value);
    }

    public void onItemSelected(AdapterView<?> parent,int position,int product_list_position){
        if(position == 0){
            return;
        }
        Utility.log(TAG,"Selected:"+parent.getItemAtPosition(position));
        products.get(product_list_position).product_type = parent.getItemAtPosition(position).toString();
    }

    public void onAddClick(View view){
        DonatedProduct donatedProduct = new DonatedProduct();
        donatedProduct.temple_id = templeId;
        products.add(products.size()==0?0:products.size() - 1,donatedProduct);
        donatedProductsAdapter.notifyItemInserted(products.size() - 1);

    }
    public void onRemoveClick(int pos){
        Utility.log(TAG,"onRemoveClick()..Pos:"+pos);
        products.remove(pos);
        donatedProductsAdapter.notifyDataSetChanged();
        /*if(products.size() != 0){
            donatedProductsAdapter.notifyItemRangeChanged(pos,products.size());
        }else{
            donatedProductsAdapter.notifyDataSetChanged();
        }*/
        /*donatedProductsAdapter.notifyItemRangeChanged(pos,products.size());*/

    }

    public void onSaveClick(View view){
        Utility.log(TAG, "onSaveClick()");
        Utility.log(TAG, "Values:"+formType_4);
        save_submit_Form(Constant.REQ_SAVE);
    }
    public void onSubmitClick(View view){
        Utility.log(TAG, "onSubmitClick()");
        formType_4.user_id = PrefManager.getUserId(getApplication());
        save_submit_Form(Constant.REQ_SUBMIT);
    }

    private void save_submit_Form(int req_type) {
        disposable.add(
                Repo_FormType_4.getInstance(getApplication()).insertForm(formType_4).toObservable().
                        flatMap(
                                id->{
                                    Repo_FormType_4.getInstance(getApplication()).deleteProducts(id.intValue()).toObservable();
                                    Repo_FormType_4.getInstance(getApplication()).insertProducts(products).toObservable();

                                    return Observable.just(id);
                                }
                        ).
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

                Repo_server.getInstance().apiService.addForm4(formType_4,products)
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
