package com.example.joanna.housepharmacy.Activities;

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

import com.example.joanna.housepharmacy.DatabaseAdapters.DatabaseFormAdapter;
import com.example.joanna.housepharmacy.DatabaseAdapters.DatabaseMedAdapter;
import com.example.joanna.housepharmacy.DatabaseAdapters.DatabasePlaceAdapter;
import com.example.joanna.housepharmacy.DatabaseImplementation.DatabaseConstants;
import com.example.joanna.housepharmacy.Medicaments.Med;
import com.example.joanna.housepharmacy.InterfacesAbstracts.RecyclerViewClickListener;
import com.example.joanna.housepharmacy.Places.Place;
import com.example.joanna.housepharmacy.Places.PlaceAdapter;
import com.example.joanna.housepharmacy.R;
import com.example.joanna.housepharmacy.InterfacesAbstracts.Toolbar;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceDisplayActivity extends Toolbar {

    @BindView(R.id.recViewPlace)
    RecyclerView recViewPlace;

    RecyclerViewClickListener listener;

    private DatabaseMedAdapter dbMed;
    private DatabasePlaceAdapter dbPlace;
    private DatabaseFormAdapter dbForm;
    PlaceAdapter placeAdapter;
    ArrayList<Place> places = new ArrayList<>();
    private ArrayList<Med> meds = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_display);
        init(PlaceDisplayActivity.this, R.string.instruction_display_place, getString(R.string.place_list_title));
        ButterKnife.bind(this);
        setRecyclerView();
        retrievePlace();
        inicialize();
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recViewPlace.setLayoutManager(layoutManager);
        recViewPlace.setLayoutManager(new LinearLayoutManager(this));
        recViewPlace.setItemAnimator(new DefaultItemAnimator());
    }

    private void retrievePlace() {
        places.clear();
        addToPlaceList(places);
        if (!(places.size() < 1)) {
            recViewPlace.setAdapter(placeAdapter);
        }
    }

    public void inicialize() {
        listener = (view, position, id, bNumber) -> {
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
                Toast.makeText(PlaceDisplayActivity.this, R.string.do_not_edit, Toast.LENGTH_LONG).show();
            }
        } else if (bNumber == 2) {
            if (Integer.valueOf(id) != 1) {
                deletePlaceFromList(id);
            } else {
                Toast.makeText(PlaceDisplayActivity.this, R.string.do_not_delete, Toast.LENGTH_LONG).show();
            }
        } else if (bNumber == 3) {
            goToContent(id);
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
        setDatabaseAdapters();
        getAllMatchingMeds(id);
        goToMatchingMedsList();
    }

    private void setDatabaseAdapters() {
        dbMed = new DatabaseMedAdapter(this);
        dbPlace = new DatabasePlaceAdapter(this);
        dbForm = new DatabaseFormAdapter(this);
    }

    private void getAllMatchingMeds(String id) {
        meds.clear();
        DatabaseMedAdapter dbMed = new DatabaseMedAdapter(this);
        dbMed.openDB();
        Cursor c = dbMed.searchMedByOne(id, DatabaseConstants.PLACE);
        addMedToList(c,dbMed,dbForm,dbPlace,meds);
    }

    private void goToMatchingMedsList() {
        Intent in = new Intent(PlaceDisplayActivity.this, MedDisplayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("MedsList", (Serializable) meds);
        in.putExtras(bundle);
        startActivity(in);
    }

    public void deletePlaceFromList(String id) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        View mView = getLayoutInflater().inflate(R.layout.dialog_delete, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        doNotDelete(mView, dialog);
        deletePlace(id, mView, dialog);
        dialog.show();

    }

    private void doNotDelete(View mView, AlertDialog dialog) {
        Button bNo = (Button) mView.findViewById(R.id.bDoNotDelete);
        bNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
    }

    private void deletePlace(String id, View mView, AlertDialog dialog) {
        Button bYes = (Button) mView.findViewById(R.id.bDeleteContent);
        bYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDeletePlaceContent(id);
                dialog.dismiss();
            }
        });
    }

    public void dialogDeletePlaceContent(String id) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        View mView = getLayoutInflater().inflate(R.layout.place_dialog, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        changePlaceToNone(id, mView, dialog);
        deletePlaceWithItsContent(id, mView, dialog);
        dialog.show();
    }

    private void deletePlaceWithItsContent(String id, View mView, AlertDialog dialog) {
        Button bYesDelete = (Button) mView.findViewById(R.id.bDeleteAllContent);
        bYesDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteWithContent(id);
                dialog.dismiss();
                retrievePlace();
            }
        });
    }

    private void changePlaceToNone(String id, View mView, AlertDialog dialog) {
        Button bNoReplece = (Button) mView.findViewById(R.id.bReplaceContentToNone);
        bNoReplece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteWithoutContent(id);
                dialog.dismiss();
                retrievePlace();

            }
        });
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


