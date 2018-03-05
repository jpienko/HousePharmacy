package com.example.joanna.housepharmacyproject;

/**
 * Created by Joanna on 2018-02-19.
 */

public class DBConstants {

    public static final String DBName = "Pharmacy.db";
    public static final int DBVersion = 3;

    public static final String MEDSTABLE = "Meds";
    public static final String ID = "Id_Med";
    public static final String NAME = "Name";
    public static final String DOSE = "Dose";
    public static final String FORM = "Form_Id";
    public static final String AMOUNT = "Amount";
    public static final String PURPOSE = "Purpose";
    public static final String PLACE = "Place_Id";

    public static final String PLACESTABLE = "Places";
    public static final String ID_PLACE = "Id_Place";
    public static final String PLACE_NAME = "Place_Name";
    public static final String PLACE_DESCRIPTION = "Description";

    public static final String FORMSTABLE = "Forms";
    public static final String ID_FORM = "Id_Form";
    public static final String FORM_NAME = "Form_Name";

    static final String CREATE_TABLE_MEDS = "CREATE TABLE " + MEDSTABLE + "("
            +ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NAME +" TEXT NOT NULL,"
            + DOSE +" VARCHAR(10),"
            + FORM +" INTEGER,"
            + AMOUNT +" DOUBLE,"
            + PURPOSE +" TEXT,"
            + PLACE + " INTEGER"
            +");";

    static final String CREATE_TABLE_PLACES = "CREATE TABLE " + PLACESTABLE + "("
            +ID_PLACE+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +PLACE_NAME+" TEXT NOT NULL, "
            +PLACE_DESCRIPTION  +" TEXT);";

    static final String CREATE_TABLE_FORMS = "CREATE TABLE " + FORMSTABLE + "("
            +ID_FORM+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +FORM_NAME+" TEXT NOT NULL);";

}
