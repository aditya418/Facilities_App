package com.example.facilities_app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Facilities {
    @SerializedName("facilities")
    private List<FacilitiesType> facilitiesTypeList;

    @SerializedName("exclusions")
    private List<List<Exclusion>> exclusions;

    public List<List<Exclusion>> getExclusions() {
        return exclusions;
    }

    public void setExclusions(List<List<Exclusion>> exclusions) {
        this.exclusions = exclusions;
    }
    public List<FacilitiesType> getFacilitiesTypeList() {
        return facilitiesTypeList;
    }

    public void setFacilitiesTypeList(List<FacilitiesType> facilitiesTypeList) {
        this.facilitiesTypeList = facilitiesTypeList;
    }


}
