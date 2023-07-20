package com.example.facilities_app.presenter.backend;


import com.example.facilities_app.model.Facilities;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices {

    String getFacilitiesInformation = "db";

    // GET METHODS --------------------------------------------------------------------------------
    @GET(getFacilitiesInformation)
    Call<Facilities> getFacilities();
}
