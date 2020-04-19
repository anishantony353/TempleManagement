package com.saarit.temple_management.templemanagement.model.repositories;

import android.content.Context;

import com.saarit.temple_management.templemanagement.model.DonatedProduct;
import com.saarit.temple_management.templemanagement.model.Festival;
import com.saarit.temple_management.templemanagement.model.FormType_2;
import com.saarit.temple_management.templemanagement.model.FormType_4;
import com.saarit.temple_management.templemanagement.model.PoojariWork;
import com.saarit.temple_management.templemanagement.model.RespectedPerson;
import com.saarit.temple_management.templemanagement.model.WorshipingHouse;
import com.saarit.temple_management.templemanagement.model.WorshipingType;
import com.saarit.temple_management.templemanagement.model.repositories.local_storage.AppDatabase;
import com.saarit.temple_management.templemanagement.model.repositories.local_storage.FormType_2_Dao;
import com.saarit.temple_management.templemanagement.model.repositories.local_storage.FormType_4_Dao;
import com.saarit.temple_management.templemanagement.util.Utility;

import java.util.List;

import io.reactivex.Single;

public class Repo_FormType_4 {

    private String TAG = Repo_FormType_4.class.getSimpleName();

    private static Repo_FormType_4 INSTANCE;

    private FormType_4_Dao dao;


    private AppDatabase db;

    public Repo_FormType_4(Context context){
        db = AppDatabase.getDataBase(context);
        dao = db.formType_4_dao();
    }

    public static Repo_FormType_4 getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new Repo_FormType_4(context);
        }

        return INSTANCE;
    }



    public Single<Long> insertForm(FormType_4 form){
        return dao.insertForm(form);
    }

    public Single<FormType_4> getFormById(long id){
        return dao.getFormById(id);
    }

    public Single<FormType_4> getFormByTempleId(long templeId){
        return dao.getFormByTempleId(templeId);
    }



    public Single<Long> getCount(){
        return dao.getCount();
    }

    public Single<Integer> deleteFormByObject(FormType_4 form){
        Utility.log(TAG,"About to Delete Form by object");
        return dao.deleteFormByObject(form);
    }

    public Single<Integer> deleteFormById(int id){
        Utility.log(TAG,"About to Delete Form by ID:"+id);
        return dao.deleteFormById(id);
    }

    //**** Donated Products ******//
    public Single<List<Long>> insertProducts(List<DonatedProduct> products){

        Utility.log(TAG,"insertProducts.."+Thread.currentThread().getName());
        return dao.insertProducts(products);

    }

    public Single<Integer> deleteProducts(int templeId){
        Utility.log(TAG,"deleteProducts.."+Thread.currentThread().getName());
        return dao.deleteProducts(templeId);
    }

    public Single<List<DonatedProduct>> getProducts(int templeId){
        Utility.log(TAG,"getProducts.."+Thread.currentThread().getName());
        return dao.getProducts(templeId);
    }

}
