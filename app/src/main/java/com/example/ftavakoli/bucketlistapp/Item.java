package com.example.ftavakoli.bucketlistapp;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by FTavakoli on 4/28/18.
 */

public class Item {
    private String id, title, description;
    int color;
    private long date;
    private Coordinate coordinates;
    private boolean status;


    public Item(String id, String title, String description, int color, long date, Coordinate coordinates) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.color = color;
        this.date = date;
        this.coordinates = coordinates;

    }

    public Item() {
    }


    public boolean isStatus() {

        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Coordinate getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinate coordinates) {
        this.coordinates = coordinates;
    }
}
