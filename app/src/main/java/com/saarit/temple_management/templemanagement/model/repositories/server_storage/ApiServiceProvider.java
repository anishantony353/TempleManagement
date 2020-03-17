package com.saarit.temple_management.templemanagement.model.repositories.server_storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.saarit.temple_management.templemanagement.util.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiServiceProvider {

    public static ApiService apiService;

    public static ApiService getService() {

        if(apiService == null){

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .serializeNulls()
                    .create();


            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(180, TimeUnit.SECONDS)
                    .readTimeout(180,TimeUnit.SECONDS)
                    .writeTimeout(180,TimeUnit.SECONDS)
                    .build();



            /*OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120,TimeUnit.SECONDS)
                    .writeTimeout(120,TimeUnit.SECONDS)
                    .build();*/


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.APP_URL)
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();


            apiService =  retrofit.create(ApiService.class);

        }

        return apiService;

    }
}
