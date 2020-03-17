package com.saarit.temple_management.templemanagement.model.repositories;

import android.content.Context;

import com.saarit.temple_management.templemanagement.model.FormType_3b_2;
import com.saarit.temple_management.templemanagement.model.repositories.local_storage.AppDatabase;
import com.saarit.temple_management.templemanagement.model.repositories.local_storage.FormType_3b_2_Dao;
import com.saarit.temple_management.templemanagement.testing_objects.Inserter;
import com.saarit.temple_management.templemanagement.util.Utility;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Single;

public class Repo_FormType_3b_2 {

    private String TAG = Repo_FormType_3b_2.class.getSimpleName();

    private static Repo_FormType_3b_2 INSTANCE;

    private FormType_3b_2_Dao dao;


    private AppDatabase db;

    public Repo_FormType_3b_2(Context context){
        db = AppDatabase.getDataBase(context);
        dao = db.formType_3b_2_dao();
    }

    public static Repo_FormType_3b_2 getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new Repo_FormType_3b_2(context);
        }

        return INSTANCE;
    }
    
    public Single<Long> insertForm(FormType_3b_2 form){
        return dao.insertForm(form);
    }

    public Single<FormType_3b_2> getFormById(long id){
        return dao.getFormById(id);
    }

    public Single<FormType_3b_2> getFormByTempleId(long templeId){
        return dao.getFormByTempleId(templeId);
    }



    public Single<Long> getCount(){
        return dao.getCount();
    }

    public Single<Integer> deleteFormByObject(FormType_3b_2 form){
        Utility.log(TAG,"About to Delete Form by object");
        return dao.deleteFormByObject(form);
    }

    public Single<Integer> deleteFormById(int id){
        Utility.log(TAG,"About to Delete Form by ID:"+id);
        return dao.deleteFormById(id);
    }

    ///////////**************Practicing Unit Testing**************///////////

    public String getStringData(){
        return "String Data";
    }

    public MutableLiveData<String> mutableLiveData = new MutableLiveData<>();

    public LiveData<String> getMutableLiveData(){
        mutableLiveData.setValue("Data from Actual Repo");
        return mutableLiveData;
    }

    Inserter inserter;

    public Repo_FormType_3b_2(Inserter inserter){
        this.inserter = inserter;
    }

    public void insertValue(int value){

        inserter.insertValue(value);
    }
    
}
