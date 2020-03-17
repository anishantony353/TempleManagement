package com.saarit.temple_management.templemanagement.model.DTOs;

import com.google.gson.annotations.SerializedName;
import com.saarit.temple_management.templemanagement.model.BaseResponse;
import com.saarit.temple_management.templemanagement.model.Festival;
import com.saarit.temple_management.templemanagement.model.FormType_1;
import com.saarit.temple_management.templemanagement.model.FormType_2;
import com.saarit.temple_management.templemanagement.model.PoojariWork;
import com.saarit.temple_management.templemanagement.model.RespectedPerson;
import com.saarit.temple_management.templemanagement.model.WorshipingHouse;
import com.saarit.temple_management.templemanagement.model.WorshipingType;

import java.util.ArrayList;

public class FormType_2_DTO extends BaseResponse {

    @SerializedName("form_2_details")
    private FormType_2 formType_2;

    @SerializedName("form_2_details_festivals")
    private ArrayList<Festival> festivals;

    @SerializedName("form_2_details_poojari_works")
    private ArrayList<PoojariWork> poojariWorks;

    @SerializedName("form_2_details_respected_persons")
    private ArrayList<RespectedPerson> respectedPersons;

    @SerializedName("form_2_details_worshiping_types")
    private ArrayList<WorshipingType> worshipingTypes;

    @SerializedName("form_2_details_worshiping_houses")
    private ArrayList<WorshipingHouse> worshipingHouses;



    public FormType_2 getFormType_2() {
        return formType_2;
    }

    public ArrayList<Festival> getFestivals() {
        return festivals;
    }

    public ArrayList<PoojariWork> getPoojariWorks() {
        return poojariWorks;
    }

    public ArrayList<RespectedPerson> getRespectedPersons() {
        return respectedPersons;
    }

    public ArrayList<WorshipingType> getWorshipingTypes() {
        return worshipingTypes;
    }

    public ArrayList<WorshipingHouse> getWorshipingHouses() {
        return worshipingHouses;
    }
}
