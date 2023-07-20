package com.example.facilities_app.model;

import com.google.gson.annotations.SerializedName;

public class Exclusion {
    @SerializedName("facility_id")
    private String facilityId;
    @SerializedName("options_id")
    private String optionsId;

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getOptionsId() {
        return optionsId;
    }

    public void setOptionsId(String optionsId) {
        this.optionsId = optionsId;
    }

}
