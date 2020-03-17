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
public class FormType_3a extends BaseObservable implements Serializable {

    //private String TAG = FormType_1.class.getSimpleName();

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int temple_id;

    public String temple_name;

    public String village_name;

    public String taluka_name;

    public String district_name;

    public String latitude;

    public String longitude;

    public String gram_panchayat;

    public String nagar_palika_milkat_no;

    public String csn_of_temple;

    public String measurement_of_temple;

    public String temple_construction_details;

    public String open_space_around;

    public String temple_composition;

    public String statue_age;

    public String statue_type;

    public boolean is_compound_present;

    public boolean is_dharmshala_present_near_temple;

    public String dharmshala_details;

    public String dharmshala_area;

    public boolean is_open_plot_available_under_dharmshala;

    public String area_open_plot;

    public boolean is_office_available_under_temple;

    public String office_area;

    public String office_no_rooms;

    public String office_no_seats;

    public String office_caretaker_details;

    public boolean is_school_available_under_temple;

    public String school_area;

    public String school_no_rooms;

    public String school_no_seats;

    public String school_caretaker_details;

    public boolean is_organisation_available_under_temple;

    public String organisation_area;

    public String organisation_no_rooms;

    public String organisation_no_seats;

    public String organisation_caretaker_details;

    public boolean is_property_rent_given;

    public String rent_deposited_to;

    public String rent_per_month;

    public boolean is_electricity_meter_available;

    public String electricity_owner_details;

    public String electricity_payable_by;

    public boolean is_enrochment_premises;

    public boolean is_enrochment_authorized;

    public String enrochment_shop_house_other;

    public String enrochment_details_person_area;

    public boolean is_sub_committee_present;

    public String sub_committee_name;

    public String sub_committee_order_no;

    public String sub_committee_order_no_date;

    public String sub_committee_president;

    public String sub_committee_members_details;

    public boolean is_audit_details_present;

    public String audit_details;

    public boolean is_renovation_going_on;

    public String renovation_permission;

    public boolean is_renovation_necessary;

    public String remarks;

    @SerializedName("last_updated_by")
    public int user_id = 1;


    ///methods

    @Bindable
    public String getSub_committee_order_no_date() {
        return sub_committee_order_no_date;
    }

    public void setSub_committee_order_no_date(String sub_committee_order_no_date) {
        this.sub_committee_order_no_date = sub_committee_order_no_date;
        notifyPropertyChanged(BR.sub_committee_order_no_date);
    }

    @NonNull
    @Override
    public String toString() {
        String str = "Temple Id:"+temple_id;
        return str;
    }
}
