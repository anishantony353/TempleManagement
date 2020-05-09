package com.saarit.temple_management.templemanagement.binding;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.material.tabs.TabLayout;
import com.saarit.temple_management.templemanagement.model.Temple_master;
import com.saarit.temple_management.templemanagement.util.Constant;
import com.saarit.temple_management.templemanagement.util.Utility;
import androidx.appcompat.widget.SwitchCompat;
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

        Utility.log(TAG,"About to clear ");

            editText.getText().clear();
            editText.requestFocus();

            editText.setError(errorMsg);

    }

    @BindingAdapter("set_tablayout_viewpager")
    public static void setViewPager(TabLayout tabLayout, ViewPager viewPager) {
        Utility.log(TAG,"setViewPager()");

        viewPager.setOffscreenPageLimit(2);

        tabLayout.setupWithViewPager(viewPager);

    }




    @BindingAdapter("adapter_recyclerView")
    public static void setRecyclerViewFormsAdapter(RecyclerView rv,RecyclerView.Adapter adapter){

        Utility.log(TAG,"setRecyclerViewFormsAdapter()");
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(adapter);
    }

    @BindingAdapter("rv_adapter")
    public static void setRVAdapter(RecyclerView rv,RecyclerView.Adapter adapter){

        Utility.log(TAG,"setRVAdapter()");
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(adapter);
    }

    @BindingAdapter("visibility")
    public static void setVisibility(View view, int reqType) {
        Utility.log(TAG,"setVisibility()..REQ Type:"+reqType);

        switch(reqType){
            case Constant.REQUEST_CODE_NEW_TEMPLE:
                view.setVisibility(View.GONE);
                break;

                default:
                    view.setVisibility(View.VISIBLE);
                    break;
        }


    }

    @BindingAdapter("setImgOnImageView")
    public static void setImgOnImageView(ImageView view, String path) {
        Utility.log(TAG,"setImgOnImageView()..Path:"+path);

        Glide.with(view.getContext().getApplicationContext()).load(path).apply(options).into(view);

    }

    @BindingAdapter({"adapter_auto_textview","onItemClicklistner"})
    public static void setAdapter(AutoCompleteTextView view, ArrayAdapter<Temple_master> adapter, AdapterView.OnItemClickListener listner){

        Utility.log(TAG,"setAdapter()");

        view.setAdapter(adapter);
        view.setOnItemClickListener(listner);

    }

    @BindingAdapter("listview.onItemClicklistner")
    public static void setListner(ListView view,AdapterView.OnItemClickListener listner){

        Utility.log(TAG,"setListner()");
        view.setOnItemClickListener(listner);

    }

    @BindingAdapter("listview.adapter")
    public static void setLvAdapter(ListView view, ArrayAdapter<Integer> adapter){

        view.setAdapter(adapter);

    }

    @BindingAdapter("clearEditText")
    public static void clearEditText(EditText et,Boolean clear){
        Utility.log(TAG,"clearEditText() :"+clear);
        if(clear == false){
            et.setText("");
        }

    }

    @BindingAdapter("switchChecked")
    public static void setSwitchCheckState(SwitchCompat view,boolean state){

//        view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                Utility.log(TAG,"State:"+b);
//
//            }
//        });

        view.setChecked(state);
    }

}
