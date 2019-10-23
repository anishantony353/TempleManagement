package com.saarit.temple_management.templemanagement.binding;

import androidx.databinding.InverseMethod;

public class Convertor {


    @InverseMethod("stringToDouble")
    public static String doubleToString(double value){

        return Double.toString(value);

    }

    public static double stringToDouble(String value){
        return Double.parseDouble(value);
    }
}
