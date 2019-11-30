package com.naruto.trango.transit;

public class Transporter {

    private int id;
    private int type;
    private String latitude;
    private String longitude;
    private String title;
    private String disanceFromMyLocation;


    public Transporter(int id, int type, String latitude, String longitude, String title) {
        this.id = id;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setDisanceFromMyLocation(String disanceFromMyLocation) {
        this.disanceFromMyLocation = disanceFromMyLocation;
    }

    public String getDisanceFromMyLocation() {
        return disanceFromMyLocation;
    }
}
