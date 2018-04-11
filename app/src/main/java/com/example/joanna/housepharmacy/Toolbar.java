package com.example.joanna.housepharmacy;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Joanna on 2018-03-20.
 */

public abstract class Toolbar extends AppCompatActivity{
    Activity activity;
    String textContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public void init(Activity activity, int text, String title){
        this.activity = activity;
        this.textContent = getResources().getString(text);
        setTitle(title);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rest, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        View mView = getLayoutInflater().inflate(R.layout.dialog_help, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        TextView tvHelp = (TextView) mView.findViewById(R.id.tvHelp);
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

    public void getAdapterMed(Context context){
        DatabaseMedAdapter dbMed = new DatabaseMedAdapter(context);
        DatabasePlaceAdapter dbPlace = new DatabasePlaceAdapter(context);
        DatabaseFormAdapter dbForm = new DatabaseFormAdapter(context);
    }
}
