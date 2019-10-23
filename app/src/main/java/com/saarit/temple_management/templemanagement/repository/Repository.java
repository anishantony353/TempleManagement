package com.saarit.temple_management.templemanagement.repository;

public class Repository {

    String TAG = Repository.class.getSimpleName();

    static Repository instance;

    public static Repository getInstance(){

        if(instance == null){
            instance = new Repository();
        }

        return instance;
    }

//    public SuccessOrFailure isValidCredentialsFromServer(LoginFeilds loginFeilds){
//        Log.i(TAG,"isValidCredentialsFromServer()");
//
//        //MutableLiveData<SuccessOrFailure> data = new MutableLiveData<>();
//
//        data.setValue();
//
//        return data;
//    }




}
