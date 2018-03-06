package com.example.joanna.housepharmacyproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DisplayPlaceActivity extends Toolbar {
    @BindView(R.id.recViewPlace)
    RecyclerView recViewPlace;

    PlaceAdapter placeAdapter;
    ArrayList<Places> places = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_place);
        ButterKnife.bind(this);
        retrievePlace();
        recViewPlace.setLayoutManager(new LinearLayoutManager(this));
        recViewPlace.setItemAnimator(new DefaultItemAnimator());
        goToUpdatePlace();
        initToolBar("Lista miejsc", 1);
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
            Places p = new Places(id, name, desc);
            places.add(p);
        }
        if (!(places.size() < 1)) {
            recViewPlace.setAdapter(placeAdapter);
        }
        db.closeDB();
    }
    public void goToUpdatePlace() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recViewPlace.setLayoutManager(layoutManager);
        RecyclerViewClickListener listener = (view, position, id) -> {
            Intent intent = new Intent(DisplayPlaceActivity.this, UpdatePlaceActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("Id", String.valueOf(id));
            intent.putExtras(bundle);
            startActivity(intent);
        };
        placeAdapter = new PlaceAdapter(places, listener);
        recViewPlace.setAdapter(placeAdapter);
    }
}
