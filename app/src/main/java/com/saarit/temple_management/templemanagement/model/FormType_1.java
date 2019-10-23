package com.saarit.temple_management.templemanagement.model;

import com.saarit.temple_management.templemanagement.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class FormType_1 extends BaseObservable {

    private String TAG = FormType_1.class.getSimpleName();

//    public String village,taluka,district,god_name,priest_name,registration_no,notification_no,land_ghat_no,taxation_of_land,
//    occupant_land;
//
//    public double latitude,longitude,gold_rupees,silver_rupees;

    public String village,taluka;

    public double latitude,longitude;

    public FormType_1(String village, String taluka, double latitide, double longitude) {
        this.village = village;
        this.taluka = taluka;
        this.latitude = latitide;
        this.longitude = longitude;
    }

    @Bindable
    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
        notifyPropertyChanged(BR.village);
    }

    @Bindable
    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
        notifyPropertyChanged(BR.taluka);
    }

    @Bindable
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
        notifyPropertyChanged(BR.latitude);
    }

    @Bindable
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
        notifyPropertyChanged(BR.longitude);
    }
}
