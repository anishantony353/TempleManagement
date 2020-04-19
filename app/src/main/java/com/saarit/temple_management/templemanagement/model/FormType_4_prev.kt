package com.saarit.temple_management.templemanagement.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import java.io.Serializable

class FormType_4_prev:BaseObservable(),Serializable{

    var temple_id = 0
    var temple_name = "tmn"
    var village_name = "vvv"
    var taluka_name = "ttt"
    var district_name = "ddd"
    var god_name = "ggnn"
    var registration_no = "rrnn"
    var is_gold_valuation_done = true
    var is_silver_valuation_done:Boolean = false
    var assessment_amount = ""
    var is_donation_box_available:Boolean = false
    var donation_box_utilisation = ""


    fun getValue(){

    }

    override fun toString(): String {
        return "temple id:$temple_id \n temple name:$temple_name \n Is gold valuation done:$is_donation_box_available \n" +
                "Is silver valuation done:$is_silver_valuation_done \n assesmnt amt:$assessment_amount \n " +
                "Is donation box available:$is_donation_box_available \n donation box utilisation:$donation_box_utilisation \n"+
                "Regis No:$registration_no"
    }


}