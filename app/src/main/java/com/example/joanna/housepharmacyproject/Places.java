package com.example.joanna.housepharmacyproject;

/**
 * Created by Joanna on 2018-02-26.
 */

public class Places {

    public int id_place;
    public  String placeName;
    public String placeDescription;

    public Places(int id_place, String placeName, String placeDescription) {
        this.id_place = id_place;
        this.placeName = placeName;
        this.placeDescription = placeDescription;
    }

    public int getId_place() {
        return id_place;
    }

    public void setId_place(int id_place) {
        this.id_place = id_place;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }




}
