package com.saarit.temple_management.templemanagement.model.repositories;

import android.content.Context;

import com.saarit.temple_management.templemanagement.model.FormType_1;
import com.saarit.temple_management.templemanagement.model.Temple_master;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Temple_master.class,FormType_1.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract Temples_master_Dao temples_master_dao();
    public abstract FormType_1_Dao formType_1_dao();

    public static AppDatabase getDataBase(Context context){

        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context,AppDatabase.class,"temple_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }
}
