package com.example.joanna.housepharmacyproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMedActivity extends AppCompatActivity {


    DatabaseAdapter dA;

    @BindView(R.id.etName)
    EditText name;

    @BindView(R.id.etAmount)
    EditText amount;

    @BindView(R.id.etDose)
    EditText dose;

    @BindView(R.id.etPlace)
    EditText place;
    @OnClick(R.id.bBackAdd)
    void click1() {
        Intent in = new Intent(this, MainActivity.class);
        startActivity(in);
    }
    @OnClick(R.id.bAddMed)
    void Click(){
        dA = new DatabaseAdapter(this);
        dA.openDB();
        if (dose.getText().toString().equals("")){
            dose.setText("0");
        }
        if (place.getText().toString().equals("")){
            place.setText("none");
        }
        if (amount.getText().toString().equals("")){
            amount.setText("0");
        }
        long didItWork = dA.addData(name.getText().toString(),
                Integer.parseInt(amount.getText().toString()),
                Double.parseDouble(dose.getText().toString().replaceAll(",",".")),
                dA.getPlaceId(place.getText().toString()));
        if (didItWork>0) {
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
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
    }

    private void ClearEditText(){
        name.getText().clear();
        amount.getText().clear();
        dose.getText().clear();
        place.getText().clear();

    }

}