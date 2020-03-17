package com.saarit.temple_management.templemanagement.model;

import com.google.gson.annotations.SerializedName;
import com.saarit.temple_management.templemanagement.BR;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class FormType_1 extends BaseObservable implements Serializable {

    //private String TAG = FormType_1.class.getSimpleName();

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name ="temple_id")
    @SerializedName("temple_id")
    public int templeId;

    @SerializedName("temple_name")
    public String temple;

    @SerializedName("village_name")
    public String village;

    @SerializedName("taluka_name")
    public String taluka;

    @SerializedName("district_name")
    public String district;

    public String god_name;

    public String priest_name;

    public String registration_no;

    public String notification_no;

    @SerializedName("land_gath_no")
    public String land_gat_no;

    public String taxation_of_land;

    public String occupant_land;

    public double latitude;

    public double longitude;

    @SerializedName("gold")
    public double gold_rupees;

    @SerializedName("gold_evaluation_price")
    public double gold_evaluation;

    @SerializedName("silver")
    public double silver_rupees;

    @SerializedName("silver_evaluation_price")
    public double silver_evaluation;

    @SerializedName("last_updated_by")
    public int user_id = 1;

    public String contact_name;

    public String contact_number;


    //public String village,taluka;

    //public double latitude,longitude;



//    @Bindable
//    public String getVillage() {
//        return village;
//    }
//
//    public void setVillage(String village) {
//        this.village = village;
//        notifyPropertyChanged(BR.village);
//    }
//
//    @Bindable
//    public String getTaluka() {
//        return taluka;
//    }
//
//    public void setTaluka(String taluka) {
//        this.taluka = taluka;
//        notifyPropertyChanged(BR.taluka);
//    }
//
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

    @Bindable
    public String getTemple() {
        return temple;
    }

    public void setTemple(String temple) {
        this.temple = temple;
        notifyPropertyChanged(BR.temple);
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
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
        notifyPropertyChanged(BR.district);
    }


    @NonNull
    @Override
    public String toString() {
        return temple;
    }
}
