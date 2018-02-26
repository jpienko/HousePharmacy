package com.example.joanna.housepharmacyproject;

/**
 * Created by Joanna on 2018-02-16.
 */

public class Meds {
    private String name;
    private String dose;
    private String place;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int amount;
    private int id;

    public Meds(int id, String name, String dose, Integer amount, String place) {
        this.name = name;
        this.dose = dose;
        this.place = place;
        this.amount = amount;
        this.id = id;
    }
}
