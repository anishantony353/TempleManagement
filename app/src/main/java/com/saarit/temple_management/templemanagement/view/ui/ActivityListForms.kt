package com.saarit.temple_management.templemanagement.view.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.saarit.temple_management.templemanagement.R
import com.saarit.temple_management.templemanagement.databinding.ActivityListFormsBinding
import com.saarit.temple_management.templemanagement.util.Constant
import com.saarit.temple_management.templemanagement.util.Utility
import com.saarit.temple_management.templemanagement.view_model.FormList_ViewModel
import com.saarit.temple_management.templemanagement.view_model.FormTypes_ViewModel

class ActivityListForms : AppCompatActivity() {

    val TAG = ActivityListForms::class.java.simpleName

    lateinit var binding : ActivityListFormsBinding
    lateinit var viewModel : FormList_ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBindings(savedInstanceState)
        setUpObservers()
    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_forms)
        viewModel = ViewModelProviders.of(this).get(FormList_ViewModel::class.java)
        if (savedInstanceState == null) {
            viewModel.init()
        }
        binding.setViewmodel(viewModel)
    }

    private fun setUpObservers() {
        viewModel.observe_activity_to_start().observe(
                this,
                Observer<String> { formType: String? ->
                    when (formType) {
                        Constant.FORM_TYPE_1 -> Utility.showToast(Constant.FORM_TYPE_1, Toast.LENGTH_SHORT,baseContext)
                        Constant.FORM_TYPE_2 -> startActivity(Intent(baseContext,ActivityListForms_2::class.java))
                        Constant.FORM_TYPE_3a -> Utility.showToast(Constant.FORM_TYPE_3a, Toast.LENGTH_SHORT,baseContext)
                        Constant.FORM_TYPE_3b_1 -> Utility.showToast(Constant.FORM_TYPE_3b_1, Toast.LENGTH_SHORT,baseContext)
                        Constant.FORM_TYPE_3b_2 -> Utility.showToast(Constant.FORM_TYPE_3b_2, Toast.LENGTH_SHORT,baseContext)
                        Constant.FORM_TYPE_4 -> Utility.showToast(Constant.FORM_TYPE_4, Toast.LENGTH_SHORT,baseContext)
                        Constant.FORM_TYPE_5 -> Utility.showToast(Constant.FORM_TYPE_5, Toast.LENGTH_SHORT,baseContext)

                        /*Constant.FORM_TYPE_1 -> startActivity(Intent(baseContext,Form2Activity::class.java))
                        Constant.FORM_TYPE_2 -> startActivity(Intent(baseContext,Form2Activity::class.java))
                        Constant.FORM_TYPE_3a -> startActivity(Intent(baseContext,Form2Activity::class.java))
                        Constant.FORM_TYPE_3b_1 -> startActivity(Intent(baseContext,Form2Activity::class.java))
                        Constant.FORM_TYPE_3b_2 -> startActivity(Intent(baseContext,Form2Activity::class.java))
                        Constant.FORM_TYPE_4 -> startActivity(Intent(baseContext,Form2Activity::class.java))
                        Constant.FORM_TYPE_5 -> startActivity(Intent(baseContext,Form2Activity::class.java))*/
                    }
                }
        )
    }
}
