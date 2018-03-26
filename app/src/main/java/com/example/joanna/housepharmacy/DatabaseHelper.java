package com.example.joanna.housepharmacy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Joanna on 2018-02-11.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context) {
        super(context, DatabaseConstants.DBName, null, DatabaseConstants.DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(DatabaseConstants.CREATE_TABLE_MEDS);
            db.execSQL(DatabaseConstants.CREATE_TABLE_PLACES);
            db.execSQL(DatabaseConstants.CREATE_TABLE_FORMS);

            placeTableStartContent(db);
            //formTableStartContent(db);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void placeTableStartContent(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseConstants.PLACE_NAME, "brak");
        cv.put(DatabaseConstants.PLACE_DESCRIPTION,"Leki nieprzypisane do żadnego miejsca");
        db.insert(DatabaseConstants.PLACESTABLE,null,cv);
         cv.clear();
        String[] forms = {"brak","tabletki","czopki","maść","syrop","zawiesina","inne"};

        for(int i=0; i<forms.length;i++) {
            cv.put(DatabaseConstants.FORM_NAME, forms[i]);
            db.insert(DatabaseConstants.FORMSTABLE, null, cv);
            cv.clear();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.MEDSTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.PLACESTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.FORMSTABLE);

        onCreate(db);

    }


}
