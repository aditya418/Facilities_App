package com.example.facilities_app.presenter.backend;

import com.example.facilities_app.listener.Listener;
import com.example.facilities_app.model.Facilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Query {
    public static void queryToGetFacilitiesDetails(Listener.OnGetFacilitiesDetailsListener listener) {
        Call<Facilities> call = RestClient.getService().getFacilities();
        call.enqueue(new Callback<Facilities>() {
            @Override
            public void onResponse(Call<Facilities> call, Response<Facilities> response) {
                listener.OnGetFacilities(true, response.body());

            }

            @Override
            public void onFailure(Call<Facilities> call, Throwable t) {
                listener.OnGetFacilities(false, null);
            }
        });
    }
}
