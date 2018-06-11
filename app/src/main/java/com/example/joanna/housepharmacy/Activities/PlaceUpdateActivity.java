package com.example.joanna.housepharmacy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joanna.housepharmacy.DatabaseAdapters.DatabaseMedAdapter;
import com.example.joanna.housepharmacy.DatabaseAdapters.DatabasePlaceAdapter;
import com.example.joanna.housepharmacy.DatabaseImplementation.DatabaseConstants;
import com.example.joanna.housepharmacy.R;
import com.example.joanna.housepharmacy.InterfacesAbstracts.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlaceUpdateActivity extends Toolbar {

    DatabasePlaceAdapter dbPlace;
    DatabaseMedAdapter dbMed;
    @BindView(R.id.tvPlaceNameUpdate)
    TextView tvPlaceNameUpdate;

    @BindView(R.id.tvDescriptionUpdate)
    TextView tvDescriptionUpdate;

    @BindView(R.id.etPlaceNameUpdate)
    EditText etPlaceNameUpdate;

    @BindView(R.id.etDescriptionUpdate)
    EditText etDescriptionUpdate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_update);
        init(PlaceUpdateActivity.this, R.string.instruction_update_place, getString(R.string.place_update_title));
        ButterKnife.bind(this);
        setTextView();
    }

    @OnClick(R.id.bUpdatePlace)
    void update() {
        dbPlace = new DatabasePlaceAdapter(this);
        dbPlace.openDB();
        String name = getNewRowData(tvPlaceNameUpdate, etPlaceNameUpdate);
        String description = getNewRowData(tvDescriptionUpdate, etDescriptionUpdate);

        int id = getID();
        long didItWork;
        if (id != 0) {
            didItWork = dbPlace.updateRow(id, name, description);
        } else {
            didItWork = 0;
        }
        if (didItWork > 0) {
            Toast.makeText(PlaceUpdateActivity.this, R.string.place_update_success, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(PlaceUpdateActivity.this, R.string.place_update_fail, Toast.LENGTH_LONG).show();
        }
        ClearEditText();
        dbPlace.closeDB();
        setTextView();

    }


    private int getID() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int id = Integer.parseInt(bundle.getString("IdPlace"));
            return id;
        } else {
            return 0;
        }

    }

    private void ClearEditText() {
        etPlaceNameUpdate.getText().clear();
        etDescriptionUpdate.getText().clear();

    }

    public void setTextView() {
        dbPlace = new DatabasePlaceAdapter(this);
        int id = getID();
        dbPlace.openDB();
        tvPlaceNameUpdate.setText(dbPlace.getColumnContent(DatabaseConstants.PLACE_NAME, id));
        tvDescriptionUpdate.setText(dbPlace.getColumnContent(DatabaseConstants.PLACE_DESCRIPTION, id));
        dbPlace.closeDB();

    }

    @OnClick(R.id.bDeletePlaceUpdate)
    void deletePl() {
        deletePlace(String.valueOf(getID()));

    }

    public void deletePlace(String id) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        View mView = getLayoutInflater().inflate(R.layout.dialog_delete, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        Button bNo = (Button) mView.findViewById(R.id.bDoNotDelete);
        bNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

        Button bYes = (Button) mView.findViewById(R.id.bDeleteContent);
        bYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDeletePlaceContent(id);
                dialog.dismiss();

            }
        });
        dialog.show();

    }

    public void dialogDeletePlaceContent(String id) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        View mView = getLayoutInflater().inflate(R.layout.place_dialog, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        Button bNoReplece = (Button) mView.findViewById(R.id.bReplaceContentToNone);
        bNoReplece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteWithoutContent(id);
                dialog.dismiss();
                Intent intent = new Intent(PlaceUpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button bYesDelete = (Button) mView.findViewById(R.id.bDeleteAllContent);
        bYesDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteWithContent(id);
                dialog.dismiss();
                Intent intent = new Intent(PlaceUpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }

    public void deleteWithoutContent(String id) {
        dbMed = new DatabaseMedAdapter(this);
        dbPlace = new DatabasePlaceAdapter(this);
        dbMed.openDB();
        dbMed.renamePlace(id);
        dbMed.closeDB();

        dbPlace.openDB();
        dbPlace.deleteOnlyPlace(id);
        dbPlace.closeDB();
    }

    public void deleteWithContent(String id) {
        dbMed = new DatabaseMedAdapter(this);
        dbPlace = new DatabasePlaceAdapter(this);
        dbMed.openDB();
        dbMed.deleteMedFromPlace(id);
        dbMed.closeDB();

        dbPlace.openDB();
        dbPlace.deleteOnlyPlace(id);
        dbPlace.closeDB();
    }


}
