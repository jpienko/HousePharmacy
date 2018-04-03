package com.example.joanna.housepharmacy;

/**
 * Created by Joanna on 2018-03-22.
 */

public class Place {
    int Id;
    String name;
    String description;

    public Place(int id, String name, String description) {
        Id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
