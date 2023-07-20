package com.example.facilities_app.presenter.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestClient {

    public static final String ROOT_URL = "https://my-json-server.typicode.com/iranjith4/ad-assignment/";// Live

    private static ApiServices apiService;

    public RestClient() {
        apiService = getClient();
    }

    private static ApiServices getClient() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(20);

        OkHttpClient client = new OkHttpClient.Builder()
                .dispatcher(dispatcher)
                .connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        return retrofit.create(ApiServices.class);
    }

    public static ApiServices getService() {
        if (apiService == null) {
            apiService = getClient();
        }
        return apiService;
    }

//    public static ApiServices getApiServicePost() {
//        OkHttpClient client = new OkHttpClient();
//        Dispatcher dispatcher = new Dispatcher();
//        dispatcher.setMaxRequests(20);
//        client.newBuilder().dispatcher(dispatcher).connectTimeout(3, TimeUnit.MINUTES)
//                .readTimeout(120, TimeUnit.SECONDS)
//                .writeTimeout(120, TimeUnit.SECONDS).build();
//        Gson gson = new GsonBuilder().setLenient().create();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(ROOT_URL)
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .client(client)
//                .build();
//        return retrofit.create(ApiServices.class);
//    }
}
