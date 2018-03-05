package com.example.joanna.housepharmacyproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddPlaceActivity extends Toolbar {

    DatabasePlaceAdapter databasePlaceAdapter;

    @BindView(R.id.etPlaceName)
    EditText name;

    @BindView(R.id.etDescription)
    EditText description;


    @OnClick(R.id.bAddPlace)
    void Click(){
        databasePlaceAdapter = new DatabasePlaceAdapter(this);
        databasePlaceAdapter.openDB();
        if (description.getText().toString().equals("")){
            description.setText("none");
        }

        long didItWork = databasePlaceAdapter.addPlace(name.getText().toString(),
                description.getText().toString());
        if (didItWork>0) {
            Toast.makeText(AddPlaceActivity.this, "Succsess", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(AddPlaceActivity.this, "Fail", Toast.LENGTH_LONG).show();
        }
        ClearEditText();
        databasePlaceAdapter.closeDB();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_add);
        ButterKnife.bind(this);
        initToolBar("Dodaj miejsce", R.string.instruction_add_place);
    }

    private void ClearEditText(){
        name.getText().clear();
        description.getText().clear();
    }
}
