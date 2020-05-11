package com.saarit.temple_management.templemanagement.model.repositories;

import android.content.Context;
import android.util.Log;

import com.saarit.temple_management.templemanagement.model.FormType_3b_1;
import com.saarit.temple_management.templemanagement.model.repositories.local_storage.AppDatabase;
import com.saarit.temple_management.templemanagement.model.repositories.local_storage.FormType_3b_1_Dao;
import com.saarit.temple_management.templemanagement.util.Utility;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Single;

public class Repo_FormType_3b_1 {

    private String TAG = Repo_FormType_3b_1.class.getSimpleName();

    private static Repo_FormType_3b_1 INSTANCE;

    private FormType_3b_1_Dao dao;


    private AppDatabase db;

    public Repo_FormType_3b_1(Context context){
        db = AppDatabase.getDataBase(context);
        dao = db.formType_3b_1_dao();
    }

    public static Repo_FormType_3b_1 getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new Repo_FormType_3b_1(context);
        }

        return INSTANCE;
    }





    public Single<Long> insertForm(FormType_3b_1 form){
        return dao.insertForm(form);
    }

    public Single<FormType_3b_1> getFormById(long id){
        return dao.getFormById(id);
    }

    public Single<FormType_3b_1> getFormByTempleId(long templeId){
        return dao.getFormByTempleId(templeId);
    }



    public Single<Long> getCount(){
        return dao.getCount();
    }

    public Single<List<Integer>> getAllTempleIds(){
        return dao.getAllTempleIds();
    }

    public Single<Integer> deleteFormByObject(FormType_3b_1 form){
        Utility.log(TAG,"About to Delete Form by object");
        return dao.deleteFormByObject(form);
    }

    public Single<Integer> deleteFormById(int id){
        Utility.log(TAG,"About to Delete Form by ID:"+id);
        return dao.deleteFormById(id);
    }

    //Practicing Unit Testing


    public String getStringData(){
        return "String Data";
    }

    public MutableLiveData<String> mutableLiveData = new MutableLiveData<>();

    public LiveData<String> getMutableLiveData(){
        mutableLiveData.setValue("Data from Actual Repo");
        return mutableLiveData;
    }

    
}
