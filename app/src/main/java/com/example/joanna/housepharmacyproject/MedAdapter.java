package com.example.joanna.housepharmacyproject;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Measure;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Joanna on 2018-02-19.
 */

public class MedAdapter extends RecyclerView.Adapter<MedHolder> {

    Context context;
    ArrayList<Meds> meds;
    RecyclerViewClickListener recyclerViewClickListener;
    public MedAdapter(Context context, ArrayList<Meds> meds) {
        this.context = context;
        this.meds = meds;
    }

    @Override
    public MedHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.items,null);
        MedHolder medHolder = new MedHolder(v);
        return medHolder;
    }

    @Override
    public void onBindViewHolder(MedHolder holder, int position) {
        holder.tvName.setText(meds.get(position).getName());
        holder.tvDose.setText(meds.get(position).getDose().toString());
        holder.tvAmount.setText(meds.get(position).getAmount().toString());
        holder.tvId.setText(String.valueOf(meds.get(position).getId()));
        holder.tvPlace.setText(meds.get(position).getPlace());
        holder.setRecyclerViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(view.getContext(),UpdateActivity.class);
                view.getContext().startActivity(intent);
            }
        });

    }
        
    @Override
    public int getItemCount() {
        return meds.size();
    }

}
