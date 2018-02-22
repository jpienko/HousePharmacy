package com.example.joanna.housepharmacyproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateActivity extends AppCompatActivity {
    DatabaseAdapter dA;

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
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        SetTextView();
    }

    @OnClick(R.id.bUpdateMed)
    void Click() {
        dA = new DatabaseAdapter(this);
        dA.openDB();
        String name = GetNewRowData(nameCurrent, nameUpdate);
        String dose = GetNewRowData(doseCurrent, doseUpdate);
        String amount = GetNewRowData(amountCurrent, amountUpdate);
        String place = GetNewRowData(placeCurrent, placeUpdate);
        String id = getID();
        long didItWork;
        if (id != null) {
            didItWork = dA.UpdateRow(id, name,
                    Integer.parseInt(amount),
                    Double.parseDouble(dose.replaceAll(",", ".")),
                    place);
        } else {
            didItWork = 0;
        }
        if (didItWork > 0) {
            Toast.makeText(UpdateActivity.this, "Succsess", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(UpdateActivity.this, "Fail", Toast.LENGTH_LONG).show();
        }
        ClearEditText();
        dA.CloseDB();

    }

    private String getID() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            return bundle.getString("Id");
        } else {
            return null;
        }
    }

    public String GetNewRowData(TextView contentCurrent, EditText contentUpdate) {
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

    public void SetTextView() {
        dA = new DatabaseAdapter(this);
        String id = getID();
        nameCurrent.setText(dA.GetColumnContent(DBConstants.NAME, id));
        placeCurrent.setText(dA.GetColumnContent(DBConstants.PLACE, id));
        amountCurrent.setText(dA.GetColumnContent(DBConstants.AMOUNT, id));
        doseCurrent.setText(dA.GetColumnContent(DBConstants.DOSE, id));

    }
}
