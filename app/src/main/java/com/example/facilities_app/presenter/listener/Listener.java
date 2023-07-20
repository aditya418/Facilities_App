package com.example.facilities_app.listener;

import androidx.annotation.Nullable;

import com.example.facilities_app.model.Facilities;

public interface Listener {
    interface OnGetFacilitiesDetailsListener {
        void OnGetFacilities(boolean isSuccess, @Nullable Facilities facilitiesResponse);
    }
}
