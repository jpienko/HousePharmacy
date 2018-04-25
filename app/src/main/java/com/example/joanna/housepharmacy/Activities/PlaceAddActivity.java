package com.example.joanna.housepharmacy.Activities;

import android.database.Cursor;
import android.os.Bundle;
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
        init(this, R.string.instruction_add_place, "Dodaj miejsce przechowywania");
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bAddPlace)
    void Click() {
        if (name.getText().toString().matches("")) {
            Toast.makeText(PlaceAddActivity.this, "Musisz podać nazwę miejsca!", Toast.LENGTH_LONG).show();
        } else if (checkIfAlreadyAdded()){
            addPlace();
        } else{
            Toast.makeText(PlaceAddActivity.this, "Mijesce o takiej nazwie już istnieje", Toast.LENGTH_LONG).show();
        }
    }

    public void addPlace() {
        dAPlace = new DatabasePlaceAdapter(this);
        dAPlace.openDB();
        if (description.getText().toString().matches("")) {
            description.setText("brak");
        }

        long didItWork = dAPlace.addPlace(name.getText().toString(),
                description.getText().toString());
        if (didItWork > 0) {
            Toast.makeText(PlaceAddActivity.this, "Pomyślnie dodano miejsce", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(PlaceAddActivity.this, "Nie udało się dodać miejsca", Toast.LENGTH_LONG).show();
        }
        ClearEditText();
        dAPlace.closeDB();
    }


    private void ClearEditText() {
        name.getText().clear();
        description.getText().clear();
    }

    private boolean checkIfAlreadyAdded() {
        dAPlace = new DatabasePlaceAdapter(this);
        ArrayList<String> placeList = new ArrayList<>();
        dAPlace.openDB();
        Cursor c = dAPlace.getAllPlaces();
        while (c.moveToNext()) {
            String name = c.getString(1);
            placeList.add(name);
        }
        dAPlace.closeDB();

        for (int i = 0; i < placeList.size(); i++) {
            if (name.getText().toString().matches(placeList.get(i))) {
                return false;
            }
        }
        return true;
    }

}
