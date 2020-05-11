package com.saarit.temple_management.templemanagement.view.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.saarit.temple_management.templemanagement.R
import com.saarit.temple_management.templemanagement.databinding.ActivityListForms2Binding
import com.saarit.temple_management.templemanagement.util.Constant
import com.saarit.temple_management.templemanagement.view_model.FormList_2_ViewModel

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

                    var intent = Intent(this, Form2Activity::class.java)
                    intent.putExtra(Constant.KEY_EXTRA_TEMPLE_ID, templeId)
                    startActivityForResult(intent,1)

                }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.fetchTempleIDs()
    }
}