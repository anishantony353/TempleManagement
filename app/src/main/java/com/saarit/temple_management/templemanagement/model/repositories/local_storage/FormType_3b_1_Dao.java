package com.saarit.temple_management.templemanagement.model.repositories.local_storage;

import com.saarit.temple_management.templemanagement.model.FormType_3b_1;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Single;

@Dao
public abstract class FormType_3b_1_Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Single<Long> insertForm(FormType_3b_1 form);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Single<List<Long>> insertForms(List<FormType_3b_1> forms);

    @Query("SELECT count(id) FROM FormType_3b_1")
    public abstract Single<Long> getCount();

    @Query("SELECT temple_id FROM FormType_3b_1")
    public abstract Single<List<Integer>> getAllTempleIds();


    @Query("SELECT * FROM FormType_3b_1 WHERE id = :id")
    public abstract Single<FormType_3b_1> getFormById(long id);

    @Query("SELECT * FROM FormType_3b_1 WHERE temple_id = :templeId")
    public abstract Single<FormType_3b_1> getFormByTempleId(long templeId);


    @Update
    public abstract Single<Integer> updateFormByObject(FormType_3b_1 form);


    @Delete
    public abstract Single<Integer> deleteFormByObject(FormType_3b_1 form);

    @Query("DELETE FROM FormType_3b_1 WHERE id = :id")
    public abstract Single<Integer> deleteFormById(int id);


}
