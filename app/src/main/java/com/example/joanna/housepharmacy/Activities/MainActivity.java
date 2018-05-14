package com.example.joanna.housepharmacy.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.example.joanna.housepharmacy.DatabaseAdapters.DatabaseFormAdapter;
import com.example.joanna.housepharmacy.DatabaseAdapters.DatabaseMedAdapter;
import com.example.joanna.housepharmacy.DatabaseAdapters.DatabasePlaceAdapter;
import com.example.joanna.housepharmacy.DatabaseImplementation.DatabaseConstants;
import com.example.joanna.housepharmacy.Medicaments.Med;
import com.example.joanna.housepharmacy.R;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends com.example.joanna.housepharmacy.InterfacesAbstracts.Toolbar
        implements NavigationView.OnNavigationItemSelectedListener {


    private DatabaseMedAdapter dAMed;
    private DatabasePlaceAdapter dAPlace;
    private DatabaseFormAdapter dAForm;
    private ArrayAdapter<String> adapterForm;
    private ArrayList<String> formList;
    private ArrayList<String> medNames;
    private ArrayAdapter<String> adapterPurpose;
    private ArrayList<String> purposeList;
    private ArrayList<Med> meds = new ArrayList<>();
    private ArrayAdapter<String> adapterMedName;

    @BindView(R.id.etSearchByName)
    AutoCompleteTextView etSearchbyName;

    @BindView(R.id.spFormMain)
    Spinner spFormMain;

    @BindView(R.id.spPurposeMain)
    Spinner spPurposeMain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbarInit();
        init(this, R.string.instruction_search, getString(R.string.app_name));
        spinnerForm(dAForm, formList, adapterForm, spFormMain);
        spinnerPurpose();
        autoCompleteName();

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
        int id = item.getItemId();

        if (id == R.id.nav_add_med) {
            Intent in = new Intent(MainActivity.this, MedAddActivity.class);
            startActivity(in);
        } else if (id == R.id.nav_add_place) {
            Intent in = new Intent(MainActivity.this, PlaceAddActivity.class);
            startActivity(in);
        } else if (id == R.id.nav_show_meds) {
            Intent in = new Intent(MainActivity.this, MedDisplayActivity.class);
            startActivity(in);
        } else if (id == R.id.nav_show_places) {
            Intent in = new Intent(MainActivity.this, PlaceDisplayActivity.class);
            startActivity(in);

        } else if (id == R.id.nav_help) {
            Intent in = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(in);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        autoCompleteName();
        spinnerForm(dAForm, formList, adapterForm, spFormMain);
        spinnerPurpose();
    }

    private void autoCompleteName() {
        getListOfMedicamentsNames();
        adapterMedName = new ArrayAdapter<String>
                (this, R.layout.spinner_item, medNames);
        etSearchbyName.setThreshold(1);
        etSearchbyName.setAdapter(adapterMedName);
        etSearchbyName.setTextColor(Color.BLACK);
    }

    private void getListOfMedicamentsNames() {
        dAMed = new DatabaseMedAdapter(this);
        medNames = new ArrayList<>();
        medNames.clear();
        addNameToList();
    }

    private void addNameToList() {
        dAMed.openDB();
        Cursor cursor = dAMed.getNames();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(0);
                medNames.add(name);
            }
        }
        dAMed.closeDB();
    }

    public void spinnerPurpose() {
        dAMed = new DatabaseMedAdapter(this);
        purposeList = new ArrayList<>();
        adapterPurpose = new ArrayAdapter<String>(this, R.layout.spinner_item, purposeList);
        dAMed.openDB();
        purposeList.add("-");
        Cursor c = dAMed.getPurpose();
        while (c.moveToNext()) {
            String name = c.getString(0);
            purposeList.add(name);
        }
        dAMed.closeDB();
        spPurposeMain.setAdapter(adapterPurpose);
    }

    @OnClick(R.id.bSearchMed)
    void searchMed() {
        setAdapters();
        meds.clear();
        getMedicamentsList(getCursorContent(byWhich()));
        ClearEditText();
        goToFoundMedsList();

    }

    private void setAdapters() {
        dAMed = new DatabaseMedAdapter(this);
        dAPlace = new DatabasePlaceAdapter(this);
        dAForm = new DatabaseFormAdapter(this);
    }

    public String[] byWhich() {

        String[] whichOnes = new String[7];
        int by = 0;
        if (!(etSearchbyName.getText().toString().matches(""))) {
            by += 1;
            addVal(whichOnes, etSearchbyName.getText().toString(), DatabaseConstants.NAME);
        }
        if (!(spFormMain.getSelectedItem().toString().matches("-"))) {
            by += 1;
            dAForm.openDB();
            addVal(whichOnes, String.valueOf(dAForm.getFromId(spFormMain.getSelectedItem().toString())), DatabaseConstants.FORM);
            dAForm.closeDB();
        }
        if (!(spPurposeMain.getSelectedItem().toString().matches("-"))) {
            by += 1;
            addVal(whichOnes, spPurposeMain.getSelectedItem().toString(), DatabaseConstants.PURPOSE);
        }
        whichOnes[0] = String.valueOf(by);
        return whichOnes;
    }

    public String[] addVal(String[] whichOnes, String content, String columnName) {
        for (int i = 1; i < whichOnes.length; i++) {
            if (whichOnes[i] == null) {
                whichOnes[i] = content;
                whichOnes[i + 1] = columnName;
                break;
            }
        }
        return whichOnes;
    }

    public Cursor getCursorContent(String[] byWhich) {

        dAMed.openDB();
        Cursor cursor;
        if (Integer.valueOf(byWhich[0]) == 1) {
            cursor = dAMed.searchMedByOne(byWhich[1], byWhich[2]);
        } else if (Integer.valueOf(byWhich[0]) == 2) {
            cursor = dAMed.searchMedByTwo(byWhich[1], byWhich[3], byWhich[2], byWhich[4]);
        } else if (Integer.valueOf(byWhich[0]) == 3) {
            cursor = dAMed.searchMedByThree(byWhich[1], byWhich[3], byWhich[5], byWhich[2], byWhich[4], byWhich[6]);
        } else {
            cursor = null;
        }
        return cursor;
    }

    private void getMedicamentsList(Cursor cursor) {
        addMedToList(cursor, dAMed, dAForm, dAPlace, meds);
    }

    private void ClearEditText() {
        spPurposeMain.setAdapter(adapterPurpose);
        etSearchbyName.getText().clear();
        spFormMain.setAdapter(adapterForm);
    }

    private void goToFoundMedsList() {
        Intent in = new Intent(MainActivity.this, MedDisplayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("MedsList", (Serializable) meds);
        in.putExtras(bundle);
        startActivity(in);
    }

}
