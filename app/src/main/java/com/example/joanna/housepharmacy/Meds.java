package com.example.joanna.housepharmacy;

import java.io.Serializable;

/**
 * Created by Joanna on 2018-03-22.
 */

public class Meds implements Serializable {
    int Id;
    String name;
    String dose;
    String form;
    Double amount;
    String purpose;
    String place;

    public Meds(int id, String name, String dose, String form, Double amount, String purpose, String place) {
        Id = id;
        this.name = name;
        this.dose = dose;
        this.form = form;
        this.amount = amount;
        this.purpose = purpose;
        this.place = place;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

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

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }



}
