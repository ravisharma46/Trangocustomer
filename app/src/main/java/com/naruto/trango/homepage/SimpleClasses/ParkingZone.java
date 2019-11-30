package com.naruto.trango.homepage.SimpleClasses;

public class ParkingZone {
    int image_id;
    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ParkingZone(int image_id, String title) {
        this.image_id = image_id;
        this.title = title;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }


}
