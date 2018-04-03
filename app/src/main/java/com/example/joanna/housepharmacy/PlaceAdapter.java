package com.example.joanna.housepharmacy;

import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Joanna on 2018-03-28.
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private ArrayList<Place> places = new ArrayList<>();
    private RecyclerViewClickListener listener;

    public PlaceAdapter(ArrayList<Place> places, RecyclerViewClickListener listener) {
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
        holder.tvIdPlace.setText(String.valueOf(places.get(position).getId()));
        holder.tvPlaceName.setText(places.get(position).getName());
        holder.tvDescription.setText(places.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RecyclerViewClickListener listener;
        int bNumber = 0;

        @BindView(R.id.tvPlaceId)
        TextView tvIdPlace;

        @BindView(R.id.tvPlaceName)
        TextView tvPlaceName;

        @BindView(R.id.tvDescrition)
        TextView tvDescription;


        @OnClick(R.id.bGoToUpdatePlace)
        void go(){
            bNumber = 1;
            onClick(itemView);
        }
        @OnClick(R.id.bDeletePlaceFromList)
        void delete(){
            bNumber = 2;
            onClick(itemView);
        }
        @OnClick(R.id.bGoToContent)
        void content(){
            bNumber = 3;
            onClick(itemView);
        }

        public ViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition(), tvIdPlace.getText().toString(), bNumber);
        }

    }}
