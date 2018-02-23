package com.example.joanna.housepharmacyproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Joanna on 2018-02-16.
 */

public class DatabaseAdapter {
    Context context;

    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    public DatabaseAdapter(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    //OPEN DATABASE
    public DatabaseAdapter openDB() {
        try {
            db = dbHelper.getWritableDatabase();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this;
    }

    //CLOSE DATABASE
    public void closeDB() {
        try {
            dbHelper.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //INSERT
    public long addData(String name, int amount, double dose, String place) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(DBConstants.NAME, name);
            cv.put(DBConstants.AMOUNT, amount);
            cv.put(DBConstants.DOSE, dose);
            cv.put(DBConstants.PLACE, place);
            return db.insert(DBConstants.table1Name, null, cv);

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return 0;
    }

    //RETRIEVE
    public Cursor getAllMeds() {
        String[] columns = {DBConstants.ID, DBConstants.NAME, DBConstants.DOSE, DBConstants.AMOUNT, DBConstants.PLACE};

        return db.query(DBConstants.table1Name, columns, null, null, null, null, null);

    }

    public long updateRow(int id, String name, int amount, double dose, String place) {
        ContentValues rowUpdate = new ContentValues();
        rowUpdate.put(DBConstants.NAME, name);
        rowUpdate.put(DBConstants.AMOUNT, amount);
        rowUpdate.put(DBConstants.DOSE, dose);
        rowUpdate.put(DBConstants.PLACE, place);
        return db.update(DBConstants.table1Name, rowUpdate, "Id=" + String.valueOf(id), null);
    }

    public String getColumnContent(String columnName, long id) {
        String columnContent = "";
        Cursor cursor;

        cursor = db.rawQuery("SELECT " + columnName + " FROM " + DBConstants.table1Name + " WHERE " + DBConstants.ID + " = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            columnContent = cursor.getString(cursor.getColumnIndex(columnName));
        }
        return columnContent;

    }

    public Cursor searchDB(String name){
        String[] columns = {DBConstants.ID, DBConstants.NAME, DBConstants.DOSE, DBConstants.AMOUNT, DBConstants.PLACE};

        return db.query(DBConstants.table1Name, columns, DBConstants.NAME +"=?", new String[]{name}, null, null, DBConstants.ID);
    }
}
