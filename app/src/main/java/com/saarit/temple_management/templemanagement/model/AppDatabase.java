package com.saarit.temple_management.templemanagement.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FormType_1.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract FormType_1_Dao formType_1_dao();

    public static AppDatabase getDataBase(Context context){

        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context,AppDatabase.class,"temple_db").build();
        }

        return INSTANCE;
    }
}
