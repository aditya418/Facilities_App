package com.example.facilities_app;

public class ExclusionTable {
    public static final String TABLE_NAME = "exclusions";
    public static final String COLUMN_FACILITY_ID = "facility_id_1";
    public static final String COLUMN_FACILITY_ID_2 = "facility_id_2";
    public static final String COLUMN_OPTION_ID = "option_id_1";
    public static final String COLUMN_OPTION_ID_2 = "option_id_2";



    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_FACILITY_ID + " TEXT,"
            + COLUMN_OPTION_ID + " TEXT,"
            + COLUMN_FACILITY_ID_2 + " TEXT,"
            + COLUMN_OPTION_ID_2 + " TEXT)";
}
