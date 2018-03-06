package com.example.joanna.housepharmacyproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;

public class UpdatePlaceActivity extends Toolbar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_place);
        ButterKnife.bind(this);
        initToolBar("Aktualizuj miejsce", 3);
    }
}
