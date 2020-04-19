package com.saarit.temple_management.templemanagement.view.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.saarit.temple_management.templemanagement.R
import com.saarit.temple_management.templemanagement.databinding.ActivityForm4Binding
import com.saarit.temple_management.templemanagement.util.Constant
import com.saarit.temple_management.templemanagement.util.Utility
import com.saarit.temple_management.templemanagement.view_model.FormType4_ViewModel

class Form4Activity : AppCompatActivity() {
    private val TAG = Form4Activity::class.java.simpleName
    lateinit var binding:ActivityForm4Binding
    lateinit var viewmodel:FormType4_ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utility.log(TAG, "onCreate()")
        setUpBinding(savedInstanceState)
        setUpObservers()
    }
    private fun setUpBinding(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_form4)
        viewmodel = ViewModelProviders.of(this).get(FormType4_ViewModel::class.java)

        if(savedInstanceState == null){
            viewmodel.init(intent.getIntExtra(Constant.KEY_EXTRA_TEMPLE_ID, 0))
        }
        binding.viewmodel = viewmodel
    }

    private fun setUpObservers() {
        viewmodel.shouldFinishActivity().observe(
                this,
                Observer { value:Any? ->finish() }
        )

        viewmodel.observeLockScreenRequest().observe(
                this,
                Observer { shouldLock  ->
                    if (shouldLock) {
                        Utility.log(TAG, "Locking Screen...")
                        lockScreen()
                    } else {
                        Utility.log(TAG, "UnLocking Screen...")
                        unlockScreen()
                    }
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