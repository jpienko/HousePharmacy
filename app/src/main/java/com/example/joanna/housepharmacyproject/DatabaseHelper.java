package com.example.joanna.housepharmacyproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.icu.lang.UProperty.NAME;
import static android.os.Build.ID;

/**
 * Created by Joanna on 2018-02-11.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName = "Pharmacy";
    public static final String table1Name = "Meds";
    //public static final String table2Name = "Places";

    public DatabaseHelper(Context context) {
        super(context, DBConstants.DBName, null, DBConstants.DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try
        {
            db.execSQL(DBConstants.CREATE_TB);

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DBConstants.table1Name);
        onCreate(db);

    }




}
