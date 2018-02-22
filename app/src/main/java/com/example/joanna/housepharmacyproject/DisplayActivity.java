package com.example.joanna.housepharmacyproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toolbar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DisplayActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recView)
    RecyclerView recView;

    MedAdapter medAdapter;
    ArrayList<Meds> meds = new ArrayList<>();
    RecyclerViewClickListener recyclerViewClickListener;
    @OnClick(R.id.bBackDisp)
    void click1() {
        Intent in = new Intent(this, MainActivity.class);
        startActivity(in);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        ButterKnife.bind(this);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setItemAnimator(new DefaultItemAnimator());
        goToUpdate();
        medAdapter = new MedAdapter(meds, recyclerViewClickListener);
        retrieve();

    }

    private void retrieve() {
        meds.clear();

        DatabaseAdapter db = new DatabaseAdapter(this);
        db.openDB();

        //RETRIEVE
        Cursor c = db.GetAllMeds();

        //LOOP AND ADD TO ARRAYLIST
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            int amount = c.getInt(2);
            Double dose = c.getDouble(3);
            String place = c.getString(4);

            Meds p = new Meds(id, name, dose, amount, place);

            //ADD TO ARRAYLIS
            meds.add(p);
        }

        //CHECK IF ARRAYLIST ISNT EMPTY
        if (!(meds.size() < 1)) {
            recView.setAdapter(medAdapter);
        }

        db.CloseDB();

    }

    public void goToUpdate() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recView.setLayoutManager(layoutManager);
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position, String id) {
                Intent intent = new Intent(DisplayActivity.this, UpdateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Id", id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };
    }


}
