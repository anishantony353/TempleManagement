package com.saarit.temple_management.templemanagement.model.DTOs;

import com.google.gson.annotations.SerializedName;
import com.saarit.temple_management.templemanagement.model.BaseResponse;
import com.saarit.temple_management.templemanagement.model.FormType_1;

import java.util.List;

public class FormType_1_DTO extends BaseResponse {

    @SerializedName("form_1_details")
    private FormType_1 formType_1;

    public FormType_1 getFormType_1() {
        return formType_1;
    }
}
