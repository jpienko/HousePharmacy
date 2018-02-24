package com.example.joanna.housepharmacyproject;

/**
 * Created by Joanna on 2018-02-19.
 */

public class DBConstants {

    public static final String DBName = "Pharmacy";
    public static final String MEDSTABLE = "Meds";
    public static final String PLACESTABLE = "Places";
    public static final String ID = "Id_Med";
    public static final String NAME = "Name";
    public static final String AMOUNT = "Amount";
    public static final String DOSE = "Dose";
    public static final String PLACE = "Place_Id";
    public static final String ID_PLACE = "Id_Place";
    public static final String PLACE_NAME = "Place_Name";
    public static final int DBVersion = '1';
    static final String CREATE_TB = "CREATE TABLE " + MEDSTABLE + " ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NAME +" TEXT NOT NULL," +
            DOSE+" DOUBLE," +
            AMOUNT+" int," +
            "FOREIGN KEY("+PLACE+") REFERENCES "+PLACESTABLE+"("+ID_PLACE+")\n);";
    static final String CREATE_TB2 = "CREATE TABLE " + PLACESTABLE + " ("+ID_PLACE+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +PLACE_NAME+" TEXT);";


}
