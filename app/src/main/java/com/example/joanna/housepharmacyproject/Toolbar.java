package com.example.joanna.housepharmacyproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Joanna on 2018-03-05.
 */

abstract class Toolbar extends AppCompatActivity {
    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;

    Activity activity = this;
    String textContent;

    protected final void onCreate(Bundle savedInstanceState, int layoutId) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
        ButterKnife.bind(this);

    }

    public void initToolBar(String name, int text) {
        this.textContent = getResources().getString(text);
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(activity, MainActivity.class);
                startActivity(in);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        View mView = getLayoutInflater().inflate(R.layout.dialog_help, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        TextView tvHelp = (TextView) mView.findViewById(R.id.tvHelp) ;
        tvHelp.setText(textContent);
        Button bHelp = (Button) mView.findViewById(R.id.bBackHelp);
        bHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

        dialog.show();

        return super.onOptionsItemSelected(item);
    }


}
