package com.saarit.temple_management.templemanagement.model.DTOs;

import com.google.gson.annotations.SerializedName;
import com.saarit.temple_management.templemanagement.model.BaseResponse;
import com.saarit.temple_management.templemanagement.model.DonatedProduct;
import com.saarit.temple_management.templemanagement.model.FormType_4;
import com.saarit.temple_management.templemanagement.model.FormType_5;

import java.util.ArrayList;

public class FormType_5_DTO extends BaseResponse {

    @SerializedName("form_5_details")
    private FormType_5 formType_5;

    public FormType_5 getFormType_5() {
        return formType_5;
    }

}
