package com.example.joanna.housepharmacyproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateMedActivity extends Toolbar {
    DatabaseMedAdapter dA;


    @BindView(R.id.tvAmountUpdate)
    TextView amountCurrent;

    @BindView(R.id.tvNameUpdate)
    TextView nameCurrent;

    @BindView(R.id.tvDoseUpdate)
    TextView doseCurrent;

    @BindView(R.id.tvPlaceUpdate)
    TextView placeCurrent;

    @BindView(R.id.etNameUpdate)
    EditText nameUpdate;

    @BindView(R.id.etAmountUpdate)
    EditText amountUpdate;

    @BindView(R.id.etPlaceUpdate)
    EditText placeUpdate;

    @BindView(R.id.etDoseUpdate)
    EditText doseUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_med);
        ButterKnife.bind(this);
        setTextView();
        initToolBar("Aktualizuj lek",1);
    }

    @OnClick(R.id.bUpdateMed)
    void Click() {
        dA = new DatabaseMedAdapter(this);
        dA.openDB();
        String name = getNewRowData(nameCurrent, nameUpdate);
        String dose = getNewRowData(doseCurrent, doseUpdate);
        String amount = getNewRowData(amountCurrent, amountUpdate);
        String place = getNewRowData(placeCurrent, placeUpdate);
        int id = getID();
        long didItWork;
        if (id != 0) {
            didItWork = dA.updateRow(id, name,
                    Integer.parseInt(amount),
                    Double.parseDouble(dose.replaceAll(",", ".")),
                    place);
        } else {
            didItWork = 0;
        }
        if (didItWork > 0) {
            Toast.makeText(UpdateMedActivity.this, "Succsess", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(UpdateMedActivity.this, "Fail", Toast.LENGTH_LONG).show();
        }
        ClearEditText();
        dA.closeDB();
        setTextView();

    }


    private int getID() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            return bundle.getInt("Id");
        } else {
            return 0;
        }
    }

    public String getNewRowData(TextView contentCurrent, EditText contentUpdate) {
        String newContent;
        if (contentUpdate.getText().toString().equals("")) {
            return newContent = contentCurrent.getText().toString();
        } else {
            return newContent = contentUpdate.getText().toString();
        }
    }

    private void ClearEditText() {
        nameUpdate.getText().clear();
        amountUpdate.getText().clear();
        doseUpdate.getText().clear();
        placeUpdate.getText().clear();

    }

    public void setTextView() {
        dA = new DatabaseMedAdapter(this);
        int id = getID();
        dA.openDB();
        nameCurrent.setText(dA.getColumnContent(DBConstants.NAME, id));
        placeCurrent.setText(dA.getColumnContent(DBConstants.PLACE, id));
        amountCurrent.setText(dA.getColumnContent(DBConstants.AMOUNT, id));
        doseCurrent.setText(dA.getColumnContent(DBConstants.DOSE, id));
        dA.closeDB();

    }
}
