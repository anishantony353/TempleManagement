package com.saarit.temple_management.templemanagement.model.DTOs;

import com.google.gson.annotations.SerializedName;
import com.saarit.temple_management.templemanagement.model.BaseResponse;
import com.saarit.temple_management.templemanagement.model.Temple_master;

import java.util.List;

public class Temples_master_DTO extends BaseResponse {

    @SerializedName("temple_details")
    private List<Temple_master> temple_masters;

    public List<Temple_master> getTemple_masters() {
        return temple_masters;
    }
}
