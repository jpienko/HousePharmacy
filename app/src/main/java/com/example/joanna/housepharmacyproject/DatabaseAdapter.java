package com.example.joanna.housepharmacyproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Joanna on 2018-02-16.
 */

public class DatabaseAdapter{
        Context context;

        SQLiteDatabase db;
        DatabaseHelper dbHelper;
    public DatabaseAdapter(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }
    //OPEN DATABASE
    public DatabaseAdapter openDB()
    {
        try {
            db=dbHelper.getWritableDatabase();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return this;
    }

    //CLOSE DATABASE
    public void CloseDB()
    {
        try {
            dbHelper.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    //INSERT
    public long addData(String name, int amount, double dose, String place) {
        try
        {
        ContentValues cv = new ContentValues();
        cv.put(DBConstants.NAME, name);
        cv.put(DBConstants.AMOUNT, amount);
        cv.put(DBConstants.DOSE, dose);
        cv.put(DBConstants.PLACE, place);
        return db.insert(DBConstants.table1Name,null,cv);

        }catch (SQLException e)
        {
            e.printStackTrace();

        }
        return 0;
    }

    //RETRIEVE
    public Cursor GetAllMeds()
    {
        String[] columns={DBConstants.ID,DBConstants.NAME,DBConstants.DOSE,DBConstants.AMOUNT,DBConstants.PLACE};

        return db.query(DBConstants.table1Name,columns,null,null,null,null,null);

    }

    public long UpdateRow(String id, String name,int amount, double dose, String place){
        ContentValues rowUpdate=new ContentValues();
        rowUpdate.put(DBConstants.NAME, name);
        rowUpdate.put(DBConstants.AMOUNT, amount);
        rowUpdate.put(DBConstants.DOSE, dose);
        rowUpdate.put(DBConstants.PLACE, place);
        return db.update(DBConstants.table1Name, rowUpdate, "Id=" + id, null);
    }


}
