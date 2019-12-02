package com.saarit.temple_management.templemanagement.view_model;


import android.view.View;

import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.model.not_in_use.FormName;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view.adapters.FormListAdapter;

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

public class FormsViewModel extends ViewModel {

    private String TAG = FormsViewModel.class.getSimpleName();


    private FormListAdapter adapter;
    public MutableLiveData<List<FormName>> formList;
    public MutableLiveData<FormName> selectedForm;
    //public ObservableArrayMap<String, String> images;
    public ObservableInt loading;
    public ObservableInt showEmpty;
    private CompositeDisposable disposable = new CompositeDisposable();

    //public ObservableField<String> firstName = new ObservableField<>();


    public void init() {
        Utility.log(TAG,"init()");
        formList = new MutableLiveData<>();
        selectedForm = new MutableLiveData<>();
        adapter = new FormListAdapter(R.layout.form_list_row, this);
        //images = new ObservableArrayMap<>();
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);

        //toObservable(firstName);


    }


    public FormListAdapter getAdapter() {
        Utility.log(TAG,"getAdapter()");
        return adapter;
    }

    public void fetchList(){

        Utility.log(TAG,"fetchList()");

        //submittedList.setValue(formTypes);

        //Use RxJava to fetch List

        disposable.add(
                getObservable().delay(2000, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<FormName>>() {
                            @Override
                            public void onSuccess(List<FormName> forms) {

                                formList.setValue(forms);

                            }

                            @Override
                            public void onError(Throwable e) {

                                Utility.log(TAG,"RxJAVA...onError()");

                            }
                        })
        );

    }

    private Single<List<FormName>> getObservable() {
        return Single.create(new SingleOnSubscribe<List<FormName>>() {
            @Override
            public void subscribe(SingleEmitter<List<FormName>> emitter) throws Exception {
                List<FormName> forms = new ArrayList<>();
                forms.add(new FormName("A51485"));
                forms.add(new FormName("A14844"));
                forms.add(new FormName("A55574"));
                forms.add(new FormName("A96899"));
                forms.add(new FormName("A35788"));
                forms.add(new FormName("A21439"));
                forms.add(new FormName("A87845"));
                forms.add(new FormName("A85478"));
                forms.add(new FormName("A11115"));
                emitter.onSuccess(forms);
            }
        });
    }

    public LiveData<List<FormName>> getList(){

        return formList;
    }

    public MutableLiveData<FormName> getSelectedForm(){

        return selectedForm;
    }


    public void setListInAdapter(List<FormName> forms) {

        this.adapter.setForms(forms);
        this.adapter.notifyDataSetChanged();


    }

    public void onItemClick(Integer position){

        FormName formNameNotinuse = getFormByPosition(position);

        selectedForm.setValue(formNameNotinuse);

    }

    public FormName getFormByPosition(Integer position) {

        return formList.getValue().get(position);
    }

    public String getName(){
        return formList.getValue().get(0).getName();
    }

    @Override
    protected void onCleared() {

        Utility.log(TAG,"ViewModel... onCleared()");

        disposable.clear();
        super.onCleared();
    }
}
