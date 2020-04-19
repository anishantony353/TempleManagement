package com.saarit.temple_management.templemanagement.model;

import com.google.gson.annotations.Expose;
import com.saarit.temple_management.templemanagement.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DonatedProduct extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    public int productId;

    public int temple_id;

    public String product_type;
    public String product_details;
    public String s_r_no;
    public String received_from;
    public String weight_approx;
    public String value_of_asset;
    public boolean is_valuation_done;
    public String valuation_details;
    public String present_authority;
    public String authority_post;
    public String remarks;


}
