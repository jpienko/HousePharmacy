package com.example.joanna.housepharmacy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MedUpdateActivity extends Toolbar {

    private DatabaseMedAdapter dbMed;
    DatabasePlaceAdapter dAPlace;
    private DatabaseFormAdapter dAForm;
    private ArrayAdapter<String> adapterForm;
    private ArrayAdapter<String> adapterPlace;
    private ArrayList<String> formList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_update);
        init(MedUpdateActivity.this, R.string.instruction_update, "Zaktualizuj lek");
        ButterKnife.bind(this);
        spinnerForm();
        spinnerPlace();
        setAllTextView();

    }

    @OnClick(R.id.bUpdateMed)
    void updateMed() {
        dbMed = new DatabaseMedAdapter(this);
        dAForm = new DatabaseFormAdapter(this);
        dAPlace = new DatabasePlaceAdapter(this);

        dAForm.openDB();
        int form = dAForm.getFromId(getNewRowIdData(formCurrent,spFormUpdate));
        dAForm.closeDB();

        dAPlace.openDB();
        int place = dAPlace.getPlaceId(getNewRowIdData(placeCurrent,spPlaceUpdate));
        dAPlace.closeDB();

        dbMed.openDB();
        String name = getNewRowData(nameCurrent, nameUpdate);
        String dose = getNewRowData(doseCurrent, doseUpdate);
        String amount = getNewRowData(amountCurrent, amountUpdate);
        String purpose = getNewRowData(purposeCurrent,purposeUpdate);
        int id = getID();
        long didItWork;
        if (id != 0) {
            didItWork = dbMed.updateRow(id, name,
                    dose,
                    form,
                    Double.parseDouble(amount),
                    purpose,
                    place);
        } else {
            didItWork = 0;
        }
        if (didItWork > 0) {
            Toast.makeText(MedUpdateActivity.this, "Pomyślnie zaktualizowano lek", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MedUpdateActivity.this, "Błąd podczas aktualizacji", Toast.LENGTH_LONG).show();
        }
        clear();
        dbMed.closeDB();
        setAllTextView();

    }

    @OnClick(R.id.bDeleteMedUpdate)
    void deleteMedUpdate(){

       delete();
    }

    @OnClick(R.id.bBackUpdateMed)
    void backWithoutChange(){
        Intent intent = new Intent(MedUpdateActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void setAllTextView() {

        dbMed = new DatabaseMedAdapter(this);
        dAForm = new DatabaseFormAdapter(this);
        dAPlace = new DatabasePlaceAdapter(this);

        int id = getID();
        dbMed.openDB();
        nameCurrent.setText(dbMed.getColumnContent(DatabaseConstants.NAME, id));
        doseCurrent.setText(dbMed.getColumnContent(DatabaseConstants.DOSE, id));
        amountCurrent.setText(dbMed.getColumnContent(DatabaseConstants.AMOUNT, id));
        purposeCurrent.setText(dbMed.getColumnContent(DatabaseConstants.PURPOSE, id));
        int form = Integer.valueOf(dbMed.getColumnContent(DatabaseConstants.FORM, id));
        int place = Integer.valueOf(dbMed.getColumnContent(DatabaseConstants.PLACE, id));
        dbMed.closeDB();

        dAForm.openDB();
        formCurrent.setText(dAForm.getFormName(form));
        dAForm.closeDB();

        dAPlace.openDB();
        placeCurrent.setText(dAPlace.getPlaceName(place));
        dAPlace.closeDB();
    }

    public String getNewRowData(TextView contentCurrent, EditText contentUpdate) {
        String newContent;
        if (contentUpdate.getText().toString().equals("")) {
            return newContent = contentCurrent.getText().toString();
        } else {
            return newContent = contentUpdate.getText().toString();
        }
    }

    public String getNewRowIdData(TextView contentCurrent, Spinner contentUpdate) {
        if (contentUpdate.getSelectedItem().toString().matches("-")) {
            return contentCurrent.getText().toString();
        } else {
            return contentUpdate.getSelectedItem().toString();
        }
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
        spFormUpdate.setAdapter(adapterForm);
    }

    private void spinnerPlace() {
        dAPlace = new DatabasePlaceAdapter(this);
        placeList = new ArrayList<>();
        adapterPlace = new ArrayAdapter<String>(this, R.layout.spinner_item, placeList);
        placeList.add("-");
        dAPlace.openDB();
        Cursor c = dAPlace.getAllPlaces();
        while (c.moveToNext()) {
            String name = c.getString(1);
            placeList.add(name);
        }
        dAPlace.closeDB();
        spPlaceUpdate.setAdapter(adapterPlace);
    }

    private int getID() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            return bundle.getInt("Id");
        } else {
            return 0;
        }
    }

    public void clear() {
        nameUpdate.getText().clear();
        doseUpdate.getText().clear();
        spFormUpdate.setAdapter(adapterForm);
        amountUpdate.getText().clear();
        purposeUpdate.getText().clear();
        spPlaceUpdate.setAdapter(adapterPlace);
    }

    public void delete(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        View mView = getLayoutInflater().inflate(R.layout.dialog_delete, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        Button bNo = (Button) mView.findViewById(R.id.bDoNotDelete);
        bNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

        Button bYes = (Button) mView.findViewById(R.id.bDeleteContent);
        bYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbMed = new DatabaseMedAdapter(getBaseContext());
                dbMed.openDB();
                dbMed.deleteMed(String.valueOf(getID()));
                dbMed.closeDB();
                Intent intent = new Intent(MedUpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }
}
