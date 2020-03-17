package com.saarit.temple_management.templemanagement.model.DTOs.not_in_use;

import com.google.gson.annotations.SerializedName;
import com.saarit.temple_management.templemanagement.model.BaseResponse;
import com.saarit.temple_management.templemanagement.model.FormType_1;

import java.util.List;

public class Forms_1_DTO extends BaseResponse {

    @SerializedName("temple_details")
    private List<FormType_1> forms_1;

    public List<FormType_1> getForms_1() {
        return forms_1;
    }
}
