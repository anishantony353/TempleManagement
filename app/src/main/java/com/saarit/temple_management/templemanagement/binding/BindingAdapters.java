package com.saarit.temple_management.templemanagement.binding;


import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.material.tabs.TabLayout;
import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.util.Constant;
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

    private static RequestOptions options = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .priority(Priority.HIGH)
            .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())));

//    .placeholder(R.drawable.check_box_selected)
//            .error(R.drawable.check_box_unselected)

    @BindingAdapter("invalid")
    public static void setError(EditText editText, String errorMsg) {
        Utility.log(TAG,"setError()");

            if(errorMsg == null || errorMsg.equals(Constant.VALID)){
                return;
            }
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

    @BindingAdapter("setImgOnImageView")
    public static void setImgOnImageView(ImageView view, String path) {
        Utility.log(TAG,"setImgOnImageView()");

        Glide.with(view.getContext()).load(path).apply(options).into(view);

    }
}
