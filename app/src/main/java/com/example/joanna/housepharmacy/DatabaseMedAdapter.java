package com.example.joanna.housepharmacy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Joanna on 2018-02-16.
 */

public class DatabaseMedAdapter {

    Context context;
    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    DatabaseMedAdapter(Context context) {
        super();
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void openDB() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void closeDB() throws SQLException {

        dbHelper.close();
    }

    public long addData(String name, String dose, int form, double amount, String purpose, int place) {

        try {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseConstants.NAME, name);
            cv.put(DatabaseConstants.DOSE, dose);
            cv.put(DatabaseConstants.FORM, form);
            cv.put(DatabaseConstants.AMOUNT, amount);
            cv.put(DatabaseConstants.PURPOSE, purpose);
            cv.put(DatabaseConstants.PLACE, place);
            return db.insert(DatabaseConstants.MEDSTABLE, null, cv);

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return 0;
    }

    //RETRIEVE
    public Cursor getAllMeds() {
        String[] columns = new String[]{DatabaseConstants.ID, DatabaseConstants.NAME, DatabaseConstants.DOSE,
                DatabaseConstants.FORM, DatabaseConstants.AMOUNT, DatabaseConstants.PURPOSE, DatabaseConstants.PLACE};

        return db.query(DatabaseConstants.MEDSTABLE,columns,null,null,null,null,null);
    }

    public long updateRow(int id, String name, String dose, int form, double amount, String purpose, int place) {
        ContentValues rowUpdate = new ContentValues();
        rowUpdate.put(DatabaseConstants.NAME, name);
        rowUpdate.put(DatabaseConstants.DOSE, dose);
        rowUpdate.put(DatabaseConstants.FORM, form);
        rowUpdate.put(DatabaseConstants.AMOUNT, amount);
        rowUpdate.put(DatabaseConstants.PURPOSE, purpose);
        rowUpdate.put(DatabaseConstants.PLACE, place);
        return db.update(DatabaseConstants.MEDSTABLE, rowUpdate, DatabaseConstants.ID+"=" + String.valueOf(id), null);
    }

    public String getColumnContent(String columnName, long id) {
        String columnContent = "";
        Cursor cursor;

        cursor = db.rawQuery("SELECT " + columnName + " FROM " + DatabaseConstants.MEDSTABLE + " WHERE " + DatabaseConstants.ID + " = ?", new String[]{String.valueOf(id)});
        cursor = db.query(DatabaseConstants.MEDSTABLE,
                new String[]{columnName},
                DatabaseConstants.ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            columnContent = cursor.getString(cursor.getColumnIndex(columnName));
        }
        return columnContent;
    }

    public Cursor getPurpose(){
        return db.query(true, DatabaseConstants.MEDSTABLE, new String[] {DatabaseConstants.PURPOSE }, null, null, DatabaseConstants.PURPOSE, null, null, null);
    }


    public Cursor searchMedByOne(String name, String columnName) {

        String[] columns = new String[]{DatabaseConstants.ID, DatabaseConstants.NAME, DatabaseConstants.DOSE,
                DatabaseConstants.FORM, DatabaseConstants.AMOUNT, DatabaseConstants.PURPOSE, DatabaseConstants.PLACE};
        return db.query(DatabaseConstants.MEDSTABLE,
                columns,
                columnName + "=?"+" COLLATE NOCASE",
                new String[]{name},
                null, null, DatabaseConstants.ID);
    }
    public Cursor searchMedByTwo(String val1, String val2, String columnName1, String columnName2){

        String[] columns = new String[]{DatabaseConstants.ID, DatabaseConstants.NAME, DatabaseConstants.DOSE,
                DatabaseConstants.FORM, DatabaseConstants.AMOUNT, DatabaseConstants.PURPOSE, DatabaseConstants.PLACE};
        return db.query(DatabaseConstants.MEDSTABLE,
                columns,
                columnName1 + "=? COLLATE NOCASE AND " + columnName2 +"=?" +" COLLATE NOCASE",
                new String[]{val1,val2},
                null, null, DatabaseConstants.ID);
    }

    public Cursor searchMedByThree(String val1, String val2, String val3, String columnName1, String columnName2, String columnName3){
        String[] columns = new String[]{DatabaseConstants.ID, DatabaseConstants.NAME, DatabaseConstants.DOSE,
                DatabaseConstants.FORM, DatabaseConstants.AMOUNT, DatabaseConstants.PURPOSE, DatabaseConstants.PLACE};
        return db.query(DatabaseConstants.MEDSTABLE,
                columns,
                columnName1 + "=? COLLATE NOCASE AND " + columnName2 +"=? COLLATE NOCASE AND " + columnName3 +"=? " +" COLLATE NOCASE",
                new String[]{val1,val2,val3},
                null, null, DatabaseConstants.ID);
    }

    public void deleteMed(String id){
        db.delete(DatabaseConstants.MEDSTABLE,DatabaseConstants.ID +"=?", new String[]{id});
    }

    public void deleteMedFromPlace(String placeId){
        db.delete(DatabaseConstants.MEDSTABLE,DatabaseConstants.PLACE +"=?", new String[]{placeId});
    }

    public void renamePlace(String placeId){
        ContentValues rowUpdate = new ContentValues();
        rowUpdate.put(DatabaseConstants.PLACE, String.valueOf(1));
        db.update(DatabaseConstants.MEDSTABLE, rowUpdate, DatabaseConstants.PLACE+"=" + String.valueOf(placeId), null);
    }
}
