package com.saarit.temple_management.templemanagement.view_model

import android.app.Application
import android.view.View
import android.widget.AdapterView
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saarit.temple_management.templemanagement.util.Utility

class FormList_ViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = FormList_ViewModel::class.java.simpleName
    var listner: AdapterView.OnItemClickListener? = null
    var observableListner = ObservableField<AdapterView.OnItemClickListener>()
    //LiveMutable to be observed by actiivty
    var activityToStart = MutableLiveData<String>()

    //Methods
    fun init() {
        listner = AdapterView.OnItemClickListener { adapterView: AdapterView<*>, view: View?, i: Int, l: Long ->
            val selectedItem = adapterView.adapter.getItem(i) as String
            Utility.log(TAG, "onItemClick::$selectedItem")
            activityToStart.setValue(selectedItem)
        }
        observableListner.set(listner)
    }

    fun observe_activity_to_start(): LiveData<String> = activityToStart
}