package com.saarit.temple_management.templemanagement.model.DTOs;

import com.google.gson.annotations.SerializedName;
import com.saarit.temple_management.templemanagement.model.BaseResponse;
import com.saarit.temple_management.templemanagement.model.FormType_1;

import java.util.List;

public class FormType_1_List_DTO extends BaseResponse {

    @SerializedName("temple_info")
    private List<FormType_1> formType_1_List;

    public List<FormType_1> getFormType_1_List() {
        return formType_1_List;
    }
}
