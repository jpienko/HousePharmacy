package com.example.joanna.housepharmacyproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Joanna on 2018-02-16.
 */

public class DatabaseMedAdapter {

    private Context context;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    DatabaseMedAdapter(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public DatabaseMedAdapter openDB() {
        try {
            db = dbHelper.getWritableDatabase();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void closeDB() {
        try {
            dbHelper.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //INSERT
    public long addData(String name, int amount, String dose, String form, String purpose, int place) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(DBConstants.NAME, name);
            cv.put(DBConstants.AMOUNT, amount);
            cv.put(DBConstants.DOSE, dose);
            cv.put(DBConstants.FORM,form);
            cv.put(DBConstants.PURPOSE,purpose);
            cv.put(DBConstants.PLACE, place);
            return db.insert(DBConstants.MEDSTABLE, null, cv);

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return 0;
    }

    //RETRIEVE
    public Cursor getAllMeds() {
        final String MY_QUERY = "SELECT med." + DBConstants.ID + ", med." + DBConstants.NAME + ", med." + DBConstants.AMOUNT + ", med." + DBConstants.DOSE + ", pl." + DBConstants.PLACE_NAME +
                " FROM " + DBConstants.MEDSTABLE + " med " +
                "INNER JOIN " + DBConstants.PLACESTABLE + " pl ON med." + DBConstants.PLACE + "=pl." + DBConstants.ID_PLACE;

        return db.rawQuery(MY_QUERY, null);

    }

    public long updateRow(int id, String name, int amount, String dose, String place) {
        ContentValues rowUpdate = new ContentValues();
        rowUpdate.put(DBConstants.NAME, name);
        rowUpdate.put(DBConstants.AMOUNT, amount);
        rowUpdate.put(DBConstants.DOSE, dose);
        rowUpdate.put(DBConstants.PLACE, place);
        return db.update(DBConstants.MEDSTABLE, rowUpdate, "Id=" + String.valueOf(id), null);
    }

    public String getColumnContent(String columnName, long id) {
        String columnContent = "";
        Cursor cursor;

        cursor = db.rawQuery("SELECT " + columnName + " FROM " + DBConstants.MEDSTABLE + " WHERE " + DBConstants.ID + " = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            columnContent = cursor.getString(cursor.getColumnIndex(columnName));
        }
        return columnContent;

    }

    public Cursor searchDB(String name) {
        String[] columns = {DBConstants.ID, DBConstants.NAME, DBConstants.DOSE, DBConstants.AMOUNT, DBConstants.PLACE};

        return db.query(DBConstants.MEDSTABLE, columns, DBConstants.NAME + "=?", new String[]{name}, null, null, DBConstants.ID);
    }


}
