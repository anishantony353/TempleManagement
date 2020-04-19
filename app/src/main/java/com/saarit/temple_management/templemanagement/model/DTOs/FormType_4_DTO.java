package com.saarit.temple_management.templemanagement.model.DTOs;

import com.google.gson.annotations.SerializedName;
import com.saarit.temple_management.templemanagement.model.BaseResponse;
import com.saarit.temple_management.templemanagement.model.DonatedProduct;
import com.saarit.temple_management.templemanagement.model.Festival;
import com.saarit.temple_management.templemanagement.model.FormType_4;
import com.saarit.temple_management.templemanagement.model.PoojariWork;
import com.saarit.temple_management.templemanagement.model.RespectedPerson;
import com.saarit.temple_management.templemanagement.model.WorshipingHouse;
import com.saarit.temple_management.templemanagement.model.WorshipingType;

import java.util.ArrayList;

public class FormType_4_DTO extends BaseResponse {

    @SerializedName("form_4_details")
    private FormType_4 formType_4;

    @SerializedName("form_4_details_products")
    private ArrayList<DonatedProduct> products;

    public FormType_4 getFormType_4() {
        return formType_4;
    }

    public ArrayList<DonatedProduct> getProducts() {
        return products;
    }
    
}
