package com.saarit.temple_management.templemanagement.view.ui;

import android.os.Bundle;


import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.util.Utility;

import androidx.appcompat.app.AppCompatActivity;

public class Form4Activity extends AppCompatActivity {

    private String TAG = Form4Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.log(TAG,"onCreate()");
        setContentView(R.layout.activity_form4);
    }
}
