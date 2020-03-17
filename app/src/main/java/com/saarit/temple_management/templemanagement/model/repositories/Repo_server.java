package com.saarit.temple_management.templemanagement.model.repositories;

import com.saarit.temple_management.templemanagement.model.repositories.server_storage.ApiService;
import com.saarit.temple_management.templemanagement.model.repositories.server_storage.ApiServiceProvider;

public class Repo_server {

    String TAG = Repo_server.class.getSimpleName();

    public static Repo_server instance;
    public ApiService apiService;

    public Repo_server(){

        apiService = ApiServiceProvider.getService();

    }

    public static Repo_server getInstance(){

        if(instance == null){
            instance = new Repo_server();
        }

        return instance;
    }


}
