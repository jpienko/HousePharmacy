package com.example.joanna.housepharmacy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private DatabaseMedAdapter dbMed;
    private DatabasePlaceAdapter dbPlace;
    private DatabaseFormAdapter dbForm;
    private DatabaseFormAdapter dAForm;
    private ArrayAdapter<String> adapterForm;
    private ArrayList<String> formList;

    private ArrayAdapter<String> adapterPurpose;
    private ArrayList<String> purposeList;
    private ArrayList<Meds> meds = new ArrayList<>();


    @BindView(R.id.etSearchByName)
    EditText etSearchbyName;

    @BindView(R.id.spFormMain)
    Spinner spFormMain;

    @BindView(R.id.spPurposeMain)
    Spinner spPurposeMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbarInit();
        spinnerForm();
        spinnerPurpose();
    }
    @OnClick(R.id.bSearchMed)
    void searchMed(){

        dbMed = new DatabaseMedAdapter(this);
        dbPlace = new DatabasePlaceAdapter(this);
        dbForm = new DatabaseFormAdapter(this);

        meds.clear();

        getMed(getCursorContent(byWhich()));
        ClearEditText();
        Intent in = new Intent(MainActivity.this, MedDisplayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("MedsList",(Serializable)meds);
        in.putExtras(bundle);
        startActivity(in);

    }

    public Cursor getCursorContent(String [] byWhich){

        dbMed.openDB();
        Cursor c;
       if (Integer.valueOf(byWhich[0])==1){
           c = dbMed.searchMedByOne(byWhich[1],byWhich[2]);
       }else if (Integer.valueOf(byWhich[0])==2) {
           c= dbMed.searchMedByTwo(byWhich[1],byWhich[3],byWhich[2],byWhich[4]);

       }else if (Integer.valueOf(byWhich[0])==3){
           c = dbMed.searchMedByThree(byWhich[1],byWhich[3],byWhich[5],byWhich[2],byWhich[4],byWhich[6]);
       }else{
           c = null;
       }

        return  c;
    }
    private void getMed(Cursor c) {
        if (c!=null) {
            while (c.moveToNext()) {
                int id = c.getInt(0);
                String name = c.getString(1);
                String dose = c.getString(2);
                int formInt = c.getInt(3);
                double amount = c.getDouble(4);
                String purpose = c.getString(5);
                int placeInt = c.getInt(6);
                dbMed.closeDB();

                dbForm.openDB();
                String form = dbForm.getFormName(formInt);
                dbForm.closeDB();

                dbPlace.openDB();
                String place = dbPlace.getPlaceName(placeInt);
                dbPlace.closeDB();

                Meds m = new Meds(id, name, dose, form, amount, purpose, place);
                meds.add(m);
            }
        }
    }

    public String[] byWhich(){

        String [] whichOnes = new String[7];
        int by = 0;
        if (!(etSearchbyName.getText().toString().matches(""))){
            by +=1;
            addVal(whichOnes,etSearchbyName.getText().toString(),DatabaseConstants.NAME);
        }
        if(!(spFormMain.getSelectedItem().toString().matches("-"))){
            by +=1;
            dbForm.openDB();
            addVal(whichOnes,String.valueOf(dbForm.getFromId(spFormMain.getSelectedItem().toString())),DatabaseConstants.FORM);
            dbForm.closeDB();
        }
        if (!(spPurposeMain.getSelectedItem().toString().matches("-"))){
            by +=1;
            addVal(whichOnes,spPurposeMain.getSelectedItem().toString(),DatabaseConstants.PURPOSE);
        }
        whichOnes[0] =String.valueOf(by);
        return whichOnes;
    }

    public String [] addVal(String[] whichOnes,String content, String columnName){
        for (int i  = 1;i<whichOnes.length;i++){
            if(whichOnes[i]==null) {
                whichOnes[i] = content;
                whichOnes[i+1] = columnName;
            }
        }
        return whichOnes;
    }
    private void spinnerForm() {

        dAForm = new DatabaseFormAdapter(this);
        formList = new ArrayList<>();
        adapterForm = new ArrayAdapter<String>(this, R.layout.spinner_item, formList);
        dAForm.openDB();
        formList.add("-");
        Cursor c = dAForm.getAllForms();
        while (c.moveToNext()) {
            String name = c.getString(1);
            formList.add(name);
        }
        dAForm.closeDB();
        spFormMain.setAdapter(adapterForm);
    }

    private void spinnerPurpose() {
        dbMed = new DatabaseMedAdapter(this);
        purposeList = new ArrayList<>();
        adapterPurpose = new ArrayAdapter<String>(this, R.layout.spinner_item, purposeList);
        dbMed.openDB();
        purposeList.add("-");
        Cursor c = dbMed.getPurpose();
        while (c.moveToNext()) {
            String name = c.getString(0);
            purposeList.add(name);
        }
        dbMed.closeDB();
        spPurposeMain.setAdapter(adapterPurpose);
    }

    private void ClearEditText() {
        spPurposeMain.setAdapter(adapterPurpose);
        etSearchbyName.getText().clear();
        spFormMain.setAdapter(adapterForm);
    }
    private void toolbarInit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_med) {
            Intent in = new Intent(MainActivity.this, MedAddActivity.class);
            startActivity(in);
    } else if (id == R.id.nav_add_place) {

        } else if (id == R.id.nav_show_meds) {
            Intent in = new Intent(MainActivity.this, MedDisplayActivity.class);
            startActivity(in);
        } else if (id == R.id.nav_show_places) {

        } else if (id == R.id.nav_help) {
            Intent in = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(in);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
