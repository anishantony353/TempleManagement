package com.saarit.temple_management.templemanagement.binding;


import android.view.View;
import android.widget.EditText;

import com.google.android.material.tabs.TabLayout;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view.adapters.FormListAdapter;
import com.saarit.temple_management.templemanagement.view.adapters.NewListAdapter;
import com.saarit.temple_management.templemanagement.view.adapters.SavedListAdapter;
import com.saarit.temple_management.templemanagement.view.adapters.SubmittedListAdapter;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class BindingAdapters {

    private static String TAG = BindingAdapters.class.getSimpleName();

    @BindingAdapter("invalid")
    public static void setError(EditText editText, String errorMsg) {
        Utility.log(TAG,"setError()");
        editText.requestFocus();

        editText.setError(errorMsg);

    }

    @BindingAdapter("set_tablayout_viewpager")
    public static void setViewPager(TabLayout tabLayout, ViewPager viewPager) {
        Utility.log(TAG,"setViewPager()");

        viewPager.setOffscreenPageLimit(2);

        tabLayout.setupWithViewPager(viewPager);

    }



    @BindingAdapter("adapter_submitted_list")
    public static void setRecyclerViewSubmittedAdapter(RecyclerView rv, SubmittedListAdapter adapter){

        Utility.log(TAG,"setRecyclerViewSubmittedAdapter()");
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(adapter);
    }

    @BindingAdapter("adapter_saved_list")
    public static void setRecyclerViewSavedAdapter(RecyclerView rv,SavedListAdapter adapter){

        Utility.log(TAG,"setRecyclerViewSavedAdapter()");
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(adapter);
    }

    @BindingAdapter("adapter_new_list")
    public static void setRecyclerViewNewAdapter(RecyclerView rv,NewListAdapter adapter){

        Utility.log(TAG,"setRecyclerViewNewAdapter()");
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(adapter);
    }

    @BindingAdapter("adapter_forms")
    public static void setRecyclerViewFormsAdapter(RecyclerView rv,FormListAdapter adapter){

        Utility.log(TAG,"setRecyclerViewFormsAdapter()");
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(adapter);
    }

    @BindingAdapter("visibility")
    public static void setVisibility(View view, int reqType) {
        Utility.log(TAG,"setVisibility()..REQ Type:"+reqType);


        view.setVisibility(View.VISIBLE);


    }
}
