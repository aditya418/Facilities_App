package com.example.facilities_app;

public class FacilityTable {
    public static final String TABLE_NAME = "facilities";
    public static final String COLUMN_FACILITY_ID = "facility_id";
    public static final String COLUMN_NAME = "name";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_FACILITY_ID + " TEXT PRIMARY KEY,"
            + COLUMN_NAME + " TEXT)";
}
