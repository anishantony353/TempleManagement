package com.saarit.temple_management.templemanagement.model.repositories;

import android.content.Context;

import com.saarit.temple_management.templemanagement.model.FormType_1;
import com.saarit.temple_management.templemanagement.model.repositories.local_storage.AppDatabase;
import com.saarit.temple_management.templemanagement.model.repositories.local_storage.FormType_1_Dao;
import com.saarit.temple_management.templemanagement.util.Utility;

import java.util.List;

import io.reactivex.Single;

public class Repo_FormType_1 {

    private String TAG = Repo_FormType_1.class.getSimpleName();

    private static Repo_FormType_1 INSTANCE;

    private FormType_1_Dao dao;

    private AppDatabase db;

    public Repo_FormType_1(Context context){
        db = AppDatabase.getDataBase(context);
        dao = db.formType_1_dao();
    }

    public static Repo_FormType_1 getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new Repo_FormType_1(context);
        }

        return INSTANCE;
    }



    public Single<Long> insertForm(FormType_1 form){
        return dao.insertForm(form);
    }

    public Single<FormType_1> getFormById(long id){
        return dao.getFormById(id);
    }

    public Single<FormType_1> getFormByTempleId(long templeId){
        return dao.getFormByTempleId(templeId);
    }

    public Single<List<FormType_1>> getLocalTemples(){
        return dao.getLocalTemples();
    }

    public Single<List<FormType_1>> getLocalTemplesForDropDown(){
        return dao.getLocalTemplesForDropDown();
    }

    public Single<List<Long>> insertForms(List<FormType_1> forms){
        Utility.log(TAG,"About to Insert List");
        return dao.insertForms(forms);
    }

    public Single<Long> getCount(){
        return dao.getCount();
    }

    public Single<Integer> getCountByTempleId(long templeId){
        return dao.getCountByTempleId(templeId);
    }

    public Single<Integer> deleteFormByObject(FormType_1 form){
        Utility.log(TAG,"About to Delete Form by object");
        return dao.deleteFormByObject(form);
    }

    public Single<Integer> deleteFormById(int id){
        Utility.log(TAG,"About to Delete Form by ID:"+id);
        return dao.deleteFormById(id);
    }


}
