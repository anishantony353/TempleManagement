package com.saarit.temple_management.templemanagement.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FormType_1_DTO extends BaseResponse {

    @SerializedName("form_1_details")
    private FormType_1 formType_1;

    public FormType_1 getFormType_1() {
        return formType_1;
    }
}
