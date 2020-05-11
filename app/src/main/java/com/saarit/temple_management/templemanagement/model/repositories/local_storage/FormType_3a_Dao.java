package com.saarit.temple_management.templemanagement.model.repositories.local_storage;

import com.saarit.temple_management.templemanagement.model.Festival;
import com.saarit.temple_management.templemanagement.model.FormType_3a;
import com.saarit.temple_management.templemanagement.model.FormType_3a;
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
public abstract class FormType_3a_Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Single<Long> insertForm(FormType_3a form);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Single<List<Long>> insertForms(List<FormType_3a> forms);

    @Query("SELECT count(id) FROM FormType_3a")
    public abstract Single<Long> getCount();

    @Query("SELECT temple_id FROM FormType_3a")
    public abstract Single<List<Integer>> getAllTempleIds();


    @Query("SELECT * FROM FormType_3a WHERE id = :id")
    public abstract Single<FormType_3a> getFormById(long id);

    @Query("SELECT * FROM FormType_3a WHERE temple_id = :templeId")
    public abstract Single<FormType_3a> getFormByTempleId(long templeId);


    @Update
    public abstract Single<Integer> updateFormByObject(FormType_3a form);


    @Delete
    public abstract Single<Integer> deleteFormByObject(FormType_3a form);

    @Query("DELETE FROM FormType_3a WHERE id = :id")
    public abstract Single<Integer> deleteFormById(int id);


}
