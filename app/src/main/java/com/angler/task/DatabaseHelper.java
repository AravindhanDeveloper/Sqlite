package com.angler.task;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserData.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_MOBILENUMBER = "mobilenumber";
    public static final String COLUMN_PASSWORD = "password";

    // Create the table query
    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USERNAME + " TEXT, " +
            COLUMN_FIRSTNAME + " TEXT, " +
            COLUMN_LASTNAME + " TEXT, " +
            COLUMN_MOBILENUMBER + " TEXT, " +
            COLUMN_PASSWORD + " TEXT" +
            ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

