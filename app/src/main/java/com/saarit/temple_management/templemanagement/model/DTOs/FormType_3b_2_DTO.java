package com.saarit.temple_management.templemanagement.model.DTOs;

import com.google.gson.annotations.SerializedName;
import com.saarit.temple_management.templemanagement.model.BaseResponse;
import com.saarit.temple_management.templemanagement.model.FormType_3b_1;
import com.saarit.temple_management.templemanagement.model.FormType_3b_2;

public class FormType_3b_2_DTO extends BaseResponse {

    @SerializedName("form_3b2_details")
    private FormType_3b_2 formType_3b_2;


    public FormType_3b_2 getFormType_3b_2() {
        return formType_3b_2;
    }

}
