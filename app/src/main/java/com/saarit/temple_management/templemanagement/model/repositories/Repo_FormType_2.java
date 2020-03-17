package com.saarit.temple_management.templemanagement.model.repositories;

import android.content.Context;

import com.saarit.temple_management.templemanagement.model.Festival;
import com.saarit.temple_management.templemanagement.model.FormType_2;
import com.saarit.temple_management.templemanagement.model.PoojariWork;
import com.saarit.temple_management.templemanagement.model.RespectedPerson;
import com.saarit.temple_management.templemanagement.model.WorshipingHouse;
import com.saarit.temple_management.templemanagement.model.WorshipingType;
import com.saarit.temple_management.templemanagement.model.repositories.local_storage.AppDatabase;
import com.saarit.temple_management.templemanagement.model.repositories.local_storage.FormType_2_Dao;
import com.saarit.temple_management.templemanagement.util.Utility;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public class Repo_FormType_2 {

    private String TAG = Repo_FormType_2.class.getSimpleName();

    private static Repo_FormType_2 INSTANCE;

    private FormType_2_Dao dao;


    private AppDatabase db;

    public Repo_FormType_2(Context context){
        db = AppDatabase.getDataBase(context);
        dao = db.formType_2_dao();
    }

    public static Repo_FormType_2 getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new Repo_FormType_2(context);
        }

        return INSTANCE;
    }



    public Single<Long> insertForm(FormType_2 form){
        return dao.insertForm(form);
    }

    public Single<FormType_2> getFormById(long id){
        return dao.getFormById(id);
    }

    public Single<FormType_2> getFormByTempleId(long templeId){
        return dao.getFormByTempleId(templeId);
    }



    public Single<Long> getCount(){
        return dao.getCount();
    }

    public Single<Integer> deleteFormByObject(FormType_2 form){
        Utility.log(TAG,"About to Delete Form by object");
        return dao.deleteFormByObject(form);
    }

    public Single<Integer> deleteFormById(int id){
        Utility.log(TAG,"About to Delete Form by ID:"+id);
        return dao.deleteFormById(id);
    }

    //**** Festivals******//
    public Single<List<Long>> insertFestivals(List<Festival> festivals){

        Utility.log(TAG,"insertFestivals.."+Thread.currentThread().getName());
        return dao.insertFestivals(festivals);

    }

    public Single<Integer> deleteFestivals(int templeId){
        Utility.log(TAG,"deleteFestivals.."+Thread.currentThread().getName());
        return dao.deleteFestivals(templeId);
    }

//    public Single clearAndInsertFestivals(int temple_id,List<Festival> festivals){
//        Utility.log(TAG,"About to clear and insert festivals");
//
//         return dao.clearAndInsertFestivals(temple_id,festivals);
//
//
//
//    }

    public Single<List<Festival>> getFestivals(int templeId){
        Utility.log(TAG,"getFestivals.."+Thread.currentThread().getName());
        return dao.getFestivals(templeId);
    }

    //**** Worshiping Houses ******//
    public Single<List<Long>> insertWorshipingHouses(List<WorshipingHouse> worshipingHouses){

        Utility.log(TAG,"insertWorshipingHouses.."+Thread.currentThread().getName());
        return dao.insertWorshipingHouses(worshipingHouses);

    }

    public Single<Integer> deleteWorshipingHouses(int templeId){
        Utility.log(TAG,"deleteWorshipingHouses.."+Thread.currentThread().getName());
        return dao.deleteWorshipingHouses(templeId);
    }



    public Single<List<WorshipingHouse>> getWorshipingHouses(int templeId){
        Utility.log(TAG,"getWorshipingHouses.."+Thread.currentThread().getName());
        return dao.getWorshipingHouses(templeId);
    }

    //**** Poojari Works ******//
    public Single<List<Long>> insertPoojariWorks(List<PoojariWork> poojariWorks){

        Utility.log(TAG,"insertPoojariWorks.."+Thread.currentThread().getName());
        return dao.insertPoojariWorks(poojariWorks);

    }

    public Single<Integer> deletePoojariWorks(int templeId){
        Utility.log(TAG,"deletePoojariWorks.."+Thread.currentThread().getName());
        return dao.deletePoojariWorks(templeId);
    }



    public Single<List<PoojariWork>> getPoojariWorks(int templeId){
        Utility.log(TAG,"getPoojariWorks.."+Thread.currentThread().getName());
        return dao.getPoojariWorks(templeId);
    }

    //**** Respected Persons ******//
    public Single<List<Long>> insertRespectedPersons(List<RespectedPerson> respectedPersons){

        Utility.log(TAG,"insertRespectedPersons.."+Thread.currentThread().getName());
        return dao.insertRespectedPersons(respectedPersons);

    }

    public Single<Integer> deleteRespectedPersons(int templeId){
        Utility.log(TAG,"deleteRespectedPersons.."+Thread.currentThread().getName());
        return dao.deleteRespectedPersons(templeId);
    }



    public Single<List<RespectedPerson>> getRespectedPersons(int templeId){
        Utility.log(TAG,"getRespectedPersons.."+Thread.currentThread().getName());
        return dao.getRespectedPersons(templeId);
    }

    //**** Worshiping Type ******//
    public Single<List<Long>> insertWorshipingTypes(List<WorshipingType> worshipingTypes){

        Utility.log(TAG,"insertWorshipingTypes.."+Thread.currentThread().getName());
        return dao.insertWorshipingTypes(worshipingTypes);

    }

    public Single<Integer> deleteWorshipingTypes(int templeId){
        Utility.log(TAG,"deleteWorshipingTypes.."+Thread.currentThread().getName());
        return dao.deleteWorshipingTypes(templeId);
    }



    public Single<List<WorshipingType>> getWorshipingTypes(int templeId){
        Utility.log(TAG,"getWorshipingTypes.."+Thread.currentThread().getName());
        return dao.getWorshipingTypes(templeId);
    }



}
