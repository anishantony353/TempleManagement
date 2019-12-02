package com.saarit.temple_management.templemanagement.model.repositories;

import com.saarit.temple_management.templemanagement.model.Temple_master;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface Temples_master_Dao {



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<List<Long>> insertTemples(List<Temple_master> temples);

    @Query("SELECT count(temple_id) FROM Temple_master")
    Single<Long> getCount();

    @Query("SELECT * FROM Temple_master")
    Single<List<Temple_master>> getTemples();


    @Query("SELECT * FROM Temple_master WHERE temple_id = :id")
    Single<Temple_master> getTempleByTempleId(long id);












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
