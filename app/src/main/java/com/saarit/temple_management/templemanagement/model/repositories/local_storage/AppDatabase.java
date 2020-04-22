package com.saarit.temple_management.templemanagement.model.repositories.local_storage;

import android.content.Context;

import com.saarit.temple_management.templemanagement.model.DonatedProduct;
import com.saarit.temple_management.templemanagement.model.Festival;
import com.saarit.temple_management.templemanagement.model.FormType_1;
import com.saarit.temple_management.templemanagement.model.FormType_2;
import com.saarit.temple_management.templemanagement.model.FormType_3a;
import com.saarit.temple_management.templemanagement.model.FormType_3b_1;
import com.saarit.temple_management.templemanagement.model.FormType_3b_2;
import com.saarit.temple_management.templemanagement.model.FormType_4;
import com.saarit.temple_management.templemanagement.model.FormType_5;
import com.saarit.temple_management.templemanagement.model.PoojariWork;
import com.saarit.temple_management.templemanagement.model.RespectedPerson;
import com.saarit.temple_management.templemanagement.model.Temple_master;
import com.saarit.temple_management.templemanagement.model.WorshipingHouse;
import com.saarit.temple_management.templemanagement.model.WorshipingType;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {
            Temple_master.class,FormType_1.class,FormType_2.class,Festival.class,WorshipingHouse.class,
                    PoojariWork.class,RespectedPerson.class,WorshipingType.class,FormType_3a.class,
                FormType_3b_1.class,FormType_3b_2.class, FormType_4.class, DonatedProduct.class, FormType_5.class
        },
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract Temples_master_Dao temples_master_dao();
    public abstract FormType_1_Dao formType_1_dao();
    public abstract FormType_2_Dao formType_2_dao();
    public abstract FormType_3a_Dao formType_3a_dao();
    public abstract FormType_3b_1_Dao formType_3b_1_dao();
    public abstract FormType_3b_2_Dao formType_3b_2_dao();
    public abstract FormType_4_Dao formType_4_dao();
    public abstract FormType_5_Dao formType_5_dao();


    public static AppDatabase getDataBase(Context context){

        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context,AppDatabase.class,"temple_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }
}
