package com.example.joanna.housepharmacyproject;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMedActivity extends Toolbar {


    DatabaseMedAdapter dA;
    DatabasePlaceAdapter dAPlace;
    DatabaseFormAdapter dAForm;
    ArrayAdapter<String> adapter;
    ArrayList<String> formList;
    ArrayList<String> placeList;

    @BindView(R.id.etName)
    EditText name;

    @BindView(R.id.etAmount)
    EditText amount;

    @BindView(R.id.etDose)
    EditText dose;

    @BindView(R.id.etPlace)
    EditText place;

    @BindView(R.id.spForm)
    Spinner spForm;

    @OnClick(R.id.bAddMed)
    void Click() {
        dA = new DatabaseMedAdapter(this);
        dAPlace = new DatabasePlaceAdapter(this);

        dA.openDB();
        if (dose.getText().toString().equals("")) {
            dose.setText("0");
        }
        if (place.getText().toString().equals("")) {
            place.setText("none");
        }
        if (amount.getText().toString().equals("")) {
            amount.setText("0");
        }
        long didItWork = dA.addData(name.getText().toString(),
                Integer.parseInt(amount.getText().toString()),
                Double.parseDouble(dose.getText().toString().replaceAll(",", ".")),
                dAPlace.getPlaceId(place.getText().toString()));
        if (didItWork > 0) {
            Toast.makeText(AddMedActivity.this, "Succsess", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(AddMedActivity.this, "Fail", Toast.LENGTH_LONG).show();
        }
        ClearEditText();
        dA.closeDB();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_add);
        ButterKnife.bind(this);
        initToolBar("Dodaj lek", R.string.instruction_add_place);
        spinnerForm();
    }

    private void spinnerForm() {

        dAForm = new DatabaseFormAdapter(this);
        formList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, formList);
        dAForm.openDB();
        Cursor c = dAForm.getAllForms();
        while (c.moveToNext()) {
            String name = c.getString(1);
            formList.add(name);
        }
        dAForm.closeDB();
        spForm.setAdapter(adapter);
    }

    private void ClearEditText() {
        name.getText().clear();
        amount.getText().clear();
        dose.getText().clear();
        place.getText().clear();

    }

}
