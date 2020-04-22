package com.saarit.temple_management.templemanagement.model;

import com.google.gson.annotations.SerializedName;
import com.saarit.temple_management.templemanagement.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FormType_5 extends BaseObservable {
    public String TAG = FormType_5.class.getSimpleName();

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int  temple_id = 0;
    public String temple_name = "tmn";
    public String village_name = "vvv";
    public String taluka_name = "ttt";
    public String district_name = "ddd";
    public String devsthan_name = "";
    public String notification_number = "";
    public String ptr_number = "";
    public String gath_number = "";
    public String sn = "";
    public String csn = "";
    public String total_area = "";
    public String shape = "";
    public String land_tenants_name = "";
    public String address = "";
    public String sanctioned_land_measurements = "";
    public String sanctioned_land_area = "";
    public boolean has_occupant_encroached_plot  = false;
    public String encroachment_area = "";
    public String shape_open_space = "";
    public String shape_temple = "";
    public String shape_dharmashala = "";
    public String purpose_given_land = "";
    public String purpose_use_given_land = "";
    public boolean is_sub_tenant_in_approved_place  = false;
    public String area_unused_plot = "";
    public String purpose_use_unused_plot = "";
    public String land_utilisation  = "";
    public String latitude = "";
    public String longitude = "";
    public String remarks = "";

    @SerializedName("last_updated_by")
    public int user_id = 0;

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
}
