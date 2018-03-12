package com.example.joanna.housepharmacyproject;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {



    @OnClick(R.id.bAdd)
    void onClickAdd(View view) {
        Intent in = new Intent(MainActivity.this, AddMedActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ClassName", "MainActivity");
        in.putExtras(bundle);
        startActivity(in);
    }

    @OnClick(R.id.bShow)
    void onClickShow(View view) {
        Intent in = new Intent(MainActivity.this, DisplayMedActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ClassName", "MainActivity");
        in.putExtras(bundle);
        startActivity(in);
    }

    @OnClick(R.id.bAddPl)
    void onClickAddPlace(View view) {
        Intent in = new Intent(MainActivity.this, AddPlaceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ClassName", "MainActivity");
        in.putExtras(bundle);
        startActivity(in);
    }

    @OnClick(R.id.bShowPlace)
    void onClickShowPlace(View view) {
        Intent in = new Intent(MainActivity.this, DisplayPlaceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ClassName", "MainActivity");
        in.putExtras(bundle);
        startActivity(in);
    }

    @OnClick(R.id.bSearch)
    void onClickSearch(View view) {
        Intent in = new Intent(MainActivity.this, SearchMedActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ClassName", "MainActivity");
        in.putExtras(bundle);
        startActivity(in);
    }

    @OnClick(R.id.bHelp)
    void onClickHelp(View view) {
        Intent in = new Intent(MainActivity.this, HelpActivity.class);
        startActivity(in);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ButterKnife.bind(this);

    }



}
