package com.example.joanna.housepharmacy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Joanna on 2018-02-26.
 */

public class DatabasePlaceAdapter {
    Context context;
    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    DatabasePlaceAdapter(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void openDB() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void closeDB() throws SQLException {

        dbHelper.close();
    }

    public long addPlace(String place, String description) {
        try {
            dbHelper.onCreate(db);
            ContentValues cv = new ContentValues();
            cv.put(DatabaseConstants.PLACE_NAME, place);
            cv.put(DatabaseConstants.PLACE_DESCRIPTION, description);
            return db.insert(DatabaseConstants.PLACESTABLE, null, cv);
        } catch (Exception exp) {
            exp.printStackTrace();
            return 0;
        }
    }

    public Cursor getAllPlaces() {
        String[] columns = {DatabaseConstants.ID_PLACE, DatabaseConstants.PLACE_NAME, DatabaseConstants.PLACE_DESCRIPTION};

        return db.query(DatabaseConstants.PLACESTABLE, columns, null, null, null, null, null);
    }


    public int getPlaceId(String placeName) {
        int id = 0;
        Cursor cursor;
        cursor = db.query(DatabaseConstants.PLACESTABLE,
                new String[]{DatabaseConstants.ID_PLACE},
                DatabaseConstants.PLACE_NAME + "=?",
                new String[]{placeName},
                null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.ID_PLACE));
        }

        return id;
    }

    public String getPlaceName(long id) {
        String placeName = "";
        Cursor cursor;
        cursor = db.query(DatabaseConstants.PLACESTABLE,
                new String[]{DatabaseConstants.PLACE_NAME},
                DatabaseConstants.ID_PLACE + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            placeName = cursor.getString(cursor.getColumnIndex(DatabaseConstants.PLACE_NAME));
        }
        return placeName;
    }

    public long updateRow(int id, String name, String description) {
        ContentValues rowUpdate = new ContentValues();
        rowUpdate.put(DatabaseConstants.PLACE_NAME, name);
        rowUpdate.put(DatabaseConstants.PLACE_DESCRIPTION, description);
        return db.update(DatabaseConstants.PLACESTABLE, rowUpdate, DatabaseConstants.ID_PLACE+"=" + String.valueOf(id), null);
    }

    public String getColumnContent(String columnName, long id) {
        String columnContent = "";
        Cursor cursor;
        cursor = db.query(DatabaseConstants.PLACESTABLE,
                new String[]{columnName},
                DatabaseConstants.ID_PLACE + "=?", new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            columnContent = cursor.getString(cursor.getColumnIndex(columnName));
        }
        return columnContent;

    }
    public void deleteOnlyPlace(String id){
        db.delete(DatabaseConstants.PLACESTABLE,DatabaseConstants.ID_PLACE +"=?", new String[]{id});
    }


}
