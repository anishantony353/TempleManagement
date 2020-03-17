package com.saarit.temple_management.templemanagement.view_model

import android.app.Application
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saarit.temple_management.templemanagement.model.BaseResponse
import com.saarit.temple_management.templemanagement.model.SuccessOrFailure
import com.saarit.temple_management.templemanagement.model.User
import com.saarit.temple_management.templemanagement.model.repositories.Repo_server
import com.saarit.temple_management.templemanagement.model.repositories.server_storage.ApiService
import com.saarit.temple_management.templemanagement.util.Utility
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.Socket
import java.net.SocketTimeoutException

class CreateUser_ViewModel(application:Application): AndroidViewModel(application){

    val TAG = CreateUser_ViewModel::class.java.simpleName
    var user = User()
    var observableField_User = ObservableField<User>()
    var errorMsg_firstName = ObservableField<String>()
    var errorMsg_LastName = ObservableField<String>()
    var errorMsg_number = ObservableField<String>()
    var errorMsg_id = ObservableField<String>()
    var errorMsg_password = ObservableField<String>()
    var errorMsg_confirmPassword = ObservableField<String>()
    var progressBar = ObservableInt(View.INVISIBLE)

    var disposable = CompositeDisposable()
    var repo_server:Repo_server = Repo_server.getInstance()
    
    /*var mutableLiveData_finishActivity = MutableLiveData<Boolean>()*/
    var mutableLiveData_baseResponse = MutableLiveData<BaseResponse>()
    var mutableLiveData_lockScreen = MutableLiveData<Boolean>()
    var mutableLiveData_toastMsg= MutableLiveData<String>()

    fun init() {
        observableField_User.set(user)
    }

    fun onItemSelected(parent: AdapterView<*>, v: View?, position: Int, id: Long) {

        when(parent.getItemAtPosition(position)){
            "Surveyor"-> {
                Utility.log(TAG, "Selected Surveyor")
                user.user_type_id = 1
            }
            else->{ user.user_type_id = 0 }
        }

    }

    fun onRegisterClick(view: View){
        var user = observableField_User.get()!!
        Utility.log(TAG,"onRegisterClick()..${user.first_name} \n ${user.last_name} \n ${user.mobile} \n" +
                "${user.email} \n ${user.password} \n ${user.confirmPassword}")

        if(isValid()){
            registerUser()
        }
    }

    private fun isValid(): Boolean {
        if(user.first_name == null || user.first_name!!.trim().length == 0){

            errorMsg_firstName.set("Enter First Name")
            errorMsg_firstName.notifyChange()
            return false
        }
        if(user.last_name == null || user.last_name!!.trim().length == 0){

            errorMsg_LastName.set("Enter Last Name")
            errorMsg_LastName.notifyChange()
            return false
        }
        if(user.mobile == null || user.mobile!!.trim().length == 0){

            errorMsg_number.set("Enter Number")
            errorMsg_number.notifyChange()
            return false
        }
        if(user.email == null || user.email!!.trim().length == 0){

            errorMsg_id.set("Enter Id")
            errorMsg_id.notifyChange()
            return false
        }
        if(user.password == null || user.password!!.trim().length == 0){

            errorMsg_password.set("Enter Password")
            errorMsg_password.notifyChange()
            return false
        }
        if(user.confirmPassword == null || user.confirmPassword!!.trim().length == 0){

            errorMsg_confirmPassword.set("Enter Confirm Password")
            errorMsg_confirmPassword.notifyChange()
            return false
        }
        if(!user.password.equals(user.confirmPassword)){

            errorMsg_confirmPassword.set("Password not matching")
            errorMsg_confirmPassword.notifyChange()
            return false
        }
        if(user.user_type_id == 0){

            Utility.showToast("Select User type",Toast.LENGTH_SHORT,getApplication())
            return false
        }

        return true


    }

    private fun registerUser() {
        disposable.add(

                repo_server.apiService.createUser(user).toObservable().
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(
                                {
                                    Utility.log(TAG," from onNext().Thread:${Thread.currentThread()}")
                                    mutableLiveData_baseResponse.value = it
                                    mutableLiveData_lockScreen.value = false
                                    progressBar.set(View.GONE)
                                  
                                },
                                {
                                    mutableLiveData_toastMsg.value = it.message
                                    mutableLiveData_lockScreen.value = false
                                    progressBar.set(View.GONE)
                                },
                                {
                                },
                                {
                                    Utility.log(TAG," initializing::.Thread:${Thread.currentThread()}")
                                    if(!Utility.isNetworkAvailable(getApplication())){
                                        throw Exception("Check Internet")
                                    }
                                    mutableLiveData_lockScreen.value = true
                                    progressBar.set(View.VISIBLE)
                                    
                                }
                        )
        )
    }
    
    /*fun observeRequest_ActivityFinish():LiveData<Boolean> = mutableLiveData_finishActivity*/
    fun observeRequest_baseResponse():LiveData<BaseResponse> = mutableLiveData_baseResponse
    fun observeRequest_LockScreen():LiveData<Boolean> = mutableLiveData_lockScreen
    fun observeRequest_toastMsg():LiveData<String> = mutableLiveData_toastMsg

    override fun onCleared() {
        Utility.log(TAG, "onCleared()")
        disposable.clear()
        super.onCleared()
    }


}