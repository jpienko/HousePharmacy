package com.example.joanna.housepharmacyproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @OnClick(R.id.bAdd)
    void onClickAdd(View view) {
        Intent in = new Intent(MainActivity.this, AddActivity.class);
        startActivity(in);
    }
    @OnClick(R.id.bShow)
    void onClickShow(View view) {
        Intent in = new Intent(MainActivity.this, DisplayActivity.class);
        startActivity(in);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
            }
}
