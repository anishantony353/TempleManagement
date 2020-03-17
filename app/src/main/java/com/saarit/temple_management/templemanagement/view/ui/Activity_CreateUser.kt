package com.saarit.temple_management.templemanagement.view.ui

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.saarit.temple_management.templemanagement.R
import com.saarit.temple_management.templemanagement.databinding.ActivityCreateUserBinding
import com.saarit.temple_management.templemanagement.util.Utility
import com.saarit.temple_management.templemanagement.view_model.CreateUser_ViewModel


class Activity_CreateUser : AppCompatActivity() {
    val TAG = Activity_CreateUser::class.java.simpleName

    lateinit var binding:ActivityCreateUserBinding
    lateinit var viewModel:CreateUser_ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBinding(savedInstanceState)
        setUpObservers()
    }
    private fun setUpBinding(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_user)
        viewModel = ViewModelProviders.of(this).get(CreateUser_ViewModel::class.java)
        if(savedInstanceState == null){
            viewModel.init()
        }
        binding.viewmodel = viewModel
    }
    private fun setUpObservers() {
        viewModel.observeRequest_baseResponse().observe(
                this,
                Observer {
                    Utility.log(TAG,"Observed Response")
                    Utility.log(TAG,"Observed Response ${it.msg}")
                    Utility.showToast(it.msg, Toast.LENGTH_SHORT,applicationContext)
                     if(it.success == 1){
                        finish()
                     }
                }
        )

        viewModel.observeRequest_LockScreen().observe(
                this,
                Observer {
                    if(it){
                        lockScreen()
                    }else{
                        unlockScreen()
                    }
                }
        )
        viewModel.observeRequest_toastMsg().observe(
                this,
                Observer {
                    Utility.showToast(it,Toast.LENGTH_LONG,getApplication())
                }
        )
    }
    private fun lockScreen() {
        window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun unlockScreen() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

}
