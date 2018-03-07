package com.example.joanna.housepharmacyproject;

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

    public DatabaseFormAdapter(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
        try {
            openDB();
        } catch (SQLException e) {
            Log.e("DatabasePlaceAdapter", "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }

    }


    public Cursor getAllForms() {
        String[] columns = {DBConstants.ID_FORM,DBConstants.FORM_NAME};

        return db.query(DBConstants.FORMSTABLE, columns, null, null, null, null, null);
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

}



