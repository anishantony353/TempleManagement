package com.saarit.temple_management.templemanagement.model.repositories;

import android.content.Context;

import com.saarit.temple_management.templemanagement.model.Festival;
import com.saarit.temple_management.templemanagement.model.FormType_3a;
import com.saarit.temple_management.templemanagement.model.PoojariWork;
import com.saarit.temple_management.templemanagement.model.RespectedPerson;
import com.saarit.temple_management.templemanagement.model.WorshipingHouse;
import com.saarit.temple_management.templemanagement.model.WorshipingType;
import com.saarit.temple_management.templemanagement.model.repositories.local_storage.AppDatabase;
import com.saarit.temple_management.templemanagement.model.repositories.local_storage.FormType_3a_Dao;
import com.saarit.temple_management.templemanagement.model.repositories.local_storage.FormType_3a_Dao;
import com.saarit.temple_management.templemanagement.util.Utility;

import java.util.List;

import io.reactivex.Single;

public class Repo_FormType_3a {

    private String TAG = Repo_FormType_3a.class.getSimpleName();

    private static Repo_FormType_3a INSTANCE;

    private FormType_3a_Dao dao;


    private AppDatabase db;

    public Repo_FormType_3a(Context context){
        db = AppDatabase.getDataBase(context);
        dao = db.formType_3a_dao();
    }

    public static Repo_FormType_3a getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new Repo_FormType_3a(context);
        }

        return INSTANCE;
    }



    public Single<Long> insertForm(FormType_3a form){
        return dao.insertForm(form);
    }

    public Single<FormType_3a> getFormById(long id){
        return dao.getFormById(id);
    }

    public Single<FormType_3a> getFormByTempleId(long templeId){
        return dao.getFormByTempleId(templeId);
    }



    public Single<Long> getCount(){
        return dao.getCount();
    }

    public Single<List<Integer>> getAllTempleIds(){
        return dao.getAllTempleIds();
    }

    public Single<Integer> deleteFormByObject(FormType_3a form){
        Utility.log(TAG,"About to Delete Form by object");
        return dao.deleteFormByObject(form);
    }

    public Single<Integer> deleteFormById(int id){
        Utility.log(TAG,"About to Delete Form by ID:"+id);
        return dao.deleteFormById(id);
    }
    
}
