package com.example.joanna.housepharmacyproject;

import android.os.AsyncTask;

/**
 * Created by Joanna on 2018-02-19.
 */

public class BackgroundTask extends AsyncTask<Void,Void,Void> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
