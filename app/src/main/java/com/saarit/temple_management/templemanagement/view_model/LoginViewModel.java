package com.saarit.temple_management.templemanagement.view_model;

import android.app.Application;

import android.view.View;

import com.saarit.temple_management.templemanagement.model.LoginFeilds;
import com.saarit.temple_management.templemanagement.model.SuccessOrFailure;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_Temples_master;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_server;
import com.saarit.temple_management.templemanagement.model.Temple_master;
import com.saarit.temple_management.templemanagement.util.Utility;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends AndroidViewModel {

    private static String TAG = LoginViewModel.class.getSimpleName();
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public LoginFeilds loginFeilds;

    public MutableLiveData<SuccessOrFailure> isSuccessFulLogin = new MutableLiveData<>();
    Repo_server reposerver;
    Repo_Temples_master repoTemplesMaster;

    private CompositeDisposable disposable = new CompositeDisposable();

    public void init(){
        Utility.log(TAG,"init()");
        loginFeilds = new LoginFeilds();
        reposerver = Repo_server.getInstance();
        repoTemplesMaster = Repo_Temples_master.getInstance(getApplication());

    }


    public void onLoginClick(View view){

        Utility.log(TAG,"onLoginClick()");

        if(loginFeilds.isValidId() && loginFeilds.isValidPassword()){

            fetchTemplesMasterFromServer();

            //fetch_LoginResult();

            //Use Retrofit for Validation from SERVER...get Result and use it in setValue()

            //isSuccessFulLogin.setValue(true);

            //reposerver = Repo_server.getInstance();


            //isSuccessFulLogin.setValue(reposerver.isValidCredentialsFromServer(loginFeilds));



        }

    }

    private void fetchTemplesMasterFromServer() {
        disposable.add(

                reposerver.apiService.getTempleMaster()
                        .flatMap(
                                templesDto -> {
                                    Utility.log(TAG,"SIZE:"+templesDto.getTemple_masters().size()
                                            +" Success:"+templesDto.getSuccess()+" Msg:"+templesDto.getMsg());


                                    return  repoTemplesMaster.insertTemples(templesDto.getTemple_masters());
                                }
                        )
                        .flatMap(

                                ids->{
                                    return repoTemplesMaster.getTemples();
                                }
                        )

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        temples->{

                            for(int i = 0;i<temples.size();i++){
                                Temple_master temple = temples.get(i);
                                Utility.log(TAG,i+":Temple Name:"+temple.temple
                                        +" Village:"+temple.village+" Taluka:"+temple.taluka+" Dist:"+temple.district);

                            }
                            fetch_LoginResult();
                        }
                )
        );
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
                            public void onSuccess(SuccessOrFailure successOrFailureNotinuse) {

                                isSuccessFulLogin.setValue(successOrFailureNotinuse);

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
