package com.saarit.temple_management.templemanagement.view.ui;

import android.os.Bundle;


import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.util.Utility;

import androidx.appcompat.app.AppCompatActivity;

public class Form5bActivity extends AppCompatActivity {

    private String TAG = Form5bActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.log(TAG,"onCreate()");
        setContentView(R.layout.activity_form5b);
    }
}