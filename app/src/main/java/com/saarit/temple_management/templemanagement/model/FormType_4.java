package com.saarit.temple_management.templemanagement.model;

import com.google.gson.annotations.SerializedName;
import com.saarit.temple_management.templemanagement.BR;
import com.saarit.temple_management.templemanagement.util.Utility;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FormType_4 extends BaseObservable {
    public String TAG = FormType_4.class.getSimpleName();

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int  temple_id = 0;
    public String temple_name = "tmn";
    public String village_name = "vvv";
    public String taluka_name = "ttt";
    public String district_name = "ddd";
    public String god_name = "ggnn";
    public String registration_no = "rrnn";
    public boolean is_gold_valuation_done = true;
    public boolean is_silver_valuation_done = false;
    public String assessment_amount = "";
    public boolean is_donation_box_available = true;
    public String donation_box_utilisation = "";

    @SerializedName("last_updated_by")
    public int user_id = 0;

    @Bindable
    public boolean isIs_donation_box_available() {
        Utility.log(TAG,"getIsDonationBoxAvailable()");
        return is_donation_box_available;
    }

    public void setIs_donation_box_available(boolean is_donation_box_available) {
        Utility.log(TAG,"setIsDonationBoxAvailable()...value:"+is_donation_box_available);
        this.is_donation_box_available = is_donation_box_available;
        notifyPropertyChanged(BR.is_donation_box_available);
    }

    @NonNull
    @Override
    public String toString() {
        return "Regis No:"+registration_no+"\n"+"Is Donation :"+is_donation_box_available+"\n"+"Utilisation:"+donation_box_utilisation;
    }
}
