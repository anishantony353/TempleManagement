package com.saarit.temple_management.templemanagement.model.repositories.local_storage;

import com.saarit.temple_management.templemanagement.model.DonatedProduct;
import com.saarit.temple_management.templemanagement.model.Festival;
import com.saarit.temple_management.templemanagement.model.FormType_2;
import com.saarit.temple_management.templemanagement.model.FormType_4;
import com.saarit.temple_management.templemanagement.model.PoojariWork;
import com.saarit.temple_management.templemanagement.model.RespectedPerson;
import com.saarit.temple_management.templemanagement.model.WorshipingHouse;
import com.saarit.temple_management.templemanagement.model.WorshipingType;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Single;

@Dao
public abstract class FormType_4_Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Single<Long> insertForm(FormType_4 form);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Single<List<Long>> insertForms(List<FormType_4> forms);

    @Query("SELECT count(id) FROM FormType_4")
    public abstract Integer getCount();


    @Query("SELECT * FROM FormType_4 WHERE id = :id")
    public abstract Single<FormType_4> getFormById(long id);

    @Query("SELECT * FROM FormType_4 WHERE temple_id = :templeId")
    public abstract Single<FormType_4> getFormByTempleId(long templeId);


    @Update
    public abstract Single<Integer> updateFormByObject(FormType_4 form);


    @Delete
    public abstract Single<Integer> deleteFormByObject(FormType_4 form);

    @Query("DELETE FROM FormType_4 WHERE id = :id")
    public abstract Single<Integer> deleteFormById(int id);

    ///**************Products***********///
    @Query("DELETE FROM DonatedProduct WHERE temple_id = :templeId")
    public abstract Single<Integer> deleteProducts(int templeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> insertProducts(List<DonatedProduct> products);


    @Query("SELECT * FROM DonatedProduct WHERE temple_id = :templeId")
    public abstract Single<List<DonatedProduct>> getProducts(int templeId);

}
