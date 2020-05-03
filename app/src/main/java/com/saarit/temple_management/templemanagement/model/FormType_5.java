package com.saarit.temple_management.templemanagement.model;

import com.google.gson.annotations.SerializedName;
import com.saarit.temple_management.templemanagement.BR;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FormType_5 extends BaseObservable {
    /*public String TAG = FormType_5.class.getSimpleName();*/

    @PrimaryKey(autoGenerate = true)
    public int id;

    @SerializedName("temple_id")
    public int  temple_id;

    public String temple_name;
    public String village_name;
    public String taluka_name;
    public String district_name;

    @SerializedName("name_of_devasthan")
    public String devsthan_name;

    @SerializedName("notification_no")
    public String notification_number;

    @SerializedName("ptr_no")
    public String ptr_number;

    @SerializedName("gath_no")
    public String gath_number;

    @SerializedName("sn")
    public String sn;

    @SerializedName("csn")
    public String csn;

    @SerializedName("total_area")
    public String total_area;

    @SerializedName("shape")
    public String shape;

    @SerializedName("name_of_land_tenants")
    public String land_tenants_name;

    @SerializedName("address")
    public String address;

    @SerializedName("measurement_of_sanctioned_land")
    public String sanctioned_land_measurements;

    @SerializedName("area_of_sactioned_land")
    public String sanctioned_land_area;

    @SerializedName("is_occupant_done_encroachment")
    public boolean has_occupant_encroached_plot;

    @SerializedName("area_of_encroachment")
    public String encroachment_area;

    @SerializedName("shape_of_open_space")
    public String shape_open_space;

    @SerializedName("shape_of_temple")
    public String shape_temple;

    @SerializedName("shape_of_dharmshala")
    public String shape_dharmashala;

    @SerializedName("purpose_of_given_land")
    public String purpose_given_land;

    @SerializedName("purpose_of_use_given")
    public String purpose_use_given_land;

    @SerializedName("sub_tenant_approved")
    public boolean is_sub_tenant_in_approved_place;

    @SerializedName("area_of_unused_plot")
    public String area_unused_plot;

    @SerializedName("purpose_of_use_unused")
    public String purpose_use_unused_plot;

    @SerializedName("utilisation_of_land")
    public String land_utilisation;

    @SerializedName("latitude")
    public String latitude;

    @SerializedName("longitude")
    public String longitude;

    @SerializedName("remarks")
    public String remarks;

    @SerializedName("last_updated_by")
    public int user_id;

    @Bindable
    public boolean isHas_occupant_encroached_plot() {
        return has_occupant_encroached_plot;
    }

    public void setHas_occupant_encroached_plot(boolean has_occupant_encroached_plot) {
        this.has_occupant_encroached_plot = has_occupant_encroached_plot;
        notifyPropertyChanged(BR.has_occupant_encroached_plot);
    }

    @Bindable
    public String getPurpose_use_given_land() {
        return purpose_use_given_land;
    }

    public void setPurpose_use_given_land(String purpose_use_given_land) {
        this.purpose_use_given_land = purpose_use_given_land;
        notifyPropertyChanged(BR.purpose_use_given_land);
    }

    @Bindable
    public String getPurpose_use_unused_plot() {
        return purpose_use_unused_plot;
    }

    public void setPurpose_use_unused_plot(String purpose_use_unused_plot) {
        this.purpose_use_unused_plot = purpose_use_unused_plot;
        notifyPropertyChanged(BR.purpose_use_unused_plot);
    }

    @Bindable
    public String getLand_utilisation() {
        return land_utilisation;
    }

    public void setLand_utilisation(String land_utilisation) {
        this.land_utilisation = land_utilisation;
        notifyPropertyChanged(BR.land_utilisation);
    }

    @Bindable
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
        notifyPropertyChanged(BR.latitude);
    }

    @Bindable
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
        notifyPropertyChanged(BR.longitude);
    }

    @NonNull
    @Override
    public String toString() {
        /*return "Lat:"+latitude+" Lon:"+longitude;*/
        return super.toString();
    }
}
