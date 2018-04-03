package com.example.joanna.housepharmacy;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Joanna on 2018-03-05.
 */

public class DatabaseFormAdapter {

    ArrayList<String> forms = new ArrayList<String>();
    Context context;
    SQLiteDatabase db;
    DatabaseHelper dbHelper;


    DatabaseFormAdapter(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void openDB() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void closeDB() throws SQLException {

        dbHelper.close();
    }

    public Cursor getAllForms() {
        String[] columns = {DatabaseConstants.ID_FORM, DatabaseConstants.FORM_NAME};

        return db.query(DatabaseConstants.FORMSTABLE, columns, null, null, null, null, null);
    }

    public String getFormName(long id) {
        String formName = "";
        Cursor cursor;
        cursor = db.query(DatabaseConstants.FORMSTABLE,
                new String[]{DatabaseConstants.FORM_NAME},
                DatabaseConstants.ID_FORM + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            formName = cursor.getString(cursor.getColumnIndex(DatabaseConstants.FORM_NAME));
        }
        return formName;
    }

    public int getFromId(String name){
        int formName = 1;
        Cursor cursor;
        cursor = db.query(DatabaseConstants.FORMSTABLE,
                new String[]{DatabaseConstants.ID_FORM},
                DatabaseConstants.FORM_NAME + "=?",
                new String[]{name},
                null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            formName = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.ID_FORM));
        }
        return formName;
    }


}



