package com.example.joanna.housepharmacy.InterfacesAbstracts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.joanna.housepharmacy.Activities.MainActivity;
import com.example.joanna.housepharmacy.DatabaseAdapters.DatabaseFormAdapter;
import com.example.joanna.housepharmacy.DatabaseAdapters.DatabaseMedAdapter;
import com.example.joanna.housepharmacy.DatabaseAdapters.DatabasePlaceAdapter;
import com.example.joanna.housepharmacy.Medicaments.Med;
import com.example.joanna.housepharmacy.Places.Place;
import com.example.joanna.housepharmacy.R;

import java.util.ArrayList;

/**
 * Created by Joanna on 2018-03-20.
 */

public abstract class Toolbar extends AppCompatActivity {
    public Activity activity;
    public String textContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public void init(Activity activity, int text, String title) {
        this.activity = activity;
        this.textContent = getResources().getString(text);
        setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rest, menu);
        if (activity.getTitle() == getString(R.string.app_name)) {
            menu.findItem(R.id.action_home).setVisible(false);
        } else {
            menu.findItem(R.id.action_home).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_help) {
            helpIconSelected();
        } else if (id == R.id.action_home) {
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);
        }

        return super.onOptionsItemSelected(item);
    }

    private void helpIconSelected() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        View mView = getLayoutInflater().inflate(R.layout.dialog_help, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        TextView tvHelp = (TextView) mView.findViewById(R.id.tvHelp);
        tvHelp.setText(textContent);
        Button bHelp = (Button) mView.findViewById(R.id.bBackHelp);
        bHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    public int getPlaceID(DatabasePlaceAdapter dAPlace, String name) {
        dAPlace.openDB();
        int place = dAPlace.getPlaceId(name);
        dAPlace.closeDB();
        return place;
    }

    public int getFormID(DatabaseFormAdapter dAForm, String name) {
        dAForm.openDB();
        int form = dAForm.getFromId(name);
        dAForm.closeDB();
        return form;
    }

    public String getPlaceName(DatabasePlaceAdapter dAPlace, int id) {
        dAPlace.openDB();
        String place = dAPlace.getPlaceName(id);
        dAPlace.closeDB();
        return place;
    }

    public String getFormName(DatabaseFormAdapter dAForm, int id) {
        dAForm.openDB();
        String form = dAForm.getFormName(id);
        dAForm.closeDB();
        return form;
    }

    public void spinnerForm(DatabaseFormAdapter dAForm, ArrayList<String> formList, ArrayAdapter<String> adapterForm, Spinner spinner) {

        dAForm = new DatabaseFormAdapter(this);
        formList = new ArrayList<>();
        adapterForm = new ArrayAdapter<String>(this, R.layout.spinner_item, formList);
        dAForm.openDB();
        formList.add("-");
        Cursor cursor = dAForm.getAllForms();
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            formList.add(name);
        }
        dAForm.closeDB();
        spinner.setAdapter(adapterForm);

    }

    public void spinnerPlace(DatabasePlaceAdapter dAPlace, ArrayList<String> placeList, ArrayAdapter<String> adapterPlace, Spinner spinner) {
        dAPlace = new DatabasePlaceAdapter(this);
        placeList = new ArrayList<>();
        adapterPlace = new ArrayAdapter<String>(this, R.layout.spinner_item, placeList);
        placeList.add("-");
        dAPlace.openDB();
        Cursor cursor = dAPlace.getAllPlaces();
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            placeList.add(name);
        }
        dAPlace.closeDB();
        spinner.setAdapter(adapterPlace);
    }

    public String getNewRowData(TextView contentCurrent, EditText contentUpdate) {

        if (contentUpdate.getText().toString().equals("")) {
            return contentCurrent.getText().toString();
        } else {
            return contentUpdate.getText().toString();
        }
    }

    public void addMedToList(Cursor cursor, DatabaseMedAdapter dbMed, DatabaseFormAdapter dAForm, DatabasePlaceAdapter dAPlace, ArrayList<Med> meds) {
       if(cursor!=null) {
           while (cursor.moveToNext()) {
               int id = cursor.getInt(0);
               String name = cursor.getString(1);
               String dose = cursor.getString(2);
               int formInt = cursor.getInt(3);
               double amount = cursor.getDouble(4);
               String purpose = cursor.getString(5);
               int placeInt = cursor.getInt(6);
               dbMed.closeDB();

               String form = getFormName(dAForm, formInt);

               String place = getPlaceName(dAPlace, placeInt);

               Med m = new Med(id, name, dose, form, amount, purpose, place);
               meds.add(m);
           }
       }
    }

    public void addToPlaceList(ArrayList<Place> places) {
        DatabasePlaceAdapter db = new DatabasePlaceAdapter(this);
        db.openDB();
        Cursor c = db.getAllPlaces();
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            String desc = c.getString(2);
            Place p = new Place(id, name, desc);
            places.add(p);
        }
        db.closeDB();
    }
    public static boolean isNumeric(String str)
    {
        for (char character : str.toCharArray())
        {
            if (!Character.isDigit(character))
            {
                if (!(String.valueOf(character).matches("."))|| !(String.valueOf(character).matches(","))) return false;
            }}
        return true;
    }

}
