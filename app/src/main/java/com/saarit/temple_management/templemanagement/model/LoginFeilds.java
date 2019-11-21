package com.saarit.temple_management.templemanagement.model;

import com.saarit.temple_management.templemanagement.BR;
import com.saarit.temple_management.templemanagement.util.Utility;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class LoginFeilds extends BaseObservable {

    private String TAG = LoginFeilds.class.getSimpleName();


    public String id,password;
    public String id_error;
    public String password_error;

    @Bindable
    public String getId() {
        Utility.log(TAG,"getId()");
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);

    }

    @Bindable
    public String getPassword() {

        Utility.log(TAG,"getPassword()");

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    public boolean isValidId(){

        Utility.log(TAG,"isValidId()");


        if(id == null || id.length() <= 0 ){
            Utility.log(TAG,"Error in id");

            setErrorId("Error in id");
            return false;
        }
        Utility.log(TAG,"ID lemgth:"+id.length());

        return true;
    }

    public void setErrorId(String msg) {
        this.id_error = msg;
        notifyPropertyChanged(BR.id_error);
    }

    @Bindable
    public String getId_error() {
        return id_error;
    }

    public void setErrorPassword(String msg) {
        this.password_error = msg;
        notifyPropertyChanged(BR.password_error);
    }

    @Bindable
    public String getPassword_error() {
        return password_error;
    }



    public boolean isValidPassword(){
        Utility.log(TAG,"isValidPassword()");

        if(password == null || password.length() <= 0 ){
            Utility.log(TAG,"Error in password");
            setErrorPassword("Error in password");
            return false;
        }
        Utility.log(TAG,"Password lemgth:"+password.length());

        return true;
    }

    //public void
}
