package com.saarit.temple_management.templemanagement.model;

import android.content.Context;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Single;

public class Repo_FormType_1 {

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

    public Single<FormType_1> getFormByTempleId(long id){
        return dao.getFormByTempleId(id);
    }

    public Single<List<FormType_1>> getLocalTemples(){
        return dao.getLocalTemples();
    }



}
