package com.example.joanna.housepharmacyproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Joanna on 2018-02-11.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context) {
        super(context, DBConstants.DBName, null, DBConstants.DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(DBConstants.CREATE_TABLE_MEDS);
            db.execSQL(DBConstants.CREATE_TABLE_PLACES);

            ContentValues cv = new ContentValues();
            cv.put(DBConstants.PLACE_NAME, "none");
            cv.put(DBConstants.PLACE_DESCRIPTION,"Leki nieprzypisane do żadnego miejsca");
            db.insert(DBConstants.PLACESTABLE,null,cv);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.MEDSTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.PLACESTABLE);
        onCreate(db);


    }


}
