package com.saarit.temple_management.templemanagement.view_model;

import android.app.Application;

import android.view.View;
import android.widget.Toast;

import com.saarit.temple_management.templemanagement.model.LoginFeilds;
import com.saarit.temple_management.templemanagement.model.SuccessOrFailure;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_Temples_master;
import com.saarit.temple_management.templemanagement.model.repositories.Repo_server;
import com.saarit.temple_management.templemanagement.model.Temple_master;
import com.saarit.temple_management.templemanagement.util.PrefManager;
import com.saarit.temple_management.templemanagement.util.Utility;

import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends AndroidViewModel {

    private static String TAG = LoginViewModel.class.getSimpleName();
    public LoginViewModel(@NonNull Application application) {
        super(application);
        Utility.log(TAG,"from Constructor...");
    }

    public ObservableInt progressVisibility = new ObservableInt(View.GONE);
    public ObservableBoolean checked = new ObservableBoolean(false);
    private MutableLiveData<Boolean> mutableLiveData_LockScreen = new MutableLiveData<Boolean>();

    public LoginFeilds loginFeilds = new LoginFeilds();

    public SingleLiveEvent<SuccessOrFailure> isSuccessFulLogin = new SingleLiveEvent<>();
    Repo_server reposerver;
    Repo_Temples_master repoTemplesMaster;

    private CompositeDisposable disposable = new CompositeDisposable();

    public void init(){
        Utility.log(TAG,"init()");
        reposerver = Repo_server.getInstance();
        repoTemplesMaster = Repo_Temples_master.getInstance(getApplication());
        setRememberMeCheckBox();

    }

    private void setRememberMeCheckBox() {
        if(PrefManager.getRemember(getApplication()) ){
            checked.set(true);
            loginFeilds.id = PrefManager.getLoginId(getApplication());
            loginFeilds.password = PrefManager.getLoginPassword(getApplication());
            loginFeilds.notifyChange();

        }
    }


    public void onLoginClick(View view){

        Utility.log(TAG,"onLoginClick()");
        /*isSuccessFulLogin.setValue(new SuccessOrFailure(1,"Success..."));*/

        if(loginFeilds.isValidId() && loginFeilds.isValidPassword()){

            fetchTemplesMasterFromServer();

        }

    }

    private void fetchTemplesMasterFromServer() {
        disposable.add(

                reposerver.apiService.getTempleMaster(loginFeilds.id,loginFeilds.password).toObservable()
                        .flatMap(
                                templesDto -> {

                                    if(templesDto.getSuccess() == 0){
                                        throw new Exception(templesDto.getMsg());
                                    }else{
                                        PrefManager.setUserId(templesDto.user_detail.getUser_id(),getApplication());
                                        PrefManager.setUserTypeId(templesDto.user_detail.getUser_type_id(),getApplication());
                                        return  repoTemplesMaster.insertTemples(templesDto.temple_details).toObservable();
                                    }

                                }
                        )


                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list->{

                            /*fetch_LoginResult();*/

                        },
                        throwable -> {
                            isSuccessFulLogin.setValue(new SuccessOrFailure(0,throwable.getMessage()));
                            progressVisibility.set(View.GONE);
                            mutableLiveData_LockScreen.setValue(false);
                        },
                        () -> {
                            isSuccessFulLogin.setValue(new SuccessOrFailure(1,"Success..."));

                            progressVisibility.set(View.GONE);
                            mutableLiveData_LockScreen.setValue(false);

                            PrefManager.setRemember(checked.get(),getApplication());
                            PrefManager.setLoginId(loginFeilds.id,getApplication());
                            PrefManager.setLoginPassword(loginFeilds.password,getApplication());


                        },
                        dsposable ->{
                            if(!Utility.isNetworkAvailable(getApplication())){
                                throw new Exception("Check Internet");
                            }
                            progressVisibility.set(View.VISIBLE);
                            mutableLiveData_LockScreen.setValue(true);
                            Utility.showToast("Logging In...",Toast.LENGTH_LONG,getApplication());
                        }

                )
        );
    }



    public SingleLiveEvent<SuccessOrFailure> getIsSucessfulLogin(){
        Utility.log(TAG,"getIsSucessfulLogin()");

        return isSuccessFulLogin;
    }

    public LiveData<Boolean> observeLockScreenRequest(){
        return mutableLiveData_LockScreen;
    }

    @Override
    protected void onCleared() {

        Utility.log(TAG,"onCleared()");
        disposable.clear();
        super.onCleared();
    }

    public class SingleLiveEvent<T> extends MutableLiveData<T> {

        private static final String TAG = "SingleLiveEvent";

        private final AtomicBoolean mPending = new AtomicBoolean(false);



        @MainThread
        public void observe(LifecycleOwner owner, final Observer<? super T> observer) {

            if (hasActiveObservers()) {
                Utility.log(TAG, "Multiple observers registered but only one will be notified of changes.");
            }

            // Observe the internal MutableLiveData
            super.observe(owner, new Observer<T>() {
                @Override
                public void onChanged(@Nullable T t) {
                    if (mPending.compareAndSet(true, false)) {
                        observer.onChanged(t);
                    }
                }
            });
        }

        @MainThread
        public void setValue(@Nullable T t) {
            mPending.set(true);
            super.setValue(t);
        }

        /**
         * Used for cases where T is Void, to make calls cleaner.
         */
        @MainThread
        public void call() {
            setValue(null);
        }
    }
}
