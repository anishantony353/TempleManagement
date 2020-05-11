package com.saarit.temple_management.templemanagement.view_model

import android.app.Application
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saarit.temple_management.templemanagement.model.FormType_1
import com.saarit.temple_management.templemanagement.model.repositories.Repo_FormType_1
import com.saarit.temple_management.templemanagement.model.repositories.Repo_FormType_2
import com.saarit.temple_management.templemanagement.util.Utility
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FormList_1_ViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = FormList_1_ViewModel::class.java.simpleName
    var listner: AdapterView.OnItemClickListener? = null
    var observableListner = ObservableField<AdapterView.OnItemClickListener>()
    var observableAdapter = ObservableField<ArrayAdapter<Int>>()
    var templeToOpen = MutableLiveData<FormType_1>()
    var list = mutableListOf<Int>()
    lateinit var adapter:ArrayAdapter<Int>

    //Methods
    fun init() {
        /*list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)

        adapter = ArrayAdapter<Int>(getApplication(),android.R.layout.simple_list_item_1,list)
        observableAdapter.set(adapter)*/

        listner = AdapterView.OnItemClickListener { adapterView: AdapterView<*>, view: View?, i: Int, l: Long ->
            val selectedItem = adapterView.adapter.getItem(i) as Int
            Utility.log(TAG, "onItemClick::$selectedItem")
            /*templeToOpen.setValue(selectedItem)*/
            getLocalForm(selectedItem.toLong())
        }

        observableListner.set(listner)
        fetchTempleIDs()
    }

    private fun getLocalForm(selectedItem: Long) {
        Repo_FormType_1.getInstance(getApplication()).getFormByTempleId(selectedItem).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(
                        {
                            templeToOpen.setValue(it)
                        },
                        {

                        }
                )


    }

    public fun fetchTempleIDs() {
        Repo_FormType_1.getInstance(getApplication()).getAllTempleIds().
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(
                        {
                            Utility.log(TAG,"SIZE:"+it.size)
                            if(it.size < 1)
                            {Utility.showToast("No local forms found",Toast.LENGTH_LONG,getApplication())}

                            list.clear()
                            list.addAll(it)
                            adapter = ArrayAdapter<Int>(getApplication(),android.R.layout.simple_list_item_1,list)
                            observableAdapter.set(adapter)

                        },
                        {

                        }
                )
    }

    fun observe_temple_to_open(): LiveData<FormType_1> = templeToOpen
}