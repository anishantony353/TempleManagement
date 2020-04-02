package com.saarit.temple_management.templemanagement.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.databinding.ActivityFormTypesBinding;
import com.saarit.temple_management.templemanagement.util.Constant;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view_model.FormTypes_ViewModel;

public class FormTypesActivity extends AppCompatActivity {

    ActivityFormTypesBinding binding;
    FormTypes_ViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBindings(savedInstanceState);
        setUpObservers();
    }

    private void setupBindings(Bundle savedInstanceState) {

        binding = DataBindingUtil.setContentView(this,R.layout.activity_form_types);
        viewModel = ViewModelProviders.of(this).get(FormTypes_ViewModel.class);
        if(savedInstanceState == null){
            viewModel.init(getIntent().getIntExtra(Constant.KEY_EXTRA_TEMPLE_ID,0));
        }
        binding.setViewmodel(viewModel);

    }

    private void setUpObservers() {
        viewModel.observe_activity_to_start().observe(

                this,
                formType->{
                    Intent intent = null;
                    switch(formType){
                        case Constant.FORM_TYPE_2:
                            intent = new Intent(this,Form2Activity.class);
                            intent.putExtra(Constant.KEY_EXTRA_TEMPLE_ID,getIntent().getIntExtra(Constant.KEY_EXTRA_TEMPLE_ID,0));
                            startActivity(intent);
                            break;
                        case Constant.FORM_TYPE_3a:
                            intent = new Intent(this,Form3aActivity.class);
                            intent.putExtra(Constant.KEY_EXTRA_TEMPLE_ID,getIntent().getIntExtra(Constant.KEY_EXTRA_TEMPLE_ID,0));
                            startActivity(intent);
                            break;
                        case Constant.FORM_TYPE_3b_1:
                            intent = new Intent(this,Form3b_1Activity.class);
                            intent.putExtra(Constant.KEY_EXTRA_TEMPLE_ID,getIntent().getIntExtra(Constant.KEY_EXTRA_TEMPLE_ID,0));
                            startActivity(intent);
                            /*Utility.showToast("Under Process",Toast.LENGTH_SHORT,this);*/
                            break;
                        case Constant.FORM_TYPE_3b_2:
                            intent = new Intent(this,Form3b_2Activity.class);
                            intent.putExtra(Constant.KEY_EXTRA_TEMPLE_ID,getIntent().getIntExtra(Constant.KEY_EXTRA_TEMPLE_ID,0));
                            /*startActivity(intent);*/
                            Utility.showToast("Under Process",Toast.LENGTH_SHORT,this);
                            break;
                        case Constant.FORM_TYPE_4:
                            intent = new Intent(this,Form2Activity.class);
                            Utility.showToast("Under Process",Toast.LENGTH_SHORT,this);
                            break;
                        case Constant.FORM_TYPE_5:
                            intent = new Intent(this,Form2Activity.class);
                            Utility.showToast("Under Process",Toast.LENGTH_SHORT,this);
                            break;

                    }
                    /*intent.putExtra(Constant.KEY_EXTRA_TEMPLE_ID,getIntent().getIntExtra(Constant.KEY_EXTRA_TEMPLE_ID,0));
                    startActivity(intent);*/
                }
        );
    }


}
