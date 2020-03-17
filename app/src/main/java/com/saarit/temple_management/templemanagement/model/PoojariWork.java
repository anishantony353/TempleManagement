package com.saarit.temple_management.templemanagement.model;



import com.saarit.temple_management.templemanagement.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity
public class PoojariWork extends BaseObservable{

    @PrimaryKey(autoGenerate = true)
    public int poojariWorkId;

    public int temple_id;

    public String poojari_work;


   @Bindable
    public String getPoojariWork() {
        return poojari_work;
    }

    public void setPoojariWork(String poojari_work) {
        this.poojari_work = poojari_work;
        notifyPropertyChanged(BR.poojariWork);
        //notifyPropertyChanged(BR.poojariWork);

    }

}
