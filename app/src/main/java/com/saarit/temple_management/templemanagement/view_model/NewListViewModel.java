package com.saarit.temple_management.templemanagement.view_model;


import android.view.View;

import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.model.not_in_use.FormType;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view.adapters.NewListAdapter;

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

public class NewListViewModel extends ViewModel {

    private String TAG = NewListViewModel.class.getSimpleName();

    //private FormTypes formtypes;
    private NewListAdapter adapter;
    public MutableLiveData<List<FormType>> newList;
    public MutableLiveData<FormType> selectedForm;
    //public ObservableArrayMap<String, String> images;
    public ObservableInt loading;
    public ObservableInt showEmpty;
    private CompositeDisposable disposable = new CompositeDisposable();


    public void init() {
        Utility.log(TAG,"init()");
        newList = new MutableLiveData<>();
        selectedForm = new MutableLiveData<>();
        adapter = new NewListAdapter(R.layout.new_list_row, this);
        //images = new ObservableArrayMap<>();
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
    }


    public NewListAdapter getAdapter() {
        Utility.log(TAG,"getAdapter()");
        return adapter;
    }

    public void fetchList(){

        Utility.log(TAG,"fetchList()");


        //newList.setValue(formTypes);

        //Use RxJava to fetch List

        disposable.add(
                getObservable().delay(3000, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<FormType>>() {
                            @Override
                            public void onSuccess(List<FormType> formTypeNotinuses) {

                                newList.setValue(formTypeNotinuses);

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
                List<FormType> formTypeNotinuses = new ArrayList<>();
                formTypeNotinuses.add(new FormType("Type new Aaa"));
                formTypeNotinuses.add(new FormType("Type new bbbb"));
                formTypeNotinuses.add(new FormType("Type new cccc"));
                formTypeNotinuses.add(new FormType("Type new ffff"));
                formTypeNotinuses.add(new FormType("Type new jfhfg"));
                emitter.onSuccess(formTypeNotinuses);
            }
        });
    }

    public LiveData<List<FormType>> getList(){

        return newList;
    }

    public MutableLiveData<FormType> getSelectedForm(){

        return selectedForm;
    }


    public void setListInAdapter(List<FormType> formTypeNotinuses) {

        this.adapter.setFormTypeNotinuses(formTypeNotinuses);
        this.adapter.notifyDataSetChanged();


    }

    public void onItemClick(Integer position){

        FormType formTypeNotinuse = getFormTypeByPosition(position);

        selectedForm.setValue(formTypeNotinuse);

    }

    public FormType getFormTypeByPosition(Integer position) {

        return newList.getValue().get(position);
    }

    public String getName(){
        return newList.getValue().get(0).getName();
    }

    @Override
    protected void onCleared() {
        disposable.clear();
        super.onCleared();
    }
}
