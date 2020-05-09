package com.saarit.temple_management.templemanagement.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.saarit.temple_management.templemanagement.R
import com.saarit.temple_management.templemanagement.databinding.ActivityListForms2Binding
import com.saarit.temple_management.templemanagement.databinding.ActivityListFormsBinding
import com.saarit.temple_management.templemanagement.util.Constant
import com.saarit.temple_management.templemanagement.util.Utility
import com.saarit.temple_management.templemanagement.view_model.FormList_2_ViewModel
import com.saarit.temple_management.templemanagement.view_model.FormList_ViewModel

class ActivityListForms_2 : AppCompatActivity() {

    val TAG = ActivityListForms_2::class.java.simpleName

    lateinit var binding : ActivityListForms2Binding
    lateinit var viewModel : FormList_2_ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBindings(savedInstanceState)
        setUpObservers()
    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_forms_2)
        viewModel = ViewModelProviders.of(this).get(FormList_2_ViewModel::class.java)
        if (savedInstanceState == null) {
            viewModel.init()
        }
        binding.setViewmodel(viewModel)
    }

    private fun setUpObservers() {
        viewModel.observe_temple_to_open().observe(
                this,
                Observer<Int> { templeId: Int? ->
                    Utility.showToast(""+templeId,Toast.LENGTH_SHORT,baseContext)
                }
        )
    }
}
