package com.saarit.temple_management.templemanagement.view.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import com.saarit.temple_management.templemanagement.view.listeners.DateSetListner;

import java.util.Calendar;

import androidx.fragment.app.DialogFragment;


public class FragDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    String TAG = FragDatePicker.class.getSimpleName();
    DateSetListner onDateSetListner;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
        //return new DatePickerDialog(new ContextThemeWrapper(this, R.style.DialogSlideAnim), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        Log.i(TAG,"onDateSet()");
        onDateSetListner.onDateSet(year,month+1,day);

    }

    public void setOnDateSetListner(DateSetListner onDateSetListner){
        this.onDateSetListner = onDateSetListner;
    }
}
