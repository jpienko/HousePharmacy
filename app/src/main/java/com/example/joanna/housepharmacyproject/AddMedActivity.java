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
    ArrayAdapter<String> adapterForm;
    ArrayAdapter<String> adapterPlace;
    ArrayList<String> formList;
    ArrayList<String> placeList;

    @BindView(R.id.etName)
    EditText name;

    @BindView(R.id.etAmount)
    EditText amount;

    @BindView(R.id.etDose)
    EditText dose;

    @BindView(R.id.spPlace)
    Spinner spPlace;

    @BindView(R.id.spForm)
    Spinner spForm;

    @BindView(R.id.etPurpose)
    EditText purpose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_add);
        ButterKnife.bind(this);
        initToolBar("Dodaj lek", R.string.instruction_add,MainActivity.class);
        spinnerForm();
        spinnerPlace();
    }

    @OnClick(R.id.bAddMed)
    void Click() {

        if (name.getText().toString().matches("")) {
            Toast.makeText(AddMedActivity.this, "Musisz podać nazwę leku!", Toast.LENGTH_LONG).show();
        } else {
            addMed();
        }
    }

    private void addMed() {
        dA = new DatabaseMedAdapter(this);
        dAPlace = new DatabasePlaceAdapter(this);

        dA.openDB();
        checkIfFilled(purpose, "brak");
        checkIfFilled(dose, "brak");
        checkIfFilled(amount, "0");

        long didItWork = dA.addData(name.getText().toString(),
                Integer.parseInt(amount.getText().toString()),
                dose.getText().toString(),
                formList.toString(),
                purpose.getText().toString(),
                dAPlace.getPlaceId(spPlace.toString()));
        if (didItWork > 0) {
            Toast.makeText(AddMedActivity.this, "Succsess", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(AddMedActivity.this, "Fail", Toast.LENGTH_LONG).show();
        }
        ClearEditText();
        dA.closeDB();
    }

    private void checkIfFilled(EditText editText, String value) {
        if (editText.getText().toString().matches("")) {
            editText.setText(value);
        }
    }

    private void spinnerForm() {

        dAForm = new DatabaseFormAdapter(this);
        formList = new ArrayList<>();
        adapterForm = new ArrayAdapter<String>(this, R.layout.spinner_item, formList);
        dAForm.openDB();
        Cursor c = dAForm.getAllForms();
        while (c.moveToNext()) {
            String name = c.getString(1);
            formList.add(name);
        }
        dAForm.closeDB();
        spForm.setAdapter(adapterForm);
    }

    private void spinnerPlace() {
        dAPlace = new DatabasePlaceAdapter(this);
        placeList = new ArrayList<>();
        adapterPlace = new ArrayAdapter<String>(this, R.layout.spinner_item, placeList);
        dAPlace.openDB();
        Cursor c = dAPlace.getAllPlaces();
        while (c.moveToNext()) {
            String name = c.getString(1);
            placeList.add(name);
        }
        dAPlace.closeDB();
        spPlace.setAdapter(adapterPlace);
    }
    private void ClearEditText() {
        name.getText().clear();
        amount.getText().clear();
        dose.getText().clear();
        spPlace.setAdapter(adapterPlace);
        spForm.setAdapter(adapterForm);
        purpose.getText().clear();


    }

}
