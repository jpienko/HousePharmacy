package com.example.joanna.housepharmacyproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DisplayMedActivity extends Toolbar {


    @BindView(R.id.recView)
    RecyclerView recView;

    MedAdapter medAdapter;
    ArrayList<Meds> meds = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_med);
        ButterKnife.bind(this);
        retrieveMed();
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setItemAnimator(new DefaultItemAnimator());
        goToUpdateMed();
        initToolBar("Lista leków", R.string.instruction_display);
    }

    private void retrieveMed() {
        meds.clear();

        DatabaseMedAdapter db = new DatabaseMedAdapter(this);
        db.openDB();

        Cursor c = db.getAllMeds();
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            int amount = c.getInt(2);
            String dose = c.getString(3);
            String place = c.getString(4);
            Meds m = new Meds(id, name, dose, amount, place);
            meds.add(m);
        }
        if (!(meds.size() < 1)) {
            recView.setAdapter(medAdapter);
        }
        db.closeDB();

    }

    public void goToUpdateMed() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recView.setLayoutManager(layoutManager);

        RecyclerViewClickListener listener = (view, position, id) -> {
            Intent intent = new Intent(DisplayMedActivity.this, UpdateMedActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("Id", Integer.parseInt(id));
            intent.putExtras(bundle);
            startActivity(intent);
        };
        medAdapter = new MedAdapter(meds, listener);
        recView.setAdapter(medAdapter);
    }


}
