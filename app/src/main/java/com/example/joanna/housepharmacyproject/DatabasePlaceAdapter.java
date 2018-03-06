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
            return db.insert(DBConstants.PLACESTABLE, null, cv);
        } catch (Exception exp) {
            exp.printStackTrace();

            return 0;
        }
    }

    public Cursor getAllPlaces() {
        String[] columns = {DBConstants.ID_PLACE, DBConstants.PLACE_NAME, DBConstants.PLACE_DESCRIPTION};

        return db.query(DBConstants.PLACESTABLE, columns, null, null, null, null, null);
    }


    public int getPlaceId(String placeName) {
        int id = 0;

        final String MY_QUERY = "SELECT " + DBConstants.ID_PLACE +
                " FROM " + DBConstants.PLACESTABLE +
                " WHERE " + DBConstants.PLACE_NAME + " = ?";

        Cursor cursor = db.rawQuery(MY_QUERY, new String[]{placeName});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(cursor.getColumnIndex(DBConstants.ID_PLACE));
        }

        return id;
    }

    public long updateRow(int id, String name, String description) {
        ContentValues rowUpdate = new ContentValues();
        rowUpdate.put(DBConstants.PLACE_NAME, name);
        rowUpdate.put(DBConstants.PLACE_DESCRIPTION, description);
        return db.update(DBConstants.PLACESTABLE, rowUpdate, "Id=" + String.valueOf(id), null);
    }

    public String getColumnContent(String columnName, long id) {
        String columnContent = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT " + columnName + " FROM " + DBConstants.PLACESTABLE + " WHERE " + DBConstants.ID_PLACE + " = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            columnContent = cursor.getString(cursor.getColumnIndex(columnName));
        }
        return columnContent;

    }
}
