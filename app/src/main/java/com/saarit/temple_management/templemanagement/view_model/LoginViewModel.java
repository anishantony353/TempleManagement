package com.saarit.temple_management.templemanagement.view_model;

import android.app.Application;

import android.view.View;

import com.saarit.temple_management.templemanagement.model.LoginFeilds;
import com.saarit.temple_management.templemanagement.model.SuccessOrFailure;
import com.saarit.temple_management.templemanagement.repository.ApiService;
import com.saarit.temple_management.templemanagement.repository.Repository;
import com.saarit.temple_management.templemanagement.util.Utility;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.disposables.CompositeDisposable;

public class LoginViewModel extends AndroidViewModel {

    private static String TAG = LoginViewModel.class.getSimpleName();

    public LoginFeilds loginFeilds;
    SuccessOrFailure successOrFailure;

    public MutableLiveData<SuccessOrFailure> isSuccessFulLogin = new MutableLiveData<>();
    Repository repository;

    private ApiService apiService;
    private CompositeDisposable disposable = new CompositeDisposable();


    public LoginViewModel(@NonNull Application application) {
        super(application);
    }


    public void init(){
        Utility.log(TAG,"init()");
        loginFeilds = new LoginFeilds();
        //successOrFailure = new SuccessOrFailure();

        //apiService = ApiClient.getClient(getApplication().getApplicationContext()).create(ApiService.class);
    }


    public void onLoginClick(View view){

        Utility.log(TAG,"onLoginClick()");

        if(loginFeilds.isValidId() && loginFeilds.isValidPassword()){

            fetch_LoginResult();

            //Use Retrofit for Validation from SERVER...get Result and use it in setValue()

            //isSuccessFulLogin.setValue(true);

            //repository = Repository.getInstance();


            //isSuccessFulLogin.setValue(repository.isValidCredentialsFromServer(loginFeilds));



        }

    }

    private void fetch_LoginResult() {

        Utility.log(TAG,"fetch_LoginResult()");

        isSuccessFulLogin.setValue(new SuccessOrFailure(1,"Success..."));

        /*
        disposable.add(
                apiService.isLoginSuccess(loginFeilds)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<SuccessOrFailure>() {
                            @Override
                            public void onSuccess(SuccessOrFailure successOrFailure) {

                                isSuccessFulLogin.setValue(successOrFailure);

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "onError: " + e.getMessage());

                                isSuccessFulLogin.setValue(new SuccessOrFailure(0,"Error fetching data"));
                            }
                        })
        );

        */
    }


    public LiveData<SuccessOrFailure> getIsSucessfulLogin(){
        Utility.log(TAG,"getIsSucessfulLogin()");

        return isSuccessFulLogin;
    }

    @Override
    protected void onCleared() {
        //super.onCleared();
        Utility.log(TAG,"onCleared()");
        disposable.clear();
    }
}
