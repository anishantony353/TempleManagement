package com.saarit.temple_management.templemanagement.model.DTOs;

import com.google.gson.annotations.SerializedName;
import com.saarit.temple_management.templemanagement.model.BaseResponse;
import com.saarit.temple_management.templemanagement.model.FormType_3b_1;

public class FormType_3b_1_DTO extends BaseResponse {

    @SerializedName("form_3b1_details")
    private FormType_3b_1 formType_3b_1;


    public FormType_3b_1 getFormType_3b_1() {
        return formType_3b_1;
    }

}
