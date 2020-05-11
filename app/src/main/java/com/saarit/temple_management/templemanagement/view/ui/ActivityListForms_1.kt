package com.saarit.temple_management.templemanagement.view.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.saarit.temple_management.templemanagement.R
import com.saarit.temple_management.templemanagement.databinding.ActivityListForms1Binding
import com.saarit.temple_management.templemanagement.databinding.ActivityListForms2Binding
import com.saarit.temple_management.templemanagement.model.FormType_1
import com.saarit.temple_management.templemanagement.util.Constant
import com.saarit.temple_management.templemanagement.view_model.FormList_1_ViewModel
import com.saarit.temple_management.templemanagement.view_model.FormList_2_ViewModel

class ActivityListForms_1 : AppCompatActivity() {

    val TAG = ActivityListForms_1::class.java.simpleName

    lateinit var binding : ActivityListForms1Binding
    lateinit var viewModel : FormList_1_ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBindings(savedInstanceState)
        setUpObservers()
    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_forms_1)
        viewModel = ViewModelProviders.of(this).get(FormList_1_ViewModel::class.java)
        if (savedInstanceState == null) {
            viewModel.init()
        }
        binding.setViewmodel(viewModel)
    }

    private fun setUpObservers() {
        viewModel.observe_temple_to_open().observe(
                this,
                Observer<FormType_1> { form: FormType_1? ->

                    var intent = Intent(this, Form1Activity::class.java)
                    intent.putExtra("id", form!!.id)
                    intent.putExtra("temple_id", form!!.templeId)
                    intent.putExtra("req_code", Constant.REQUEST_CODE_CLICK_FORM_FROM_LIST)
                    startActivityForResult(intent,1)

                }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.fetchTempleIDs()
    }
}
