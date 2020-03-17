package com.saarit.temple_management.templemanagement.model;

import com.google.gson.annotations.SerializedName;
import com.saarit.temple_management.templemanagement.BR;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FormType_2 extends BaseObservable implements Serializable {

    //private String TAG = FormType_1.class.getSimpleName();

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int temple_id;

    public String temple_name;

    public String village_name;

    public String taluka_name;

    public String district_name;

    public String survey_report_notification_no;

    public String registration_no;

    public String origin_establishment_history;

    public String custom_tradition_festival;

    public String village_population;

    public String poojari_name;

    public String poojari_address;

    public String poojari_contact_no;

    public double poojari_income;

    public boolean is_worship_different_houses;

    public boolean is_officer_available;

    public boolean is_respected_person_available;

    public boolean is_worshiping_regular_basis;

    public String worshiping_procedure;

    public boolean types_of_worshiping;

    public boolean is_both_gender_allowed_to_worship;

    public boolean is_both_gender_allowed_in_premises;

    public boolean is_other_servicemen_available;

    public String servicemen_work;

    public boolean is_land_allocated_to_them;

    public boolean is_allocated_land_utilised;

    @SerializedName("last_updated_by")
    public int user_id = 1;


    @NonNull
    @Override
    public String toString() {
        String str = "Temple Id:"+temple_id+"\n"+"Survey Report No:"+survey_report_notification_no
                +"\n"+"is_worship_different_houses:"+is_worship_different_houses;
        return str;
    }
}
