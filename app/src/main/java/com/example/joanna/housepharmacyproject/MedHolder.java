package com.example.joanna.housepharmacyproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Joanna on 2018-02-19.
 */

public class MedHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    RecyclerViewClickListener listener;
    @BindView(R.id.bGoToUpdate)
    Button goToUpdate;

    @BindView(R.id.tvId)
    TextView tvId;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvDose)
    TextView tvDose;

    @BindView(R.id.tvAmount)
    TextView tvAmount;

    @BindView(R.id.tvPlace)
    TextView tvPlace;

    MedHolder(View itemView,RecyclerViewClickListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.listener = listener;
        goToUpdate.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        listener.onClick(view,getAdapterPosition(),String.valueOf(tvId.getId()));

    }

}
