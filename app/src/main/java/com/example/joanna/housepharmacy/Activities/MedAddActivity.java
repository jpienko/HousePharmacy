package com.example.joanna.housepharmacy.Activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.joanna.housepharmacy.DatabaseAdapters.DatabaseFormAdapter;
import com.example.joanna.housepharmacy.DatabaseAdapters.DatabaseMedAdapter;
import com.example.joanna.housepharmacy.DatabaseAdapters.DatabasePlaceAdapter;
import com.example.joanna.housepharmacy.InterfacesAbstracts.Toolbar;
import com.example.joanna.housepharmacy.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MedAddActivity extends Toolbar {

    private DatabaseMedAdapter dbMed;
    private DatabasePlaceAdapter dAPlace;
    private DatabaseFormAdapter dAForm;
    private ArrayAdapter<String> adapterForm;
    private ArrayAdapter<String> adapterPlace;
    private ArrayList<String> formList;
    private ArrayList<String> placeList;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_add);
        ButterKnife.bind(this);
        init(this, R.string.instruction_add, getString(R.string.add_med_title));
        spinnerForm(dAForm, formList, adapterForm, spFormAdd);
        spinnerPlace(dAPlace, placeList, adapterPlace, spPlaceAdd);

    }

    @OnClick(R.id.bAddMed)
    void AddClick() {
        if (name.getText().toString().matches("")) {
            Toast.makeText(MedAddActivity.this, R.string.fill_name_remind, Toast.LENGTH_LONG).show();
        } else if ((isNumeric(amount.getText().toString()))) {
            Toast.makeText(MedAddActivity.this, R.string.amount_int_remind, Toast.LENGTH_LONG).show();
        } else {
            addMed();
        }
        spinnerForm(dAForm, formList, adapterForm, spFormAdd);
        spinnerPlace(dAPlace, placeList, adapterPlace, spPlaceAdd);
    }

    private void addMed() {
        setDatabaseAdapters();
        checkFilling();
        checkIfWorked();
        ClearEditText();

    }

    private void setDatabaseAdapters() {
        dbMed = new DatabaseMedAdapter(this);
        dAPlace = new DatabasePlaceAdapter(this);
        dAForm = new DatabaseFormAdapter(this);
    }

    private void checkFilling() {
        checkIfFilled(purpose, getString(R.string.none));
        checkIfFilled(dose, getString(R.string.none));
        checkIfFilled(amount, "0");
        checkIfFilled(purpose, getString(R.string.none));
    }

    private void checkIfFilled(EditText editText, String value) {
        if (editText.getText().toString().matches("")) {
            editText.setText(value);
        }
    }

    private void checkIfWorked() {
        long didItWork = getDidItWork();
        if (didItWork > 0) {
            Toast.makeText(MedAddActivity.this, R.string.add_med_succsess, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MedAddActivity.this, R.string.add_med_fail, Toast.LENGTH_LONG).show();
        }
    }

    private long getDidItWork() {
        int place = getPlaceID(dAPlace, spinnerCorrection(spPlaceAdd));
        int form = getFormID(dAForm, spinnerCorrection(spFormAdd));
        dbMed.openDB();
        long didItWork = addMedicamentToDatabase(place, form);
        dbMed.closeDB();
        return didItWork;
    }

    private String spinnerCorrection(Spinner spinner) {
        if (spinner.getSelectedItem().toString().matches("-")) {
            return getString(R.string.none);
        } else
            return spinner.getSelectedItem().toString();
    }

    private long addMedicamentToDatabase(int place, int form) {
        return dbMed.addData(name.getText().toString(),
                dose.getText().toString(),
                form,
                Double.parseDouble(amount.getText().toString().replaceAll(",",".")),
                purpose.getText().toString(),
                place);
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
