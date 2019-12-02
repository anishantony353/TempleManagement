package com.saarit.temple_management.templemanagement.model.repositories;

import android.content.Context;

import com.saarit.temple_management.templemanagement.model.Temple_master;
import com.saarit.temple_management.templemanagement.util.Utility;

import java.util.List;

import io.reactivex.Single;

public class Repo_Temples_master {

    private String TAG = Repo_Temples_master.class.getSimpleName();

    private static Repo_Temples_master INSTANCE;

    private Temples_master_Dao dao;

    private AppDatabase db;

    public Repo_Temples_master(Context context){
        db = AppDatabase.getDataBase(context);
        dao = db.temples_master_dao();
    }

    public static Repo_Temples_master getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new Repo_Temples_master(context);
        }

        return INSTANCE;
    }


    public Single<List<Temple_master>> getTemples(){
        return dao.getTemples();
    }

    public Single<List<Long>> insertTemples(List<Temple_master> temples){
        Utility.log(TAG,"About to Insert List");
        return dao.insertTemples(temples);
    }







}
