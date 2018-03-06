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
 * Created by Joanna on 2018-02-26.
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private ArrayList<Places> places = new ArrayList<>();
    private RecyclerViewClickListener listener;

    public PlaceAdapter(ArrayList<Places> places, RecyclerViewClickListener listener) {
        this.places = places;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.places, null);
        return new ViewHolder(itemLayoutView, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvIdPlace.setText(String.valueOf(places.get(position).getId_place()));
        holder.tvPlaceName.setText(places.get(position).getPlaceName());
        holder.tvDescription.setText(places.get(position).getPlaceDescription());

    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RecyclerViewClickListener listener;

        @BindView(R.id.bGoToUpdatePlace)
        Button goToUpdatePlace;

        @BindView(R.id.bGoToContent)
        Button goToContent;

        @BindView(R.id.tvPlaceId)
        TextView tvIdPlace;

        @BindView(R.id.tvPlaceName)
        TextView tvPlaceName;

        @BindView(R.id.tvDescrition)
        TextView tvDescription;

        public ViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.listener = listener;
            goToUpdatePlace.setOnClickListener(this);
            goToContent.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition(), tvPlaceName.getText().toString());
        }
    }}