package com.saarit.temple_management.templemanagement.model;

import com.saarit.temple_management.templemanagement.BR;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WorshipingType extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    public int worshipingTypeId;

    public int temple_id;

    public String worshiping_type_preaching;
    public String worshiping_type_steps;
    public String worshiping_type_ritual;

    @Bindable
    public String getWorshipingTypePreaching() {
        return worshiping_type_preaching;
    }

    public void setWorshipingTypePreaching(String worshipingTypePreaching) {
        this.worshiping_type_preaching = worshipingTypePreaching;
        notifyPropertyChanged(BR.worshipingTypePreaching);
    }
    @Bindable
    public String getWorshipingTypeSteps() {
        return worshiping_type_steps;
    }

    public void setWorshipingTypeSteps(String worshipingTypeSteps) {
        this.worshiping_type_steps = worshipingTypeSteps;
        notifyPropertyChanged(BR.worshipingTypeSteps);
    }

    @Bindable
    public String getWorshipingTypeRitual() {
        return worshiping_type_ritual;
    }

    public void setWorshipingTypeRitual(String worshipingTypeRitual) {
        this.worshiping_type_ritual = worshipingTypeRitual;
        notifyPropertyChanged(BR.worshipingTypeRitual);
    }
}
