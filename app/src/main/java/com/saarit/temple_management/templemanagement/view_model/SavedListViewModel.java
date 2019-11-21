package com.saarit.temple_management.templemanagement.view_model;


import android.view.View;

import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.model.FormType;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view.adapters.SavedListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SavedListViewModel extends ViewModel {

    private String TAG = SavedListViewModel.class.getSimpleName();

    //private FormTypes formtypes;
    private SavedListAdapter adapter;
    public MutableLiveData<List<FormType>> savedList;
    public MutableLiveData<FormType> selectedForm;
    //public ObservableArrayMap<String, String> images;
    public ObservableInt loading;
    public ObservableInt showEmpty;

    private CompositeDisposable disposable = new CompositeDisposable();


    public void init() {
        Utility.log(TAG,"init()");
        savedList = new MutableLiveData<>();
        selectedForm = new MutableLiveData<>();
        adapter = new SavedListAdapter(R.layout.saved_list_row, this);
        //images = new ObservableArrayMap<>();
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
    }


    public SavedListAdapter getAdapter() {
        Utility.log(TAG,"getAdapter()");
        return adapter;
    }

    public void fetchList(){

        Utility.log(TAG,"fetchList()");


        //savedList.setValue(formTypes);

        //Use RxJava to fetch List

        disposable.add(
                getObservable().delay(8000, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<FormType>>() {
                            @Override
                            public void onSuccess(List<FormType> formTypes) {

                                savedList.setValue(formTypes);

                            }

                            @Override
                            public void onError(Throwable e) {

                                Utility.log(TAG,"RxJAVA...onError()");



                            }
                        })
        );

    }

    private Single<List<FormType>> getObservable() {
        return Single.create(new SingleOnSubscribe<List<FormType>>() {
            @Override
            public void subscribe(SingleEmitter<List<FormType>> emitter) throws Exception {
                List<FormType> formTypes = new ArrayList<>();
                formTypes.add(new FormType("Type Aaa"));
                formTypes.add(new FormType("Type bbbb"));
                formTypes.add(new FormType("Type cccc"));
                formTypes.add(new FormType("Type ffff"));
                formTypes.add(new FormType("Type jfhfg"));
                emitter.onSuccess(formTypes);
            }
        });
    }

    public LiveData<List<FormType>> getList(){

        return savedList;
    }

    public MutableLiveData<FormType> getSelectedForm(){

        return selectedForm;
    }


    public void setListInAdapter(List<FormType> formTypes) {

        this.adapter.setFormTypes(formTypes);
        this.adapter.notifyDataSetChanged();


    }

    public void onItemClick(Integer position){

        FormType formType = getFormTypeByPosition(position);

        selectedForm.setValue(formType);

    }

    public FormType getFormTypeByPosition(Integer position) {

        return savedList.getValue().get(position);
    }

    public String getName(){
        return savedList.getValue().get(0).getName();
    }

    @Override
    protected void onCleared() {
        disposable.clear();
        super.onCleared();
    }
}