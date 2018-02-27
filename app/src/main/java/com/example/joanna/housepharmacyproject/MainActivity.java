package com.example.joanna.housepharmacyproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @OnClick(R.id.bAdd)
    void onClickAdd(View view) {
        Intent in = new Intent(MainActivity.this, AddMedActivity.class);
        startActivity(in);
    }
    @OnClick(R.id.bShow)
    void onClickShow(View view) {
        Intent in = new Intent(MainActivity.this, DisplayMedActivity.class);
        startActivity(in);
    }

    @OnClick(R.id.bAddPl)
    void onClickAddPlace(View view) {
        Intent in = new Intent(MainActivity.this, AddPlaceActivity.class);
        startActivity(in);
    }
    @OnClick(R.id.bShowPlace)
    void onClickShowPlace(View view) {
        Intent in = new Intent(MainActivity.this, DisplayPlaceActivity.class);
        startActivity(in);
    }
    @OnClick(R.id.bSearch)
    void onClickSearch(View view) {
        Intent in = new Intent(MainActivity.this, SearchMedActivity.class);
        startActivity(in);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
            }
}
