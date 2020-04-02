package com.saarit.temple_management.templemanagement.model;

import com.google.gson.annotations.SerializedName;
import com.saarit.temple_management.templemanagement.BR;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FormType_3b_1 extends BaseObservable implements Serializable {

    //private String TAG = FormType_1.class.getSimpleName();

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int temple_id;

    public String temple_name;

    public String god_name;

    public String village_name;

    public String taluka_name;

    public String district_name;

    public String latitude;

    public String longitude;

    public String group_no;

    public String notification_no;

    public String register_no;

    public String area_hectare;

    public boolean is_any_land_cultivation;

    public String area_agriculture_land;

    public String area_non_agriculture_land;

    public String total_area_cultivation;

    public String shape;

    public String annual_budget;

    public String controller_name;

    public String controller_responsibility;

    public String controller_work;

    public String land_encroachment_on_construction;

    public boolean is_land_surveyed;

    public String survey_date;

    public boolean is_border_fixed;

    public String whether_map_received;

    public String land_document_a;

    public String land_document_b;

    public String planning_water_availability;

    public String options_land_productive;

    public String utilisation_land_business;

    public boolean is_land_used_cultivation;

    public String land_cultivation_procedure;

    public String remarks;

    @SerializedName("last_updated_by")
    public int user_id = 1;

    //Methods
    @Bindable
    public String getSurvey_date() {
        return survey_date;
    }

    public void setSurvey_date(String survey_date) {
        this.survey_date = survey_date;
        notifyPropertyChanged(BR.survey_date);
    }

    @Bindable
    public String getLand_document_a() {
        return land_document_a;
    }

    public void setLand_document_a(String land_document) {
        this.land_document_a = land_document;
        notifyPropertyChanged(BR.land_document_a);
    }

    @Bindable
    public String getLand_document_b() {
        return land_document_b;
    }

    public void setLand_document_b(String land_document) {
        this.land_document_b = land_document;
        notifyPropertyChanged(BR.land_document_b);
    }

    @NonNull
    @Override
    public String toString() {
        String str = "Temple Id:"+temple_id;
        return str;
    }
}
