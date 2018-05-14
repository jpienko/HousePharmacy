package com.example.joanna.housepharmacy.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.joanna.housepharmacy.DatabaseAdapters.DatabaseFormAdapter;
import com.example.joanna.housepharmacy.DatabaseAdapters.DatabaseMedAdapter;
import com.example.joanna.housepharmacy.DatabaseAdapters.DatabasePlaceAdapter;
import com.example.joanna.housepharmacy.InterfacesAbstracts.RecyclerViewClickListener;
import com.example.joanna.housepharmacy.InterfacesAbstracts.Toolbar;
import com.example.joanna.housepharmacy.Medicaments.Med;
import com.example.joanna.housepharmacy.Medicaments.MedAdapter;
import com.example.joanna.housepharmacy.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedDisplayActivity extends Toolbar {

    MedAdapter medAdapter;
    ArrayList<Med> meds;
    Context context = this;
    DatabaseMedAdapter dAMed;
    DatabasePlaceAdapter dAPlace;
    DatabaseFormAdapter dAForm;

    @BindView(R.id.recView)
    RecyclerView recView;

    @BindView(R.id.tvHowManyMeds)
    TextView tvHowManyMeds;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_display);
        init(this, R.string.instruction_display, getString(R.string.med_list_title));
        ButterKnife.bind(this);
        implementRecycle();
        retriveBundle();
        inicialize();
    }

    private void implementRecycle() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recView.setLayoutManager(layoutManager);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setItemAnimator(new DefaultItemAnimator());
    }

    private void retriveBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            getBundleContent(bundle);
        } else {
            meds = new ArrayList<>();
            tvHowManyMeds.setText(R.string.all_med_added);
            retrieveMed();
        }
    }

    private void getBundleContent(Bundle bundle) {
        meds = (ArrayList<Med>) bundle.getSerializable("MedsList");
        if (!(meds.size() < 1)) {
            recView.setAdapter(medAdapter);
            tvHowManyMeds.setText(getString(R.string.list_of_found_meds) + String.valueOf(meds.size()));
        } else {
            tvHowManyMeds.setText(R.string.no_meds_found);
        }
    }

    private void retrieveMed() {
        meds.clear();
        getMedObject();

        if (!(meds.size() < 1)) {
            recView.setAdapter(medAdapter);
        }
    }

    private void getMedObject() {
        setDatabaseAdapters();
        dAMed.openDB();
        Cursor cursor = dAMed.getAllMeds();
        addMedToList(cursor,dAMed,dAForm,dAPlace,meds);
    }

    private void setDatabaseAdapters() {
        dAMed = new DatabaseMedAdapter(this);
        dAPlace = new DatabasePlaceAdapter(this);
        dAForm = new DatabaseFormAdapter(this);
    }

    public void inicialize() {
        RecyclerViewClickListener listener = (view, position, id, bNumber) -> {
            whichWasClicked(id, bNumber);
        };
        medAdapter = new MedAdapter(meds, listener);
        recView.setAdapter(medAdapter);
    }

    private void whichWasClicked(String id, int bNumber) {
        if (bNumber == 1) {
            goToUpdateMed(id);
        } else if (bNumber == 2) {
            deleteMedFromList(id);
        }
    }

    public void goToUpdateMed(String id) {

        Intent intent = new Intent(MedDisplayActivity.this, MedUpdateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Id", Integer.parseInt(id));
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void deleteMedFromList(String id) {

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
                deleteMed(id);
                dialog.dismiss();
                retrieveMed();
            }
        });
        dialog.show();

    }

    private void deleteMed(String id) {
        DatabaseMedAdapter dbMed = new DatabaseMedAdapter(getBaseContext());
        dbMed.openDB();
        dbMed.deleteMed(String.valueOf(id));
        dbMed.closeDB();

    }

}