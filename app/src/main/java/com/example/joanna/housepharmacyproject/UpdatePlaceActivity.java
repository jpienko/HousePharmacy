package com.example.joanna.housepharmacyproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdatePlaceActivity extends Toolbar {
    DatabasePlaceAdapter dA;
    @BindView(R.id.tvPlaceNameUpdate)
    TextView tvPlaceNameUpdate;

    @BindView(R.id.tvDescriptionUpdate)
    TextView tvDescriptionUpdate;

    @BindView(R.id.etPlaceNameUpdate)
    EditText etPlaceNameUpdate;

    @BindView(R.id.etDescriptionUpdate)
    EditText etDescriptionUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_place);
        ButterKnife.bind(this);
        setTextView();
        initToolBar("Aktualizuj miejsce", R.string.instruction_update_place, setBack());
    }

    @OnClick(R.id.bUpdatePlace)
    void Click() {
        dA = new DatabasePlaceAdapter(this);
        dA.openDB();
        String name = getNewRowData(tvPlaceNameUpdate, etPlaceNameUpdate);
        String description = getNewRowData(tvDescriptionUpdate, etDescriptionUpdate);

        int id = getID();
        long didItWork;
        if (id != 0) {
            didItWork = dA.updateRow(id, name, description);
        } else {
            didItWork = 0;
        }
        if (didItWork > 0) {
            Toast.makeText(UpdatePlaceActivity.this, "Succsess", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(UpdatePlaceActivity.this, "Fail", Toast.LENGTH_LONG).show();
        }
        ClearEditText();
        dA.closeDB();
        setTextView();

    }


    private int getID() {
        dA = new DatabasePlaceAdapter(this);
        dA.openDB();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int id =  dA.getPlaceId(bundle.getString("Id"));
            dA.closeDB();
            return id;
        } else {
            return 0;
        }

    }

    public String getNewRowData(TextView contentCurrent, EditText contentUpdate) {
        String newContent;
        if (contentUpdate.getText().toString().equals("")) {
            return newContent = contentCurrent.getText().toString();
        } else {
            return newContent = contentUpdate.getText().toString();
        }
    }

    private void ClearEditText() {
        etPlaceNameUpdate.getText().clear();
        etDescriptionUpdate.getText().clear();

    }
    public void setTextView() {
        dA = new DatabasePlaceAdapter(this);
        int id = getID();
        dA.openDB();
        tvPlaceNameUpdate.setText(dA.getColumnContent(DBConstants.PLACE_NAME, id));
        tvDescriptionUpdate.setText(dA.getColumnContent(DBConstants.PLACE_DESCRIPTION, id));
        dA.closeDB();

    }

}
