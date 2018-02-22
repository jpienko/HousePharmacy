package com.example.joanna.housepharmacyproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Joanna on 2018-02-19.
 */

public class MedAdapter extends RecyclerView.Adapter<MedHolder> {


    private ArrayList<Meds> meds = new ArrayList<>();
    private RecyclerViewClickListener recyclerViewClickListener;

    MedAdapter(ArrayList<Meds> meds, RecyclerViewClickListener listener) {
        this.recyclerViewClickListener = listener;
        this.meds = meds;
    }

    @Override
    public MedHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, null);
        MedHolder medHolder = new MedHolder(v, recyclerViewClickListener);
        return medHolder;
    }

    @Override
    public void onBindViewHolder(MedHolder holder, int position) {
        holder.tvName.setText(meds.get(position).getName());
        holder.tvDose.setText(meds.get(position).getDose().toString());
        holder.tvAmount.setText(meds.get(position).getAmount().toString());
        holder.tvId.setText(String.valueOf(meds.get(position).getId()));
        holder.tvPlace.setText(meds.get(position).getPlace());


    }

    @Override
    public int getItemCount() {
        return meds.size();
    }

}
