package com.example.joanna.housepharmacy.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joanna.housepharmacy.DatabaseAdapters.DatabasePlaceAdapter;
import com.example.joanna.housepharmacy.R;
import com.example.joanna.housepharmacy.InterfacesAbstracts.Toolbar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlaceAddActivity extends Toolbar {


    DatabasePlaceAdapter dAPlace;

    @BindView(R.id.etPlaceName)
    EditText name;

    @BindView(R.id.etDescription)
    EditText description;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_add);
        init(this, R.string.instruction_add_place, getString(R.string.add_place_title));
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bAddPlace)
    void Click() {
        if (name.getText().toString().matches("")) {
            Toast.makeText(PlaceAddActivity.this, R.string.add_name_remind, Toast.LENGTH_LONG).show();
        } else if (checkIfAlreadyAdded()){
            addPlace();
        } else{
            Toast.makeText(PlaceAddActivity.this, R.string.place_exists_remind, Toast.LENGTH_LONG).show();
        }
    }

    public void addPlace() {
        getContent();
        didAddWorked();
        ClearEditText();
    }

    private void getContent() {
        dAPlace = new DatabasePlaceAdapter(this);
        dAPlace.openDB();
        if (description.getText().toString().matches("")) {
            description.setText(R.string.none);
        }
    }

    private void didAddWorked() {
        long didItWork = dAPlace.addPlace(name.getText().toString(),
                description.getText().toString());
        dAPlace.closeDB();
        if (didItWork > 0) {
            Toast.makeText(PlaceAddActivity.this, R.string.add_place_succsess, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(PlaceAddActivity.this, R.string.add_place_fail, Toast.LENGTH_LONG).show();
        }
    }

    private void ClearEditText() {
        name.getText().clear();
        description.getText().clear();
    }

    private boolean checkIfAlreadyAdded() {
        ArrayList<String> placeList = createNamesList();
        return checkNameList(placeList);
    }

    @NonNull
    private ArrayList<String> createNamesList() {
        dAPlace = new DatabasePlaceAdapter(this);
        ArrayList<String> placeList = new ArrayList<>();
        dAPlace.openDB();
        Cursor c = dAPlace.getAllPlaces();
        while (c.moveToNext()) {
            String name = c.getString(1);
            placeList.add(name);
        }
        dAPlace.closeDB();
        return placeList;
    }

    private boolean checkNameList(ArrayList<String> placeList) {
        for (int i = 0; i < placeList.size(); i++) {
            if (name.getText().toString().matches(placeList.get(i))) {
                return false;
            }
        }
        return true;
    }

}
