package com.example.joanna.housepharmacyproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Joanna on 2018-02-19.
 */

public class MedAdapter extends RecyclerView.Adapter<MedAdapter.ViewHolder> {

    private ArrayList<Meds>  meds = new ArrayList<>();
    private RecyclerViewClickListener listener;

    public MedAdapter(ArrayList<Meds> meds, RecyclerViewClickListener listener) {
        this.meds = meds;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meds, null);
        return new ViewHolder(itemLayoutView, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(meds.get(position).getName());
        holder.tvDose.setText(meds.get(position).getDose().toString());
        holder.tvAmount.setText(String.valueOf(meds.get(position).getAmount()));
        holder.tvId.setText(String.valueOf(meds.get(position).getId()));
        holder.tvPlace.setText(meds.get(position).getPlace());
    }

    @Override
    public int getItemCount() {
        return meds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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

        public ViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.listener = listener;
            goToUpdate.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition(),Integer.parseInt(tvId.getText().toString()));
        }
    }
}