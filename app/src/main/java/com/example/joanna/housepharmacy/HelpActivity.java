package com.example.joanna.housepharmacy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

public class HelpActivity extends Toolbar{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        init(this,R.string.instruction_help,"Pomoc");
    }

}
