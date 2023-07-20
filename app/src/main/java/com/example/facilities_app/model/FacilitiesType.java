package com.example.facilities_app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FacilitiesType {

    @SerializedName("facility_id")
    private String facilityId;
    @SerializedName("name")
    private String name;
    @SerializedName("options")
    private List<Options> optionsList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Options> getOptionsList() {
        return optionsList;
    }

    public void setOptionsList(List<Options> optionsList) {
        this.optionsList = optionsList;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

}
