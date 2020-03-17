package com.saarit.temple_management.templemanagement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saarit.temple_management.templemanagement.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Festival extends BaseObservable {

    @PrimaryKey(autoGenerate = true)

    @Expose(serialize = false)
    public int festivalId;

    public int temple_id;

    public String festival_name;
    public String festival_duration;
    public String festival_month;




    public void setfestival_name(String name){
        festival_name = name;
        notifyPropertyChanged(BR.festival_name);

    }

    @Bindable
    public String getfestival_name(){
        return festival_name;
    }

    public void setfestival_duration(String duration){
        festival_duration = duration;
        notifyPropertyChanged(BR.festival_duration);

    }

    @Bindable
    public String getfestival_duration(){
        return festival_duration;
    }

    public void setfestival_month(String month){
        festival_month = month;
        notifyPropertyChanged(BR.festival_month);

    }

    @Bindable
    public String getfestival_month(){
        return festival_month;
    }

}
