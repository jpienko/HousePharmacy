package com.example.joanna.housepharmacyproject;

/**
 * Created by Joanna on 2018-02-19.
 */

public class DBConstants {

    public static final String DBName = "Pharmacy";
    public static final String table1Name = "Meds";
    public static final String ID = "Id";
    public static final String NAME = "Name";
    public static final String AMOUNT =  "Amount";
    public static final String DOSE = "Dose";
    public static final String PLACE = "Place";
    public static final int DBVersion = '1';
    static final String CREATE_TB="CREATE TABLE "+table1Name+" (Id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "Name TEXT NOT NULL," +
            "Dose DOUBLE," +
            "Amount int," +
            "Place TEXT);";


}
