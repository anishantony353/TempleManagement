package com.saarit.temple_management.templemanagement.model

import androidx.databinding.BaseObservable

class User:BaseObservable(){

    var first_name:String? = null
    var last_name:String? = null
    var email:String? = null
    var password:String? = null
    @Transient var confirmPassword:String? = null
    var mobile:String? = null
    var user_type_id:Int = 0
    var user_id = 0

}