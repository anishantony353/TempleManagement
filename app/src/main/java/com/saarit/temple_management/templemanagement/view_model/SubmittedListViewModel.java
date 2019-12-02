package com.saarit.temple_management.templemanagement.view_model;


import android.view.View;

import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.model.not_in_use.FormType;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view.adapters.SubmittedListAdapter;

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

public class SubmittedListViewModel extends ViewModel {

    private String TAG = SubmittedListViewModel.class.getSimpleName();

    //private FormTypes formtypes;
    private SubmittedListAdapter adapter;
    public MutableLiveData<List<FormType>> submittedList;
    public MutableLiveData<FormType> selectedForm;
    //public ObservableArrayMap<String, String> images;
    public ObservableInt loading;
    public ObservableInt showEmpty;
    private CompositeDisposable disposable = new CompositeDisposable();

    public void init() {
        Utility.log(TAG,"init()");
        submittedList = new MutableLiveData<>();
        selectedForm = new MutableLiveData<>();
        adapter = new SubmittedListAdapter(R.layout.submitted_list_row, this);
        //images = new ObservableArrayMap<>();
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
    }


    public SubmittedListAdapter getAdapter() {
        Utility.log(TAG,"getAdapter()");
        return adapter;
    }

    public void fetchList(){

        Utility.log(TAG,"fetchList()");

        //submittedList.setValue(formTypes);

        //Use RxJava to fetch List

        disposable.add(
                getObservable().delay(5000, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<FormType>>() {
                            @Override
                            public void onSuccess(List<FormType> formTypeNotinuses) {

                                submittedList.setValue(formTypeNotinuses);
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
                formTypeNotinuses.add(new FormType("Type A"));
                formTypeNotinuses.add(new FormType("Type 2"));
                formTypeNotinuses.add(new FormType("Type 3"));
                formTypeNotinuses.add(new FormType("Type 4"));
                formTypeNotinuses.add(new FormType("Type 5"));
                formTypeNotinuses.add(new FormType("Type 6"));
                formTypeNotinuses.add(new FormType("Type 7"));
                formTypeNotinuses.add(new FormType("Type 8"));
                formTypeNotinuses.add(new FormType("Type 9"));
                emitter.onSuccess(formTypeNotinuses);
            }
        });
    }

    public LiveData<List<FormType>> getList(){

        return submittedList;
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

        return submittedList.getValue().get(position);
    }

    public String getName(){
        return submittedList.getValue().get(0).getName();
    }

    @Override
    protected void onCleared() {
        disposable.clear();
        super.onCleared();
    }
}
