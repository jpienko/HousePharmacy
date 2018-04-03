package com.example.joanna.housepharmacy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceDisplayActivity extends Toolbar {

    @BindView(R.id.recViewPlace)
    RecyclerView recViewPlace;

    private DatabaseMedAdapter dbMed;
    private DatabasePlaceAdapter dbPlace;
    private DatabaseFormAdapter dbForm;
    PlaceAdapter placeAdapter;
    ArrayList<Place> places = new ArrayList<>();
    private ArrayList<Med> meds = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_display);
        init(PlaceDisplayActivity.this, R.string.instruction_display_place, "Lista miejsc");
        ButterKnife.bind(this);
        recViewPlace.setLayoutManager(new LinearLayoutManager(this));
        recViewPlace.setItemAnimator(new DefaultItemAnimator());
        retrievePlace();
        inicialize();


    }

    public void inicialize() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recViewPlace.setLayoutManager(layoutManager);
        RecyclerViewClickListener listener = (view, position, id, bNumber) -> {
            whichOneWasClicked(id, bNumber);
        };
        placeAdapter = new PlaceAdapter(places, listener);
        recViewPlace.setAdapter(placeAdapter);
    }

    private void whichOneWasClicked(String id, int bNumber) {
        if (bNumber == 1) {
            if (Integer.valueOf(id) != 1) {
                goToUpdatePlace(id);
            } else {
                Toast.makeText(PlaceDisplayActivity.this, "Tej pozycji nie możesz edytować", Toast.LENGTH_LONG).show();
            }
        } else if (bNumber == 2) {
            if (Integer.valueOf(id) != 1) {
                deletePlaceFromList(id);

            } else {
                Toast.makeText(PlaceDisplayActivity.this, "Tej pozycji nie możesz usunąć", Toast.LENGTH_LONG).show();
            }
        } else if (bNumber == 3) {
            goToContent(id);
        }
    }

    private void retrievePlace() {
        places.clear();

        DatabasePlaceAdapter db = new DatabasePlaceAdapter(this);
        db.openDB();
        Cursor c = db.getAllPlaces();
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            String desc = c.getString(2);
            Place p = new Place(id, name, desc);
            places.add(p);
        }
        db.closeDB();

        if (!(places.size() < 1)) {
            recViewPlace.setAdapter(placeAdapter);
        }

    }

    public void goToUpdatePlace(String id) {

        Intent intent = new Intent(PlaceDisplayActivity.this, PlaceUpdateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("IdPlace", id);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void goToContent(String id) {
        dbMed = new DatabaseMedAdapter(this);
        dbPlace = new DatabasePlaceAdapter(this);
        dbForm = new DatabaseFormAdapter(this);
        meds.clear();
        DatabaseMedAdapter dbMed = new DatabaseMedAdapter(this);
        dbMed.openDB();
        Cursor c = dbMed.searchMedByOne(id, DatabaseConstants.PLACE);
        getMed(c);

        Intent in = new Intent(PlaceDisplayActivity.this, MedDisplayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("MedsList", (Serializable) meds);
        in.putExtras(bundle);
        startActivity(in);

    }

    private void getMed(Cursor c) {
        if (c != null) {
            while (c.moveToNext()) {
                int id = c.getInt(0);
                String name = c.getString(1);
                String dose = c.getString(2);
                int formInt = c.getInt(3);
                double amount = c.getDouble(4);
                String purpose = c.getString(5);
                int placeInt = c.getInt(6);
                dbMed.closeDB();

                dbForm.openDB();
                String form = dbForm.getFormName(formInt);
                dbForm.closeDB();

                dbPlace.openDB();
                String place = dbPlace.getPlaceName(placeInt);
                dbPlace.closeDB();

                Med m = new Med(id, name, dose, form, amount, purpose, place);
                meds.add(m);
            }
        }
    }


    public void deletePlaceFromList(String id) {

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
                dialogDeletePlaceContent(id);
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void dialogDeletePlaceContent(String id) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        View mView = getLayoutInflater().inflate(R.layout.place_dialog, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        Button bNoReplece = (Button) mView.findViewById(R.id.bReplaceContentToNone);
        bNoReplece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteWithoutContent(id);
                dialog.dismiss();
                retrievePlace();

            }
        });

        Button bYesDelete = (Button) mView.findViewById(R.id.bDeleteAllContent);
        bYesDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteWithContent(id);
                dialog.dismiss();
                retrievePlace();
            }
        });
        dialog.show();
    }

    public void deleteWithoutContent(String id) {
        dbMed = new DatabaseMedAdapter(this);
        dbPlace = new DatabasePlaceAdapter(this);
        dbMed.openDB();
        dbMed.renamePlace(id);
        dbMed.closeDB();

        dbPlace.openDB();
        dbPlace.deleteOnlyPlace(id);
        dbPlace.closeDB();
    }

    public void deleteWithContent(String id) {
        dbMed = new DatabaseMedAdapter(this);
        dbPlace = new DatabasePlaceAdapter(this);
        dbMed.openDB();
        dbMed.deleteMedFromPlace(id);
        dbMed.closeDB();

        dbPlace.openDB();
        dbPlace.deleteOnlyPlace(id);
        dbPlace.closeDB();
    }

}


