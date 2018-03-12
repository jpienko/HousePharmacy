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
            db.execSQL(DBConstants.CREATE_TABLE_FORMS);

            placeTableStartContent(db);
            //formTableStartContent(db);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void placeTableStartContent(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DBConstants.PLACE_NAME, "brak");
        cv.put(DBConstants.PLACE_DESCRIPTION,"Leki nieprzypisane do żadnego miejsca");
        db.insert(DBConstants.PLACESTABLE,null,cv);
         cv.clear();
        String[] forms = {"brak","tabletki","czopki","maść","syrop","zawiesina","inne"};

        for(int i=0; i<forms.length;i++) {
            cv.put(DBConstants.FORM_NAME, forms[i]);
            db.insert(DBConstants.FORMSTABLE, null, cv);
            cv.clear();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.MEDSTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.PLACESTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.FORMSTABLE);

        onCreate(db);

    }


}
