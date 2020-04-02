package com.saarit.temple_management.templemanagement.util

import android.content.Context
import android.content.SharedPreferences

object PrefManager {
    //Inject Context using DAGGER later
    private val TAG = PrefManager::class.java.simpleName

    private val LOGIN_PASSWORD = "login_password"
    private val LOGIN_ID =  "login_id"
    private val USER_ID =  "user_id"
    private val REMEMBER = "remember_me"
    private val USER_TYPE_ID =  "user_type_id"

    lateinit var sharedPreference:SharedPreferences
    lateinit var editor:SharedPreferences.Editor


    fun init(context: Context){
        if(!::sharedPreference.isInitialized or !::editor.isInitialized ){
            sharedPreference = context.getSharedPreferences("pref",Context.MODE_PRIVATE)
            editor = sharedPreference.edit()

        }
    }

    @JvmStatic fun setRemember(value: Boolean,context:Context) {
        init(context)
        editor.putBoolean(REMEMBER, value)
        editor.commit()

    }

    @JvmStatic fun getRemember(context: Context): Boolean {
        init(context)
        return sharedPreference.getBoolean(REMEMBER, false)
    }

    @JvmStatic fun setLoginId(loginId: String,context:Context) {
        init(context)
        editor.putString(LOGIN_ID, loginId)
        editor.commit()
    }

    @JvmStatic fun getLoginId(context:Context): String {
        init(context)
        return sharedPreference.getString(LOGIN_ID, "")
    }

    @JvmStatic fun setLoginPassword(loginPassword: String,context:Context) {
        init(context)
        editor.putString(LOGIN_PASSWORD, loginPassword)
        editor.commit()
    }

    @JvmStatic fun getLoginPassword(context:Context): String {
        init(context)
        return sharedPreference.getString(LOGIN_PASSWORD, "")
    }

    @JvmStatic fun setUserId(userId: Int,context:Context) {
        init(context)
        editor.putInt(USER_ID, userId)
        editor.commit()
    }

    @JvmStatic fun getUserId(context:Context): Int {
        init(context)
        return sharedPreference.getInt(USER_ID, 0)
    }

    @JvmStatic fun setUserTypeId(userTypeId: Int,context:Context) {
        init(context)
        editor.putInt(USER_TYPE_ID, userTypeId)
        editor.commit()
    }

    @JvmStatic fun getUserTypeId(context:Context): Int {
        init(context)
        return sharedPreference.getInt(USER_TYPE_ID, 0)
    }

}