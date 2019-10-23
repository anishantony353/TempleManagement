package com.saarit.temple_management.templemanagement.view.ui;


import android.os.Bundle;


import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.databinding.ActivityTabBinding;
import com.saarit.temple_management.templemanagement.view.adapters.TabAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class TabActivity extends AppCompatActivity {

    ActivityTabBinding binding;
    TabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setBinding();

    }

    private void setBinding() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tab);

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new SubmittedListFragment(), "Submitted");
        adapter.addFragment(new SavedListFragment(), "Saved");
        adapter.addFragment(new NewListFragment(), "New");

        binding.setAdapter(adapter);

    }
}
