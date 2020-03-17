package com.saarit.temple_management.templemanagement.model;

import com.saarit.temple_management.templemanagement.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WorshipingHouse extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    public int worshipingHouseId;

    public int temple_id;

    public String worshiping_house_name;

    public String worshiping_house_role;


    public void setworshiping_house_name(String name){
        worshiping_house_name = name;
        notifyPropertyChanged(BR.worshiping_house_name);

    }

    @Bindable
    public String getworshiping_house_name(){
        return worshiping_house_name;
    }

    public void setworshiping_house_role(String role){
        worshiping_house_role = role;
        notifyPropertyChanged(BR.worshiping_house_role);

    }

    @Bindable
    public String getworshiping_house_role(){
        return worshiping_house_role;
    }


}
