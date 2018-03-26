package com.example.joanna.housepharmacy;

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

public class MedAddActivity extends Toolbar {
    private DatabaseMedAdapter dbMed;
    DatabasePlaceAdapter dAPlace;
    private DatabaseFormAdapter dAForm;
    private ArrayAdapter<String> adapterForm;
    private ArrayAdapter<String> adapterPlace;
    private ArrayList<String> formList;
    ArrayList<String> placeList;

    @BindView(R.id.etNameAdd)
    EditText name;

    @BindView(R.id.etAmountAdd)
    EditText amount;

    @BindView(R.id.etDoseAdd)
    EditText dose;

    @BindView(R.id.etPurposeAdd)
    EditText purpose;

    @BindView(R.id.spFormAdd)
    Spinner spFormAdd;

    @BindView(R.id.spPlaceAdd)
    Spinner spPlaceAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_add);
        ButterKnife.bind(this);
        init(this,R.string.instruction_add,"Dodaj lek");
        spinnerForm();
        spinnerPlace();

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
        spFormAdd.setAdapter(adapterForm);
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
        spPlaceAdd.setAdapter(adapterPlace);
    }

    @OnClick(R.id.bAddMed)
    void Click() {

        if (name.getText().toString().matches("")) {
            Toast.makeText(MedAddActivity.this, "Musisz podać nazwę leku!", Toast.LENGTH_LONG).show();
        } else {
            addMed();
        }
    }

    private void addMed() {
        dbMed = new DatabaseMedAdapter(this);
        dAPlace = new DatabasePlaceAdapter(this);
        dAForm = new DatabaseFormAdapter(this);

        checkIfFilled(purpose, "brak");
        checkIfFilled(dose, "brak");
        checkIfFilled(amount, "0");
        checkIfFilled(purpose,"brak");

        dAPlace.openDB();
        int place = dAPlace.getPlaceId(spPlaceAdd.getSelectedItem().toString());
        dAPlace.closeDB();

        dAForm.openDB();
        int form = dAForm.getFromId(spFormAdd.getSelectedItem().toString());
        dAForm.closeDB();

        dbMed.openDB();
        long didItWork = dbMed.addData(name.getText().toString(),
                dose.getText().toString(),
                form,
                Double.parseDouble(amount.getText().toString()),
                purpose.getText().toString(),
                place);
        dbMed.closeDB();

        if (didItWork > 0) {
            Toast.makeText(MedAddActivity.this, "Pomyślnie dodano lek", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MedAddActivity.this, "Błąd podczas dodawania", Toast.LENGTH_LONG).show();
        }
        ClearEditText();

    }

    private void checkIfFilled(EditText editText, String value) {
        if (editText.getText().toString().matches("")) {
            editText.setText(value);
        }
    }
    private void ClearEditText() {
        name.getText().clear();
        amount.getText().clear();
        dose.getText().clear();
        spPlaceAdd.setAdapter(adapterPlace);
        spFormAdd.setAdapter(adapterForm);
        purpose.getText().clear();
    }
}
