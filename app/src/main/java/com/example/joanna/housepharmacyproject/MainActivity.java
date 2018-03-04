package com.example.joanna.housepharmacyproject;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;

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
        initToolBar();
    }

    public void initToolBar() {
        toolbar.setTitle("Domowa apteczka");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_med, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_help, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        Button back = (Button) mView.findViewById(R.id.bBackHelp);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

        dialog.show();

        return super.onOptionsItemSelected(item);
    }


}
