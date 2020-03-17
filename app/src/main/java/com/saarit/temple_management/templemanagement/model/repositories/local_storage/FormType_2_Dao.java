package com.saarit.temple_management.templemanagement.model.repositories.local_storage;

import com.saarit.temple_management.templemanagement.model.Festival;
import com.saarit.temple_management.templemanagement.model.FormType_1;
import com.saarit.temple_management.templemanagement.model.FormType_2;
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
import androidx.room.Transaction;
import androidx.room.Update;
import io.reactivex.Single;

@Dao
public abstract class FormType_2_Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Single<Long> insertForm(FormType_2 form);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Single<List<Long>> insertForms(List<FormType_2> forms);

    @Query("SELECT count(id) FROM FormType_2")
    public abstract Single<Long> getCount();


    @Query("SELECT * FROM FormType_2 WHERE id = :id")
    public abstract Single<FormType_2> getFormById(long id);

    @Query("SELECT * FROM FormType_2 WHERE temple_id = :templeId")
    public abstract Single<FormType_2> getFormByTempleId(long templeId);


    @Update
    public abstract Single<Integer> updateFormByObject(FormType_2 form);


    @Delete
    public abstract Single<Integer> deleteFormByObject(FormType_2 form);

    @Query("DELETE FROM FormType_2 WHERE id = :id")
    public abstract Single<Integer> deleteFormById(int id);

    ///**************Festivals***********///
    @Query("DELETE FROM Festival WHERE temple_id = :templeId")
    public abstract Single<Integer> deleteFestivals(int templeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Single<List<Long>> insertFestivals(List<Festival> festivals);

    /*@Transaction
    public Single<List<Long>> clearAndInsertFestivals(int temple_id,List<Festival> festivals){

        deleteFestivals(temple_id);
        return insertFestivals(festivals);


    }*/

    @Query("SELECT * FROM Festival WHERE temple_id = :templeId")
    public abstract Single<List<Festival>> getFestivals(int templeId);


    ///**************Worshiping Houses************///
    @Query("DELETE FROM WorshipingHouse WHERE temple_id = :templeId")
    public abstract Single<Integer> deleteWorshipingHouses(int templeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Single<List<Long>> insertWorshipingHouses(List<WorshipingHouse> worshipingHouses);


    @Query("SELECT * FROM WorshipingHouse WHERE temple_id = :templeId")
    public abstract Single<List<WorshipingHouse>> getWorshipingHouses(int templeId);


    ///**************Poojari Works************///
    @Query("DELETE FROM PoojariWork WHERE temple_id = :templeId")
    public abstract Single<Integer> deletePoojariWorks(int templeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Single<List<Long>> insertPoojariWorks(List<PoojariWork> poojariWorks);


    @Query("SELECT * FROM PoojariWork WHERE temple_id = :templeId")
    public abstract Single<List<PoojariWork>> getPoojariWorks(int templeId);

    ///**************Respected Persons************///
    @Query("DELETE FROM RespectedPerson WHERE temple_id = :templeId")
    public abstract Single<Integer> deleteRespectedPersons(int templeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Single<List<Long>> insertRespectedPersons(List<RespectedPerson> respectedPersons);


    @Query("SELECT * FROM RespectedPerson WHERE temple_id = :templeId")
    public abstract Single<List<RespectedPerson>> getRespectedPersons(int templeId);

    ///**************Worshiping Types************///
    @Query("DELETE FROM WorshipingType WHERE temple_id = :templeId")
    public abstract Single<Integer> deleteWorshipingTypes(int templeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Single<List<Long>> insertWorshipingTypes(List<WorshipingType> worshipingTypes);


    @Query("SELECT * FROM WorshipingType WHERE temple_id = :templeId")
    public abstract Single<List<WorshipingType>> getWorshipingTypes(int templeId);


}
