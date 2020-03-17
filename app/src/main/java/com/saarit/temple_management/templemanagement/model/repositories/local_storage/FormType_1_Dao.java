package com.saarit.temple_management.templemanagement.model.repositories.local_storage;

import com.saarit.temple_management.templemanagement.model.FormType_1;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface FormType_1_Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertForm(FormType_1 form);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<List<Long>> insertForms(List<FormType_1> forms);

    @Query("SELECT count(temple_id) FROM FormType_1")
    Single<Long> getCount();


    @Query("SELECT * FROM FormType_1 WHERE id = :id")
    Single<FormType_1> getFormById(long id);

    @Query("SELECT * FROM FormType_1 WHERE temple_id = :templeId")
    Single<FormType_1> getFormByTempleId(long templeId);

    @Query("SELECT COUNT(id) FROM FormType_1 WHERE temple_id = :templeId")
    Single<Integer> getCountByTempleId(long templeId);

    @Query("SELECT * FROM FormType_1 where latitude != 0.0")
    Single<List<FormType_1>> getLocalTemples();

    @Query("SELECT * FROM FormType_1 where latitude == 0.0")
    Single<List<FormType_1>> getLocalTemplesForDropDown();

    @Update
    Single<Integer> updateFormByObject(FormType_1 form);


    @Delete
    Single<Integer> deleteFormByObject(FormType_1 form);

    @Query("DELETE FROM FormType_1 WHERE id = :id")
    Single<Integer> deleteFormById(int id);










//    @Query("SELECT * FROM FormType_1 WHERE user_id = :user_id")
//    Single<List<FormType_1>> getAllTrees(long user_id);
//

//
//    @Query("SELECT count(id) FROM TreeData WHERE tree_details_id_old = :id")
//    Integer getNumOfOldTreeById(long id);
//
//    @Query("SELECT count(id) FROM TreeData WHERE task_id = :id")
//    Integer getNumOfRecordByTaskId(long id);
//
//    @Query("SELECT COUNT(id) FROM TreeData WHERE user_id = :user_id")
//    Flowable<Integer> getTreesCount(long user_id);
//
////    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
////            "last_name LIKE :last LIMIT 1")
////    User findByName(String first, String last);
//

//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    Single<List<Long>> insertTrees(List<TreeData> tree);
//
//    @Delete
//    int deleteTreeByObject(TreeData tree);
//
//    @Update
//    Single<Integer> updateTreeByObject(TreeData tree);
//
//    @Query("DELETE FROM TreeData WHERE id = :id")
//    int deleteTreeById(long id);
}
