package com.example.facilities_app;

public class OptionTable {
    public static final String TABLE_NAME = "options";
    public static final String COLUMN_FACILITY_ID = "facility_id";
    public static final String COLUMN_OPTION_NAME = "name";
    public static final String COLUMN_OPTION_ICON = "icon";
    public static final String COLUMN_OPTION_ID = "id";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_FACILITY_ID + " TEXT,"
            + COLUMN_OPTION_NAME + " TEXT,"
            + COLUMN_OPTION_ICON + " TEXT,"
            + COLUMN_OPTION_ID + " TEXT)";

}
