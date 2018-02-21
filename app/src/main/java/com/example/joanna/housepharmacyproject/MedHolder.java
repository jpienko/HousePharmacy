package com.example.joanna.housepharmacyproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Joanna on 2018-02-19.
 */

public class MedHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView tvName,tvId,tvDose,tvAmount,tvPlace;
    RecyclerViewClickListener recyclerViewClickListener;
    public MedHolder(View itemView) {
        super(itemView);

        tvId = (TextView) itemView.findViewById(R.id.tvId);
        tvName = (TextView) itemView.findViewById(R.id.tvName);
        tvDose = (TextView) itemView.findViewById(R.id.tvDose);
        tvAmount = (TextView) itemView.findViewById(R.id.tvAmount);
        tvPlace = (TextView) itemView.findViewById(R.id.tvPlace);
        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        this.recyclerViewClickListener.onClick(view,getLayoutPosition());

    }

    public void setRecyclerViewClickListener(RecyclerViewClickListener rvcl){
        this.recyclerViewClickListener = rvcl;
    }
}
