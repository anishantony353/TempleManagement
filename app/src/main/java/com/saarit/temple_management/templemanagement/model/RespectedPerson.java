package com.saarit.temple_management.templemanagement.model;

import com.saarit.temple_management.templemanagement.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RespectedPerson extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    public int respectedPersonId;

    public int temple_id;

    public String respected_person_name;
    public String respected_person_role;

    @Bindable
    public String getRespectedPersonName() {
        return respected_person_name;
    }

    public void setRespectedPersonName(String respectedPersonName) {
        this.respected_person_name = respectedPersonName;
        notifyPropertyChanged(BR.respectedPersonName);
    }

    @Bindable
    public String getRespectedPersonRole() {
        return respected_person_role;
    }

    public void setRespectedPersonRole(String respectedPersonRole) {
        this.respected_person_role = respectedPersonRole;
        notifyPropertyChanged(BR.respectedPersonRole);
    }
}
