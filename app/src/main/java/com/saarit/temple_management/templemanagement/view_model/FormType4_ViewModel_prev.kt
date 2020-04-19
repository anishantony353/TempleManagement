package com.saarit.temple_management.templemanagement.view_model

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import com.saarit.temple_management.templemanagement.model.FormType_4_prev
import com.saarit.temple_management.templemanagement.util.Utility

class FormType4_ViewModel_prev(application: Application) : AndroidViewModel(application) {
    val TAG = FormType4_ViewModel_prev::class.java.simpleName

    lateinit var formType_4:FormType_4_prev

    var local_server_new_ObservableField = ObservableField<String>("")
    var formType_4_observable = ObservableField<FormType_4_prev>()
    var progressBar = ObservableInt(View.GONE)

    fun init(templeId: Int) {
        formType_4 = FormType_4_prev()
        formType_4_observable.set(formType_4)
    }

    fun onAddClick(view: View){

    }

    public fun onSaveClick(view: View){
        Utility.log(TAG, "onSaveClick()")
        Utility.log(TAG, "Values:"+formType_4)

    }

    fun onSubmitClick(view: View){
        Utility.log(TAG, "onSubmitClick()")

    }

}