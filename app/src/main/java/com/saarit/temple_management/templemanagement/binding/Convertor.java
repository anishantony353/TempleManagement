package com.saarit.temple_management.templemanagement.binding;

import androidx.databinding.InverseMethod;

public class Convertor {


    @InverseMethod("stringToDouble")
    public static String doubleToString(double value){

        return Double.toString(value);

    }

    public static double stringToDouble(String value){
        if(value.trim().equals("")){
            return 0.0;
        }else{
            return Double.parseDouble(value);
        }

    }

    @InverseMethod("stringToInt")
    public static String intToString(int value){

        return Integer.toString(value);

    }

    public static int stringToInt(String value){
        return Integer.parseInt(value);
    }
}
