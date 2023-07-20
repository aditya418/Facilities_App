package com.example.facilities_app.view;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.facilities_app.ExclusionTable;
import com.example.facilities_app.FacilityTable;
import com.example.facilities_app.MyDatabaseHelper;
import com.example.facilities_app.OptionTable;
import com.example.facilities_app.R;
import com.example.facilities_app.databinding.ActivityMainBinding;
import com.example.facilities_app.listener.Listener;
import com.example.facilities_app.model.Exclusion;
import com.example.facilities_app.model.Facilities;
import com.example.facilities_app.model.FacilitiesType;
import com.example.facilities_app.model.Options;
import com.example.facilities_app.presenter.adaptor.FacilitiesListAdaptor;
import com.example.facilities_app.presenter.backend.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    FacilitiesListAdaptor facilitiesListAdaptor;
    LinearLayoutManager layoutManager;
    MyDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        databaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        if (checkTime()) {
            dropDb();
        }

        if (getAllFacilities().isEmpty()) {
            Query.queryToGetFacilitiesDetails((isSuccess, facilitiesResponse) -> {
                if (isSuccess && facilitiesResponse != null) {
                    for (FacilitiesType facility : facilitiesResponse.getFacilitiesTypeList()) {
                        // Insert facility data into FacilityTable
                        ContentValues facilityValues = new ContentValues();
                        facilityValues.put(FacilityTable.COLUMN_FACILITY_ID, facility.getFacilityId());
                        facilityValues.put(FacilityTable.COLUMN_NAME, facility.getName());
                        db.insert(FacilityTable.TABLE_NAME, null, facilityValues);

                        for (Options option : facility.getOptionsList()) {
                            ContentValues optionValues = new ContentValues();
                            optionValues.put(OptionTable.COLUMN_FACILITY_ID, facility.getFacilityId());
                            optionValues.put(OptionTable.COLUMN_OPTION_NAME, option.getName());
                            optionValues.put(OptionTable.COLUMN_OPTION_ICON, option.getIcon());
                            optionValues.put(OptionTable.COLUMN_OPTION_ID, option.getId());
                            db.insert(OptionTable.TABLE_NAME, null, optionValues);
                        }
                    }

                    for (List<Exclusion> exclusionList : facilitiesResponse.getExclusions()) {
                        ContentValues exclusionValues = new ContentValues();
                        exclusionValues.put(ExclusionTable.COLUMN_FACILITY_ID, exclusionList.get(0).getFacilityId());
                        exclusionValues.put(ExclusionTable.COLUMN_OPTION_ID, exclusionList.get(0).getOptionsId());
                        exclusionValues.put(ExclusionTable.COLUMN_FACILITY_ID_2, exclusionList.get(1).getFacilityId());
                        exclusionValues.put(ExclusionTable.COLUMN_OPTION_ID_2, exclusionList.get(1).getOptionsId());
                        db.insert(ExclusionTable.TABLE_NAME, null, exclusionValues);
                    }
                    putTimestamp();
                    setupUi();
                }
            });
        } else {
            setupUi();
        }

    }

    private void setupUi() {
        facilitiesListAdaptor = new FacilitiesListAdaptor(getAllFacilities(), getAllExclusions());
        layoutManager = new LinearLayoutManager(MainActivity.this);
        mainBinding.idFacilitiesRV.setLayoutManager(layoutManager);
        mainBinding.idFacilitiesRV.setAdapter(facilitiesListAdaptor);
    }

    private void dropDb() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.rawQuery("DELETE * FROM " + FacilityTable.TABLE_NAME, null);
        db.rawQuery("DELETE * FROM " + FacilityTable.TABLE_NAME, null);
    }

    private void putTimestamp() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        sharedPreferences.edit().putLong("timestamp", new Date().getTime()).apply();
    }

    private boolean checkTime() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        long timestamp = sharedPreferences.getLong("timestamp", 0);
        Date current = new Date();
        long diff = current.getTime() - timestamp;
        int hours = (int) (diff / (1000 * 60 * 60));
        return timestamp != 0 && hours > 24;
    }

    public List<FacilitiesType> getAllFacilities() {
        List<FacilitiesType> facilities = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String query = "SELECT * FROM " + FacilityTable.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String facilityId = cursor.getString(cursor.getColumnIndex(FacilityTable.COLUMN_FACILITY_ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(FacilityTable.COLUMN_NAME));

                FacilitiesType facility = new FacilitiesType();
                facility.setFacilityId(facilityId);
                facility.setName(name);

                // Query the options for the current facility
                List<Options> options = getOptionsForFacility(facilityId);
                facility.setOptionsList(options);

                facilities.add(facility);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return facilities;
    }

    public List<List<Exclusion>> getAllExclusions() {
        List<List<Exclusion>> allExclusions = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String query = "SELECT * FROM " + ExclusionTable.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);


        if (cursor.moveToFirst()) {
            do {
                List<Exclusion> currentExclusionList = new ArrayList<Exclusion>();
                @SuppressLint("Range") String facilityId = cursor.getString(cursor.getColumnIndex(ExclusionTable.COLUMN_FACILITY_ID));
                @SuppressLint("Range") String optionId = cursor.getString(cursor.getColumnIndex(ExclusionTable.COLUMN_OPTION_ID));
                @SuppressLint("Range") String facilityId2 = cursor.getString(cursor.getColumnIndex(ExclusionTable.COLUMN_FACILITY_ID_2));
                @SuppressLint("Range") String optionId2 = cursor.getString(cursor.getColumnIndex(ExclusionTable.COLUMN_OPTION_ID_2));

                Exclusion exclusion = new Exclusion();
                exclusion.setFacilityId(facilityId);
                exclusion.setOptionsId(optionId);

                Exclusion exclusion2 = new Exclusion();
                exclusion2.setFacilityId(facilityId2);
                exclusion2.setOptionsId(optionId2);

                currentExclusionList.add(exclusion);
                currentExclusionList.add(exclusion2);

                allExclusions.add(currentExclusionList);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return allExclusions;
    }


    private List<Options> getOptionsForFacility(String facilityId) {
        List<Options> options = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String query = "SELECT * FROM " + OptionTable.TABLE_NAME +
                " WHERE " + OptionTable.COLUMN_FACILITY_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{facilityId});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(OptionTable.COLUMN_OPTION_NAME));
                @SuppressLint("Range") String icon = cursor.getString(cursor.getColumnIndex(OptionTable.COLUMN_OPTION_ICON));
                @SuppressLint("Range") String optionId = cursor.getString(cursor.getColumnIndex(OptionTable.COLUMN_OPTION_ID));

                Options option = new Options();
                option.setName(name);
                option.setIcon(icon);
                option.setId(optionId);

                options.add(option);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return options;
    }
}