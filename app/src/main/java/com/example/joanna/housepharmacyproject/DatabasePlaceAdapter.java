package com.example.joanna.housepharmacyproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * Created by Joanna on 2018-02-26.
 */

public class DatabasePlaceAdapter {
    Context context;
    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    public DatabasePlaceAdapter(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
        try {
            openDB();
        } catch (SQLException e) {
            Log.e("DatabasePlaceAdapter", "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void openDB() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void closeDB() {
        try {
            dbHelper.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long addPlace(String place,String description) {
        try {
            dbHelper.onCreate(db);
            ContentValues cv = new ContentValues();
            cv.put(DBConstants.PLACE_NAME, place);
            cv.put(DBConstants.PLACE_DESCRIPTION,description);
            try
            {
                db.insert(DBConstants.PLACESTABLE, null, cv);
            }
            catch ( SQLiteException e)
            {
                dbHelper.onCreate(db);
                db.insert(DBConstants.PLACESTABLE, null, cv);
            }

            return db.insert(DBConstants.PLACESTABLE, null, cv);
        } catch (Exception exp) {
            exp.printStackTrace();

            return 0;
        }
    }

    public Cursor getAllPlaces() {
        final String MY_QUERY = "SELECT * FROM "+DBConstants.PLACESTABLE;

        return db.rawQuery(MY_QUERY, null);
    }

}
