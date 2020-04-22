package com.saarit.temple_management.templemanagement.model.repositories.local_storage;

import com.saarit.temple_management.templemanagement.model.FormType_5;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Single;

@Dao
public abstract class FormType_5_Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Single<Long> insertForm(FormType_5 form);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Single<List<Long>> insertForms(List<FormType_5> forms);

    @Query("SELECT count(id) FROM FormType_5")
    public abstract Single<Long> getCount();


    @Query("SELECT * FROM FormType_5 WHERE id = :id")
    public abstract Single<FormType_5> getFormById(long id);

    @Query("SELECT * FROM FormType_5 WHERE temple_id = :templeId")
    public abstract Single<FormType_5> getFormByTempleId(long templeId);


    @Update
    public abstract Single<Integer> updateFormByObject(FormType_5 form);


    @Delete
    public abstract Single<Integer> deleteFormByObject(FormType_5 form);

    @Query("DELETE FROM FormType_5 WHERE id = :id")
    public abstract Single<Integer> deleteFormById(int id);


}
