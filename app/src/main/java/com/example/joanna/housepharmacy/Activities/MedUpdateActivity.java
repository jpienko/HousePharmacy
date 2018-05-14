package com.example.joanna.housepharmacy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joanna.housepharmacy.DatabaseAdapters.DatabaseFormAdapter;
import com.example.joanna.housepharmacy.DatabaseAdapters.DatabaseMedAdapter;
import com.example.joanna.housepharmacy.DatabaseAdapters.DatabasePlaceAdapter;
import com.example.joanna.housepharmacy.DatabaseImplementation.DatabaseConstants;
import com.example.joanna.housepharmacy.InterfacesAbstracts.Toolbar;
import com.example.joanna.housepharmacy.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MedUpdateActivity extends Toolbar {

    DatabasePlaceAdapter dAPlace;
    ArrayList<String> placeList;

    @BindView(R.id.tvNameUpdate)
    TextView nameCurrent;
    @BindView(R.id.tvDoseUpdate)
    TextView doseCurrent;
    @BindView(R.id.tvFormUpdate)
    TextView formCurrent;
    @BindView(R.id.tvAmountUpdate)
    TextView amountCurrent;
    @BindView(R.id.tvPurposeUpdate)
    TextView purposeCurrent;
    @BindView(R.id.tvPlaceUpdate)
    TextView placeCurrent;
    @BindView(R.id.etNameUpdate)
    EditText nameUpdate;
    @BindView(R.id.etDoseUpdate)
    EditText doseUpdate;
    @BindView(R.id.spFormUpdate)
    Spinner spFormUpdate;
    @BindView(R.id.etAmountUpdate)
    EditText amountUpdate;
    @BindView(R.id.etPurposeUpdate)
    EditText purposeUpdate;
    @BindView(R.id.spPlaceUpdate)
    Spinner spPlaceUpdate;

    private DatabaseMedAdapter dAMed;
    private DatabaseFormAdapter dAForm;
    private ArrayAdapter<String> adapterForm;
    private ArrayAdapter<String> adapterPlace;
    private ArrayList<String> formList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_update);
        init(MedUpdateActivity.this, R.string.instruction_update, getString(R.string.update_med_title));
        ButterKnife.bind(this);
        spinnerForm(dAForm, formList, adapterForm, spFormUpdate);
        spinnerPlace(dAPlace, placeList, adapterPlace, spPlaceUpdate);
        setAllTextView();
    }

    @OnClick(R.id.bUpdateMed)
    void updateMed() {
        if (isDouble(amountUpdate)) {
            Toast.makeText(MedUpdateActivity.this, R.string.amount_int_remind, Toast.LENGTH_LONG).show();
        } else {
            getDatabaseAdapter();
            updateInDatabase();
            setAllTextView();
            spinnerForm(dAForm, formList, adapterForm, spFormUpdate);
            spinnerPlace(dAPlace, placeList, adapterPlace, spPlaceUpdate);
        }
    }

    private void getDatabaseAdapter() {
        dAMed = new DatabaseMedAdapter(this);
        dAForm = new DatabaseFormAdapter(this);
        dAPlace = new DatabasePlaceAdapter(this);
    }

    public void setAllTextView() {
        getDatabaseAdapter();
        setTextView();
    }

    private void setTextView() {
        int id = getID();
        dAMed.openDB();
        nameCurrent.setText(dAMed.getColumnContent(DatabaseConstants.NAME, id));
        doseCurrent.setText(dAMed.getColumnContent(DatabaseConstants.DOSE, id));
        amountCurrent.setText(dAMed.getColumnContent(DatabaseConstants.AMOUNT, id));
        purposeCurrent.setText(dAMed.getColumnContent(DatabaseConstants.PURPOSE, id));
        int form = Integer.valueOf(dAMed.getColumnContent(DatabaseConstants.FORM, id));
        int place = Integer.valueOf(dAMed.getColumnContent(DatabaseConstants.PLACE, id));
        dAMed.closeDB();

        dAForm.openDB();
        formCurrent.setText(dAForm.getFormName(form));
        dAForm.closeDB();

        dAPlace.openDB();
        placeCurrent.setText(dAPlace.getPlaceName(place));
        dAPlace.closeDB();
    }

    private int getID() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            return bundle.getInt("Id");
        } else {
            return 0;
        }
    }

    private void updateInDatabase() {
        int form = getFormID(dAForm, getNewRowIdData(formCurrent, spFormUpdate));
        int place = getPlaceID(dAPlace, getNewRowIdData(placeCurrent, spPlaceUpdate));
        dAMed.openDB();
        String name = getNewRowData(nameCurrent, nameUpdate);
        String dose = getNewRowData(doseCurrent, doseUpdate);
        String amount = getNewRowData(amountCurrent, amountUpdate);
        String purpose = getNewRowData(purposeCurrent, purposeUpdate);

        int id = getID();
        checkIfUpdateWorked(form, place, name, dose, amount, purpose, id);
        clear();
        dAMed.closeDB();
    }

    public String getNewRowIdData(TextView contentCurrent, Spinner contentUpdate) {
        if (contentUpdate.getSelectedItem().toString().matches("-")) {
            return contentCurrent.getText().toString();
        } else {
            return contentUpdate.getSelectedItem().toString();
        }
    }

    private void checkIfUpdateWorked(int form, int place, String name, String dose, String amount, String purpose, int id) {
        long didItWork;
        if (id != 0) {
            didItWork = getDidItWork(form, place, name, dose, amount, purpose, id);
        } else {
            didItWork = 0;
        }
        if (didItWork > 0) {
            Toast.makeText(MedUpdateActivity.this, R.string.update_med_succsess, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MedUpdateActivity.this, R.string.update_med_fail, Toast.LENGTH_LONG).show();
        }
    }

    private long getDidItWork(int form, int place, String name, String dose, String amount, String purpose, int id) {
        long didItWork;
        didItWork = dAMed.updateRow(id, name,
                dose,
                form,
                Double.parseDouble(amount),
                purpose,
                place);
        return didItWork;
    }

    public void clear() {
        nameUpdate.getText().clear();
        doseUpdate.getText().clear();
        spFormUpdate.setAdapter(adapterForm);
        amountUpdate.getText().clear();
        purposeUpdate.getText().clear();
        spPlaceUpdate.setAdapter(adapterPlace);
    }

    @OnClick(R.id.bDeleteMedUpdate)
    void deleteMedUpdate() {
        delete();
    }

    public void delete() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        View mView = getLayoutInflater().inflate(R.layout.dialog_delete, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        buttonDoNotDelete(mView, dialog);
        buttonDeleteChosen(mView);
        dialog.show();
    }

    private void buttonDeleteChosen(View mView) {
        Button bYes = (Button) mView.findViewById(R.id.bDeleteContent);
        bYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dAMed = new DatabaseMedAdapter(getBaseContext());
                dAMed.openDB();
                dAMed.deleteMed(String.valueOf(getID()));
                dAMed.closeDB();
                Intent intent = new Intent(MedUpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void buttonDoNotDelete(View mView, AlertDialog dialog) {
        Button bNo = (Button) mView.findViewById(R.id.bDoNotDelete);
        bNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
    }
}
