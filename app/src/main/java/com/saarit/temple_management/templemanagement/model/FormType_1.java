package com.saarit.temple_management.templemanagement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saarit.temple_management.templemanagement.BR;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class FormType_1 extends BaseObservable implements Serializable {

    //private String TAG = FormType_1.class.getSimpleName();

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name ="id")
    @SerializedName("id")
    public int id;


    @ColumnInfo(name ="temple_id")
    @SerializedName("temple_id")
    public int templeId;

    @ColumnInfo(name ="temple_name")
    @SerializedName("temple_name")
    public String temple;


    @ColumnInfo(name ="village_name")
    @SerializedName("village_name")
    public String village;


    @ColumnInfo(name ="taluka_name")
    @SerializedName("taluka_name")
    public String taluka;


    @ColumnInfo(name ="district_name")
    @SerializedName("district_name")
    public String district;

    @ColumnInfo(name ="god_name")
    public String god_name;

    @ColumnInfo(name ="priest_name")
    public String priest_name;

    @ColumnInfo(name ="registration_no")
    public String registration_no;

    @ColumnInfo(name ="notification_no")
    public String notification_no;

    @ColumnInfo(name ="land_gat_no")
    @SerializedName("land_gath_no")
    public String land_gat_no;

    @ColumnInfo(name ="taxation_of_land")
    public String taxation_of_land;

    @ColumnInfo(name ="occupant_land")
    public String occupant_land;

    @ColumnInfo(name ="latitude")
    public double latitude;

    @ColumnInfo(name ="longitude")
    public double longitude;

    @ColumnInfo(name ="gold_rupees")
    @SerializedName("gold")
    public double gold_rupees;

    @ColumnInfo(name ="gold_evaluation")
    @SerializedName("gold_evaluation_price")
    public double gold_evaluation;

    @ColumnInfo(name ="silver_rupees")
    @SerializedName("silver")
    public double silver_rupees;

    @ColumnInfo(name ="silver_evaluation")
    @SerializedName("silver_evaluation_price")
    public double silver_evaluation;

    @SerializedName("last_updated_by")
    public int user_id = 1;




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
